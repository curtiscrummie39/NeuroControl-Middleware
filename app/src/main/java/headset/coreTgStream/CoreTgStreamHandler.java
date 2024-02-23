package headset.coreTgStream;

import android.util.Log;
import com.neurosky.AlgoSdk.NskAlgoDataType;
import com.neurosky.AlgoSdk.NskAlgoSdk;
import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
import headset.coreNskAlgo.CoreNskAlgoSdk;
import headset.coreNskAlgo.CoreNskAlgoSdkEventsController;
import headset.events.AttentionData;
import headset.events.MeditationData;
import headset.events.SignalQualityData;
import headset.events.headsetStateChange.HeadsetStateChangeEvent;
import headset.events.headsetStateChange.HeadsetStateChangeEventHandler;
import headset.events.headsetStateChange.HeadsetStateTypes;
import headset.events.headsetStateChange.IHeadsetStateChangeEventListener;
import headset.events.nskAlgo.IAlgoEventListener;
import headset.events.nskAlgo.NskAlgoEvent;
import headset.events.stream.IStreamEventListener;
import headset.events.stream.StreamEvent;
import headset.events.stream.streamAttention.StreamAttentionEvent;
import headset.events.stream.streamBandPower.StreamBandPower;
import headset.events.stream.streamBandPower.StreamBandPowerEvent;
import headset.events.stream.streamMeditation.StreamMeditationEvent;
import headset.events.stream.streamRaw.StreamRawData;
import headset.events.stream.streamRaw.StreamRawDataEvent;
import headset.events.stream.streamSignalQuality.StreamSignalQualityEvent;
import java.util.EventListener;
import java.util.Objects;

public class CoreTgStreamHandler implements TgStreamHandler {

  private final CoreStreamEventsController eventsHandler;
  private final HeadsetStateChangeEventHandler headsetStateEventHandler;
  private final short[] raw_data = new short[512];
  private CoreNskAlgoSdk coreNskAlgoSdk;
  private int raw_data_index = 0;
  private TgStreamReader tgStreamReader;

  public CoreTgStreamHandler() {
    this.coreNskAlgoSdk = new CoreNskAlgoSdk();
    this.eventsHandler = new CoreStreamEventsController();
    this.headsetStateEventHandler = new HeadsetStateChangeEventHandler();
  }

  public CoreTgStreamHandler(TgStreamReader tgStreamReader) {
    this.coreNskAlgoSdk = new CoreNskAlgoSdk();
    this.eventsHandler = new CoreStreamEventsController();
    this.headsetStateEventHandler = new HeadsetStateChangeEventHandler();
    this.tgStreamReader = tgStreamReader;
  }

  @Override
  public void onDataReceived(int dataType, int data, Object obj) {
    switch (dataType) {
      case MindDataType.CODE_ATTENTION -> {
        short[] attValue = {(short) data};
        eventsHandler.fireEvent(new StreamAttentionEvent(this, new AttentionData(data)));
        NskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_ATT.value, attValue, 1);
      }
      case MindDataType.CODE_MEDITATION -> {
        short[] medValue = {(short) data};
        eventsHandler.fireEvent(new StreamMeditationEvent(this, new MeditationData(data)));
        NskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_MED.value, medValue, 1);
      }
      case MindDataType.CODE_POOR_SIGNAL -> {
        short[] psValue = {(short) data};
        eventsHandler.fireEvent(new StreamSignalQualityEvent(this, new SignalQualityData(data)));
        NskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_PQ.value, psValue, 1);
      }
      case MindDataType.CODE_RAW -> {
        raw_data[raw_data_index++] = (short) data;
        if (raw_data_index >= 512) {
          eventsHandler.fireEvent(new StreamRawDataEvent(this, new StreamRawData(raw_data)));
          NskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_EEG.value, raw_data, 512);
          raw_data_index = 0;
        }
      }
      case MindDataType.CODE_EEGPOWER -> {
        EEGPower bandPower = ((EEGPower) obj);
        eventsHandler.fireEvent(new StreamBandPowerEvent(this,
            new StreamBandPower(bandPower.delta, bandPower.theta, bandPower.lowAlpha,
                bandPower.highAlpha, bandPower.lowBeta, bandPower.highBeta, bandPower.lowGamma,
                bandPower.middleGamma)));
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
          //FIXME: This is will be enabled when we have the real headset connected
          this.coreNskAlgoSdk = new CoreNskAlgoSdk();
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
        tgStreamReader.stop();
        tgStreamReader.close();
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.GET_DATA_TIME_OUT));
      }

      case ConnectionStates.STATE_STOPPED -> {
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.STOPPED));
      }

      case ConnectionStates.STATE_DISCONNECTED -> {
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.DISCONNECTED));
      }

      case ConnectionStates.STATE_FAILED -> {
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.CONNECTION_FAILED));
      }

      case ConnectionStates.STATE_ERROR -> {
        headsetStateEventHandler.fireEvent(
            new HeadsetStateChangeEvent(this, HeadsetStateTypes.ERROR));
      }
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
      throw new IllegalArgumentException("Listener not supported");
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
      throw new IllegalArgumentException("Listener not supported");
    }
  }

  //FIXME: This method is for testing purposes only
  public boolean containsListener(EventListener listener) {
    if (listener instanceof IHeadsetStateChangeEventListener) {
      return this.getHeadsetStateEventHandler()
          .containsListener((IHeadsetStateChangeEventListener) listener);
    } else if (listener instanceof IStreamEventListener) {
      return this.getStreamEventsHandler().containsListener(listener);
    } else if (listener instanceof IAlgoEventListener) {
      return this.getNskAlgoSdkEventsHandler().containsListener(listener);
    } else {
      throw new IllegalArgumentException("Listener not supported");
    }
  }

  //FIXME: This method is for testing purposes only
  public void fireEvent(Object event) {
    if (event instanceof HeadsetStateChangeEvent) {
      this.getHeadsetStateEventHandler().fireEvent((HeadsetStateChangeEvent) event);
    } else if (event instanceof StreamEvent) {
      this.getStreamEventsHandler().fireEvent((StreamEvent) event);
    } else if (event instanceof NskAlgoEvent) {
      this.getNskAlgoSdkEventsHandler().fireEvent((NskAlgoEvent) event);
    } else {
      throw new IllegalArgumentException("Event not supported");
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
