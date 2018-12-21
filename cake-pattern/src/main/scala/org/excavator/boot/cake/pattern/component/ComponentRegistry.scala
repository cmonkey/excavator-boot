package org.excavator.boot.cake.pattern.component
import org.excavator.boot.cake.pattern.dao.UserRepository
import org.excavator.boot.cake.pattern.dao.mock.MockUserRepository
import org.excavator.boot.cake.pattern.service.UserService

object ComponentRegistry extends UserServiceComponent with UserRepositoryComponent {
  override val userService: UserService = {

    val userService = new UserService

    userService.userRepository = userRepository

    userService
  }
  override val userRepository: UserRepository = {
    new MockUserRepository
  }
}
