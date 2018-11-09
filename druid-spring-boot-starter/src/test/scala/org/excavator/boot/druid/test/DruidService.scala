package org.excavator.boot.druid.test

import javax.annotation.Resource
import javax.sql.DataSource
import org.springframework.stereotype.Service

@Service
class DruidService{

  @Resource
  val dataSource: DataSource = null


  def getId(): Long = {

    val sql = "select id from orderRecord"
    val rs = dataSource.getConnection.prepareStatement(sql).executeQuery()
    var id = 0L
    while(rs.next()){
      id = rs.getLong("id")
    }

    id
  }
}
