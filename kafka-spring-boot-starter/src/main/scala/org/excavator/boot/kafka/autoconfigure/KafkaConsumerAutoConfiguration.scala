package org.excavator.boot.kafka.autoconfigure

import org.apache.commons.lang3.StringUtils
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
@EnableKafka
class KafkaConsumerAutoConfiguration {

  val logger = LoggerFactory.getLogger(classOf[KafkaConsumerAutoConfiguration])

  @Value("${spring.kafka.bootstrap-servers}")
  val bootstrapServers: String = null

  @Value("${spring.kafka.consumer.group-id:}")
  val groupId:String = null

  @Value("${spring.application.name:defaultGroup}")
  val applicationName:String = null

  @Bean
  @ConditionalOnMissingBean
  def consumerFactory() = {
    val groupName = if(StringUtils.isBlank(groupId)) applicationName else groupId

    val map = Map(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> bootstrapServers, 
      ConsumerConfig.GROUP_ID_CONFIG -> groupName, 
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG ->  "earliest",
      ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> true,
      ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG -> "100"
      )

    logger.info(s"init consumerFactory param = ${map}")

    import scala.collection.JavaConverters._

    val javaMap = map.map{case (k, v) => k -> v.asInstanceOf[Object]}.asJava

    new DefaultKafkaConsumerFactory[String, String](javaMap)
  }

}
