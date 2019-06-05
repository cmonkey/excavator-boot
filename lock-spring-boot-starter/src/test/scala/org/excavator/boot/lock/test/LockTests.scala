package org.excavator.boot.lock.test

import java.util
import java.util.{ArrayList, List, Objects}
import java.util.concurrent._

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[LockTestApplication]))
class LockTests {
  private val logger = LoggerFactory.getLogger(classOf[LockTests])

  @Autowired
  private[test] val testLockService:TestLockService = null

  /**
    * 同一进程内多线程获取锁测试
    *
    * @throws Exception
    */
  @Test
  @throws[Exception]
  def multithreadingTest(): Unit = {
    val nThreads = Runtime.getRuntime.availableProcessors
    val executorService = Executors.newFixedThreadPool(nThreads)
    val tasks = new util.ArrayList[Callable[String]](nThreads)
    for(i <- 0 until nThreads){
      val callable:Callable[String] = () => testLockService.getValue("test")
      tasks.add(callable)

    }
    val futures = executorService.invokeAll(tasks)
    var count = 0
    futures.forEach(f => {
      val data = f.get()
      if(Objects.nonNull(data)){
        count += 1
      }
    })

    executorService.shutdown()
    executorService.awaitTermination(1, TimeUnit.SECONDS)
    assertEquals(count, nThreads)
  }

  @Test
  def testLock() = {
    val r = testLockService.getAopService("lock")
    assertTrue(r.contains("lock"))
  }
}
