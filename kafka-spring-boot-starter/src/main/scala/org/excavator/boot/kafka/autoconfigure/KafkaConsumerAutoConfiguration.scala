package org.excavator.boot.kafka.autoconfigure

import java.util

import org.apache.commons.lang3.StringUtils
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.{ConcurrentKafkaListenerContainerFactory, KafkaListenerContainerFactory}
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer

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

    val map = new util.HashMap[String, Any]()
    map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG , bootstrapServers)
    map.put(ConsumerConfig.GROUP_ID_CONFIG , groupName)
    map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG , classOf[StringDeserializer])
    map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG , classOf[StringDeserializer])
    map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG ,  "earliest")
    map.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG , true)
    map.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG , "100")

    logger.info(s"init consumerFactory param = $map")

    new DefaultKafkaConsumerFactory[String, String](map)
  }

  @Bean
  @ConditionalOnMissingBean
  def kafkaListenerContainerFactory():KafkaListenerContainerFactory[ConcurrentMessageListenerContainer[String, String]] = {
    val concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory[String, String]()

    concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory())
    concurrentKafkaListenerContainerFactory.getContainerProperties().setPollTimeout(1500L)

    logger.info(s"init concurrentKafkaListenerContainerFactory = ${concurrentKafkaListenerContainerFactory}")

    concurrentKafkaListenerContainerFactory
  }

}
