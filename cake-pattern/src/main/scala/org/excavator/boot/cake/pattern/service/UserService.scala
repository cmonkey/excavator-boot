package org.excavator.boot.cake.pattern.service

import javax.annotation.Resource
import org.excavator.boot.cake.pattern.dao.UserRepository
import org.excavator.boot.cake.pattern.domain.User

class UserService {

  @Resource
  var userRepository: UserRepository = _

  def create(user: User) = userRepository.create(user)

  def find(name: String) = userRepository.find(name)

  def update(user: User) = userRepository.update(user)

  def delete(user: User) = userRepository.delete(user)

}
