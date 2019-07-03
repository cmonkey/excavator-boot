package org.excavator.boot.helper

import java.util.Optional

import org.apache.commons.lang3.StringUtils
import org.excavator.boot.common.enums.ResolveEnum
import org.excavator.boot.common.utils.{GeneratePublicPrivateKey, GeneratePublicPrivateKeys, PublicPrivateKey}
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class CryptoHelper(stringRedisTemplate: StringRedisTemplate) {
  val logger = LoggerFactory.getLogger(classOf[CryptoHelper])

  val USERS_CRYPTO_SETS: String = "users:crypto:sets:"
  val crypto_member_resolve: String = "resolve"
  val crypto_member_private: String = "private"
  val crypto_member_public: String = "public"

  def saveUserCryptoInfo(customerId: String, generatePublicPrivateKey: GeneratePublicPrivateKey, resolveEnum: ResolveEnum): Optional[Boolean]= {
    if(StringUtils.isNotBlank(customerId)) {
      val cacheKey = USERS_CRYPTO_SETS+customerId
      val hashOperations = stringRedisTemplate.opsForHash[String, String]()

      hashOperations.put(cacheKey,crypto_member_resolve, resolveEnum.toString)

      var privateKey = ""
      var publicKey = ""

      resolveEnum match {
        case ResolveEnum.BASE64 => {
          privateKey = generatePublicPrivateKey.getPrivateKeyEncodeBase64String
          publicKey = generatePublicPrivateKey.getPublicKeyEncodeBase64String
        }
        case ResolveEnum.HEX => {
          privateKey = generatePublicPrivateKey.getPrivateKeyEncodeHexString
          publicKey = generatePublicPrivateKey.getPublicKeyEncodeHexString
        }
        case _ => {
          logger.warn("saveUserCryptoInfo resolve match failed")
        }
      }

      hashOperations.put(cacheKey, crypto_member_private, privateKey)
      hashOperations.put(cacheKey, crypto_member_public, publicKey)

      Optional.of(true)
    }else{
      Optional.empty()
    }
  }

  def getPublicPrivateKey(customerId: String, algorithm: String): Optional[PublicPrivateKey] = {
    if(StringUtils.isNotBlank(customerId)) {

      val hashOperations = stringRedisTemplate.opsForHash[String, String]()

      val cacheKey = USERS_CRYPTO_SETS + customerId

      val privateKey = hashOperations.get(cacheKey, crypto_member_private)
      val publicKey = hashOperations.get(cacheKey, crypto_member_public)
      val resolve = hashOperations.get(cacheKey, crypto_member_resolve)

      if(StringUtils.isBlank(privateKey) || StringUtils.isBlank(publicKey) || StringUtils.isBlank(resolve)){
        Optional.empty()
      }else {

        val resolveEnum = ResolveEnum.valueOf(resolve)

        val generatePublicPrivateKey = new GeneratePublicPrivateKey()

        resolveEnum match {
          case ResolveEnum.BASE64 => {
            generatePublicPrivateKey.setPrivateKeyEncodeBase64String(privateKey)
            generatePublicPrivateKey.setPublicKeyEncodeBase64String(publicKey)
          }
          case ResolveEnum.HEX => {
            generatePublicPrivateKey.setPrivateKeyEncodeHexString(privateKey)
            generatePublicPrivateKey.setPublicKeyEncodeHexString(publicKey)
          }
          case _ => {
            logger.warn("getPublicPrivateKey resolve match failed ")
          }
        }

        val optionalPublicPrivateKey = GeneratePublicPrivateKeys.getPublicPrivateKey(algorithm, generatePublicPrivateKey, resolveEnum)

        optionalPublicPrivateKey
      }
    }else{
      Optional.empty()
    }
  }
}
