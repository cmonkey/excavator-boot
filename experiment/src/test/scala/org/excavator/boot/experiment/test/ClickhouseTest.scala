package org.excavator.boot.experiment.test

import java.sql.{Connection, DriverManager}

import org.junit.jupiter.api.{BeforeAll, DisplayName, Test}

class ClickhouseTest {

  @Test
  @DisplayName("testSimpleQuery")
  def testSimpleQuery(): Unit = {
    val statement = ClickhouseTest.connection.createStatement()
    val rs = statement.executeQuery("select (number % 3 + 1) as n, sum(number) from numbers(10000000) group by n")
    while(rs.next()){
      println(rs.getInt(1) + "\t" + rs.getLong(2))
    }
  }

  @Test
  @DisplayName("testExecuteQuery")
  def testExecuteQuery(): Unit = {

  }
}

object ClickhouseTest{
  var connection: Connection = null
  @BeforeAll
  def init(): Unit = {
    connection = DriverManager.getConnection("jdbc:clickhouse://127.0.0.1:9000")
  }
}
