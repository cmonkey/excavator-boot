package org.excavator.boot.config.test.service

import java.io.{ByteArrayInputStream, File}
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

  val downloadFile = System.getProperty("java.io.tmpdir") + "/downloadDir/"

  @PostConstruct
  def init(): Unit = {
    var file = new File(uploadFile)

    if(!file.exists()){
      file.mkdir()
    }

    file = new File(downloadFile)
    if(!file.exists()){
      file.mkdir()
    }
  }

  def storeFile(multipartFile: MultipartFile): Unit = {

    val copyLocation = Paths.get(uploadFile + File.separator + StringUtils.cleanPath(multipartFile.getOriginalFilename))

    log.info("storeFile copyLocation = [{}]", copyLocation)

    Files.copy(multipartFile.getInputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING)
  }

  def readFile(fileName: String) = {
    val location = Paths.get(uploadFile + File.separator + StringUtils.cleanPath(fileName))

    Files.readAllBytes(location)
  }

  def storeDownloadFile(bytes: Array[Byte], fileName:String) : Unit = {
    val copyLocation = Paths.get(downloadFile + File.separator + StringUtils.cleanPath(fileName))
    val byteInputStream = new ByteArrayInputStream(bytes)
    Files.copy(byteInputStream, copyLocation, StandardCopyOption.REPLACE_EXISTING)
  }
}
