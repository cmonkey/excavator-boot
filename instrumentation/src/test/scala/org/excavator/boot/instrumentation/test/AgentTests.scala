package org.excavator.boot.instrumentation.test

import org.excavator.boot.instrumentation.application.AgentLoader
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class AgentTests {

  @Test
  @DisplayName("testJavassistAgentLoader")
  def testJavassistAgentLoader() = {

    val agentLoader = new AgentLoader
    assertThrows(classOf[RuntimeException], () => agentLoader.runJavassist(Array("")))
  }

  @Test
  @DisplayName("testByteBuddyAgentLoader")
  def testByteBuddyAgentLoader() = {

  }
}
