package org.excavator.boot.config.test.service

import com.google.common.collect.Lists
import javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ConfigService {
  val logger = LoggerFactory.getLogger(classOf[ConfigService])

  val list = Lists.newArrayList[String]()

  @PostConstruct
  def init(): Unit = {
    list.add("foo")
    list.add("cmonkey")
  }

  def findAll() = {
    list
  }

  def getUserName(userName: String) = {
    list.stream().filter(p => p.equals(userName)).findFirst().get()
  }

  def addUserName(userName: String) = {
    list.add(userName)
  }

  def deleteUserName(userName: String) = {
    val iterator = list.iterator()

    while(iterator.hasNext){
      val elem = iterator.next()

      if(elem.equals(userName)){
        synchronized(iterator.remove())
      }
    }
  }

}
