package headset;

import android.util.Log;
import java.util.EventListener;

import headset.events.attention.AttentionData;
import headset.events.attention.AttentionDataUpdateEvent;
import headset.events.attention.AttentionDataUpdateEventHandler;
import headset.events.attention.IAttentionDataUpdateEventListener;

import headset.events.blink.BlinkData;
import headset.events.blink.BlinkEvent;
import headset.events.blink.BlinkEventHandler;
import headset.events.blink.IBlinkEventListener;

import headset.events.bandPower.BandPowerData;
import headset.events.bandPower.BandPowerDataUpdateEvent;
import headset.events.bandPower.BandPowerDataUpdateEventHandler;
import headset.events.bandPower.IBandPowerDataUpdateEventListener;

import headset.events.meditation.MeditationData;
import headset.events.meditation.MeditationDataUpdateEvent;
import headset.events.meditation.MeditationDataUpdateEventHandler;
import headset.events.meditation.IMeditationDataUpdateEventListener;

import headset.events.raw.RawData;
import headset.events.raw.RawDataUpdateEvent;
import headset.events.raw.RawDataUpdateEventHandler;
import headset.events.raw.IRawDataUpdateEventListener;

import headset.events.signalQuality.SignalQualityData;
import headset.events.signalQuality.SignalQualityUpdateEvent;
import headset.events.signalQuality.SignalQualityUpdateEventHandler;
import headset.events.signalQuality.ISignalQualityUpdateEventListener;

import headset.events.HeadsetDataTypes;

public class CoreNskAlgoSdkEventsController {

  private final BlinkEventHandler blinkEventHandler;
  private final AttentionDataUpdateEventHandler attentionDataUpdateEventHandler;
  private final MeditationDataUpdateEventHandler meditationDataUpdateEventHandler;
  private final RawDataUpdateEventHandler rawDataUpdateEventHandler;

  private final SignalQualityUpdateEventHandler signalQualityUpdateEventHandler;

  private final BandPowerDataUpdateEventHandler bandPowerDataUpdateEventHandler;


  CoreNskAlgoSdkEventsController() {
    this.blinkEventHandler = new BlinkEventHandler();
    this.attentionDataUpdateEventHandler = new AttentionDataUpdateEventHandler();
    this.meditationDataUpdateEventHandler = new MeditationDataUpdateEventHandler();
    this.rawDataUpdateEventHandler = new RawDataUpdateEventHandler();
    this.signalQualityUpdateEventHandler = new SignalQualityUpdateEventHandler();
    this.bandPowerDataUpdateEventHandler = new BandPowerDataUpdateEventHandler();
  }

  public void fireEvent(HeadsetDataTypes type, Object data) {
    Log.i("Headset Data Update Event",String.format("Event Type: %s | Event Data: %d",type,data.toString()));
    switch (type) {
      case BLINK ->
          this.blinkEventHandler.fireEvent(new BlinkEvent(this, new BlinkData((Integer) data)));

      case ATTENTION -> this.attentionDataUpdateEventHandler.fireEvent(
          new AttentionDataUpdateEvent(this, new AttentionData((Integer) data)));

      case MEDITATION -> this.meditationDataUpdateEventHandler.fireEvent(
          new MeditationDataUpdateEvent(this, new MeditationData((Integer) data)));

      case RAW -> this.rawDataUpdateEventHandler.fireEvent(
          new RawDataUpdateEvent(this, new RawData((short[]) data)));

      case SIGNAL_QUALITY -> this.signalQualityUpdateEventHandler.fireEvent(
          new SignalQualityUpdateEvent(this, new SignalQualityData((Integer) data)));

      case BAND_POWER -> {
        float[] dataArr = (float[]) data;
        this.bandPowerDataUpdateEventHandler.fireEvent(
            new BandPowerDataUpdateEvent(this, new BandPowerData(dataArr[0], dataArr[1], dataArr[2],
                dataArr[3], dataArr[4])));
      }

      default -> throw new IllegalArgumentException("Unknown event type: " + type);
    }
  }

  public void addEventListener(EventListener listener) {
    if (listener instanceof IBlinkEventListener) {
      this.blinkEventHandler.addListener((IBlinkEventListener) listener);
    } else if (listener instanceof IAttentionDataUpdateEventListener) {
      this.attentionDataUpdateEventHandler.addListener((IAttentionDataUpdateEventListener) listener);
    } else if (listener instanceof IMeditationDataUpdateEventListener) {
      this.meditationDataUpdateEventHandler.addListener(
          (IMeditationDataUpdateEventListener) listener);
    } else if (listener instanceof IRawDataUpdateEventListener) {
      this.rawDataUpdateEventHandler.addListener((IRawDataUpdateEventListener) listener);
    } else if (listener instanceof ISignalQualityUpdateEventListener) {
      this.signalQualityUpdateEventHandler.addListener((ISignalQualityUpdateEventListener) listener);
    } else if (listener instanceof IBandPowerDataUpdateEventListener) {
      this.bandPowerDataUpdateEventHandler.addListener((IBandPowerDataUpdateEventListener) listener);
    } else {
      throw new IllegalArgumentException("Unknown listener type: " + listener.getClass().getName());
    }
  }

  public void removeEventListener(EventListener listener) {
    if (listener instanceof IBlinkEventListener) {
      this.blinkEventHandler.removeListener((IBlinkEventListener) listener);
    } else if (listener instanceof IAttentionDataUpdateEventListener) {
      this.attentionDataUpdateEventHandler.removeListener(
          (IAttentionDataUpdateEventListener) listener);
    } else if (listener instanceof IMeditationDataUpdateEventListener) {
      this.meditationDataUpdateEventHandler.removeListener(
          (IMeditationDataUpdateEventListener) listener);
    } else if (listener instanceof IRawDataUpdateEventListener) {
      this.rawDataUpdateEventHandler.removeListener((IRawDataUpdateEventListener) listener);
    } else if (listener instanceof ISignalQualityUpdateEventListener) {
      this.signalQualityUpdateEventHandler.removeListener(
          (ISignalQualityUpdateEventListener) listener);
    } else if (listener instanceof IBandPowerDataUpdateEventListener) {
      this.bandPowerDataUpdateEventHandler.removeListener(
          (IBandPowerDataUpdateEventListener) listener);
    } else {
      throw new IllegalArgumentException("Unknown listener type: " + listener.getClass().getName());
    }
  }

  public boolean containsListener(EventListener listener) {
    if (listener instanceof IBlinkEventListener) {
      return this.blinkEventHandler.containsListener((IBlinkEventListener) listener);
    } else if (listener instanceof IAttentionDataUpdateEventListener) {
      return this.attentionDataUpdateEventHandler.containsListener(
          (IAttentionDataUpdateEventListener) listener);
    } else if (listener instanceof IMeditationDataUpdateEventListener) {
      return this.meditationDataUpdateEventHandler.containsListener(
          (IMeditationDataUpdateEventListener) listener);
    } else if (listener instanceof IRawDataUpdateEventListener) {
      return this.rawDataUpdateEventHandler.containsListener((IRawDataUpdateEventListener) listener);
    } else if (listener instanceof ISignalQualityUpdateEventListener) {
      return this.signalQualityUpdateEventHandler.containsListener(
          (ISignalQualityUpdateEventListener) listener);
    } else if (listener instanceof IBandPowerDataUpdateEventListener) {
      return this.bandPowerDataUpdateEventHandler.containsListener(
          (IBandPowerDataUpdateEventListener) listener);
    } else {
      throw new IllegalArgumentException("Unknown listener type: " + listener.getClass().getName());
    }
  }

}
