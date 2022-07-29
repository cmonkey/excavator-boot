package org.excavator.boot.experiment.stackwalking

import java.lang.StackWalker.StackFrame
import java.util
import java.util.concurrent.TimeUnit
import java.util.stream.{Collectors, Stream => JStream}
import java.util.{List => JList}

class StackWalkerDemo {

  def methodOne(): Unit = {
    methodTwo()
  }

  def methodTwo(): Unit = {
    methodThree()
  }

  def methodThree(): Unit = {
    val stackTrace = StackWalker.getInstance()
      .walk(walkExample)
    stackTrace.forEach(trace => {
      println(s"stackTrace = $trace")
    })

    val stackTrace2 = StackWalker.getInstance()
      .walk(walkExample2)
    stackTrace2.forEach(trace => {
      println(s"stackTrace2 = $trace")
    })

    val stackTrace3 = StackWalker.getInstance()
      .walk(walkExample3)
    println(s"stackTrace3 = $stackTrace3")

    val stackTrace1ByCapturingTheReflectionFrames = StackWalker.getInstance(StackWalker.Option.SHOW_REFLECT_FRAMES)
      .walk(walkExample)
    stackTrace1ByCapturingTheReflectionFrames.forEach(trace => {
      println(s"stackTrace1ByCapturingTheReflectionFrames = $trace")
    })

    val r:Runnable = () => {
      val stackTraceShowHiddenFrames = StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES)
        .walk(walkExample)
      stackTraceShowHiddenFrames.forEach(trace => {
        println(s"stack show hidden frames = $trace")
      })
    }

    r.run()
  }

  def walkExample(stackFrameStream: JStream[StackFrame]) : JList[StackFrame] = {
    stackFrameStream.collect(Collectors.toList[StackFrame])
  }

  def walkExample2(stackFrameStream:JStream[StackFrame]): JList[StackFrame] = {
    stackFrameStream.filter(f => {
      f.getClassName.contains("org.excavator.boot.experiment")
    }).toList
  }

  def walkExample3(stackFrameStream:JStream[StackFrame]): String = {
    stackFrameStream.filter(frame => {
      frame.getClassName.contains("org.excavator.boot.experiment") &&
        frame.getClassName.endsWith("Test")
    }).findFirst()
      .map(f => f.getClassName + "#" + f.getMethodName + ",Line " + f.getLineNumber)
      .orElse("Unknown caller")
  }

  def findCaller():Unit = {
    val caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
      .getCallerClass
    println(caller.getCanonicalName)
  }

}
