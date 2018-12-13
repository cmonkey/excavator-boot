package org.excavator.boot.processor.autoconfigure

import java.util.Properties

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.core.env.{ConfigurableEnvironment, PropertiesPropertySource}
import org.springframework.stereotype.Component

@Component
class MyEnvironmentPostProcessor extends EnvironmentPostProcessor {
  val logger = LoggerFactory.getLogger(classOf[MyEnvironmentPostProcessor])

  override def postProcessEnvironment(configurableEnvironment: ConfigurableEnvironment, springApplication: SpringApplication): Unit = {

    val inputStream = classOf[MyEnvironmentPostProcessor].getClassLoader.getResourceAsStream("application.properties")
    //val inputStream = new FileInputStream()
    val properties = new Properties()
    properties.load(inputStream)

    val propertiesPropertySource = new PropertiesPropertySource("my", properties)

    configurableEnvironment.getPropertySources.addLast(propertiesPropertySource)
  }
}
