package org.excavator.boot.instrumentation.application

import java.io.File
import java.util.Optional

import com.sun.tools.attach.VirtualMachine
import org.slf4j.LoggerFactory

class AgentLoader {

  val logger = LoggerFactory.getLogger(classOf[AgentLoader])

  def run(args: Array[String]): Unit = {
    val agentFilePath = "/tmp/instrumentation-2.0.0-SNAPSHOT-jar-with-dependencies.jar"
    val applicationName = "AgentLoader"

    val jvmProcessOpt = Optional.ofNullable(VirtualMachine.list().stream().filter(jvm => {
      logger.info("jvm : {}", jvm.displayName())
      jvm.displayName().contains(applicationName)
    }).findFirst().get().id())

    if(!jvmProcessOpt.isPresent){
      logger.error("target Application not found")
    }else{
      val agentFile = new File(agentFilePath)

      try{
        val jvmPid = jvmProcessOpt.get()
        logger.info(s"Attaching to target jvm with pid [{${jvmPid}}]")
        val jvm = VirtualMachine.attach(jvmPid)
        jvm.loadAgent(agentFile.getAbsolutePath)
        jvm.detach()
        MyAtm.withdrawMoney(50)
        logger.info("attached to target jvm and loaded java agent successfully")
      }catch{
        case e:Throwable => throw new RuntimeException(e)
      }
    }
  }
}
