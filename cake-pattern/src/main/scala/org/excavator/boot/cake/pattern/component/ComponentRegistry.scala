package org.excavator.boot.cake.pattern.component
import org.excavator.boot.cake.pattern.dao.UserRepository
import org.excavator.boot.cake.pattern.dao.mock.MockUserRepository
import org.excavator.boot.cake.pattern.service.UserService

object ComponentRegistry extends UserServiceComponent with UserRepositoryComponent {
  override val userRepository: UserRepository = new MockUserRepository
  override val userService: UserService = {
    val service = new UserService

    service.userRepository = userRepository

    service
  }
}
