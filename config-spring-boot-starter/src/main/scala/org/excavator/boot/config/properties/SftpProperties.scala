package org.excavator.boot.config.properties

import com.google.common.collect.Maps
import org.excavator.boot.common.utils.SftpItem
import org.apache.commons.lang3.builder.{ToStringBuilder, ToStringStyle}
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "sftp")
class SftpProperties {
  var items = Maps.newHashMap[String, SftpItem]()

  def getItems(): java.util.Map[String, SftpItem] = {
    items
  }

  def setItems(elems: java.util.HashMap[String, SftpItem]): Unit = {
    items = elems
  }

  override def toString: String = {
    ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
  }
}
