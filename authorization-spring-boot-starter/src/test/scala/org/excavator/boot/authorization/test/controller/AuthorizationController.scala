package org.excavator.boot.authorization.test.controller

import javax.annotation.Resource
import org.excavator.boot.authorization.annotation.{Authorization, CurrentUser}
import org.excavator.boot.authorization.manager.TokenManager
import org.excavator.boot.authorization.test.entity.Customer
import org.excavator.boot.common.helper.JSONProxy
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.{GetMapping, RestController}

@RestController
class AuthorizationController {
  val log = LoggerFactory.getLogger(classOf[AuthorizationController])

  @Resource val tokenManager:TokenManager = null

  @GetMapping(Array("/token")) def createToken: String =
    tokenManager.createToken(1).orElse(null).getToken

  @Authorization
  @GetMapping(Array("/auth")) def authorization: Boolean = {
    log.info("auth method run time = {}", System.nanoTime)
    true
  }

  @Authorization
  @GetMapping(Array("/msg")) def currentUser(@CurrentUser customer: Customer): String = {
    log.info("msg method run time = {}, current user = {}", System.nanoTime, customer)
    JSONProxy.toJSONString(customer.getFieldMap)
  }

}
