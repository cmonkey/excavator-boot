package org.excavator.boot.cake.pattern.test

import org.excavator.boot.cake.pattern.component.ComponentRegistry
import org.excavator.boot.cake.pattern.dao.mock.MockUserRepository
import org.excavator.boot.cake.pattern.domain.User
import org.excavator.boot.cake.pattern.service.UserService
import org.junit.jupiter.api.{BeforeAll, DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class CakePatternTests{

  @Test
  @DisplayName("testMockUserService")
  def testMock() = {

    val userService = new UserService

    userService.userRepository = CakePatternTests.userRepository

    val userName = "cmonkey"

    val user = User(userName)

    userService.create(user)

    val r = userService.find(userName)

    r match {
      case Some(u) => assertEquals(u, user)
      case None => fail(() => "find fail")
    }
  }

  @Test
  @DisplayName("test cake pattern")
  def testCakePattern() = {
    val userName = "cmonkey"

    val user = User(userName)

    val userService = ComponentRegistry.userService

    userService.create(user)

    userService.find(userName) match {
      case Some(u) => assertEquals(user, u)
      case None => fail(() => "find fail")
    }

  }

}

object CakePatternTests{
  var userRepository: MockUserRepository = _
  @BeforeAll
  def initMockService() = {

    userRepository = new MockUserRepository
  }
}
