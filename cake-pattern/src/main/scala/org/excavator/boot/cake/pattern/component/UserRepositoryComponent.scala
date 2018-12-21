package org.excavator.boot.cake.pattern.component

import org.excavator.boot.cake.pattern.dao.UserRepository

trait UserRepositoryComponent {

  val userRepository: UserRepository

}
