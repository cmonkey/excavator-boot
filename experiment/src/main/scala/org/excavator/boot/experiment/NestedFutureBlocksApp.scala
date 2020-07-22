import scala.concurrent._,ExecutionContext.Implicits._,duration.Duration.Inf

object NestedFutureBlocksApp extends App{
  def slow(key: String) = Future{
    println(s"$key start")
    Thread.sleep(1000)
    println(s"$key end")
    key
  }

  def runAsyncSerial(): Future[Seq[String]] = slow("A").flatMap{
    a => Future.sequence(Seq(slow("B"), slow("C"), slow("D")))
  }

  Await.result(runAsyncSerial(), Inf)
}

