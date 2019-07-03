package org.excavator.boot.authorization.mananger.impl

import java.util.{Optional}
import java.util.concurrent.TimeUnit

import javax.annotation.Resource
import org.excavator.boot.authorization.config.AuthorizationProperties
import org.excavator.boot.authorization.constant.{CacheKeys}
import org.excavator.boot.authorization.manager.TokenManager
import org.excavator.boot.authorization.model.Token
import org.excavator.boot.helper.CustomerHelper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.{SetOperations, StringRedisTemplate}
import org.springframework.stereotype.Component

@Component
class CacheTokenManager(stringRedisTemplate: StringRedisTemplate) extends TokenManager {

  private val logger = LoggerFactory.getLogger(classOf[CacheTokenManager])

  @Resource
  val authorizationProperties: AuthorizationProperties = null

  @Resource
  val customerHelper: CustomerHelper = null

  override def createToken(customerId: Long): Optional[Token] = {
    val token = customerHelper.createToken(customerId, authorizationProperties.getExpire_second)

    token match {
      case Some(value) => {
        val tokenModel = new Token
        tokenModel.setCustomerId(customerId)
        tokenModel.setToken(value)

        Optional.of(tokenModel)
      }
      case None =>{
        Optional.empty()
      }
    }
  }

  override def checkToken(token: Token): Boolean = {
    if (token == null) {
      false
    }else {

      val valueOperations = stringRedisTemplate.opsForValue

      val cacheKey = CacheKeys.USERS_AUTH_TOKEN + token.getToken

      val customerId = valueOperations.get(cacheKey)
      val modelCustomerId = token.getCustomerId.toString
      if (customerId == null || !(customerId == modelCustomerId)) {
        false
      }else {
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        valueOperations.set(cacheKey, customerId, authorizationProperties.getExpire_second, TimeUnit.SECONDS)

        true
      }
    }
  }

  override def getToken(authenticate: String): Optional[Token] = {
    customerHelper.getCustomerId(authenticate) match {
      case Some(customerId) => {
        val token = new Token
        token.setCustomerId(customerId)
        token.setToken(authenticate)

        Optional.of(token)
      }

      case None => Optional.empty()
    }

    customerHelper.getCustomerId(authenticate) match {
      case Some(customerId) => {
        val token = new Token
        token.setCustomerId(customerId)
        token.setToken(authenticate)

        Optional.of(token)
      }

      case None => Optional.empty()
    }
  }

  override def deleteToken(token: String): Unit = {
    customerHelper.getCustomerId(token) match {
      case Some(customerId) => {

      val cacheKey = CacheKeys.USERS_AUTH_TOKEN + token

      stringRedisTemplate.delete(cacheKey)

      val setOps:SetOperations[String, String] = stringRedisTemplate.opsForSet

      val customerIdStr = String.valueOf(customerId)
      setOps.remove(CacheKeys.USERS_AUTH_SET, customerIdStr)

      val hashOps = stringRedisTemplate.opsForHash

      hashOps.delete(CacheKeys.USERS_AUTH_HASH, customerIdStr)
    }
    case None => logger.warn("token get customerId is null")
  }
}
