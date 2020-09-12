package org.excavator.boot.experiment.zio

import zio.{IO, Task, UIO, URIO, ZIO}

class ZIOApp extends App{

  val s1 = ZIO.succeed(42)

  val s3: Task[Int] = Task.succeed(42)

  val now = ZIO.effectTotal(System.currentTimeMillis())

  val f1 = ZIO.fail("Uh oh!")

  val f2 = Task.fail(new Exception("Uh oh!"))

  val zoption: IO[Option[Nothing], Int] = ZIO.fromOption(Some(2))

  val zoption2: IO[String, Int] = zoption.mapError(_ => "It wasn't there!")

  val maybeId: IO[Option[Nothing], String] = ZIO.fromOption(Some("abc123"))
  def getUser(userId:String): IO[Throwable, Option[User]] = ???
  def getTeam(teamId:String): IO[Throwable, Team] = ???

  val result: IO[Throwable, Option[(User, Team)]] = (for {
    id <- maybeId
    user <- getUser(id).some
    team <- getTeam(user.teamId).asSomeError
  } yield (user, team)).optional

  val zeither = ZIO.fromEither(Right("Success"))

  import scala.util.Try
  val ztry = ZIO.fromTry(Try(42 / 0))

  val zfun:URIO[Int, Int] =
    ZIO.fromFunction((i: Int) => i * i)

  import scala.concurrent.Future

  lazy val future = Future.successful("Hello")

  val zfuture: Task[String] =
    ZIO.fromFuture({implicit ec =>
      future.map(_ => "Goodbye!")
    })

  import scala.io.StdIn
  val getStrin:Task[String] =
    ZIO.effect(StdIn.readLine())

  def putStrin(line:String):UIO[Unit] =
    ZIO.effectTotal(println(line))

  import java.io.IOException
  val getStrin2: IO[IOException, String] =
    ZIO.effect(StdIn.readLine()).refineOrDie[IOException]

  object legacy{
    def login(onSuccess: User => Unit,
             onFailure: AuthError => Unit): Unit = ???
  }

  val login:IO[AuthError, User] =
    IO.effectAsync[AuthError, User] {
      callback =>
        legacy.login(
          user => callback(IO.succeed(user)),
          err => callback(IO.fail(err))
        )
    }

  import zio.blocking._
  val sleeping = effectBlocking(Thread.sleep(Long.MaxValue))

  import java.net.ServerSocket
  import zio.UIO
  def accept(l:ServerSocket) =
    effectBlockingCancelable(l.accept())(UIO.effectTotal(l.close()))

  import scala.io.{Codec, Source}
  def download(url:String) =
    Task.effect{
      Source.fromURL(url)(Codec.UTF8).mkString
    }

  def safeDownload(url:String) =
    blocking(download(url))

}

case class AuthError() extends RuntimeException
case class User(teamId:String)
case class Team()
