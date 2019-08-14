package org.excavator.boot.helper.test

import java.nio.charset.StandardCharsets

import javax.annotation.Resource
import org.apache.commons.codec.binary.Hex
import org.excavator.boot.common.enums.ResolveEnum
import org.excavator.boot.common.utils.GeneratePublicPrivateKeys
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

}
