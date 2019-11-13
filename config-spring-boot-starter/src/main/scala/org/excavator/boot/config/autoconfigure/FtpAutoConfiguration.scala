package org.excavator.boot.config.autoconfigure

import javax.annotation.{PostConstruct, Resource}
import org.excavator.boot.config.properties.SftpProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = Array("sftp.enabled"), havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(value = Array(classOf[SftpProperties]))
class FtpAutoConfiguration {

  val log = LoggerFactory.getLogger(classOf[FtpAutoConfiguration])

  @Resource
  val sftpProperties: SftpProperties = null

  @PostConstruct
  def init() : Unit = {
    log.info("sftpProperties = [{}]", sftpProperties)
  }
}
