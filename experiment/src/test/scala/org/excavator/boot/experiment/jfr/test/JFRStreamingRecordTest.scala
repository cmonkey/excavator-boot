package org.excavator.boot.experiment.jfr.test

import java.util.concurrent.TimeUnit

import org.excavator.boot.experiment.jfr.JFRStreamingRecord
import org.junit.jupiter.api.{DisplayName, Test}

class JFRStreamingRecordTest {

  @Test
  @DisplayName("testRecordingStream")
  def testRecordingStream(): Unit = {
    JFRStreamingRecord.recodingStream()
    TimeUnit.SECONDS.sleep(10)
  }

}
