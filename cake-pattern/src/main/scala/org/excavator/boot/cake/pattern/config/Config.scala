package org.excavator.boot.cake.pattern.config

trait Config {

  val text: String

  def load: Unit

}
