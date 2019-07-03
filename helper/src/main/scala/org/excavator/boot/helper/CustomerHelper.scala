package org.excavator.boot.helper

import java.util.{Optional, UUID}
import java.util.concurrent.TimeUnit

import org.apache.commons.lang3.StringUtils
import org.excavator.boot.authorization.constant.CacheKeys
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.{HashOperations, StringRedisTemplate, ValueOperations}
import org.springframework.stereotype.Component

@Component
class CustomerHelper(stringRedisTemplate: StringRedisTemplate) {

  val logger = LoggerFactory.getLogger(classOf[CustomerHelper])

  def getCustomerId(token:String): Option[Long] = {
    getCustomerIdByToken(token)
  }

  private def saveBaseToken(token: String, customerId: Long, expireSeconds: Long): Unit = { //存储到redis并设置过期时间

    val valueOperations = stringRedisTemplate.opsForValue

    val cacheKey = CacheKeys.USERS_AUTH_TOKEN + token

    valueOperations.set(cacheKey, String.valueOf(customerId), expireSeconds, TimeUnit.SECONDS)
  }

  private def getNowToken(customerId: Long, expireSeconds: Long) = {

    val token = UUID.randomUUID.toString.replace("-", "")

    saveBaseToken(token, customerId, expireSeconds)

    logger.info(s"getNowTokenModel by ${customerId} in token = ${token}")

    token
  }

  def createToken(customerId: Long, expireSeconds: Long) = {
    logger.info(s"createToken param customerId = ${customerId}")

    val setOps = stringRedisTemplate.opsForSet

    var token: String = null

    val hashOps:HashOperations[String, String, String]  = stringRedisTemplate.opsForHash[String,String]

    if (setOps.isMember(CacheKeys.USERS_AUTH_SET, customerId)) {

      val auth = hashOps.get(CacheKeys.USERS_AUTH_HASH, customerId)

      logger.info(s"createToken ${customerId} isMember in auth = ${auth}")

      if(StringUtils.isNotBlank(auth)){

        getCustomerIdByToken(auth) match {

          case Some(t) => {
            logger.info(s"createToken getToken by ${auth} in customerId = ${t}")
            token = auth
          }

          case None => {

            hashOps.delete(CacheKeys.USERS_AUTH_HASH, String.valueOf(customerId))

            token = getNowToken(customerId, expireSeconds)

            hashOps.put(CacheKeys.USERS_AUTH_HASH, String.valueOf(customerId), token)

            logger.info(s"createToken getToken by ${auth} new build Token = ${token}")
          }
        }

      }
    }else {
      setOps.add(CacheKeys.USERS_AUTH_SET, String.valueOf(customerId))

      token = getNowToken(customerId, expireSeconds)

      hashOps.put(CacheKeys.USERS_AUTH_HASH, String.valueOf(customerId), token)

      logger.info(s"createToken ${customerId} noMember in token = ${token}")
    }

    logger.info(s"createToken result = ${token}")

    if(StringUtils.isNotBlank(token)){
      Some(token)
    }else{
      None
    }
  }

  private def getCustomerIdByToken(authorization: String): Option[Long] = {

    if (StringUtils.isBlank(authorization)){
      None
    }else {


      val valueOperations = stringRedisTemplate.opsForValue

      val cacheKey = CacheKeys.USERS_AUTH_TOKEN + authorization

      val customerId = valueOperations.get(cacheKey)

      if (StringUtils.isBlank(customerId)) {
        None
      }else {

        Some(customerId.toLong)
      }
    }
  }
}
