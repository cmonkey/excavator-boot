package org.excavator.boot.experiment

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}
import java.nio.file.Path
import java.util

object BitsetSort {

  def sortFile(inputFilePath:Path, outputFilePath:Path) = {
    val totalNumbers = 100000000
    val bitSet = new util.BitSet(totalNumbers)
    val reader = new BufferedReader(new FileReader(inputFilePath.toFile))
    var line = reader.readLine()
    while(null != line){
      val number = line.toInt
      bitSet.set(number)
      line = reader.readLine()
    }

    reader.close()

    val writer = new BufferedWriter(new FileWriter(outputFilePath.toFile))
    var index = 0
    var count = 0
    while(count < totalNumbers){
      index = bitSet.nextSetBit(index)
      if(index >= 0){
        writer.write(s"$index\n")
        count += 1
        index += 1
      }else{
        count = totalNumbers
      }
    }
    println(s"index is [${index}] count is [${count}]")
    writer.close()
    println("Sorting completed!")
  }

}
