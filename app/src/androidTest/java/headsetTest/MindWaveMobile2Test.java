package headsetTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import headset.MindWaveMobile2;
import headset.events.HeadsetDataTypes;
import headset.events.stream.stateChange.HeadsetStateChangeEvent;
import headset.events.stream.stateChange.HeadsetStateTypes;
import headsetTest.eventsTest.AttentionEventMockListener;
import headsetTest.eventsTest.BandPowerEventMockListener;
import headsetTest.eventsTest.BlinkEventMockListener;
import headsetTest.eventsTest.HeadsetStateEventMockListener;
import headsetTest.eventsTest.MeditationEventMockListener;
import headsetTest.eventsTest.OtherEventMockListener;
import headsetTest.eventsTest.RawEventMockListener;
import headsetTest.eventsTest.SignalQualityEventMockListener;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MindWaveMobile2Test {

  private final MindWaveMobile2 mindWaveMobile2 = new MindWaveMobile2();

  public MindWaveMobile2 getMindWaveMobile2() {
    return this.mindWaveMobile2;
  }

  @Test
  //"EventTest 1: Test to verify if the event listener is added to the blinkTest event handler.")
  public void test1() {
    Log.e("Test1", "HI");
    BlinkEventMockListener blinkEventMockListener = new BlinkEventMockListener();
    this.mindWaveMobile2.addEventListener(blinkEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(blinkEventMockListener)).as(
        "Blink Event handler should have this blink event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(blinkEventMockListener);
  }

  @Test
  //"EventTest 2: Test to verify if the event listener is removed from the blinkTest event handler.")
  public void test2() {
    BlinkEventMockListener blinkEventMockListener = new BlinkEventMockListener();
    this.mindWaveMobile2.addEventListener(blinkEventMockListener);
    this.mindWaveMobile2.removeEventListener(blinkEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(blinkEventMockListener)).as(
        "Blink event handler should not have this blink event listener").isFalse();
  }

  @Test
  //"EventTest 3: Test to verify if the blinkTest event is fired.")
  public void test3() {
    BlinkEventMockListener blinkEventMockListener1 = new BlinkEventMockListener();
    this.mindWaveMobile2.addEventListener(blinkEventMockListener1);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.BLINK, 20);
    assertThat(blinkEventMockListener1.getBlinkCount()).as(
        "Blink event listener should have blink count equal to 1").isEqualTo(1);
    assertThat(blinkEventMockListener1.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 20").isEqualTo(20);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.BLINK, 30);
    assertThat(blinkEventMockListener1.getBlinkCount()).as(
        "Blink event listener should have the blink count equal to 2").isEqualTo(2);
    assertThat(blinkEventMockListener1.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 30").isEqualTo(30);

    BlinkEventMockListener blinkEventMockListener2 = new BlinkEventMockListener();
    this.mindWaveMobile2.addEventListener(blinkEventMockListener2);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.BLINK, 20);
    assertThat(blinkEventMockListener2.getBlinkCount()).as(
        "Blink event listener should have blink count equal to 1").isEqualTo(1);
    assertThat(blinkEventMockListener2.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 20").isEqualTo(20);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.BLINK, 30);
    assertThat(blinkEventMockListener2.getBlinkCount()).as(
        "Blink event listener should have the blink count equal to 2").isEqualTo(2);
    assertThat(blinkEventMockListener2.getLastBlinkStrength()).as(
        "Blink event listener should have last blink strength equal to 30").isEqualTo(30);

  }

  @Test
  //"EventTest 4: Test to verify if the event listener is added to the attention data update event handler.")
  public void test4() {
    AttentionEventMockListener attentionEventMockListener = new AttentionEventMockListener();
    this.mindWaveMobile2.addEventListener(attentionEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(attentionEventMockListener)).as(
        "Attention Event handler should have this attention event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(attentionEventMockListener);
  }

  @Test
  //"EventTest 5: Test to verify if the event listener is removed from the attention data update event handler.")
  public void test5() {
    AttentionEventMockListener attentionEventMockListener = new AttentionEventMockListener();
    this.mindWaveMobile2.addEventListener(attentionEventMockListener);
    this.mindWaveMobile2.removeEventListener(attentionEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(attentionEventMockListener)).as(
        "Attention event handler should not have this attention event listener").isFalse();
  }

  @Test
  //"EventTest 6: Test to verify if the attention data update event is fired.")
  public void test6() {
    AttentionEventMockListener attentionEventMockListener = new AttentionEventMockListener();
    this.mindWaveMobile2.addEventListener(attentionEventMockListener);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.ATTENTION, 20);
    assertThat(attentionEventMockListener.getAttentionCount()).as(
        "Attention event listener should have attention count equal to 1").isEqualTo(1);
    assertThat(attentionEventMockListener.getLastAttentionValue()).as(
        "Attention event listener should have last attention value equal to 20").isEqualTo(20);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.ATTENTION, 30);
    assertThat(attentionEventMockListener.getAttentionCount()).as(
        "Attention event listener should have the attention count equal to 2").isEqualTo(2);
    assertThat(attentionEventMockListener.getLastAttentionValue()).as(
        "Attention event listener should have last attention value equal to 30").isEqualTo(30);
  }

  @Test
  //"EventTest 7: Test to verify if the event listener is added to the meditationTest data update event handler.")
  public void test7() {
    MeditationEventMockListener meditationEventMockListener = new MeditationEventMockListener();
    this.mindWaveMobile2.addEventListener(meditationEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(meditationEventMockListener)).as(
        "Meditation Event handler should have this meditation event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(meditationEventMockListener);
  }

  @Test
  //"EventTest 8: Test to verify if the event listener is removed from the meditationTest data update event handler.")
  public void test8() {
    MeditationEventMockListener meditationEventMockListener = new MeditationEventMockListener();
    this.mindWaveMobile2.addEventListener(meditationEventMockListener);
    this.mindWaveMobile2.removeEventListener(meditationEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(meditationEventMockListener)).as(
        "Meditation event handler should not have this meditation event listener").isFalse();
  }

  @Test
  //"EventTest 9: Test to verify if the meditationTest data update event is fired.")
  public void test9() {
    MeditationEventMockListener meditationEventMockListener = new MeditationEventMockListener();
    this.mindWaveMobile2.addEventListener(meditationEventMockListener);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.MEDITATION, 20);
    assertThat(meditationEventMockListener.getMeditationCount()).as(
        "Meditation event listener should have meditation count equal to 1").isEqualTo(1);
    assertThat(meditationEventMockListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 20").isEqualTo(20);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.MEDITATION, 30);
    assertThat(meditationEventMockListener.getMeditationCount()).as(
        "Meditation event listener should have the meditation count equal to 2").isEqualTo(2);
    assertThat(meditationEventMockListener.getLastMeditationValue()).as(
        "Meditation event listener should have last meditation value equal to 30").isEqualTo(30);
  }

  @Test
  //"EventTest 10: Test to verify if the event listener is added to the rawTest data update event handler.")
  public void test10() {
    RawEventMockListener rawEventMockListener = new RawEventMockListener();
    this.mindWaveMobile2.addEventListener(rawEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(rawEventMockListener)).as(
        "Raw Event handler should have this raw event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(rawEventMockListener);
  }

  @Test
  //"EventTest 11: Test to verify if the event listener is removed from the rawTest data update event handler.")
  public void test11() {
    RawEventMockListener rawEventMockListener = new RawEventMockListener();
    this.mindWaveMobile2.addEventListener(rawEventMockListener);
    this.mindWaveMobile2.removeEventListener(rawEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(rawEventMockListener)).as(
        "Raw event handler should not have this raw event listener").isFalse();
  }

  @Test
  //"EventTest 12: Test to verify if the rawTest data update event is fired.")
  public void test12() {
    RawEventMockListener rawEventMockListener = new RawEventMockListener();
    this.mindWaveMobile2.addEventListener(rawEventMockListener);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.RAW, new short[]{20});
    assertThat(rawEventMockListener.getRawCount()).as(
        "Raw event listener should have raw count equal to 1").isEqualTo(1);
    assertThat(rawEventMockListener.getLastRawValue()).as(
        "Raw event listener should have last raw value equal to 20").isEqualTo(new short[]{20});

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.RAW, new short[]{30});
    assertThat(rawEventMockListener.getRawCount()).as(
        "Raw event listener should have the raw count equal to 2").isEqualTo(2);
    assertThat(rawEventMockListener.getLastRawValue()).as(
        "Raw event listener should have last raw value equal to 30").isEqualTo(new short[]{30});
  }

  @Test
  //"EventTest 13: Test to verify if the event listener is added to the signal quality update event handler.")
  public void test13() {
    SignalQualityEventMockListener signalEventMockListener = new SignalQualityEventMockListener();
    this.mindWaveMobile2.addEventListener(signalEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(signalEventMockListener)).as(
        "SignalQuality Event handler should have this signal event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(signalEventMockListener);
  }


  @Test
  //"EventTest 14: Test to verify if the event listener is removed from the signal quality update event handler.")
  public void test14() {
    SignalQualityEventMockListener signalEventMockListener = new SignalQualityEventMockListener();
    this.mindWaveMobile2.addEventListener(signalEventMockListener);
    this.mindWaveMobile2.removeEventListener(signalEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(signalEventMockListener)).as(
        "SignalQuality event handler should not have this signal event listener").isFalse();
  }

  @Test
  //"EventTest 15: Test to verify if the signal quality update event is fired.")
  public void test15() {
    SignalQualityEventMockListener signalEventMockListener = new SignalQualityEventMockListener();
    this.mindWaveMobile2.addEventListener(signalEventMockListener);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.SIGNAL_QUALITY, 20);
    assertThat(signalEventMockListener.getSignalQualityCount()).as(
        "SignalQuality event listener should have signal quality count equal to 1").isEqualTo(1);
    assertThat(signalEventMockListener.getLastSignalQuality()).as(
            "SignalQuality event listener should have last signal quality value equal to 20")
        .isEqualTo(20);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.SIGNAL_QUALITY, 30);
    assertThat(signalEventMockListener.getSignalQualityCount()).as(
            "SignalQuality event listener should have the signal quality count equal to 2")
        .isEqualTo(2);
    assertThat(signalEventMockListener.getLastSignalQuality()).as(
            "SignalQuality event listener should have last signal quality value equal to 30")
        .isEqualTo(30);
  }

  @Test
  //"EventTest 16: Test to verify if the event listener is added to the band power data update event handler.")
  public void test16() {
    BandPowerEventMockListener bandPowerEventMockListener = new BandPowerEventMockListener();
    this.mindWaveMobile2.addEventListener(bandPowerEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(bandPowerEventMockListener)).as(
        "BandPower Event handler should have this band power event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(bandPowerEventMockListener);
  }

  @Test
  //"EventTest 17: Test to verify if the event listener is removed from the band power data update event handler.")
  public void test17() {
    BandPowerEventMockListener bandPowerEventMockListener = new BandPowerEventMockListener();
    this.mindWaveMobile2.addEventListener(bandPowerEventMockListener);
    this.mindWaveMobile2.removeEventListener(bandPowerEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(bandPowerEventMockListener)).as(
        "BandPower event handler should not have this band power event listener").isFalse();
  }

  @Test
  //"EventTest 18: Test to verify if the band power data update event is fired.")
  public void test18() {
    BandPowerEventMockListener bandPowerEventMockListener = new BandPowerEventMockListener();
    this.mindWaveMobile2.addEventListener(bandPowerEventMockListener);

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.BAND_POWER, new float[]{20, 30, 40, 50, 60});
    assertThat(bandPowerEventMockListener.getBandPowerCount()).as(
        "BandPower event listener should have band power count equal to 1").isEqualTo(1);
    assertThat(bandPowerEventMockListener.getLastBandPowerValue()).as(
            "BandPower event listener should have last band power data equal to 20, 30, 40, 50, 60")
        .isEqualTo(new float[]{20, 30, 40, 50, 60});

    this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
        .fireEvent(HeadsetDataTypes.BAND_POWER, new float[]{30, 40, 50, 60, 70});
    assertThat(bandPowerEventMockListener.getBandPowerCount()).as(
        "BandPower event listener should have the band power count equal to 2").isEqualTo(2);
    assertThat(bandPowerEventMockListener.getLastBandPowerValue()).as(
            "BandPower event listener should have last band power data equal to 30, 40, 50, 60, 70")
        .isEqualTo(new float[]{30, 40, 50, 60, 70});
  }


  @Test
  //"EventTest 20: Test to verify if the event listener Fails to be added to the event handler if it's unknown.")
  public void test19() {
    OtherEventMockListener otherEventMockListener = new OtherEventMockListener();
    assertThatExceptionOfType(IllegalArgumentException.class).describedAs(
        "Addition Unknown listener type should throw IllegalArgumentException").isThrownBy(() -> {
      this.mindWaveMobile2.addEventListener(otherEventMockListener);
    }).withMessage("Unknown listener type: headsetTest.eventsTest.OtherEventMockListener");
  }

  @Test
  //"EventTest 19: Test to verify if the event listener Fails to be removed from the event handler if it's unknown.")
  public void test20() {
    OtherEventMockListener otherEventMockListener = new OtherEventMockListener();
    assertThatExceptionOfType(IllegalArgumentException.class).describedAs(
        "Removal of Unknown listener type should throw IllegalArgumentException").isThrownBy(() -> {
      this.mindWaveMobile2.removeEventListener(otherEventMockListener);
    }).withMessage("Unknown listener type: headsetTest.eventsTest.OtherEventMockListener");
  }

  @Test
  //"EventTest 21: Test to verify if the event listener Fails to be fired if it's unknown.")
  public void test21() {
    OtherEventMockListener otherEventMockListener = new OtherEventMockListener();
    assertThatExceptionOfType(IllegalArgumentException.class).describedAs(
        "Firing Unknown listener type should throw IllegalArgumentException").isThrownBy(() -> {
      this.mindWaveMobile2.getCoreTgStreamHandler().getCoreNskAlgoSdk().getEventsHandler()
          .fireEvent(HeadsetDataTypes.UNKNOWN, new Object());
    }).withMessage("Unknown event type: UNKNOWN");
  }

  @Test
  //"EventTest 22: Test to verify if the event listener is added to the headset state change event handler.")
  public void test22() {
    HeadsetStateEventMockListener headsetStateEventMockListener = new HeadsetStateEventMockListener();
    this.mindWaveMobile2.addEventListener(headsetStateEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(headsetStateEventMockListener)).as(
        "HeadsetState Event handler should have this headset state event listener").isTrue();
    this.mindWaveMobile2.removeEventListener(headsetStateEventMockListener);
  }

  @Test
  //"EventTest 23: Test to verify if the event listener is removed from the headset state change event handler.")
  public void test23() {
    HeadsetStateEventMockListener headsetStateEventMockListener = new HeadsetStateEventMockListener();
    this.mindWaveMobile2.addEventListener(headsetStateEventMockListener);
    this.mindWaveMobile2.removeEventListener(headsetStateEventMockListener);
    assertThat(this.mindWaveMobile2.containsListener(headsetStateEventMockListener)).as(
        "HeadsetState event handler should not have this headset state event listener").isFalse();
  }

  @Test
  //"EventTest 24: Test to verify if the headset state change event is fired.")
  public void test24() {
    HeadsetStateEventMockListener headsetStateEventMockListener = new HeadsetStateEventMockListener();
    this.mindWaveMobile2.addEventListener(headsetStateEventMockListener);

    assertThat(headsetStateEventMockListener.getStateChangeCount()).as(
        "HeadsetState event listener should have headset state count equal to 0").isEqualTo(0);
    assertThat(headsetStateEventMockListener.getLastState()).as(
            "HeadsetState event listener should have last headset state equal to TEST")
        .isEqualTo(HeadsetStateTypes.TEST);

    this.mindWaveMobile2.getCoreTgStreamHandler().fireHeadsetStateChangeEvent(
        new HeadsetStateChangeEvent(this, HeadsetStateTypes.CONNECTED));

    assertThat(headsetStateEventMockListener.getStateChangeCount()).as(
        "HeadsetState event listener should have headset state count equal to 1").isEqualTo(1);
    assertThat(headsetStateEventMockListener.getLastState()).as(
            "HeadsetState event listener should have last headset state equal to CONNECTED")
        .isEqualTo(HeadsetStateTypes.CONNECTED);

    this.mindWaveMobile2.getCoreTgStreamHandler().fireHeadsetStateChangeEvent(
        new HeadsetStateChangeEvent(this, HeadsetStateTypes.DISCONNECTED));
    assertThat(headsetStateEventMockListener.getStateChangeCount()).as(
        "HeadsetState event listener should have the headset state count equal to 2").isEqualTo(2);
    assertThat(headsetStateEventMockListener.getLastState()).as(
            "HeadsetState event listener should have last headset state equal to DISCONNECTED")
        .isEqualTo(HeadsetStateTypes.DISCONNECTED);
  }


}
