package org.excavator.boot.cake.pattern.test

import org.excavator.boot.cake.pattern.config.EnvRegistry
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class EnvRegistryTests {

  @Test
  @DisplayName("test EnvRegistry")
  def testEnvRegistry() = {
    assertEquals(EnvRegistry.text, EnvRegistry.welcome)
  }

}
