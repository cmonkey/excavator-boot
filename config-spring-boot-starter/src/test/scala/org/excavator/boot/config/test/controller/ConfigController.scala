package org.excavator.boot.config.test.controller

import io.swagger.annotations.{Api, ApiOperation, ApiResponse, ApiResponses}
import org.excavator.boot.config.test.service.ConfigService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/v1"))
@Api(value = "Config info Api", description = "Config Service")
class ConfigController(configService: ConfigService) {
  val log = LoggerFactory.getLogger(classOf[ConfigController])

  @ApiOperation(value = "首页", response = classOf[ResponseEntity[String]], notes = "首页详情")
  @ApiResponses(value = Array(
    new ApiResponse(code = 200, message = "成功"),
    new ApiResponse(code = 10001, message = "参数为空"),
    new ApiResponse(code = 10003, message = "系统异常")
  )
  )
  @GetMapping(Array("/users"))
  def findAll(): ResponseEntity[java.util.List[String]] = {
    log.info("home running by time = [{}]", System.nanoTime())
    ResponseEntity.ok(configService.findAll())
  }

  @GetMapping(Array("/users/{userName}"))
  def getUserName(@PathVariable("userName") userName: String) = {
    ResponseEntity.ok(configService.getUserName(userName))
  }

}
