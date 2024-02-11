package headset.coreNskAlgo;

import com.neurosky.AlgoSdk.NskAlgoDataType;
import com.neurosky.AlgoSdk.NskAlgoSdk;
import com.neurosky.AlgoSdk.NskAlgoType;
import headset.events.nskAlgo.AlgoEventTypes;

public class CoreNskAlgoSdk extends NskAlgoSdk {

  private final CoreNskAlgoSdkEventsController eventsHandler = new CoreNskAlgoSdkEventsController();

  public CoreNskAlgoSdk() {
    super();

    this.setOnStateChangeListener(new OnStateChangeListener() {
      @Override
      public void onStateChange(int state, int reason) {
        eventsHandler.fireEvent(AlgoEventTypes.STATE, new int[]{state, reason});
      }
    });

    this.setOnAttAlgoIndexListener(new OnAttAlgoIndexListener() {
      @Override
      public void onAttAlgoIndex(int attention) {
        eventsHandler.fireEvent(AlgoEventTypes.ATTENTION, attention);
      }
    });

    this.setOnMedAlgoIndexListener(new OnMedAlgoIndexListener() {
      @Override
      public void onMedAlgoIndex(int meditation) {
        eventsHandler.fireEvent(AlgoEventTypes.MEDITATION, meditation);
      }
    });

    this.setOnEyeBlinkDetectionListener(new OnEyeBlinkDetectionListener() {
      @Override
      public void onEyeBlinkDetect(int strength) {
        eventsHandler.fireEvent(AlgoEventTypes.BLINK, strength);
      }
    });

    this.setOnSignalQualityListener(new OnSignalQualityListener() {
      @Override
      public void onSignalQuality(int signalQuality) {
        eventsHandler.fireEvent(AlgoEventTypes.SIGNAL_QUALITY, signalQuality);
      }
    });

    this.setOnBPAlgoIndexListener(new OnBPAlgoIndexListener() {
      @Override
      public void onBPAlgoIndex(float delta, float theta, float alpha, float beta, float gamma) {
        eventsHandler.fireEvent(AlgoEventTypes.BAND_POWER,
            new float[]{delta, theta, alpha, beta, gamma});
      }
    });

    startAlgo();
  }

  public void startAlgo() {
    NskAlgoInit(NskAlgoType.NSK_ALGO_TYPE_ATT.value +
        NskAlgoType.NSK_ALGO_TYPE_MED.value +
        NskAlgoType.NSK_ALGO_TYPE_BP.value +
        NskAlgoType.NSK_ALGO_TYPE_BLINK.value, "");
    NskAlgoStart(false);
  }

  public void stopAlgo() {
    NskAlgoUninit();
  }

  public void UpdateAlgoData(NskAlgoDataType dataType, Object data, int dataLength) {
    short[] dataArr = data instanceof Integer ? new short[]{(short) data} : (short[]) data;
    
    NskAlgoDataStream(dataType.value, dataArr, dataLength);
  }

  public CoreNskAlgoSdkEventsController getEventsHandler() {
    return this.eventsHandler;
  }

}
