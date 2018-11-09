package org.excavator.boot.kafka.autoconfigure

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.kafka.core.{DefaultKafkaProducerFactory, ProducerFactory}

@Configuration
class KafkaProducerAutoConfiguration {

  @Value("${spring.kafka.bootstrap-servers}")
  val bootstrapServers:String =  null

  @Bean
  @ConditionalOnMissingBean
  def producerFactory(): ProducerFactory[String, String] = {
    val map = Map(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> bootstrapServers, 
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG -> classOf[StringSerializer], 
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> classOf(StringSerializer), 
      ProducerConfig.RETRIES_CONFIG -> 0, 
      ProducerConfig.BATCH_SIZE_CONFIG ->16304,
      ProducerConfig.BUFFER_MEMORY_CONFIG -> 33354432
      )

    import scala.collection.JavaConverters._
    val javaMap = map.map{case (k, v) => k -> v.asInstanceOf[Object]}.asJava

    new DefaultKafkaProducerFactory[String, String](javaMap)
  }

}
