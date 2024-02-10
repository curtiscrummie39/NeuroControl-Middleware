package headset;

import com.neurosky.AlgoSdk.NskAlgoDataType;
import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
import headset.events.stateChange.HeadsetStateChangeEvent;
import headset.events.stateChange.HeadsetStateChangeEventHandler;
import headset.events.stateChange.HeadsetStateTypes;
import headset.events.stateChange.IHeadsetStateChangeEventListener;


/*FIXME: Currently we don't have an event to handle the different states of NskAlgoSDK,
   so we are using the same event as the headset state change event.*/

public class CoreTgStreamHandler implements TgStreamHandler {

  private final HeadsetStateChangeEventHandler headsetStateEventHandler;
  private final CoreNskAlgoSdk coreNskAlgoSdk;

  private TgStreamReader tgStreamReader;


  public CoreTgStreamHandler() {
    this.coreNskAlgoSdk = new CoreNskAlgoSdk();
    this.headsetStateEventHandler = new HeadsetStateChangeEventHandler();
  }

  public CoreTgStreamHandler(TgStreamReader tgStreamReader) {
    this.coreNskAlgoSdk = new CoreNskAlgoSdk();
    this.headsetStateEventHandler = new HeadsetStateChangeEventHandler();
    this.tgStreamReader = tgStreamReader;
  }

  @Override
  public void onDataReceived(int dataType, int data, Object obj) {
    switch (dataType) {
      case MindDataType.CODE_ATTENTION ->
          coreNskAlgoSdk.UpdateAlgoData(NskAlgoDataType.NSK_ALGO_DATA_TYPE_ATT, data, 1);
      case MindDataType.CODE_MEDITATION ->
          coreNskAlgoSdk.UpdateAlgoData(NskAlgoDataType.NSK_ALGO_DATA_TYPE_MED, data, 1);
      case MindDataType.CODE_POOR_SIGNAL -> {
        headsetStateEventHandler.fireEvent(headsetStateEventInit(HeadsetStateTypes.POOR_SIGNAL));
        coreNskAlgoSdk.UpdateAlgoData(NskAlgoDataType.NSK_ALGO_DATA_TYPE_PQ, data, 1);
      }
      case MindDataType.CODE_RAW ->
          coreNskAlgoSdk.UpdateAlgoData(NskAlgoDataType.NSK_ALGO_DATA_TYPE_EEG, data, 1);
    }
  }

  @Override
  public void onChecksumFail(byte[] payload, int length, int checksum) {
    headsetStateEventHandler.fireEvent(headsetStateEventInit(HeadsetStateTypes.CHECK_SUM_FAIL));
  }

  @Override
  public void onRecordFail(int flag) {
    headsetStateEventHandler.fireEvent(headsetStateEventInit(HeadsetStateTypes.RECORD_FAIL));
  }

  @Override
  public void onStatesChanged(int connectionStates) {
    switch (connectionStates) {
      case ConnectionStates.STATE_CONNECTED -> {
        this.tgStreamReader.start();
        headsetStateEventHandler.fireEvent(headsetStateEventInit(HeadsetStateTypes.CONNECTED));
      }
      case ConnectionStates.STATE_WORKING ->
          headsetStateEventHandler.fireEvent(headsetStateEventInit(HeadsetStateTypes.WORKING));
      case ConnectionStates.STATE_STOPPED ->
          headsetStateEventHandler.fireEvent(headsetStateEventInit(HeadsetStateTypes.STOPPED));
      case ConnectionStates.STATE_DISCONNECTED ->
          headsetStateEventHandler.fireEvent(headsetStateEventInit(HeadsetStateTypes.DISCONNECTED));
      case ConnectionStates.STATE_GET_DATA_TIME_OUT -> headsetStateEventHandler.fireEvent(
          headsetStateEventInit(HeadsetStateTypes.GET_DATA_TIME_OUT));
      case ConnectionStates.STATE_FAILED -> headsetStateEventHandler.fireEvent(
          headsetStateEventInit(HeadsetStateTypes.CONNECTION_FAILED));
      case ConnectionStates.STATE_ERROR ->
          headsetStateEventHandler.fireEvent(headsetStateEventInit(HeadsetStateTypes.ERROR));
    }
  }

  private HeadsetStateChangeEvent headsetStateEventInit(HeadsetStateTypes state) {
    return new HeadsetStateChangeEvent(this, state);
  }

  public void addHeadsetStateChangeEventListener(IHeadsetStateChangeEventListener listener) {
    headsetStateEventHandler.addListener(listener);
  }

  public void removeHeadsetStateChangeEventListener(IHeadsetStateChangeEventListener listener) {
    headsetStateEventHandler.removeListener(listener);
  }

  public void fireHeadsetStateChangeEvent(HeadsetStateChangeEvent event) {
    headsetStateEventHandler.fireEvent(event);
  }

  public boolean containsHeadsetStateChangeEventListener(
      IHeadsetStateChangeEventListener listener) {
    return headsetStateEventHandler.containsListener(listener);
  }

  public CoreNskAlgoSdk getCoreNskAlgoSdk() {
    return coreNskAlgoSdk;
  }

}
