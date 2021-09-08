package org.excavator.boot.experiment.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.{DisplayName, Test}
import org.springframework.boot.SpringBootVersion
import org.springframework.core.SpringVersion

class SpringVersionTest {

  @Test
  @DisplayName("test  spring version")
  def testSpringVersion(): Unit = {
    val version = SpringVersion.getVersion
    assertEquals("5.3.9", version)
  }

  @Test
  @DisplayName("test spring boot version")
  def testSpringBootVersion():Unit = {
    val version = SpringBootVersion.getVersion
    assertEquals("2.5.4", version)
  }

}
