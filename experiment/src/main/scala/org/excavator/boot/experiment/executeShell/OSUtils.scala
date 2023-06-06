package org.excavator.boot.experiment.executeShell

import cats.effect.IO
import org.apache.commons.text.StringEscapeUtils
import sun.nio.cs.UTF_8

import java.nio.file.Path

object OSUtils {

  def executeCommand(executable:Path, args:String*):IO[CommandResult] =
    IO.blocking{
      val commandArgs = executable.toAbsolutePath.toString +: args
      Runtime.getRuntime.exec(commandArgs.toArray)
    }.bracket{
      proc =>
        val collectStdout = IO.blocking{
          new String(proc.getInputStream.readAllBytes(), UTF_8)
        }
        val collectStderr = IO.blocking{
          new String(proc.getErrorStream.readAllBytes(), UTF_8)
        }

        val awaitReturnCode = IO.interruptible{
          proc.waitFor()
        }

        for{
          stdoutFiber <- collectStdout.start
          stderrFier <- collectStderr.start
          code <- awaitReturnCode
          stdout <- stdoutFiber.joinWithNever
          stderr <- stderrFier.joinWithNever
        }yield{
          CommandResult(code, stdout, stderr)
        }
    }{proc =>
      IO.blocking{
        println("Destroying process")
        proc.destroy()
      }
    }

  def executeShellCommand(command:String, args:String*):IO[CommandResult] =
    executeCommand(Path.of("/bin/sh"), "-c", (command +: args).map(StringEscapeUtils.escapeXSI).mkString(" "))

}
