package org.excavator.boot.experiment

import cats.effect.{ExitCode, IO}
import org.excavator.boot.experiment.executeShell.{OSUtils, OSUtilsScala}
import org.junit.jupiter.api.{DisplayName, Test}

import scala.concurrent.duration.DurationInt

class OSUtilsTests{

  @Test
  @DisplayName("test os Utils")
  def testOSUtils():Unit = {

    for{
      r <- OSUtilsScala.executeShellCommand("ls", "-alh").timeout(3.seconds)
      _ <- IO.print(r.stdout)
      _ <- IO.print(r.stderr)
    }yield ExitCode(r.exitCode)

  }

}
