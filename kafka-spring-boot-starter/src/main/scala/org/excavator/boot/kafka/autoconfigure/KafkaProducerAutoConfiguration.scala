package org.excavator.boot.kafka.autoconfigure

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
    val map = Map(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> bootstrapServers, 
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG -> classOf[StringSerializer], 
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> classOf[StringSerializer], 
      ProducerConfig.RETRIES_CONFIG -> 0, 
      ProducerConfig.BATCH_SIZE_CONFIG ->16384,
      ProducerConfig.BUFFER_MEMORY_CONFIG -> 33554432
      )

    logger.info(s"init producerFactory in properties = $map")

    import scala.jdk.CollectionConverters._
    val javaMap = map.map{case (k, v) => k -> v.asInstanceOf[Object]}.asJava

    new DefaultKafkaProducerFactory[String, String](javaMap)
  }

  @Bean
  @ConditionalOnMissingBean
  def kafkaTemplate(): KafkaTemplate[String, String] = {
    logger.info("KafkaTemplate init")

    new KafkaTemplate[String, String](producerFactory())
  }

}
