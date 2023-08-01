package org.excavator.boot.experiment

import org.slf4j.LoggerFactory

import java.io.{BufferedWriter, FileWriter}
import java.nio.file.Path
import scala.util.Random

object GenerationFile {

  def generateRandomNumbers(outputFilePath:Path) = {
    println(s"outputFilePath is [${outputFilePath}]")
    val totalNumbers = 100000000
    val digits = 8

    val random = new Random()
    val write = new BufferedWriter(new FileWriter(outputFilePath.toFile))
    for(_ <- 1 to totalNumbers){
      val number = random.nextInt(math.pow(10, digits).toInt)
      val formattedNumber = f"$number%08d"
      write.write(s"$formattedNumber\n")
    }
    write.close()
  }

}
