package org.excavator.boot.experiment.jfr.test

import java.time.Duration

import org.excavator.boot.experiment.jfr.JFRStreamingRecord
import org.junit.jupiter.api.{DisplayName, Test}
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class JFRStreamingRecordTest {

  @DisplayName("testRecordingStream")
  @ParameterizedTest
  @ValueSource(ints = Array(10))
  def testRecordingStream(seconds: Int): Unit = {
    JFRStreamingRecord.recodingStream(Duration.ofSeconds(seconds))
  }

  @DisplayName("testRecordingConfiguration")
  @ParameterizedTest
  @ValueSource(inits = Array(10))
  def testRecordingConfiguration(): Unit = {
    JFRStreamingRecord.recordingConfiguration()
  }

  @DisplayName("testOpenRepository")
  @ParameterizedTest
  @ValueSource(inits = Array(10))
  def testOpenRepository(): Unit = {
    JFRStreamingRecord.openRepository()
  }
}
