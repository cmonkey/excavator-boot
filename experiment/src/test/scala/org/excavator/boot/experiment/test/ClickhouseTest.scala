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

    val stmt = ClickhouseTest.connection.createStatement()
    stmt.executeQuery("drop table if exists test_example")
    stmt.executeQuery("create table test_example(day default toDate(toDateTime(timestamp)), timestamp UInt64, name String, impressions UInt32) Engine=MergeTree(day, (timestamp, name), 8179)")
    stmt.executeQuery("insert into test_example(timestamp, name, impressions) values(1598511393098, 'cmonkey', 200)")
    stmt.executeQuery("alter table test_example add column custs Float32")

    val pstmt = ClickhouseTest.connection.prepareStatement("insert into test_example(timestamp , name, impressions, custs) values(?, ?, ?,?)")

    var batchSize = 0
    for(i <- 0 until 10000){
      val timestamp = System.currentTimeMillis()
      val name = "cmonkey-" + i
      val impressions = i
      val custs = i.toFloat
      pstmt.setLong(1, timestamp)
      pstmt.setString(2, name)
      pstmt.setInt(3, impressions)
      pstmt.setFloat(4, custs)

      pstmt.addBatch()
      if(batchSize == 10000){
        pstmt.executeBatch()
        batchSize = 0
      }

      batchSize += 1
    }

    pstmt.executeBatch()
  }
}

object ClickhouseTest{
  var connection: Connection = null
  @BeforeAll
  def init(): Unit = {
    connection = DriverManager.getConnection("jdbc:clickhouse://127.0.0.1:9000")
  }
}
