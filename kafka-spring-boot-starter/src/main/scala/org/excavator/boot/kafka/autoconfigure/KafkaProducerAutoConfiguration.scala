package org.excavator.boot.kafka.autoconfigure

import java.util

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.kafka.core.{DefaultKafkaProducerFactory, KafkaTemplate, ProducerFactory}

@Configuration
class KafkaProducerAutoConfiguration {
  val logger = LoggerFactory.getLogger(classOf[KafkaProducerAutoConfiguration])

  @Value("${spring.kafka.bootstrap-servers}")
  val bootstrapServers:String =  null

  @Bean
  @ConditionalOnMissingBean
  def producerFactory(): ProducerFactory[String, String] = {
    val map = new util.HashMap[String, Any]()
    map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    map.put(ProducerConfig.RETRIES_CONFIG , 0)
    map.put(ProducerConfig.BATCH_SIZE_CONFIG ,16384)
    map.put(ProducerConfig.BUFFER_MEMORY_CONFIG , 33554432)

    logger.info(s"init producerFactory in properties = $map")

    new DefaultKafkaProducerFactory[String, String](map)
  }

  @Bean
  @ConditionalOnMissingBean
  def kafkaTemplate(): KafkaTemplate[String, String] = {
    logger.info("KafkaTemplate init")

    new KafkaTemplate[String, String](producerFactory())
  }

}
