package org.excavator.boot.instrumentation.test

import org.excavator.boot.instrumentation.application.AgentLoader
import org.junit.jupiter.api.{BeforeAll, DisplayName, Test}
import org.junit.jupiter.api.Assertions._

class AgentTest {

  @Test
  @DisplayName("testJavassistAgentLoader")
  def testJavassistAgentLoader() = {
    assertThrows(classOf[RuntimeException], () => AgentTest.agentLoader.run(Array("")))
  }

  @Test
  @DisplayName("testByteBuddyAgentLoader")
  def testByteBuddyAgentLoader() = {

    assertThrows(classOf[RuntimeException], () => AgentTest.agentLoader.run(Array("")))
  }
}

object AgentTest{
  var agentLoader: AgentLoader = null

  @BeforeAll
  def initLoader() = {
    agentLoader = new AgentLoader
  }
}
