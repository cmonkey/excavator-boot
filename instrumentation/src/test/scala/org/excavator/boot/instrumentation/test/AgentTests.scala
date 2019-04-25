package org.excavator.boot.instrumentation.test

import org.excavator.boot.instrumentation.application.AgentLoader
import org.junit.jupiter.api.{DisplayName, Test}

class AgentTests {

  @Test
  @DisplayName("testAgentLoader")
  def testAgentLoader() = {

    val agentLoader = new AgentLoader
    agentLoader.run(Array(""))
  }
}
