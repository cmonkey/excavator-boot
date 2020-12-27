package org.excavator.boot.cake.pattern.dao

import org.excavator.boot.cake.pattern.domain.User

trait UserRepository {

  def create(user: User):Unit

  def find(name:String): Option[User]

  def update(user: User):Unit

  def delete(user: User):Unit
}
