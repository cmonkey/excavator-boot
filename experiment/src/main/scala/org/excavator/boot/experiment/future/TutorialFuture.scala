package org.excavator.boot.experiment.future

import java.lang.Thread.sleep
import java.math.BigInteger
import java.net.URL
import java.security.MessageDigest

import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

object TutorialFuture extends App{

  def dountStock(donut:String):Future[Option[Int]] = Future {
    // assume some long running database operation
    println("checking donut stock")
    if(donut == "vanilla donut") Some(10) else None
  }

  val vanillaDonutStock = Await.result(dountStock("vanilla donut"), 3.seconds)
  println(s"Stock of vanila donut = $vanillaDonutStock")

  dountStock("vanilla donut").onComplete({
    case Success(value) => println(s"Stock fro vanilla donut $value")
    case Failure(exception) => println(s"Failed to find vanilla donut stock, exception = $exception")
  })

  def buyDonuts(quantity:Int): Future[Boolean] = Future {
    println(s"buying $quantity donuts")
    if(quantity > 0)true else false
  }

  val buyingDonuts: Future[Boolean] = dountStock("plain donut").flatMap(qty => buyDonuts(qty.getOrElse(0)))

  val isSuccess = Await.result(buyingDonuts, 5.seconds)

  for{
    stock <- dountStock("vanilla donut")
    isSuccess <- buyDonuts(stock.getOrElse(0))
  }yield println(s" Buying vanilla donut was successful = $isSuccess")

  dountStock("vanilla donut").map(someQty => println(s"Buying ${someQty.getOrElse(0)} vanilla donuts"))

  def getStockPrice(stockSymbol:String) :Future[Double] = Future {
    val r = scala.util.Random
    val randomSleepTime = r.nextInt(3000)
    val randomPrice = r.nextDouble * 1000
    sleep(randomSleepTime)

    randomPrice
  }

  val result = Future{
    println("Long running computation started.")
    val result = {
      Thread.sleep(5000)
      5
    }
    println("Our computation, finally finished.")
    result
  }

  type Name = String
  type Email = String 
  type Password = String 
  type Avatar = URL

  case class User(name:Name, email:Email, password: Password, avatar:Avatar)

  def notExist(email:Email):Future[Boolean] = Future{
    Thread.sleep(100)
    true
  }

  def md5hash(str:String):String = 
    new BigInteger(1, MessageDigest.getInstance("MD5").digest(str.getBytes)).toString(16)

  def avatar(email:Email):Future[Avatar] = Future{
    Thread.sleep(200)
    new Avatar("http://avatar.example.com/user/23k520f23f4.png")
  }

  def createUser(name:Name, email:Email, password:Password):Future[User] = 
    for{
      _ <- notExist(email)
      avatar <- avatar(email)
      hashedPassword = md5hash(password)
    }yield User(name, email, hashedPassword, avatar)

  val userFuture: Future[User] = 
    createUser("John", "john@example.com", "secret")

  userFuture.onComplete{
    case Success(user) =>  println(s"User created: $user")
    case Failure(exception) =>
      println(s"Creating user failed due to the exception, $exception")
  }

  val user:User = Await.result(userFuture, Duration.Inf)

  val completedFuture: Future[User] = Await.ready(userFuture, Duration.Inf)

  completedFuture.value.get match{
    case Success(user) => {
    println(s"User created: $user")}
    case Failure(exception) =>
      println(s"Creating user failed due to the exception: $exception")
  }

  def runByPromise[T](block: => T)(implicit ec:ExecutionContext):Future[T] =
  {
    val p = Promise[T]
    ec.execute{() => {
      try{
        p.success(block)
      }catch{
        case NonFatal(e) => p.failure(e)
      }
    }}
    p.future
  }

}
