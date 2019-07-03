package org.excavator.boot.authorization.mananger.impl

import java.util.{Optional, UUID}
import java.util.concurrent.TimeUnit

import javax.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.excavator.boot.authorization.config.AuthorizationProperties
import org.excavator.boot.authorization.constant.{CacheKeys, TokenConstants}
import org.excavator.boot.authorization.manager.TokenManager
import org.excavator.boot.authorization.model.Token
import org.excavator.boot.helper.CustomerHelper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.{HashOperations, SetOperations, StringRedisTemplate, ValueOperations}
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

  private def saveBaseToken(token: String, customerId: Long): Unit = { //存储到redis并设置过期时间

    val valueOperations = stringRedisTemplate.opsForValue

    val cacheKey = CacheKeys.USERS_AUTH_TOKEN + token

    valueOperations.set(cacheKey, String.valueOf(customerId), authorizationProperties.getExpire_second, TimeUnit.SECONDS)
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
    getTokenOption(authenticate) match {
      case Some(t) => Optional.of(t)
      case None => Optional.empty()
    }
  }

  def getTokenOption(authorization: String): Option[Token] = {

    if (StringUtils.isBlank(authorization)){
      None
    }else {


      val valueOperations = stringRedisTemplate.opsForValue

      val cacheKey = CacheKeys.USERS_AUTH_TOKEN + authorization

      val customerId = valueOperations.get(cacheKey)

      if (StringUtils.isBlank(customerId)) {
        None
      }else {

        val tokenModel = new Token

        tokenModel.setCustomerId(customerId.toLong)
        tokenModel.setToken(authorization)

        Some(tokenModel)
      }
    }
  }

  override def deleteToken(token: String): Unit = {
    getTokenOption(token).map(tokenModel => {

      val cacheKey = CacheKeys.USERS_AUTH_TOKEN + token

      stringRedisTemplate.delete(cacheKey)

      val setOps:SetOperations[String, String] = stringRedisTemplate.opsForSet

      val customerIdStr = tokenModel.getCustomerId.toString
      setOps.remove(CacheKeys.USERS_AUTH_SET, customerIdStr)

      val hashOps = stringRedisTemplate.opsForHash

      hashOps.delete(CacheKeys.USERS_AUTH_HASH, customerIdStr)

    })
  }
}
