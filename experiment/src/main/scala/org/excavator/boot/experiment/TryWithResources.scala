package org.excavator.boot.experiment

import scala.util.control.NonFatal

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

  private def closeAndAddSuppressed(e:Throwable, resource:AutoCloseable): Unit = {
    if(e != null){
      try{
        resource.close()
      }catch{
        case NonFatal(suppressed) => 
          e.addSuppressed(suppressed)
      }
    }else{
      resource.close()
    }

  }

}

object TryWithResources{
  def apply() = new TryWithResources
}
