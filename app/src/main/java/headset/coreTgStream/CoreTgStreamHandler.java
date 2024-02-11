package headset.coreTgStream;

import android.util.Log;
import com.neurosky.AlgoSdk.NskAlgoDataType;
import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
import headset.coreNskAlgo.CoreNskAlgoSdk;
import headset.coreNskAlgo.CoreNskAlgoSdkEventsController;
import headset.events.headsetStateChange.HeadsetStateChangeEvent;
import headset.events.headsetStateChange.HeadsetStateChangeEventHandler;
import headset.events.headsetStateChange.HeadsetStateTypes;
import headset.events.headsetStateChange.IHeadsetStateChangeEventListener;
import headset.events.nskAlgo.IAlgoEventListener;
import headset.events.stream.IStreamEventListener;
import headset.events.stream.StreamEventTypes;
import headset.events.stream.streamRaw.StreamRawData;
import java.util.EventListener;
import java.util.Objects;


/*FIXME: Currently we don't have an event to handle the different states of NskAlgoSDK,
   so we are using the same event as the headset state change event.*/

public class CoreTgStreamHandler implements TgStreamHandler {

  private final CoreStreamEventsController eventsHandler;
  private final HeadsetStateChangeEventHandler headsetStateEventHandler;
  private final short[] raw_data = new short[512];
  private CoreNskAlgoSdk coreNskAlgoSdk;
  private int raw_data_index = 0;
  private TgStreamReader tgStreamReader;


  public CoreTgStreamHandler() {
    this.eventsHandler = new CoreStreamEventsController();
    this.headsetStateEventHandler = new HeadsetStateChangeEventHandler();
  }

  public CoreTgStreamHandler(TgStreamReader tgStreamReader) {
    this.eventsHandler = new CoreStreamEventsController();
    this.headsetStateEventHandler = new HeadsetStateChangeEventHandler();
    this.tgStreamReader = tgStreamReader;
  }

  @Override
  public void onDataReceived(int dataType, int data, Object obj) {
    switch (dataType) {
      case MindDataType.CODE_ATTENTION -> {
        eventsHandler.fireEvent(StreamEventTypes.ATTENTION, data);
        coreNskAlgoSdk.UpdateAlgoData(NskAlgoDataType.NSK_ALGO_DATA_TYPE_ATT, data, 1);
      }

      case MindDataType.CODE_MEDITATION -> {
        eventsHandler.fireEvent(StreamEventTypes.MEDITATION, data);
        coreNskAlgoSdk.UpdateAlgoData(NskAlgoDataType.NSK_ALGO_DATA_TYPE_MED, data, 1);
      }

      case MindDataType.CODE_POOR_SIGNAL -> {
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.POOR_SIGNAL));

        coreNskAlgoSdk.UpdateAlgoData(NskAlgoDataType.NSK_ALGO_DATA_TYPE_PQ, data, 1);
      }
      case MindDataType.CODE_RAW -> {
        raw_data[raw_data_index] = (short) data;
        if (raw_data_index >= 512) {
          eventsHandler.fireEvent(StreamEventTypes.RAW_DATA, new StreamRawData(raw_data));
          coreNskAlgoSdk.UpdateAlgoData(NskAlgoDataType.NSK_ALGO_DATA_TYPE_EEG, raw_data, 512);
          raw_data_index = 0;
        }
      }

      case MindDataType.CODE_EEGPOWER -> {
        int delta = ((int[]) obj)[0];
        Log.w("CoreTgStreamHandler", "EEG delta: " + delta);
        eventsHandler.fireEvent(StreamEventTypes.EEG, obj);
      }
    }
  }

  @Override
  public void onChecksumFail(byte[] payload, int length, int checksum) {
    headsetStateEventHandler.fireEvent(
        new HeadsetStateChangeEvent(this, HeadsetStateTypes.CHECK_SUM_FAIL));
  }

  @Override
  public void onRecordFail(int flag) {
    headsetStateEventHandler.fireEvent(
        new HeadsetStateChangeEvent(this, HeadsetStateTypes.RECORD_FAIL));
  }

  @Override
  public void onStatesChanged(int connectionStates) {
    switch (connectionStates) {
      case ConnectionStates.STATE_CONNECTING -> Log.w("CoreTgStreamHandler", "Connecting...");

      case ConnectionStates.STATE_CONNECTED -> {
        if (Objects.nonNull(tgStreamReader)) {
          tgStreamReader.setGetDataTimeOutTime(20);
          tgStreamReader.start();
          this.coreNskAlgoSdk = new CoreNskAlgoSdk();
          Log.w("CoreTgStreamHandler", "tgStreamReader started");
        } else {
          Log.w("CoreTgStreamHandler", "tgStreamReader is null");
        }
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.CONNECTED));
      }

      case ConnectionStates.STATE_WORKING -> {
        tgStreamReader.startRecordRawData();
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.WORKING));
      }

      case ConnectionStates.STATE_GET_DATA_TIME_OUT -> {
        Log.w("CoreTgStreamHandler", "tgStreamReader get data time out");
        tgStreamReader.stop();
        tgStreamReader.close();
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.GET_DATA_TIME_OUT));
      }

      case ConnectionStates.STATE_STOPPED -> {
        Log.w("CoreTgStreamHandler", "tgStreamReader stopped");
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.STOPPED));
      }

      case ConnectionStates.STATE_DISCONNECTED -> {
        Log.w("CoreTgStreamHandler", "tgStreamReader disconnected");
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.DISCONNECTED));
      }

      case ConnectionStates.STATE_FAILED -> headsetStateEventHandler.fireEvent(
          new HeadsetStateChangeEvent(this, HeadsetStateTypes.CONNECTION_FAILED));

      case ConnectionStates.STATE_ERROR -> headsetStateEventHandler.fireEvent(
          new HeadsetStateChangeEvent(this, HeadsetStateTypes.ERROR));
    }
  }

  public void setTgStreamReader(TgStreamReader tgStreamReader) {
    this.tgStreamReader = tgStreamReader;
  }

  public void addEventListener(EventListener listener) {
    if (listener instanceof IHeadsetStateChangeEventListener) {
      this.getHeadsetStateEventHandler().addListener((IHeadsetStateChangeEventListener) listener);
    } else if (listener instanceof IStreamEventListener) {
      this.getStreamEventsHandler().addEventListener(listener);
    } else if (listener instanceof IAlgoEventListener) {
      this.getNskAlgoSdkEventsHandler().addEventListener(listener);
    } else {
      throw new IllegalArgumentException("Invalid listener type");
    }

  }

  public void removeEventListener(EventListener listener) {
    if (listener instanceof IHeadsetStateChangeEventListener) {
      this.getHeadsetStateEventHandler()
          .removeListener((IHeadsetStateChangeEventListener) listener);
    } else if (listener instanceof IStreamEventListener) {
      this.getStreamEventsHandler().removeEventListener(listener);
    } else if (listener instanceof IAlgoEventListener) {
      this.getNskAlgoSdkEventsHandler().removeEventListener(listener);
    } else {
      throw new IllegalArgumentException("Invalid listener type");
    }
  }

  private CoreStreamEventsController getStreamEventsHandler() {
    return this.eventsHandler;
  }

  private CoreNskAlgoSdkEventsController getNskAlgoSdkEventsHandler() {
    return this.coreNskAlgoSdk.getEventsHandler();
  }

  private HeadsetStateChangeEventHandler getHeadsetStateEventHandler() {
    return this.headsetStateEventHandler;
  }


}
