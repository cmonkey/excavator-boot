package org.excavator.boot.helper

import java.util.Optional

import javax.crypto.SecretKey
import org.apache.commons.lang3.StringUtils
import org.excavator.boot.common.enums.ResolveEnum
import org.excavator.boot.common.utils.{GeneratePublicPrivateKey, GeneratePublicPrivateKeys, GenerateSymmetricencryption, PublicPrivateKey}
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
  val crypt_member_encoded = "encoded"

  def saveUserCryptoInfo(customerId: String, generatePublicPrivateKey: GeneratePublicPrivateKey, resolveEnum: ResolveEnum): Optional[Boolean]= {
    if(StringUtils.isNotBlank(customerId)) {
      val cacheKey = USERS_CRYPTO_SETS+customerId
      val hashOperations = stringRedisTemplate.opsForHash[String, String]()

      hashOperations.put(cacheKey,crypto_member_resolve, resolveEnum.toString)

      var privateKey = ""
      var publicKey = ""

      resolveEnum match {
        case ResolveEnum.BASE64 => {
          privateKey = generatePublicPrivateKey.privateKeyEncodeBase64String
          publicKey = generatePublicPrivateKey.publicKeyEncodeBase64String
        }
        case ResolveEnum.HEX => {
          privateKey = generatePublicPrivateKey.privateKeyEncodeHexString
          publicKey = generatePublicPrivateKey.publicKeyEncodeHexString
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

        var generatePublicPrivateKey = new GeneratePublicPrivateKey(null, null, null, null)

        resolveEnum match {
          case ResolveEnum.BASE64 => {
            generatePublicPrivateKey = new GeneratePublicPrivateKey(null, null, privateKey, publicKey)
          }
          case ResolveEnum.HEX => {
            generatePublicPrivateKey = new GeneratePublicPrivateKey(privateKey, publicKey, null, null)
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

  def saveSecretKeyEncoded(customerId: String, encoded: String):Optional[Boolean] = {

    if(StringUtils.isNotBlank(customerId) && StringUtils.isNotBlank(encoded)) {

      val cacheKey = USERS_CRYPTO_SETS+customerId
      val hashOperations = stringRedisTemplate.opsForHash[String, String]()

      hashOperations.put(cacheKey, crypt_member_encoded, encoded)

      Optional.of(true)

    }else{
      Optional.empty()
    }
  }


  def getSecretKey(customerId: String, algorithm: String, resolveEnum: ResolveEnum): Optional[SecretKey] = {
    if(StringUtils.isNotBlank(customerId)) {

      val hashOperations = stringRedisTemplate.opsForHash[String, String]()

      val cacheKey = USERS_CRYPTO_SETS + customerId

      val encoded = hashOperations.get(cacheKey, crypt_member_encoded)

      if(StringUtils.isBlank(encoded)){
        Optional.empty()
      }else {
        GenerateSymmetricencryption.decodeKeyFromString(encoded, algorithm, resolveEnum)
      }
    }else{
      Optional.empty()
    }
  }


  def getSecretKeyBySM4(customerId: String, resolveEnum: ResolveEnum): Optional[SecretKey] = {
    getSecretKey(customerId, "SM4", resolveEnum)
  }
}
