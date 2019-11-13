package org.excavator.boot.config.test.controller


import io.swagger.annotations.{Api, ApiOperation, ApiResponse, ApiResponses}
import org.excavator.boot.config.test.service.{ConfigService, FileService}
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, PostMapping, PutMapping, RequestBody, RequestHeader, RequestMapping, RequestParam, RestController}
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(Array("/v1"))
@Api(value = "Config info Api", description = "Config Service")
class ConfigController(configService: ConfigService, fileService: FileService) {
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

  @PostMapping(Array("/users"))
  def addUserName(@RequestParam("userName") userName: String) = {
    ResponseEntity.ok(configService.addUserName(userName))
  }

  @PostMapping(Array("/users/body"))
  def addUserNameByBody(@RequestBody msg:java.util.Map[String, String]) = {
    println(s"body = ${msg}")
    msg.forEach((k, v) => configService.addUserName(v))
    ResponseEntity.ok(true)
  }



  @PostMapping(Array("/upload"))
  def upload(@RequestParam("userName") userName: String,
             @RequestParam("files") multipartFile: Array[MultipartFile]) = {

    println(s"userName = ${userName} and multipartFile size = ${multipartFile.size}")

    multipartFile.foreach(file => {
      fileService.storeFile(file)
    })

    ResponseEntity.ok(configService.addUserName(userName))

  }

  @GetMapping(Array("/download/{filename}"))
  def download(@PathVariable("filename") filename: String) = {
    ResponseEntity.ok(fileService.readFile(filename))
  }

  @GetMapping(Array("/headers/{userName}"))
  def getHeader(@PathVariable("userName") userName: String, @RequestHeader("token") token: String) = {
    println(s"userName = ${userName} token = ${token}")

    ResponseEntity.ok(token)
  }

  @PutMapping(Array("/users/{originName}/{newName}"))
  def updateUserName(@PathVariable("originName") originName: String, @PathVariable("newName") newName: String) = {
    configService.update(originName, newName)
    ResponseEntity.ok(newName)
  }

}
