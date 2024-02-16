package headsetTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import headset.MindWaveMobile2;
import headset.events.AttentionData;
import headset.events.MeditationData;
import headset.events.headsetStateChange.HeadsetStateChangeEvent;
import headset.events.headsetStateChange.HeadsetStateTypes;
import headset.events.nskAlgo.algoAttention.AlgoAttentionEvent;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerData;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerEvent;
import headset.events.nskAlgo.algoBlink.AlgoBlinkData;
import headset.events.nskAlgo.algoBlink.AlgoBlinkEvent;
import headset.events.nskAlgo.algoMeditation.AlgoMeditationEvent;
import headset.events.nskAlgo.algoSignalQuality.AlgoSignalQualityData;
import headset.events.nskAlgo.algoSignalQuality.AlgoSignalQualityEvent;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeEvent;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeReasons;
import headset.events.nskAlgo.algoStateChange.AlgoStateTypes;
import headset.events.stream.streamAttention.StreamAttentionEvent;
import headset.events.stream.streamEEG.StreamEEGData;
import headset.events.stream.streamEEG.StreamEEGDataEvent;
import headset.events.stream.streamMeditation.StreamMeditationEvent;
import headset.events.stream.streamRaw.StreamRawData;
import headset.events.stream.streamRaw.StreamRawDataEvent;
import headsetTest.eventsTest.OtherEventMockListener;
import headsetTest.eventsTest.TestEvent;
import headsetTest.eventsTest.headsetStateChange.HeadsetStateEventMockListener;
import headsetTest.eventsTest.nskAlgo.AlgoAttentionMockEventListener;
import headsetTest.eventsTest.nskAlgo.AlgoBandPowerMockEventListener;
import headsetTest.eventsTest.nskAlgo.AlgoBlinkMockEventListener;
import headsetTest.eventsTest.nskAlgo.AlgoMeditationMockEventListener;
import headsetTest.eventsTest.nskAlgo.AlgoSignalQualityMockEventListener;
import headsetTest.eventsTest.nskAlgo.AlgoStateMockEventListener;
import headsetTest.eventsTest.stream.StreamAttentionMockEventListener;
import headsetTest.eventsTest.stream.StreamEEGDataMockEventListener;
import headsetTest.eventsTest.stream.StreamMeditationMockEventListener;
import headsetTest.eventsTest.stream.StreamRawMockEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MindWaveMobile2Test {


  private final MindWaveMobile2 mindWaveMobile2 = new MindWaveMobile2();

  //TODO:AlgoBlinkMockEventListener test
  @Test
  public void test1() {
    Log.e("Test1", "HI");
    AlgoBlinkMockEventListener blinkEventMockListener = new AlgoBlinkMockEventListener();
    this.mindWaveMobile2.addEventListener(blinkEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(blinkEventMockListener)).as(
        "Blink Event handler should have this blink event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(blinkEventMockListener);
  }

  @Test
  public void test2() {
    AlgoBlinkMockEventListener blinkEventMockListener = new AlgoBlinkMockEventListener();
    this.mindWaveMobile2.addEventListener(blinkEventMockListener);
    this.mindWaveMobile2.removeEventListener(blinkEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(blinkEventMockListener)).as(
        "Blink event handler should not have this blink event listener").isFalse();
  }

  @Test
  public void test3() {
    AlgoBlinkMockEventListener blinkEventMockListener1 = new AlgoBlinkMockEventListener();
    this.mindWaveMobile2.addEventListener(blinkEventMockListener1);

    this.mindWaveMobile2
        .fireEvent(new AlgoBlinkEvent(this, new AlgoBlinkData(20)));
    assertThat(blinkEventMockListener1.getBlinkCount()).as(
        "Blink event listener should have blink count equal to 1").isEqualTo(1);
    assertThat(blinkEventMockListener1.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 20").isEqualTo(20);

    this.mindWaveMobile2.fireEvent(new AlgoBlinkEvent(this, new AlgoBlinkData(30)));
    assertThat(blinkEventMockListener1.getBlinkCount()).as(
        "Blink event listener should have the blink count equal to 2").isEqualTo(2);
    assertThat(blinkEventMockListener1.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 30").isEqualTo(30);

    AlgoBlinkMockEventListener blinkEventMockListener2 = new AlgoBlinkMockEventListener();
    this.mindWaveMobile2.addEventListener(blinkEventMockListener2);

    this.mindWaveMobile2.fireEvent(new AlgoBlinkEvent(this, new AlgoBlinkData(20)));
    assertThat(blinkEventMockListener2.getBlinkCount()).as(
        "Blink event listener should have blink count equal to 1").isEqualTo(1);
    assertThat(blinkEventMockListener2.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 20").isEqualTo(20);

    this.mindWaveMobile2.fireEvent(new AlgoBlinkEvent(this, new AlgoBlinkData(30)));
    assertThat(blinkEventMockListener2.getBlinkCount()).as(
        "Blink event listener should have the blink count equal to 2").isEqualTo(2);
    assertThat(blinkEventMockListener2.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 30").isEqualTo(30);

  }

  //TODO:AlgoAttentionMockEventListener test
  @Test
  public void test4() {
    AlgoAttentionMockEventListener algoAttentionMockEventListener = new AlgoAttentionMockEventListener();
    this.mindWaveMobile2.addEventListener(algoAttentionMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(algoAttentionMockEventListener)).as(
        "Attention Event handler should have this attention event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(algoAttentionMockEventListener);
  }

  @Test
  public void test5() {
    AlgoAttentionMockEventListener algoAttentionMockEventListener = new AlgoAttentionMockEventListener();
    this.mindWaveMobile2.addEventListener(algoAttentionMockEventListener);
    this.mindWaveMobile2.removeEventListener(algoAttentionMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(algoAttentionMockEventListener)).as(
        "Attention event handler should not have this attention event listener").isFalse();
  }

  @Test
  public void test6() {
    AlgoAttentionMockEventListener algoAttentionMockEventListener = new AlgoAttentionMockEventListener();
    this.mindWaveMobile2.addEventListener(algoAttentionMockEventListener);

    this.mindWaveMobile2.fireEvent(new AlgoAttentionEvent(this, new AttentionData(20)));
    assertThat(algoAttentionMockEventListener.getAttentionCount()).as(
        "Attention event listener should have attention count equal to 1").isEqualTo(1);
    assertThat(algoAttentionMockEventListener.getLastAttentionValue()).as(
        "Attention event listener should have last attention value equal to 20").isEqualTo(20);

    this.mindWaveMobile2.fireEvent(new AlgoAttentionEvent(this, new AttentionData(30)));
    assertThat(algoAttentionMockEventListener.getAttentionCount()).as(
        "Attention event listener should have the attention count equal to 2").isEqualTo(2);
    assertThat(algoAttentionMockEventListener.getLastAttentionValue()).as(
        "Attention event listener should have last attention value equal to 30").isEqualTo(30);
  }

  //TODO:AlgoMeditationMockEventListener test
  @Test
  public void test7() {
    AlgoMeditationMockEventListener algoMeditationMockEventListener = new AlgoMeditationMockEventListener();
    this.mindWaveMobile2.addEventListener(algoMeditationMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(algoMeditationMockEventListener)).as(
        "Meditation Event handler should have this meditation event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(algoMeditationMockEventListener);
  }

  @Test
  public void test8() {
    AlgoMeditationMockEventListener algoMeditationMockEventListener = new AlgoMeditationMockEventListener();
    this.mindWaveMobile2.addEventListener(algoMeditationMockEventListener);
    this.mindWaveMobile2.removeEventListener(algoMeditationMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(algoMeditationMockEventListener)).as(
        "Meditation event handler should not have this meditation event listener").isFalse();
  }

  @Test
  public void test9() {
    AlgoMeditationMockEventListener algoMeditationMockEventListener = new AlgoMeditationMockEventListener();
    this.mindWaveMobile2.addEventListener(algoMeditationMockEventListener);

    this.mindWaveMobile2.fireEvent(new AlgoMeditationEvent(this, new MeditationData(20)));
    assertThat(algoMeditationMockEventListener.getMeditationCount()).as(
        "Meditation event listener should have meditation count equal to 1").isEqualTo(1);
    assertThat(algoMeditationMockEventListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 20").isEqualTo(20);

    this.mindWaveMobile2.fireEvent(new AlgoMeditationEvent(this, new MeditationData(30)));
    assertThat(algoMeditationMockEventListener.getMeditationCount()).as(
        "Meditation event listener should have the meditation count equal to 2").isEqualTo(2);
    assertThat(algoMeditationMockEventListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 30").isEqualTo(30);
  }

  //TODO:AlgoSignalQualityMockEventListener test
  @Test
  public void test13() {
    AlgoSignalQualityMockEventListener algoSignalEventMockListener = new AlgoSignalQualityMockEventListener();
    this.mindWaveMobile2.addEventListener(algoSignalEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(algoSignalEventMockListener)).as(
        "SignalQuality Event handler should have this signal event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(algoSignalEventMockListener);
  }

  @Test
  public void test14() {
    AlgoSignalQualityMockEventListener algoSignalEventMockListener = new AlgoSignalQualityMockEventListener();
    this.mindWaveMobile2.addEventListener(algoSignalEventMockListener);
    this.mindWaveMobile2.removeEventListener(algoSignalEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(algoSignalEventMockListener)).as(
        "SignalQuality event handler should not have this signal event listener").isFalse();
  }

  @Test
  public void test15() {
    AlgoSignalQualityMockEventListener algoSignalEventMockListener = new AlgoSignalQualityMockEventListener();
    this.mindWaveMobile2.addEventListener(algoSignalEventMockListener);

    this.mindWaveMobile2.fireEvent(new AlgoSignalQualityEvent(this, new AlgoSignalQualityData(20)));
    assertThat(algoSignalEventMockListener.getSignalQualityCount()).as(
        "SignalQuality event listener should have signal quality count equal to 1").isEqualTo(1);
    assertThat(algoSignalEventMockListener.getLastSignalQuality()).as(
            "SignalQuality event listener should have last signal quality value equal to 20")
        .isEqualTo(20);

    this.mindWaveMobile2.fireEvent(new AlgoSignalQualityEvent(this, new AlgoSignalQualityData(30)));
    assertThat(algoSignalEventMockListener.getSignalQualityCount()).as(
            "SignalQuality event listener should have the signal quality count equal to 2")
        .isEqualTo(2);
    assertThat(algoSignalEventMockListener.getLastSignalQuality()).as(
            "SignalQuality event listener should have last signal quality value equal to 30")
        .isEqualTo(30);
  }

  //TODO:AlgoBandPowerMockEventListener test
  @Test
  public void test16() {
    AlgoBandPowerMockEventListener algoBandPowerEventMockListener = new AlgoBandPowerMockEventListener();
    this.mindWaveMobile2.addEventListener(algoBandPowerEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(algoBandPowerEventMockListener)).as(
        "BandPower Event handler should have this band power event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(algoBandPowerEventMockListener);
  }

  @Test
  public void test17() {
    AlgoBandPowerMockEventListener algoBandPowerEventMockListener = new AlgoBandPowerMockEventListener();
    this.mindWaveMobile2.addEventListener(algoBandPowerEventMockListener);
    this.mindWaveMobile2.removeEventListener(algoBandPowerEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(algoBandPowerEventMockListener)).as(
        "BandPower event handler should not have this band power event listener").isFalse();
  }

  @Test
  public void test18() {
    AlgoBandPowerMockEventListener algoBandPowerEventMockListener = new AlgoBandPowerMockEventListener();
    this.mindWaveMobile2.addEventListener(algoBandPowerEventMockListener);

    this.mindWaveMobile2.fireEvent(
        new AlgoBandPowerEvent(this, new AlgoBandPowerData(20, 30, 40, 50, 60)));
    assertThat(algoBandPowerEventMockListener.getBandPowerCount()).as(
        "BandPower event listener should have band power count equal to 1").isEqualTo(1);
    assertThat(algoBandPowerEventMockListener.getLastBandPowerValue()).as(
            "BandPower event listener should have last band power data equal to 20, 30, 40, 50, 60")
        .isEqualTo(new float[]{20, 30, 40, 50, 60});

    this.mindWaveMobile2.fireEvent(
        new AlgoBandPowerEvent(this, new AlgoBandPowerData(30, 40, 50, 60, 70)));
    assertThat(algoBandPowerEventMockListener.getBandPowerCount()).as(
        "BandPower event listener should have the band power count equal to 2").isEqualTo(2);
    assertThat(algoBandPowerEventMockListener.getLastBandPowerValue()).as(
            "BandPower event listener should have last band power data equal to 30, 40, 50, 60, 70")
        .isEqualTo(new float[]{30, 40, 50, 60, 70});
  }

  //TODO:AlgoStateMockEventListener test
  @Test
  public void test25() {
    AlgoStateMockEventListener algoStateMockEventListener = new AlgoStateMockEventListener();
    this.mindWaveMobile2.addEventListener(algoStateMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(algoStateMockEventListener)).as(
        "AlgoState Event handler should have this algo state event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(algoStateMockEventListener);
  }

  @Test
  public void test26() {
    AlgoStateMockEventListener algoStateMockEventListener = new AlgoStateMockEventListener();
    this.mindWaveMobile2.addEventListener(algoStateMockEventListener);
    this.mindWaveMobile2.removeEventListener(algoStateMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(algoStateMockEventListener)).as(
        "AlgoState event handler should not have this algo state event listener").isFalse();
  }

  @Test
  public void test27() {
    AlgoStateMockEventListener algoStateMockEventListener = new AlgoStateMockEventListener();
    this.mindWaveMobile2.addEventListener(algoStateMockEventListener);

    this.mindWaveMobile2.fireEvent(
        new AlgoStateChangeEvent(this, AlgoStateTypes.TEST.ordinal(),
            AlgoStateChangeReasons.TEST.ordinal()));
    assertThat(algoStateMockEventListener.getStateChangeCount()).as(
        "AlgoState event listener should have algo state count equal to 1").isEqualTo(1);
    assertThat(algoStateMockEventListener.getLastState()).as(
            "AlgoState event listener should have last algo state equal to TEST")
        .isEqualTo(AlgoStateTypes.TEST);
    assertThat(algoStateMockEventListener.getLastReason()).as(
            "AlgoState event listener should have last algo state change reason equal to TEST")
        .isEqualTo(AlgoStateChangeReasons.TEST);
  }

  //TODO:StreamAttentionMockEventListener test
  @Test
  public void test28() {
    StreamAttentionMockEventListener streamAttentionMockEventListener = new StreamAttentionMockEventListener();
    this.mindWaveMobile2.addEventListener(streamAttentionMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(streamAttentionMockEventListener)).as(
        "Raw Event handler should have this raw event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(streamAttentionMockEventListener);
  }

  @Test
  public void test29() {
    StreamAttentionMockEventListener streamAttentionMockEventListener = new StreamAttentionMockEventListener();
    this.mindWaveMobile2.addEventListener(streamAttentionMockEventListener);
    this.mindWaveMobile2.removeEventListener(streamAttentionMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(streamAttentionMockEventListener)).as(
        "Raw event handler should not have this raw event listener").isFalse();
  }

  @Test
  public void test30() {
    StreamAttentionMockEventListener streamAttentionMockEventListener = new StreamAttentionMockEventListener();
    this.mindWaveMobile2.addEventListener(streamAttentionMockEventListener);

    this.mindWaveMobile2.fireEvent(
        new StreamAttentionEvent(this, new AttentionData(20)));
    assertThat(streamAttentionMockEventListener.getAttentionCount()).as(
        "Raw event listener should have raw count equal to 1").isEqualTo(1);
    assertThat(streamAttentionMockEventListener.getLastAttentionValue()).as(
        "Raw event listener should have last raw value equal to 20").isEqualTo(20);

    this.mindWaveMobile2.fireEvent(
        new StreamAttentionEvent(this, new AttentionData(30)));
    assertThat(streamAttentionMockEventListener.getAttentionCount()).as(
        "Raw event listener should have the raw count equal to 2").isEqualTo(2);
    assertThat(streamAttentionMockEventListener.getLastAttentionValue()).as(
        "Raw event listener should have last raw value equal to 30").isEqualTo(30);
  }

  //TODO:StreamMeditationMockEventListener test
  @Test
  public void test31() {
    StreamMeditationMockEventListener streamMeditationMockEventListener = new StreamMeditationMockEventListener();
    this.mindWaveMobile2.addEventListener(streamMeditationMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(streamMeditationMockEventListener)).as(
        "Meditation Event handler should have this meditation event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(streamMeditationMockEventListener);
  }

  @Test
  public void test32() {
    StreamMeditationMockEventListener streamMeditationMockEventListener = new StreamMeditationMockEventListener();
    this.mindWaveMobile2.addEventListener(streamMeditationMockEventListener);
    this.mindWaveMobile2.removeEventListener(streamMeditationMockEventListener);
    assertThat(this.mindWaveMobile2.containsListener(streamMeditationMockEventListener)).as(
        "Meditation event handler should not have this meditation event listener").isFalse();
  }

  @Test
  public void test33() {
    StreamMeditationMockEventListener streamMeditationMockEventListener = new StreamMeditationMockEventListener();
    this.mindWaveMobile2.addEventListener(streamMeditationMockEventListener);

    this.mindWaveMobile2.fireEvent(
        new StreamMeditationEvent(this, new MeditationData(20)));
    assertThat(streamMeditationMockEventListener.getMeditationCount()).as(
        "Meditation event listener should have meditation count equal to 1").isEqualTo(1);
    assertThat(streamMeditationMockEventListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 20").isEqualTo(20);

    this.mindWaveMobile2.fireEvent(
        new StreamMeditationEvent(this, new MeditationData(30)));
    assertThat(streamMeditationMockEventListener.getMeditationCount()).as(
        "Meditation event listener should have the meditation count equal to 2").isEqualTo(2);
    assertThat(streamMeditationMockEventListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 30").isEqualTo(30);
  }

  //TODO:StreamEEGDataMockEventListener test
  @Test
  public void test34() {
    StreamEEGDataMockEventListener streamEEGDataMockListener = new StreamEEGDataMockEventListener();
    this.mindWaveMobile2.addEventListener(streamEEGDataMockListener);
    assertThat(this.mindWaveMobile2.containsListener(streamEEGDataMockListener)).as(
        "Raw Event handler should have this EEGData event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(streamEEGDataMockListener);
  }

  @Test
  public void test35() {
    StreamEEGDataMockEventListener streamEEGDataMockListener = new StreamEEGDataMockEventListener();
    this.mindWaveMobile2.addEventListener(streamEEGDataMockListener);
    this.mindWaveMobile2.removeEventListener(streamEEGDataMockListener);
    assertThat(this.mindWaveMobile2.containsListener(streamEEGDataMockListener)).as(
        "Raw event handler should not have this EEGData event listener").isFalse();
  }

  @Test
  public void test36() {
    StreamEEGDataMockEventListener streamEEGDataMockListener = new StreamEEGDataMockEventListener();
    this.mindWaveMobile2.addEventListener(streamEEGDataMockListener);

    this.mindWaveMobile2.fireEvent(
        new StreamEEGDataEvent(this, new StreamEEGData(20, 30, 40, 50, 60, 70, 80, 90)));
    assertThat(streamEEGDataMockListener.getEEGDataCount()).as(
        "EEGData event listener should have EEGData count equal to 1").isEqualTo(1);
    assertThat(streamEEGDataMockListener.getLastEEGDataValue()).as(
            "EEGData event listener should have last EEGData value equal to 20, 30, 40, 50, 60, 70, 80, 90")
        .isEqualTo(new int[]{20, 30, 40, 50, 60, 70, 80, 90});

    this.mindWaveMobile2.fireEvent(
        new StreamEEGDataEvent(this, new StreamEEGData(30, 40, 50, 60, 70, 80, 90, 100)));
    assertThat(streamEEGDataMockListener.getEEGDataCount()).as(
        "EEGData event listener should have the EEGData count equal to 2").isEqualTo(2);
    assertThat(streamEEGDataMockListener.getLastEEGDataValue()).as(
            "EEGData event listener should have last EEGData value equal to 30, 40, 50, 60, 70, 80, 90, 100")
        .isEqualTo(new int[]{30, 40, 50, 60, 70, 80, 90, 100});
  }


  //TODO:StreamRawMockEventListener test
  @Test
  public void test10() {
    StreamRawMockEventListener streamRawEventMockListener = new StreamRawMockEventListener();
    this.mindWaveMobile2.addEventListener(streamRawEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(streamRawEventMockListener)).as(
        "Raw Event handler should have this raw event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(streamRawEventMockListener);
  }

  @Test
  public void test11() {
    StreamRawMockEventListener streamRawEventMockListener = new StreamRawMockEventListener();
    this.mindWaveMobile2.addEventListener(streamRawEventMockListener);
    this.mindWaveMobile2.removeEventListener(streamRawEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(streamRawEventMockListener)).as(
        "Raw event handler should not have this raw event listener").isFalse();
  }

  @Test
  public void test12() {
    StreamRawMockEventListener streamRawEventMockListener = new StreamRawMockEventListener();
    this.mindWaveMobile2.addEventListener(streamRawEventMockListener);

    this.mindWaveMobile2.fireEvent(
        new StreamRawDataEvent(this, new StreamRawData(new short[]{20})));
    assertThat(streamRawEventMockListener.getRawCount()).as(
        "Raw event listener should have raw count equal to 1").isEqualTo(1);
    assertThat(streamRawEventMockListener.getLastRawValue()).as(
        "Raw event listener should have last raw value equal to 20").isEqualTo(new short[]{20});

    this.mindWaveMobile2.fireEvent(
        new StreamRawDataEvent(this, new StreamRawData(new short[]{30})));
    assertThat(streamRawEventMockListener.getRawCount()).as(
        "Raw event listener should have the raw count equal to 2").isEqualTo(2);
    assertThat(streamRawEventMockListener.getLastRawValue()).as(
        "Raw event listener should have last raw value equal to 30").isEqualTo(new short[]{30});
  }


  //TODO:HeadsetStateEventMockListener test
  @Test
  public void test22() {
    HeadsetStateEventMockListener headsetStateEventMockListener = new HeadsetStateEventMockListener();
    this.mindWaveMobile2.addEventListener(headsetStateEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(headsetStateEventMockListener)).as(
        "HeadsetState Event handler should have this headset state event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(headsetStateEventMockListener);
  }

  @Test
  public void test23() {
    HeadsetStateEventMockListener headsetStateEventMockListener = new HeadsetStateEventMockListener();
    this.mindWaveMobile2.addEventListener(headsetStateEventMockListener);
    this.mindWaveMobile2.removeEventListener(headsetStateEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(headsetStateEventMockListener)).as(
        "HeadsetState event handler should not have this headset state event listener").isFalse();
  }

  @Test
  public void test24() {
    HeadsetStateEventMockListener headsetStateEventMockListener = new HeadsetStateEventMockListener();
    this.mindWaveMobile2.addEventListener(headsetStateEventMockListener);

    assertThat(headsetStateEventMockListener.getStateChangeCount()).as(
        "HeadsetState event listener should have headset state count equal to 0").isEqualTo(0);
    assertThat(headsetStateEventMockListener.getLastState()).as(
            "HeadsetState event listener should have last headset state equal to TEST")
        .isEqualTo(HeadsetStateTypes.TEST);

    this.mindWaveMobile2.fireEvent(
        new HeadsetStateChangeEvent(this, HeadsetStateTypes.CONNECTED));

    assertThat(headsetStateEventMockListener.getStateChangeCount()).as(
        "HeadsetState event listener should have headset state count equal to 1").isEqualTo(1);
    assertThat(headsetStateEventMockListener.getLastState()).as(
            "HeadsetState event listener should have last headset state equal to CONNECTED")
        .isEqualTo(HeadsetStateTypes.CONNECTED);

    this.mindWaveMobile2.fireEvent(
        new HeadsetStateChangeEvent(this, HeadsetStateTypes.DISCONNECTED));
    assertThat(headsetStateEventMockListener.getStateChangeCount()).as(
        "HeadsetState event listener should have the headset state count equal to 2").isEqualTo(2);
    assertThat(headsetStateEventMockListener.getLastState()).as(
            "HeadsetState event listener should have last headset state equal to DISCONNECTED")
        .isEqualTo(HeadsetStateTypes.DISCONNECTED);
  }


  //TODO:OtherEventMockListener test
  @Test
  public void test19() {
    OtherEventMockListener otherEventMockListener = new OtherEventMockListener();
    assertThatExceptionOfType(IllegalArgumentException.class).describedAs(
        "Addition Invalid Listener Type should throw IllegalArgumentException").isThrownBy(() -> {
      this.mindWaveMobile2.addEventListener(otherEventMockListener);
    }).withMessage("Invalid Listener Type");
  }

  @Test
  public void test20() {
    OtherEventMockListener otherEventMockListener = new OtherEventMockListener();
    assertThatExceptionOfType(IllegalArgumentException.class).describedAs(
        "Removal of Invalid Listener Type should throw IllegalArgumentException").isThrownBy(() -> {
      this.mindWaveMobile2.removeEventListener(otherEventMockListener);
    }).withMessage("Invalid Listener Type");
  }

  @Test
  public void test21() {
    OtherEventMockListener otherEventMockListener = new OtherEventMockListener();
    assertThatExceptionOfType(IllegalArgumentException.class).describedAs(
        "Firing Invalid Listener Type should throw IllegalArgumentException").isThrownBy(() -> {
      this.mindWaveMobile2.fireEvent(new TestEvent(this));
    }).withMessage("Invalid Event Type");
  }

}
