package org.excavator.boot.experiment.des

import java.io.{File, FileOutputStream}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path}
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

class TripleDataEncryptionAlgorithm {

  val secretKey = "9mng65v8jf4lxn93nabf981m".getBytes(StandardCharsets.UTF_8)

  val secretKeySpec = TripleDataEncryptionAlgorithmHelper.buildSecretKeySpec(secretKey, "TripleDES")

  val iv = "a76nb5h9".getBytes(StandardCharsets.UTF_8)

  val ivSpec = new IvParameterSpec(iv)

  def encryption(secretMessage:String) = {
    val encryptCipher = Cipher.getInstance("TripleDES/CBC/PKCSSPadding")
    encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)

    val secretMessagesBytes = secretMessage.getBytes(StandardCharsets.UTF_8)
    val encryptedMessageBytes = encryptCipher.doFinal(secretMessagesBytes)

    Base64.getEncoder.encodeToString(encryptedMessageBytes)
  }

  def decryptiong(secretMessage:String, encryptedMessage:String) = {
    val encryptedMessageBytes = encryptedMessage.getBytes(StandardCharsets.UTF_8)
    val decryptCipher = Cipher.getInstance("TripleDES/CBC/PKCSSPadding")
    val decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes)
    val decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8)

    secretMessage.equals(decryptedMessage)
  }

  def withFile(originalContent:String) = {
    val tempFile = Files.createTempFile("temp", "txt")
    val fileBytes = Files.readAllBytes(tempFile)

    val encryptCipher = Cipher.getInstance("TripleDES/CBC/PKCSSPadding")
    encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
    val encryptedFileBytes = encryptCipher.doFinal(fileBytes)

    val stream = new FileOutputStream(tempFile.toFile)
    try{
      stream.write(encryptedFileBytes)
    }catch{
      case ex:Exception => {
        ex.printStackTrace()
      }
    }finally{
      stream.close()
    }

  }

  def readFile(tempFile:Path) = {
    val encryptedFileBytes = Files.readAllBytes(tempFile)
    val decryptCipher = Cipher.getInstance("TripleDES/CBC/PKCSSPadding")
    decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
    val decryptedFileBytes = decryptCipher.doFinal(encryptedFileBytes)

    val stream = new FileOutputStream(tempFile.toFile)
    try{
      stream.write(decryptedFileBytes)
    }catch{
      case ex:Exception => {
        ex.printStackTrace()
      }
    }finally{
      stream.close()
    }
  }

}
