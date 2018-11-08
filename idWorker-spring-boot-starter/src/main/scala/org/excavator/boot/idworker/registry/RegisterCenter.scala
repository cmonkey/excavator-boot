package org.excavator.boot.idworker.registry

trait RegisterCenter {

  def init(): Unit

  def close(): Unit

  def get(key: String): String

  def isExisted(key: String): Boolean

  def persist(key:String, value:String): Unit

  def update(key: String, value: String): Unit

  def remove(key: String): Unit

  def getChildrenNum(key: String): Int

  def getChildrenKeys(key: String): java.util.List[String]

  def persistEphemeral(key: String, value: String): Unit

  def getRawClient(): AnyRef
}
