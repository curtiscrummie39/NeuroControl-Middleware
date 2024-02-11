package headset.coreNskAlgo;

import android.util.Log;
import headset.events.AttentionData;
import headset.events.MeditationData;
import headset.events.nskAlgo.AlgoEventTypes;
import headset.events.nskAlgo.algoAttention.AlgoAttentionEvent;
import headset.events.nskAlgo.algoAttention.AlgoAttentionEventHandler;
import headset.events.nskAlgo.algoAttention.IAlgoAttentionEventListener;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerData;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerEvent;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerEventHandler;
import headset.events.nskAlgo.algoBandPower.IAlgoBandPowerEventListener;
import headset.events.nskAlgo.algoBlink.AlgoBlinkData;
import headset.events.nskAlgo.algoBlink.AlgoBlinkEvent;
import headset.events.nskAlgo.algoBlink.AlgoBlinkEventHandler;
import headset.events.nskAlgo.algoBlink.IAlgoBlinkEventListener;
import headset.events.nskAlgo.algoMeditation.AlgoMeditationEvent;
import headset.events.nskAlgo.algoMeditation.AlgoMeditationEventHandler;
import headset.events.nskAlgo.algoMeditation.IAlgoMeditationEventListener;
import headset.events.nskAlgo.algoSignalQuality.AlgoSignalQualityData;
import headset.events.nskAlgo.algoSignalQuality.AlgoSignalQualityEvent;
import headset.events.nskAlgo.algoSignalQuality.AlgoSignalQualityEventHandler;
import headset.events.nskAlgo.algoSignalQuality.IAlgoSignalQualityEventListener;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeEvent;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeEventHandler;
import headset.events.nskAlgo.algoStateChange.IAlgoStateChangeEventListener;
import java.util.EventListener;


public class CoreNskAlgoSdkEventsController {

  private final AlgoBlinkEventHandler algoBlinkEventHandler;
  private final AlgoBandPowerEventHandler algoBandPowerEventHandler;
  private final AlgoMeditationEventHandler algoMeditationEventHandler;
  private final AlgoAttentionEventHandler algoAttentionEventHandler;
  private final AlgoStateChangeEventHandler algoStateChangeEventHandler;
  private final AlgoSignalQualityEventHandler algoSignalQualityEventHandler;

  CoreNskAlgoSdkEventsController() {
    this.algoBlinkEventHandler = new AlgoBlinkEventHandler();
    this.algoBandPowerEventHandler = new AlgoBandPowerEventHandler();
    this.algoMeditationEventHandler = new AlgoMeditationEventHandler();
    this.algoAttentionEventHandler = new AlgoAttentionEventHandler();
    this.algoStateChangeEventHandler = new AlgoStateChangeEventHandler();
    this.algoSignalQualityEventHandler = new AlgoSignalQualityEventHandler();
  }

  public void fireEvent(AlgoEventTypes type, Object data) {
    Log.w("CoreNskAlgoSdkEventsController", "fireEvent: " + type + " " + data.getClass().getName());
    switch (type) {
      case STATE -> {
        int state = ((int[]) data)[0];
        int reason = ((int[]) data)[1];
        this.algoStateChangeEventHandler.fireEvent(
            new AlgoStateChangeEvent(this, state, reason));
      }

      case BLINK -> this.algoBlinkEventHandler.fireEvent(
          new AlgoBlinkEvent(this, new AlgoBlinkData((Integer) data)));

      case ATTENTION -> this.algoAttentionEventHandler.fireEvent(
          new AlgoAttentionEvent(this, new AttentionData((Integer) data)));

      case MEDITATION -> this.algoMeditationEventHandler.fireEvent(
          new AlgoMeditationEvent(this, new MeditationData((Integer) data)));

      case SIGNAL_QUALITY -> this.algoSignalQualityEventHandler.fireEvent(
          new AlgoSignalQualityEvent(this, new AlgoSignalQualityData((Integer) data)));

      case BAND_POWER -> {
        float[] dataArr = (float[]) data;
        this.algoBandPowerEventHandler.fireEvent(
            new AlgoBandPowerEvent(this, new AlgoBandPowerData(dataArr[0], dataArr[1], dataArr[2],
                dataArr[3], dataArr[4])));
      }

      default -> throw new IllegalArgumentException("Unknown event type: " + type);
    }
  }

