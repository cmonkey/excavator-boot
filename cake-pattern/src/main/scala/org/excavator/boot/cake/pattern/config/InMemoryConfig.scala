package org.excavator.boot.cake.pattern.config

trait InMemoryConfig extends Config {

  override lazy val text: String = "inMemory config"

  override def load: Unit = println(s"load in memory config ${text}")

}
