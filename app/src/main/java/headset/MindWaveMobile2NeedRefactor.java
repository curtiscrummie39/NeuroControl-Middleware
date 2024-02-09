package headset;

import android.util.Log;
import headset.events.stateChange.IHeadsetStateChangeEventListener;
import java.util.EventListener;
import java.util.Objects;

import com.neurosky.AlgoSdk.NskAlgoSdk;
import com.neurosky.AlgoSdk.NskAlgoType;
import com.neurosky.AlgoSdk.NskAlgoDataType;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;

import com.neurosky.connection.TgStreamReader;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.DataType.MindDataType;


public class MindWaveMobile2NeedRefactor {

  private BluetoothDevice bluetoothDevice;
  private TgStreamReader tgStreamReader;

  private NskAlgoSdk nskAlgoSdk;
  private short raw_data[]=new short[512];
  private int raw_data_index=0;

  private TgStreamHandler tgStreamHandler=new TgStreamHandler() {
    @Override
    public void onDataReceived(int i, int i1, Object o) {
      switch (i) {
        case MindDataType.CODE_ATTENTION:
          short[] attValue = { (short) i1 };

          nskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_ATT.value, attValue, 1);
          break;
        case MindDataType.CODE_MEDITATION:
          short[] medValue = { (short) i1 };
          nskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_MED.value, medValue, 1);
          break;
        case MindDataType.CODE_POOR_SIGNAL:
          short[] psValue = { (short) i1 };
          nskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_PQ.value, psValue, 1);
          break;
        case MindDataType.CODE_RAW:
          raw_data[raw_data_index++] = (short) i1;
          if (raw_data_index == 512) {
            nskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_EEG.value, raw_data, raw_data_index);
            raw_data_index = 0;
          }
          break;
        default:
          break;
      }
    }

    @Override
    public void onStatesChanged(int i) {
        switch (i){
          case 1-> Log.w("State","calling connect and start");
          case 2->{
            Log.w("State","Calling start after connected");
            tgStreamReader.start();
          }
        }
    }

    @Override
    public void onChecksumFail(byte[] bytes, int i, int i1) {
        Log.w("Error","Check sum failure");
    }

    @Override
    public void onRecordFail(int i) {
      Log.w("Error","Record failure");
    }
  };
//  private final CoreTgStreamHandler coreTgStreamHandler;

  public MindWaveMobile2NeedRefactor() {
//    this.coreTgStreamHandler = new CoreTgStreamHandler();
  }

  public MindWaveMobile2NeedRefactor(BluetoothManager bluetoothManager, String deviceName) {
    BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
    this.bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceName);
//    this.coreTgStreamHandler = new CoreTgStreamHandler();
  }

  public void connect() {
    setUpAlgo();
    if (Objects.isNull(this.tgStreamReader)) {
      this.tgStreamReader = new TgStreamReader(this.bluetoothDevice,tgStreamHandler);
      //TODO check this line
      tgStreamReader.connectAndStart();
    }
  }

  public void disconnect() {
    if (Objects.nonNull(this.tgStreamReader)) {
      this.tgStreamReader.stop();
      this.tgStreamReader.close();
      this.tgStreamReader = null;
    }
  }

  private void setUpAlgo(){
    NskAlgoSdk nskAlgoSdk = new NskAlgoSdk();
    nskAlgoSdk.setOnBPAlgoIndexListener(new NskAlgoSdk.OnBPAlgoIndexListener() {
      @Override
      public void onBPAlgoIndex(float delta, float theta, float alpha, float beta, float gamma) {
        Log.w("Algo","BandPower");
      }
    });

    nskAlgoSdk.setOnEyeBlinkDetectionListener(new NskAlgoSdk.OnEyeBlinkDetectionListener() {
      @Override
      public void onEyeBlinkDetect(int eyeBlinkStrengthValue) {
        Log.w("Algo","Blink");
      }
    });

    nskAlgoSdk.setOnMedAlgoIndexListener(new NskAlgoSdk.OnMedAlgoIndexListener() {
      @Override
      public void onMedAlgoIndex(int meditationValue) {
        Log.w("Algo","Meditation");
      }
    });

    nskAlgoSdk.setOnSignalQualityListener(new NskAlgoSdk.OnSignalQualityListener() {
      @Override
      public void onSignalQuality(int signalQualityLevel) {
        Log.w("Algo","Signal Quality");
      }
    });

    int algoTypes = NskAlgoType.NSK_ALGO_TYPE_ATT.value +
        NskAlgoType.NSK_ALGO_TYPE_MED.value +
        NskAlgoType.NSK_ALGO_TYPE_BP.value +
        NskAlgoType.NSK_ALGO_TYPE_BLINK.value;
    nskAlgoSdk.NskAlgoInit(algoTypes, "");
    nskAlgoSdk.NskAlgoStart(false);
  }


  public void changeBluetoothDevice(BluetoothManager bluetoothManager, String deviceName) {
    if (Objects.nonNull(this.tgStreamReader)) {
      this.disconnect();
      BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
      this.bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceName);
      this.connect();
    }
  }

//  public void addEventListener(EventListener listener) {
//    if (listener instanceof IHeadsetStateChangeEventListener) {
//      this.coreTgStreamHandler.addHeadsetStateChangeEventListener(
//          (IHeadsetStateChangeEventListener) listener);
//    } else {
//      this.coreTgStreamHandler.getCoreNskAlgoSdk().getEventsHandler().addEventListener(listener);
//    }
//  }

//  public void removeEventListener(EventListener listener) {
//    if (listener instanceof IHeadsetStateChangeEventListener) {
//      this.coreTgStreamHandler.removeHeadsetStateChangeEventListener(
//          (IHeadsetStateChangeEventListener) listener);
//    } else {
//      this.coreTgStreamHandler.getCoreNskAlgoSdk().getEventsHandler().removeEventListener(listener);
//    }
//  }

//  public boolean containsListener(EventListener listener) {
//    if (listener instanceof IHeadsetStateChangeEventListener) {
//      return this.coreTgStreamHandler.containsHeadsetStateChangeEventListener(
//          (IHeadsetStateChangeEventListener) listener);
//    } else {
//      return this.coreTgStreamHandler.getCoreNskAlgoSdk().getEventsHandler()
//          .containsListener(listener);
//    }
//  }

  //FIXME: This method is not used anywhere in the code just for testing purpose
//  public CoreTgStreamHandler getCoreTgStreamHandler() {
//    return this.coreTgStreamHandler;
//  }

}
