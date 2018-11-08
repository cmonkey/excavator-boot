package org.excavator.boot.idworker.generator

trait IdGenerator {

  def nextId: Long

  def nextId(size: Int): Array[Long]
}
