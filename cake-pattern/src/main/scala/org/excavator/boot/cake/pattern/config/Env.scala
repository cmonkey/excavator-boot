package org.excavator.boot.cake.pattern.config

trait Env{

  this: Config =>

  def welcome = this.text

}
