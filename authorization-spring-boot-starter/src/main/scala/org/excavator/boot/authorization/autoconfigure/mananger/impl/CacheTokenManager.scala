package org.excavator.boot.authorization.autoconfigure.mananger.impl

import java.util.UUID
import java.util.concurrent.TimeUnit

import org.apache.commons.lang3.StringUtils
import org.excavator.boot.authorization.constant.{CacheKeys, TokenConstants}
import org.excavator.boot.authorization.manager.TokenManager
import org.excavator.boot.authorization.model.Token
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.{HashOperations, SetOperations, StringRedisTemplate, ValueOperations}
import org.springframework.stereotype.Component

@Component
class CacheTokenManager(val stringRedisTemplate: StringRedisTemplate) extends TokenManager {

  val logger = LoggerFactory.getLogger(classOf[CacheTokenManager])

  override def createToken(customerId: Long): Token = {
    logger.info(s"createToken param customerId = ${customerId}")

    val setOps = stringRedisTemplate.opsForSet

    val customerIdStr = customerId.toString

    var token:Token = null

    val hashOps:HashOperations[String, String, String]  = stringRedisTemplate.opsForHash

    if (setOps.isMember(CacheKeys.USERS_AUTH_SET, customerIdStr)) {

      val auth = hashOps.get(CacheKeys.USERS_AUTH_HASH, customerIdStr)

      logger.info(s"createToken ${customerId} isMember in auth = ${auth}")

      if(StringUtils.isNotBlank(auth)){
        val optionToken = getToken(auth) match {
          case Some(t) => token = t
          case None => token = {

            hashOps.delete(CacheKeys.USERS_AUTH_HASH, customerIdStr)
            val newToken = getNowTokenModel(customerId)
            hashOps.put(CacheKeys.USERS_AUTH_HASH, customerIdStr, token.getToken)

            newToken
          }
        }

      }
    }
    else {
      setOps.add(CacheKeys.USERS_AUTH_SET, customerIdStr)
      token = getNowTokenModel(customerId)
      hashOps.put(CacheKeys.USERS_AUTH_HASH, customerIdStr, token.getToken)
    }

    logger.info(s"createToken result = ${token}")

    return token

  }

  private def getNowTokenModel(customerId: Long) = {

    val token = UUID.randomUUID.toString.replace("-", "")

    val model = new Token

    model.setCustomerId(customerId)
    model.setToken(token)

    saveBaseToken(token, customerId)

    model
  }

  private def saveBaseToken(token: String, customerId: Long): Unit = { //存储到redis并设置过期时间

    val valueOperations = stringRedisTemplate.opsForValue

    val cacheKey = CacheKeys.USERS_AUTH_TOKEN + token

    valueOperations.set(cacheKey, String.valueOf(customerId), TokenConstants.TOKEN_EXPIRES_DAY, TimeUnit.DAYS)
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
        valueOperations.set(cacheKey, customerId, TokenConstants.TOKEN_EXPIRES_DAY, TimeUnit.DAYS)

        true
      }
    }
  }

  override def getToken(authorization: String): Option[Token] = {

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
    getToken(token).map(tokenModel => {

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
