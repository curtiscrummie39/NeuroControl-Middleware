package headsetTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import headset.HeadsetController;
import headset.events.AttentionData;
import headset.events.MeditationData;
import headset.events.SignalQualityData;
import headset.events.headsetStateChange.HeadsetState;
import headset.events.headsetStateChange.HeadsetStateChangeEvent;
import headset.events.nskAlgo.algoAttention.AlgoAttentionEvent;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerData;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerEvent;
import headset.events.nskAlgo.algoBlink.AlgoBlinkData;
import headset.events.nskAlgo.algoBlink.AlgoBlinkEvent;
import headset.events.nskAlgo.algoMeditation.AlgoMeditationEvent;
import headset.events.nskAlgo.algoSignalQuality.AlgoSignalQualityEvent;
import headset.events.nskAlgo.algoStateChange.AlgoState;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeEvent;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeReason;
import headset.events.stream.streamAttention.StreamAttentionEvent;
import headset.events.stream.streamBandPower.StreamBandPower;
import headset.events.stream.streamBandPower.StreamBandPowerEvent;
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
import headsetTest.eventsTest.stream.StreamBandPowerMockEventListener;
import headsetTest.eventsTest.stream.StreamMeditationMockEventListener;
import headsetTest.eventsTest.stream.StreamRawMockEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class HeadsetControllerTest {


  private final HeadsetController headsetController = new HeadsetController();

  //TODO:AlgoBlinkMockEventListener test
  @Test
  public void test1() {
    Log.e("Test1", "HI");
    AlgoBlinkMockEventListener blinkEventMockListener = new AlgoBlinkMockEventListener();
    this.headsetController.addEventListener(blinkEventMockListener);
    assertThat(this.headsetController.containsListener(blinkEventMockListener)).as(
        "Blink Event handler should have this blink event listener").isTrue();
    this.headsetController.removeEventListener(blinkEventMockListener);
  }

  @Test
  public void test2() {
    AlgoBlinkMockEventListener blinkEventMockListener = new AlgoBlinkMockEventListener();
    this.headsetController.addEventListener(blinkEventMockListener);
    this.headsetController.removeEventListener(blinkEventMockListener);
    assertThat(this.headsetController.containsListener(blinkEventMockListener)).as(
        "Blink event handler should not have this blink event listener").isFalse();
  }

  @Test
  public void test3() {
    AlgoBlinkMockEventListener blinkEventMockListener1 = new AlgoBlinkMockEventListener();
    this.headsetController.addEventListener(blinkEventMockListener1);

    this.headsetController
        .fireEvent(new AlgoBlinkEvent(this, new AlgoBlinkData(20)));
    assertThat(blinkEventMockListener1.getBlinkCount()).as(
        "Blink event listener should have blink count equal to 1").isEqualTo(1);
    assertThat(blinkEventMockListener1.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 20").isEqualTo(20);

    this.headsetController.fireEvent(new AlgoBlinkEvent(this, new AlgoBlinkData(30)));
    assertThat(blinkEventMockListener1.getBlinkCount()).as(
        "Blink event listener should have the blink count equal to 2").isEqualTo(2);
    assertThat(blinkEventMockListener1.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 30").isEqualTo(30);

    AlgoBlinkMockEventListener blinkEventMockListener2 = new AlgoBlinkMockEventListener();
    this.headsetController.addEventListener(blinkEventMockListener2);

    this.headsetController.fireEvent(new AlgoBlinkEvent(this, new AlgoBlinkData(20)));
    assertThat(blinkEventMockListener2.getBlinkCount()).as(
        "Blink event listener should have blink count equal to 1").isEqualTo(1);
    assertThat(blinkEventMockListener2.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 20").isEqualTo(20);

    this.headsetController.fireEvent(new AlgoBlinkEvent(this, new AlgoBlinkData(30)));
    assertThat(blinkEventMockListener2.getBlinkCount()).as(
        "Blink event listener should have the blink count equal to 2").isEqualTo(2);
    assertThat(blinkEventMockListener2.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 30").isEqualTo(30);

  }

  //TODO:AlgoAttentionMockEventListener test
  @Test
  public void test4() {
    AlgoAttentionMockEventListener algoAttentionMockEventListener = new AlgoAttentionMockEventListener();
    this.headsetController.addEventListener(algoAttentionMockEventListener);
    assertThat(this.headsetController.containsListener(algoAttentionMockEventListener)).as(
        "Attention Event handler should have this attention event listener").isTrue();
    this.headsetController.removeEventListener(algoAttentionMockEventListener);
  }

  @Test
  public void test5() {
    AlgoAttentionMockEventListener algoAttentionMockEventListener = new AlgoAttentionMockEventListener();
    this.headsetController.addEventListener(algoAttentionMockEventListener);
    this.headsetController.removeEventListener(algoAttentionMockEventListener);
    assertThat(this.headsetController.containsListener(algoAttentionMockEventListener)).as(
        "Attention event handler should not have this attention event listener").isFalse();
  }

  @Test
  public void test6() {
    AlgoAttentionMockEventListener algoAttentionMockEventListener = new AlgoAttentionMockEventListener();
    this.headsetController.addEventListener(algoAttentionMockEventListener);

    this.headsetController.fireEvent(new AlgoAttentionEvent(this, new AttentionData(20)));
    assertThat(algoAttentionMockEventListener.getAttentionCount()).as(
        "Attention event listener should have attention count equal to 1").isEqualTo(1);
    assertThat(algoAttentionMockEventListener.getLastAttentionValue()).as(
        "Attention event listener should have last attention value equal to 20").isEqualTo(20);

    this.headsetController.fireEvent(new AlgoAttentionEvent(this, new AttentionData(30)));
    assertThat(algoAttentionMockEventListener.getAttentionCount()).as(
        "Attention event listener should have the attention count equal to 2").isEqualTo(2);
    assertThat(algoAttentionMockEventListener.getLastAttentionValue()).as(
        "Attention event listener should have last attention value equal to 30").isEqualTo(30);
  }

  //TODO:AlgoMeditationMockEventListener test
  @Test
  public void test7() {
    AlgoMeditationMockEventListener algoMeditationMockEventListener = new AlgoMeditationMockEventListener();
    this.headsetController.addEventListener(algoMeditationMockEventListener);
    assertThat(this.headsetController.containsListener(algoMeditationMockEventListener)).as(
        "Meditation Event handler should have this meditation event listener").isTrue();
    this.headsetController.removeEventListener(algoMeditationMockEventListener);
  }

  @Test
  public void test8() {
    AlgoMeditationMockEventListener algoMeditationMockEventListener = new AlgoMeditationMockEventListener();
    this.headsetController.addEventListener(algoMeditationMockEventListener);
    this.headsetController.removeEventListener(algoMeditationMockEventListener);
    assertThat(this.headsetController.containsListener(algoMeditationMockEventListener)).as(
        "Meditation event handler should not have this meditation event listener").isFalse();
  }

  @Test
  public void test9() {
    AlgoMeditationMockEventListener algoMeditationMockEventListener = new AlgoMeditationMockEventListener();
    this.headsetController.addEventListener(algoMeditationMockEventListener);

    this.headsetController.fireEvent(new AlgoMeditationEvent(this, new MeditationData(20)));
    assertThat(algoMeditationMockEventListener.getMeditationCount()).as(
        "Meditation event listener should have meditation count equal to 1").isEqualTo(1);
    assertThat(algoMeditationMockEventListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 20").isEqualTo(20);

    this.headsetController.fireEvent(new AlgoMeditationEvent(this, new MeditationData(30)));
    assertThat(algoMeditationMockEventListener.getMeditationCount()).as(
        "Meditation event listener should have the meditation count equal to 2").isEqualTo(2);
    assertThat(algoMeditationMockEventListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 30").isEqualTo(30);
  }

  //TODO:AlgoSignalQualityMockEventListener test
  @Test
  public void test13() {
    AlgoSignalQualityMockEventListener algoSignalEventMockListener = new AlgoSignalQualityMockEventListener();
    this.headsetController.addEventListener(algoSignalEventMockListener);
    assertThat(this.headsetController.containsListener(algoSignalEventMockListener)).as(
        "SignalQuality Event handler should have this signal event listener").isTrue();
    this.headsetController.removeEventListener(algoSignalEventMockListener);
  }

  @Test
  public void test14() {
    AlgoSignalQualityMockEventListener algoSignalEventMockListener = new AlgoSignalQualityMockEventListener();
    this.headsetController.addEventListener(algoSignalEventMockListener);
    this.headsetController.removeEventListener(algoSignalEventMockListener);
    assertThat(this.headsetController.containsListener(algoSignalEventMockListener)).as(
        "SignalQuality event handler should not have this signal event listener").isFalse();
  }

  @Test
  public void test15() {
    AlgoSignalQualityMockEventListener algoSignalEventMockListener = new AlgoSignalQualityMockEventListener();
    this.headsetController.addEventListener(algoSignalEventMockListener);

    this.headsetController.fireEvent(new AlgoSignalQualityEvent(this, new SignalQualityData(20)));
    assertThat(algoSignalEventMockListener.getSignalQualityCount()).as(
        "SignalQuality event listener should have signal quality count equal to 1").isEqualTo(1);
    assertThat(algoSignalEventMockListener.getLastSignalQuality()).as(
            "SignalQuality event listener should have last signal quality value equal to 20")
        .isEqualTo(20);

    this.headsetController.fireEvent(new AlgoSignalQualityEvent(this, new SignalQualityData(30)));
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
    this.headsetController.addEventListener(algoBandPowerEventMockListener);
    assertThat(this.headsetController.containsListener(algoBandPowerEventMockListener)).as(
        "BandPower Event handler should have this band power event listener").isTrue();
    this.headsetController.removeEventListener(algoBandPowerEventMockListener);
  }

  @Test
  public void test17() {
    AlgoBandPowerMockEventListener algoBandPowerEventMockListener = new AlgoBandPowerMockEventListener();
    this.headsetController.addEventListener(algoBandPowerEventMockListener);
    this.headsetController.removeEventListener(algoBandPowerEventMockListener);
    assertThat(this.headsetController.containsListener(algoBandPowerEventMockListener)).as(
        "BandPower event handler should not have this band power event listener").isFalse();
  }

  @Test
  public void test18() {
    AlgoBandPowerMockEventListener algoBandPowerEventMockListener = new AlgoBandPowerMockEventListener();
    this.headsetController.addEventListener(algoBandPowerEventMockListener);

    this.headsetController.fireEvent(
        new AlgoBandPowerEvent(this, new AlgoBandPowerData(20, 30, 40, 50, 60)));
    assertThat(algoBandPowerEventMockListener.getBandPowerCount()).as(
        "BandPower event listener should have band power count equal to 1").isEqualTo(1);
    assertThat(algoBandPowerEventMockListener.getLastBandPowerValue()).as(
            "BandPower event listener should have last band power data equal to 20, 30, 40, 50, 60")
        .isEqualTo(new float[]{20, 30, 40, 50, 60});

    this.headsetController.fireEvent(
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
    this.headsetController.addEventListener(algoStateMockEventListener);
    assertThat(this.headsetController.containsListener(algoStateMockEventListener)).as(
        "AlgoState Event handler should have this algo state event listener").isTrue();
    this.headsetController.removeEventListener(algoStateMockEventListener);
  }

  @Test
  public void test26() {
    AlgoStateMockEventListener algoStateMockEventListener = new AlgoStateMockEventListener();
    this.headsetController.addEventListener(algoStateMockEventListener);
    this.headsetController.removeEventListener(algoStateMockEventListener);
    assertThat(this.headsetController.containsListener(algoStateMockEventListener)).as(
        "AlgoState event handler should not have this algo state event listener").isFalse();
  }

  @Test
  public void test27() {
    AlgoStateMockEventListener algoStateMockEventListener = new AlgoStateMockEventListener();
    this.headsetController.addEventListener(algoStateMockEventListener);

    this.headsetController.fireEvent(
        new AlgoStateChangeEvent(this, AlgoState.TEST.ordinal(),
            AlgoStateChangeReason.TEST.ordinal()));
    assertThat(algoStateMockEventListener.getStateChangeCount()).as(
        "AlgoState event listener should have algo state count equal to 1").isEqualTo(1);
    assertThat(algoStateMockEventListener.getLastState()).as(
            "AlgoState event listener should have last algo state equal to TEST")
        .isEqualTo(AlgoState.TEST);
    assertThat(algoStateMockEventListener.getLastReason()).as(
            "AlgoState event listener should have last algo state change reason equal to TEST")
        .isEqualTo(AlgoStateChangeReason.TEST);
  }

  //TODO:StreamAttentionMockEventListener test
  @Test
  public void test28() {
    StreamAttentionMockEventListener streamAttentionMockEventListener = new StreamAttentionMockEventListener();
    this.headsetController.addEventListener(streamAttentionMockEventListener);
    assertThat(this.headsetController.containsListener(streamAttentionMockEventListener)).as(
        "Raw Event handler should have this raw event listener").isTrue();
    this.headsetController.removeEventListener(streamAttentionMockEventListener);
  }

  @Test
  public void test29() {
    StreamAttentionMockEventListener streamAttentionMockEventListener = new StreamAttentionMockEventListener();
    this.headsetController.addEventListener(streamAttentionMockEventListener);
    this.headsetController.removeEventListener(streamAttentionMockEventListener);
    assertThat(this.headsetController.containsListener(streamAttentionMockEventListener)).as(
        "Raw event handler should not have this raw event listener").isFalse();
  }

  @Test
  public void test30() {
    StreamAttentionMockEventListener streamAttentionMockEventListener = new StreamAttentionMockEventListener();
    this.headsetController.addEventListener(streamAttentionMockEventListener);

    this.headsetController.fireEvent(
        new StreamAttentionEvent(this, new AttentionData(20)));
    assertThat(streamAttentionMockEventListener.getAttentionCount()).as(
        "Raw event listener should have raw count equal to 1").isEqualTo(1);
    assertThat(streamAttentionMockEventListener.getLastAttentionValue()).as(
        "Raw event listener should have last raw value equal to 20").isEqualTo(20);

    this.headsetController.fireEvent(
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
    this.headsetController.addEventListener(streamMeditationMockEventListener);
    assertThat(this.headsetController.containsListener(streamMeditationMockEventListener)).as(
        "Meditation Event handler should have this meditation event listener").isTrue();
    this.headsetController.removeEventListener(streamMeditationMockEventListener);
  }

  @Test
  public void test32() {
    StreamMeditationMockEventListener streamMeditationMockEventListener = new StreamMeditationMockEventListener();
    this.headsetController.addEventListener(streamMeditationMockEventListener);
    this.headsetController.removeEventListener(streamMeditationMockEventListener);
    assertThat(this.headsetController.containsListener(streamMeditationMockEventListener)).as(
        "Meditation event handler should not have this meditation event listener").isFalse();
  }

  @Test
  public void test33() {
    StreamMeditationMockEventListener streamMeditationMockEventListener = new StreamMeditationMockEventListener();
    this.headsetController.addEventListener(streamMeditationMockEventListener);

    this.headsetController.fireEvent(
        new StreamMeditationEvent(this, new MeditationData(20)));
    assertThat(streamMeditationMockEventListener.getMeditationCount()).as(
        "Meditation event listener should have meditation count equal to 1").isEqualTo(1);
    assertThat(streamMeditationMockEventListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 20").isEqualTo(20);

    this.headsetController.fireEvent(
        new StreamMeditationEvent(this, new MeditationData(30)));
    assertThat(streamMeditationMockEventListener.getMeditationCount()).as(
        "Meditation event listener should have the meditation count equal to 2").isEqualTo(2);
    assertThat(streamMeditationMockEventListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 30").isEqualTo(30);
  }

  //TODO:StreamEEGDataMockEventListener test
  @Test
  public void test34() {
    StreamBandPowerMockEventListener streamEEGDataMockListener = new StreamBandPowerMockEventListener();
    this.headsetController.addEventListener(streamEEGDataMockListener);
    assertThat(this.headsetController.containsListener(streamEEGDataMockListener)).as(
        "Raw Event handler should have this EEGData event listener").isTrue();
    this.headsetController.removeEventListener(streamEEGDataMockListener);
  }

  @Test
  public void test35() {
    StreamBandPowerMockEventListener streamEEGDataMockListener = new StreamBandPowerMockEventListener();
    this.headsetController.addEventListener(streamEEGDataMockListener);
    this.headsetController.removeEventListener(streamEEGDataMockListener);
    assertThat(this.headsetController.containsListener(streamEEGDataMockListener)).as(
        "Raw event handler should not have this EEGData event listener").isFalse();
  }

  @Test
  public void test36() {
    StreamBandPowerMockEventListener streamEEGDataMockListener = new StreamBandPowerMockEventListener();
    this.headsetController.addEventListener(streamEEGDataMockListener);

    this.headsetController.fireEvent(
        new StreamBandPowerEvent(this, new StreamBandPower(20, 30, 40, 50, 60, 70, 80, 90)));
    assertThat(streamEEGDataMockListener.getEEGDataCount()).as(
        "EEGData event listener should have EEGData count equal to 1").isEqualTo(1);
    assertThat(streamEEGDataMockListener.getLastEEGDataValue()).as(
            "EEGData event listener should have last EEGData value equal to 20, 30, 40, 50, 60, 70, 80, 90")
        .isEqualTo(new int[]{20, 30, 40, 50, 60, 70, 80, 90});

    this.headsetController.fireEvent(
        new StreamBandPowerEvent(this, new StreamBandPower(30, 40, 50, 60, 70, 80, 90, 100)));
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
    this.headsetController.addEventListener(streamRawEventMockListener);
    assertThat(this.headsetController.containsListener(streamRawEventMockListener)).as(
        "Raw Event handler should have this raw event listener").isTrue();
    this.headsetController.removeEventListener(streamRawEventMockListener);
  }

  @Test
  public void test11() {
    StreamRawMockEventListener streamRawEventMockListener = new StreamRawMockEventListener();
    this.headsetController.addEventListener(streamRawEventMockListener);
    this.headsetController.removeEventListener(streamRawEventMockListener);
    assertThat(this.headsetController.containsListener(streamRawEventMockListener)).as(
        "Raw event handler should not have this raw event listener").isFalse();
  }

  @Test
  public void test12() {
    StreamRawMockEventListener streamRawEventMockListener = new StreamRawMockEventListener();
    this.headsetController.addEventListener(streamRawEventMockListener);

    this.headsetController.fireEvent(
        new StreamRawDataEvent(this, new StreamRawData(new short[]{20})));
    assertThat(streamRawEventMockListener.getRawCount()).as(
        "Raw event listener should have raw count equal to 1").isEqualTo(1);
    assertThat(streamRawEventMockListener.getLastRawValue()).as(
        "Raw event listener should have last raw value equal to 20").isEqualTo(new short[]{20});

    this.headsetController.fireEvent(
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
    this.headsetController.addEventListener(headsetStateEventMockListener);
    assertThat(this.headsetController.containsListener(headsetStateEventMockListener)).as(
        "HeadsetState Event handler should have this headset state event listener").isTrue();
    this.headsetController.removeEventListener(headsetStateEventMockListener);
  }

  @Test
  public void test23() {
    HeadsetStateEventMockListener headsetStateEventMockListener = new HeadsetStateEventMockListener();
    this.headsetController.addEventListener(headsetStateEventMockListener);
    this.headsetController.removeEventListener(headsetStateEventMockListener);
    assertThat(this.headsetController.containsListener(headsetStateEventMockListener)).as(
        "HeadsetState event handler should not have this headset state event listener").isFalse();
  }

  @Test
  public void test24() {
    HeadsetStateEventMockListener headsetStateEventMockListener = new HeadsetStateEventMockListener();
    this.headsetController.addEventListener(headsetStateEventMockListener);

    assertThat(headsetStateEventMockListener.getStateChangeCount()).as(
        "HeadsetState event listener should have headset state count equal to 0").isEqualTo(0);
    assertThat(headsetStateEventMockListener.getLastState()).as(
            "HeadsetState event listener should have last headset state equal to TEST")
        .isEqualTo(HeadsetState.TEST);

    this.headsetController.fireEvent(
        new HeadsetStateChangeEvent(this, HeadsetState.CONNECTED));

    assertThat(headsetStateEventMockListener.getStateChangeCount()).as(
        "HeadsetState event listener should have headset state count equal to 1").isEqualTo(1);
    assertThat(headsetStateEventMockListener.getLastState()).as(
            "HeadsetState event listener should have last headset state equal to CONNECTED")
        .isEqualTo(HeadsetState.CONNECTED);

    this.headsetController.fireEvent(
        new HeadsetStateChangeEvent(this, HeadsetState.DISCONNECTED));
    assertThat(headsetStateEventMockListener.getStateChangeCount()).as(
        "HeadsetState event listener should have the headset state count equal to 2").isEqualTo(2);
    assertThat(headsetStateEventMockListener.getLastState()).as(
            "HeadsetState event listener should have last headset state equal to DISCONNECTED")
        .isEqualTo(HeadsetState.DISCONNECTED);
  }


  //TODO:OtherEventMockListener test
  @Test
  public void test19() {
    OtherEventMockListener otherEventMockListener = new OtherEventMockListener();
    assertThatExceptionOfType(IllegalArgumentException.class).describedAs(
        "Addition Invalid Listener Type should throw IllegalArgumentException").isThrownBy(() -> {
      this.headsetController.addEventListener(otherEventMockListener);
    }).withMessage("Invalid Listener Type");
  }

  @Test
  public void test20() {
    OtherEventMockListener otherEventMockListener = new OtherEventMockListener();
    assertThatExceptionOfType(IllegalArgumentException.class).describedAs(
        "Removal of Invalid Listener Type should throw IllegalArgumentException").isThrownBy(() -> {
      this.headsetController.removeEventListener(otherEventMockListener);
    }).withMessage("Invalid Listener Type");
  }

  @Test
  public void test21() {
    OtherEventMockListener otherEventMockListener = new OtherEventMockListener();
    assertThatExceptionOfType(IllegalArgumentException.class).describedAs(
        "Firing Invalid Listener Type should throw IllegalArgumentException").isThrownBy(() -> {
      this.headsetController.fireEvent(new TestEvent(this));
    }).withMessage("Invalid Event Type");
  }

}
