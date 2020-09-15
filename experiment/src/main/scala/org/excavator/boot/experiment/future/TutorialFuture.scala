package org.excavator.boot.experiment.future

import java.lang.Thread.sleep

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class TutorialFuture {

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

  val buyingDonuts: Future[Boolean] = dountStock("plain donut").flatMap(qty => buyDonuts(qty))

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

}