  public void addEventListener(EventListener listener) {
    if (listener instanceof IAlgoBlinkEventListener) {
      this.algoBlinkEventHandler.addListener((IAlgoBlinkEventListener) listener);

    } else if (listener instanceof IAlgoBandPowerEventListener) {
      this.algoBandPowerEventHandler.addListener(
          (IAlgoBandPowerEventListener) listener);

    } else if (listener instanceof IAlgoAttentionEventListener) {
      this.algoAttentionEventHandler.addListener(
          (IAlgoAttentionEventListener) listener);

    } else if (listener instanceof IAlgoMeditationEventListener) {
      this.algoMeditationEventHandler.addListener(
          (IAlgoMeditationEventListener) listener);

    } else if (listener instanceof IAlgoSignalQualityEventListener) {
      this.algoSignalQualityEventHandler.addListener(
          (IAlgoSignalQualityEventListener) listener);

    } else if (listener instanceof IAlgoStateChangeEventListener) {
      this.algoStateChangeEventHandler.addListener(
          (IAlgoStateChangeEventListener) listener);

    } else {
      throw new IllegalArgumentException("Unknown listener type: " + listener.getClass().getName());
    }
  }

  public void removeEventListener(EventListener listener) {
    if (listener instanceof IAlgoBlinkEventListener) {
      this.algoBlinkEventHandler.removeListener((IAlgoBlinkEventListener) listener);

    } else if (listener instanceof IAlgoBandPowerEventListener) {
      this.algoBandPowerEventHandler.removeListener(
          (IAlgoBandPowerEventListener) listener);

    } else if (listener instanceof IAlgoAttentionEventListener) {
      this.algoAttentionEventHandler.removeListener(
          (IAlgoAttentionEventListener) listener);

    } else if (listener instanceof IAlgoMeditationEventListener) {
      this.algoMeditationEventHandler.removeListener(
          (IAlgoMeditationEventListener) listener);

    } else if (listener instanceof IAlgoSignalQualityEventListener) {
      this.algoSignalQualityEventHandler.removeListener(
          (IAlgoSignalQualityEventListener) listener);

    } else if (listener instanceof IAlgoStateChangeEventListener) {
      this.algoStateChangeEventHandler.removeListener(
          (IAlgoStateChangeEventListener) listener);

    } else {
      throw new IllegalArgumentException("Unknown listener type: " + listener.getClass().getName());
    }
  }

  public boolean containsListener(EventListener listener) {
    if (listener instanceof IAlgoBlinkEventListener) {
      return this.algoBlinkEventHandler.containsListener((IAlgoBlinkEventListener) listener);

    } else if (listener instanceof IAlgoBandPowerEventListener) {
      return this.algoBandPowerEventHandler.containsListener(
          (IAlgoBandPowerEventListener) listener);

    } else if (listener instanceof IAlgoAttentionEventListener) {
      return this.algoAttentionEventHandler.containsListener(
          (IAlgoAttentionEventListener) listener);

    } else if (listener instanceof IAlgoMeditationEventListener) {
      return this.algoMeditationEventHandler.containsListener(
          (IAlgoMeditationEventListener) listener);

    } else if (listener instanceof IAlgoSignalQualityEventListener) {
      return this.algoSignalQualityEventHandler.containsListener(
          (IAlgoSignalQualityEventListener) listener);

    } else if (listener instanceof IAlgoStateChangeEventListener) {
      return this.algoStateChangeEventHandler.containsListener(
          (IAlgoStateChangeEventListener) listener);

    } else {
      throw new IllegalArgumentException("Unknown listener type: " + listener.getClass().getName());
    }
  }
}
