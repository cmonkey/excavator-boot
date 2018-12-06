package org.excavator.boot.quartz.test

import org.quartz.{DisallowConcurrentExecution, Job, JobExecutionContext}
import org.slf4j.LoggerFactory

@DisallowConcurrentExecution
class QuartzBatchJobBean extends Job{
  val logger = LoggerFactory.getLogger(classOf[QuartzBatchJobBean])

  def customJob() = {
    QuartzConstants.msg
  }

  override def execute(jobExecutionContext: JobExecutionContext): Unit = {
    val r = customJob()

    jobExecutionContext.setResult(r)
  }
}
