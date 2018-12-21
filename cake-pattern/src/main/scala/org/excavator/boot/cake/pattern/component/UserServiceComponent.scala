package org.excavator.boot.cake.pattern.component

import org.excavator.boot.cake.pattern.service.UserService

trait UserServiceComponent {

  this: UserRepositoryComponent =>

  val userService: UserService

}
