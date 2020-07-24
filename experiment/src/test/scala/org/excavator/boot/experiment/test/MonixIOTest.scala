package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.MonixIO
import org.junit.jupiter.api.{DisplayName, Test}

class MonixIOTest {

  @Test
  @DisplayName("testMonixIO")
  def testMonixIO() = {
    MonixIO.run()
  }

}
