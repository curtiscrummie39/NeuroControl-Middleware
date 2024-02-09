package headset;

import com.neurosky.AlgoSdk.NskAlgoSdk;
import com.neurosky.AlgoSdk.NskAlgoDataType;
import com.neurosky.AlgoSdk.NskAlgoType;

import headset.events.HeadsetDataTypes;

public class CoreNskAlgoSdk extends NskAlgoSdk {

  private final CoreNskAlgoSdkEventsController eventsHandler = new CoreNskAlgoSdkEventsController();

  private final short[] rawData = new short[512];
  private int rawDataIdx = 0;

  public CoreNskAlgoSdk() {
    super();
    startAlgo();

    this.setOnAttAlgoIndexListener(
        att -> this.eventsHandler.fireEvent(HeadsetDataTypes.ATTENTION, att));

    this.setOnMedAlgoIndexListener(
        med -> this.eventsHandler.fireEvent(HeadsetDataTypes.MEDITATION, med));

    this.setOnEyeBlinkDetectionListener(
        strength -> this.eventsHandler.fireEvent(HeadsetDataTypes.BLINK, strength));

    this.setOnSignalQualityListener(
        signalQualityLevel -> this.eventsHandler.fireEvent(
            HeadsetDataTypes.SIGNAL_QUALITY, signalQualityLevel));

    this.setOnBPAlgoIndexListener(
        (delta, theta, alpha, beta, gamma) -> this.eventsHandler.fireEvent(
            HeadsetDataTypes.BAND_POWER, new float[]{delta, theta, alpha, beta, gamma}));
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
