package org.excavator.boot.instrumentation.test

import org.excavator.boot.instrumentation.application.AgentLoader
import org.junit.jupiter.api.{BeforeAll, DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class AgentTests {

  @Test
  @DisplayName("testJavassistAgentLoader")
  def testJavassistAgentLoader() = {
    assertThrows(classOf[RuntimeException], () => AgentTests.agentLoader.run(Array("")))
  }

  @Test
  @DisplayName("testByteBuddyAgentLoader")
  def testByteBuddyAgentLoader() = {

    assertThrows(classOf[RuntimeException], () => AgentTests.agentLoader.run(Array("")))
  }
}

object AgentTests{
  var agentLoader: AgentLoader = null

  @BeforeAll
  def initLoader() = {
    agentLoader = new AgentLoader
  }
}
