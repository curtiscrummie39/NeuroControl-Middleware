package headset;

import com.neurosky.AlgoSdk.NskAlgoDataType;
import com.neurosky.AlgoSdk.NskAlgoSdk;
import com.neurosky.AlgoSdk.NskAlgoType;
import headset.events.HeadsetDataTypes;

public class CoreNskAlgoSdk extends NskAlgoSdk {

  private final CoreNskAlgoSdkEventsController eventsHandler = new CoreNskAlgoSdkEventsController();

  private final short[] rawData = new short[512];
  private int rawDataIdx = 0;

  public CoreNskAlgoSdk() {
    super();
    startAlgo();

    this.setOnAttAlgoIndexListener(new OnAttAlgoIndexListener() {
      @Override
      public void onAttAlgoIndex(int attention) {
        eventsHandler.fireEvent(HeadsetDataTypes.ATTENTION, attention);
      }
    });

    this.setOnMedAlgoIndexListener(new OnMedAlgoIndexListener() {
      @Override
      public void onMedAlgoIndex(int meditation) {
        eventsHandler.fireEvent(HeadsetDataTypes.MEDITATION, meditation);
      }
    });

    this.setOnEyeBlinkDetectionListener(new OnEyeBlinkDetectionListener() {
      @Override
      public void onEyeBlinkDetect(int strength) {
        eventsHandler.fireEvent(HeadsetDataTypes.BLINK, strength);
      }
    });

    this.setOnSignalQualityListener(new OnSignalQualityListener() {
      @Override
      public void onSignalQuality(int signalQuality) {
        eventsHandler.fireEvent(HeadsetDataTypes.SIGNAL_QUALITY, signalQuality);
      }
    });

    this.setOnBPAlgoIndexListener(new OnBPAlgoIndexListener() {
      @Override
      public void onBPAlgoIndex(float delta, float theta, float alpha, float beta, float gamma) {
        eventsHandler.fireEvent(HeadsetDataTypes.BAND_POWER,
            new float[]{delta, theta, alpha, beta, gamma});
      }
    });
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

  public void UpdateAlgoData(NskAlgoDataType dataType, int data, int dataLength) {
    if (dataType == NskAlgoDataType.NSK_ALGO_DATA_TYPE_EEG) {
      rawData[rawDataIdx++] = (short) data;
      if (rawDataIdx >= 512) {
        NskAlgoDataStream(dataType.value, rawData, rawDataIdx);
        this.eventsHandler.fireEvent(HeadsetDataTypes.RAW, rawData);
        rawDataIdx = 0;
      }
    } else {
      short[] dataArr = {(short) data};
      NskAlgoDataStream(dataType.value, dataArr, dataLength);
    }
  }

  public CoreNskAlgoSdkEventsController getEventsHandler() {
    return this.eventsHandler;
  }

}
