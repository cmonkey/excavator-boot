package org.excavator.boot.helper.test

import java.nio.charset.StandardCharsets

import javax.annotation.Resource
import org.apache.commons.codec.binary.{Base64, Hex}
import org.excavator.boot.common.enums.ResolveEnum
import org.excavator.boot.common.utils.{GeneratePublicPrivateKeys, GenerateSymmetricencryption}
import org.excavator.boot.helper.CryptoHelper
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.{DisplayName, Test}
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(Array(classOf[SpringExtension]))
@SpringBootTest(classes = Array(classOf[HelperApplication]))
class CryptoHelperTests {

  @Resource
  val cryptoHelper: CryptoHelper = null

  @Test
  @DisplayName("test crypto saveUserInfo")
  def testSaveCryptoInfo() = {
    val customerId = "1"
    val algorithm = "EC"

    val generatePublicPrivateKey = GeneratePublicPrivateKeys.generateKeysByEC(algorithm)
    val resolveEnum = ResolveEnum.BASE64

    val status = cryptoHelper.saveUserCryptoInfo(customerId, generatePublicPrivateKey.get(), resolveEnum)

    assertEquals(true, status.isPresent)
  }

  @Test
  @DisplayName("test crypto getPublicPrivateKey")
  def testGetPublicPrivateKey() = {
    val customerId = "1"
    val algorithm = "EC"

    val optionalPublicPrivateKey = cryptoHelper.getPublicPrivateKey(customerId, algorithm)

    assertEquals(true, optionalPublicPrivateKey.isPresent)
  }


  @Test
  @DisplayName("test Decrypt by crypto")
  def testDecrypt(): Unit = {
    val text = "049ef8042ac47d18b7d67c7acfef2309e861763a7b0d54a0aadfdacb558a51f1bf90082bfceb8aa2599965ce1afbf316c522f5e90d4373ab570f7f145b0435a59334b4cfdd0f0dd82f7bd98e126799a75fe21c21106a96e0c676203ba512ad3cc208fd22d49ef6da7dfebde5f2c071ac8831989ac222ddafb13a4f918d53402945d120a9362d8278a6e1ed6cb763b617da14b53009a28ecb98c3eba31ce96b"

    val customerId = "1";

    val algorithm = "EC"

    val optionalPublicPrivateKey = cryptoHelper.getPublicPrivateKey(customerId, algorithm);

    optionalPublicPrivateKey.ifPresent(publicPrivateKey => {
      val input = Hex.decodeHex(text)
      val dec  = GeneratePublicPrivateKeys.decryptBySM2(input, publicPrivateKey.getPrivateKey)

      dec.ifPresent(r => {
        val result = new String(r, StandardCharsets.UTF_8)
        println(s"${result}")
      })
    })

  }


  @Test
  @DisplayName("test saveCryptoSM4")
  def testSaveCryptoSM4(): Unit = {
    val algorithm = "sm4"
    val customerId = "1"
    val secretKeyOptional = GenerateSymmetricencryption.getSecretKey(algorithm, 128)

    assertEquals(true, secretKeyOptional.isPresent)

    val encoded = secretKeyOptional.get().getEncoded

    val base64Encoded = Base64.encodeBase64String(encoded)
    val hexEncoded = Hex.encodeHexString(encoded)

    var sm4Optional = cryptoHelper.saveCryptoBySM4(customerId, base64Encoded)

    assertEquals(true, sm4Optional.isPresent)

    sm4Optional = cryptoHelper.saveCryptoBySM4(customerId, hexEncoded)

    assertEquals(true, sm4Optional.isPresent)
  }


  @Test
  @DisplayName("test getSecretKey")
  def testGetSecretKey(): Unit = {
    val algorithm = "SM4"
    val customerId = "1"

    val secretKeyOptional = GenerateSymmetricencryption.getSecretKey(algorithm, 128)

    assertEquals(true, secretKeyOptional.isPresent)

    val encoded = secretKeyOptional.get().getEncoded

    val base64Encoded = Base64.encodeBase64String(encoded)
    val hexEncoded = Hex.encodeHexString(encoded)

    val base64SaveOptional = cryptoHelper.saveCryptoBySM4(customerId, base64Encoded)

    assertEquals(true, base64SaveOptional.isPresent)

    val base64SecretKeyOptional = cryptoHelper.getSecretKey(customerId, algorithm, ResolveEnum.BASE64)

    assertEquals(true,base64SecretKeyOptional.isPresent)

    assertEquals(base64Encoded, Base64.encodeBase64String(base64SecretKeyOptional.get().getEncoded))

    val hexSaveOptional = cryptoHelper.saveCryptoBySM4(customerId, hexEncoded)

    assertEquals(true, hexSaveOptional.isPresent)

    val hexSecretKeyOptional = cryptoHelper.getSecretKey(customerId, algorithm, ResolveEnum.HEX)

    assertEquals(true, hexSecretKeyOptional.isPresent)

    assertEquals(hexEncoded, Hex.encodeHexString(hexSecretKeyOptional.get().getEncoded))
  }

}
