package org.excavator.boot.cake.pattern.dao.mock

import java.util

import org.excavator.boot.cake.pattern.dao.UserRepository
import org.excavator.boot.cake.pattern.domain.User

class MockUserRepository extends UserRepository {
  val list = new util.ArrayList[User]()

  override def create(user: User): Unit = {
    println(s"create user = $user")
    list.add(user)
  }

  override def find(name: String): Option[User] = {
    println(s"find username = $name")

    val optional = list.stream().filter(u => u.name == name).findFirst()

    if(optional.isPresent){
      Some(optional.get())
    }else{
      None
    }
  }

  override def update(user: User): Unit = {
    println(s"update user = $user")
  }

  override def delete(user: User): Unit = {
    println(s"delete user = $user")
  }
}
