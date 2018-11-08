package org.excavator.boot.netty.response

import java.util.concurrent.{ArrayBlockingQueue, TimeUnit}

import org.excavator.boot.netty.enums.State
import io.netty.util.concurrent.{Future, GenericFutureListener}

import scala.concurrent.TimeoutException

class ResponseFuture extends Future[String]{

  @volatile private var state = State.WAITING

  private val blockingQueue = new ArrayBlockingQueue[String](1)

  override def isSuccess: Boolean = state == State.DONE

  override def isCancellable: Boolean = false

  override def cause(): Throwable = null

  override def addListener(genericFutureListener: GenericFutureListener[_ <: Future[_ >: String]]): Future[String] = null

  override def addListeners(genericFutureListeners: GenericFutureListener[_ <: Future[_ >: String]]*): Future[String] = null

  override def removeListener(genericFutureListener: GenericFutureListener[_ <: Future[_ >: String]]): Future[String] = null

  override def removeListeners(genericFutureListeners: GenericFutureListener[_ <: Future[_ >: String]]*): Future[String] = null

  override def sync(): Future[String] = null

  override def syncUninterruptibly(): Future[String] = null

  override def await(): Future[String] = null

  override def awaitUninterruptibly(): Future[String] = null

  override def await(l: Long, timeUnit: TimeUnit): Boolean = false

  override def await(l: Long): Boolean = false

  override def awaitUninterruptibly(l: Long, timeUnit: TimeUnit): Boolean = false

  override def awaitUninterruptibly(l: Long): Boolean = false

  override def getNow: String = {
    blockingQueue.poll()
  }

  override def cancel(b: Boolean): Boolean = false

  override def isCancelled: Boolean = false

  override def isDone: Boolean = {
    state == State.DONE
  }

  override def get(): String = {
    val aux = blockingQueue.take()
    blockingQueue.put(aux)
    aux
  }

  override def get(timeout: Long, unit: TimeUnit): String = {
    val responseAfterWait = blockingQueue.poll(timeout, unit)

    if(null == responseAfterWait){
      throw new TimeoutException()
    }

    responseAfterWait
  }

  def set(msg: String) = {
    if (state == State.DONE) {
      ""
    } else {
      blockingQueue.put(msg)

      state = State.DONE
    }
  }
}
