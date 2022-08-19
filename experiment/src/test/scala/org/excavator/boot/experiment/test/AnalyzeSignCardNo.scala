package org.excavator.boot.experiment.test


import org.excavator.boot.experiment.WriteHelper

import java.time.LocalDateTime
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import scala.io.Source
import scala.jdk.CollectionConverters.SeqHasAsJava

object AnalyzeSignCardNo extends App{

  val file = "/home/cmonkey/Downloads/sign_userids.csv"
  val out_file = "/home/cmonkey/Downloads/convert_sign_userIds.csv"
  def format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  def format_line = DateTimeFormatter.ofPattern("dd/M/yyyy HH:mm:ss")
  def format_line_ext = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss")

  val lines = Source.fromFile(file).getLines().toList

  val outLines = lines.map(line => {
    val split = line.split(",")
    split(1)
    val cardNo = split(0).replaceAll("\"", "")
    val time = split(1).replaceAll("\"", "")
    var now = LocalDateTime.now();
    try {
      now = LocalDateTime.parse(time, format_line)
    }catch {
      case ex:DateTimeParseException => {
        now = LocalDateTime.parse(time,format_line_ext)
      }
    }
    println(s"cardNo is $cardNo time is $time now is $now")
    println(now.format(format))
    val outTime = now.format(format)
    cardNo + "," + outTime
  })

  WriteHelper.write(out_file,outLines.asJava)

}