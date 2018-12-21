package org.excavator.boot.cake.pattern.dao

import org.excavator.boot.cake.pattern.domain.User

trait UserRepository {

  def create(user: User)

  def find(name:String): Option[User]

  def update(user: User)

  def delete(user: User)
}
