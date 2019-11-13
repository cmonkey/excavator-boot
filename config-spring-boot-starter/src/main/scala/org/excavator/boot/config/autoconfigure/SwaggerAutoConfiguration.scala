package org.excavator.boot.config.autoconfigure

import java.util.Collections

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.{ConditionalOnMissingBean, ConditionalOnProperty}
import org.springframework.context.annotation.{Bean, Configuration}
import springfox.documentation.builders.{PathSelectors, RequestHandlerSelectors}
import springfox.documentation.service.{ApiInfo, Contact}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = Array("swagger.enabled"), havingValue = "true", matchIfMissing = true)
class SwaggerAutoConfiguration {

  val logger = LoggerFactory.getLogger(classOf[SwaggerAutoConfiguration])

  @Value("${swagger.basePackage:''}")
  val basePackage: String = null

  @Bean
  @ConditionalOnMissingBean
  def productApi(): Docket = {
    logger.info("build docket in basePackage = [{}]", basePackage)
    new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage(basePackage))
      .paths(PathSelectors.any())
      .build()
      .apiInfo(metadata())
  }

  def metadata(): ApiInfo = {
    new ApiInfo("Restful 服务",
    "Rest api",
    "1.0",
    "Terams of service",
      new Contact("cmonkey", "cmonkey.github.com", "42.codemonkey at gmail.com"),
    "Apache License Version 2.0",
    "http://www.apache.org/licenses/LICENSE-2.0",
      Collections.emptyList())
  }

}
