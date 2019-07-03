package org.excavator.boot.helper.test

import javax.annotation.Resource
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

}
