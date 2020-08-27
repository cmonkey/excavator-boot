package org.excavator.boot.experiment.test

import java.sql.DriverManager

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ClickhouseTest {

  @Test
  @DisplayName("testSimpleQuery")
  def testSimpleQuery(): Unit = {
    val connection = DriverManager.getConnection("jdbc:clickhouse://127.0.0.1:9000")
    val statement = connection.createStatement()
    val rs = statement.executeQuery("select (number % 3 + 1) as n, sum(number) from numbers(10000000) group by n")
    while(rs.next()){
      println(rs.getInt(1) + "\t" + rs.getLong(2))
    }
  }
}
