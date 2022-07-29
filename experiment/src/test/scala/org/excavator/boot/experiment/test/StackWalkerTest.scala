package org.excavator.boot.experiment.test

import org.excavator.boot.experiment.stackwalking.StackWalkerDemo
import org.junit.jupiter.api.Test

class StackWalkerTest {

  @Test
  def giveStalkWalker_whenWalkingTheStack_thenShowStackFrames():Unit = {
    val demo = new StackWalkerDemo
    demo.methodOne()
  }

  @Test
  def giveStalkWalker_whenInvokingFindCaller_thenFindCallingClass():Unit = {
    val demo = new StackWalkerDemo
    demo.findCaller()
  }

}
