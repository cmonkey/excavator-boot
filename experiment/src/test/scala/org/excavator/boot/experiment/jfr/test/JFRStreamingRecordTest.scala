package org.excavator.boot.experiment.jfr.test

import java.time.Duration

import org.excavator.boot.experiment.jfr.JFRStreamingRecord
import org.junit.jupiter.api.{Disabled, DisplayName}
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@Disabled
class JFRStreamingRecordTest {

  @DisplayName("testRecordingStream")
  @ParameterizedTest
  @ValueSource(ints = Array(10))
  def testRecordingStream(seconds: Int): Unit = {
    JFRStreamingRecord.recodingStream(Duration.ofSeconds(seconds))
  }

  @DisplayName("testRecordingConfiguration")
  @ParameterizedTest
  @ValueSource(ints = Array(10))
  def testRecordingConfiguration(seconds: Int): Unit = {
    JFRStreamingRecord.recordingConfiguration(Duration.ofSeconds(seconds))
  }

  @DisplayName("testOpenRepository")
  @ParameterizedTest
  @ValueSource(ints = Array(10))
  def testOpenRepository(seconds: Int): Unit = {
    JFRStreamingRecord.openRepository(Duration.ofSeconds(seconds))
  }
}
