package org.excavator.boot.config.test.service

import java.io.File
import java.nio.file.{Files, Paths, StandardCopyOption}

import javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile

@Service
class FileService {
  val log = LoggerFactory.getLogger(classOf[FileService])
  val uploadFile = System.getProperty("java.io.tmpdir") + "/uploadDir/"

  @PostConstruct
  def init(): Unit = {
    val file = new File(uploadFile)

    if(!file.exists()){
      file.mkdir()
    }
  }

  def storeFile(multipartFile: MultipartFile): Unit = {

    val copyLocation = Paths.get(uploadFile + File.separator + StringUtils.cleanPath(multipartFile.getOriginalFilename))

    Files.copy(multipartFile.getInputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING)
  }

  def readFile(fileName: String) = {
    val location = Paths.get(uploadFile + File.separator + StringUtils.cleanPath(fileName))

    Files.readAllBytes(location)
  }
}
