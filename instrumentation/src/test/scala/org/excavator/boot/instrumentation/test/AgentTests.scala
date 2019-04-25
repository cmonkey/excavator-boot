package org.excavator.boot.instrumentation.test

import org.excavator.boot.instrumentation.application.AgentLoader
import org.junit.jupiter.api.{BeforeAll, BeforeEach, DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class AgentTests {

  @Test
  @DisplayName("testJavassistAgentLoader")
  def testJavassistAgentLoader() = {
    assertThrows(classOf[RuntimeException], () => AgentTests.agentLoader.runJavassist(Array("")))
  }

  @Test
  @DisplayName("testByteBuddyAgentLoader")
  def testByteBuddyAgentLoader() = {

  }
}

object AgentTests{
  var agentLoader: AgentLoader = null

  @BeforeEach
  def initLoader() = {
    agentLoader = new AgentLoader
  }
}
