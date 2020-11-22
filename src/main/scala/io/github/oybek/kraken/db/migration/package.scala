package io.github.oybek.kraken.db

import cats.Show
import cats.data.NonEmptyList
import cats.effect.Bracket
import cats.implicits.toFoldableOps
import cats.instances.list._
import cats.instances.option._
import cats.syntax.all._
import doobie.implicits._
import doobie.{Fragment, Transactor}
import io.github.oybek.kraken.db.migration.model.{Commit, Migration}

package object migration {

  implicit val showFragment: Show[Fragment] = Show.show(
    (x: Fragment) => x.update.sql
  )

  def migrate[F[_] : Bracket[*[_], Throwable]](migrations: List[Migration], log: Option[String => F[Unit]] = None)
                                              (implicit tx: Transactor[F]): F[Unit] =
    for {
      _ <- log.traverse_(_ ("Migration - Create table if not exists migrations"))
      _ <- Queries.createCommitTable.run.transact(tx)
      _ <- log.traverse_(_ ("Migration - Loading commits"))
      commits <- Queries.selectCommit.to[List].transact(tx)
      _ <- log.traverse_(_ ("Migration - Sorting loaded commits by index"))
      sortedCommits = commits.sortBy(_.index)
      _ <- sortedCommits
        .map(Some(_))
        .zipAll(migrations.map(Some(_)), None, None)
        .traverse_ {
          case (commit, migration) => cases(log)(commit, migration)
        }
      _ <- log.traverse_(_ ("Migration - Migration successful"))
    } yield ()

  private def cases[F[_] : Bracket[*[_], Throwable]](log: Option[String => F[Unit]])
                                                    (commit: Option[Commit], migration: Option[Migration])
                                                    (implicit tx: Transactor[F]): F[Unit] =
    (commit, migration) match {
      case (Some(c), None) =>
        fail[F](s"No migration matching commit $c")

      case (None, Some(m@Migration(label, fr, frs@_*))) =>
        log.traverse(_ (s"Migration - Applying migration '$label'")) >> (
          Queries.insertCommit(label, m.md5Hash).run >>
            NonEmptyList
              .of(fr, frs: _*)
              .map(_.update.run)
              .reduceLeft(_ >> _)
          ).transact(tx).void

      case (Some(c), Some(m)) if c.label != m.label =>
        fail[F](s"Label not matching got: '${m.label}' expected: '${c.label}'")

      case (Some(c), Some(m)) if c.md5 != m.md5Hash =>
        fail[F](s"Migration '${c.label}' md5 mismatch, got: '${m.md5Hash}' expected: '${c.md5}'")

      case (Some(_), Some(m)) =>
        log.traverse_(_ (s"Migration - Skipping migration '${m.label}'"))

      case _ => ().pure[F]
    }

  def fail[F[_] : Bracket[*[_], Throwable]](message: String): F[Unit] =
    new Throwable(s"Migration - ERROR - $message").raiseError[F, Unit]
}
