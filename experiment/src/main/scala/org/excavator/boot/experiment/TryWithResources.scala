package org.excavator.boot.experiment

class TryWithResources{

  def withResources[T <: AutoCloseable, V] (r: => T)(f: T => V) :V = {
    val resource: T = r

    require(resource != null, "resource is null")

    var exception: Throwable = null

    try{
      f(resource)
    }catch{
      case NonFatal(e) => 
        exception = e
        throw e
    }finally{
      closeAndAddSuppressed(exception, resource)
    }

  }

}
