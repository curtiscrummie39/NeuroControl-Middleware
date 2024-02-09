package headset;

import android.util.Log;
import headset.events.stateChange.IHeadsetStateChangeEventListener;
import java.util.EventListener;
import java.util.Objects;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;

import com.neurosky.connection.TgStreamReader;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.DataType.MindDataType;


public class MindWaveMobile2NeedRefactor {

  private BluetoothDevice bluetoothDevice;
  private TgStreamReader tgStreamReader;

  private TgStreamHandler tgStreamHandler=new TgStreamHandler() {
    @Override
    public void onDataReceived(int i, int i1, Object o) {
      switch (i){
        case MindDataType.CODE_ATTENTION -> Log.w("data recieved","Attention");
        case MindDataType.CODE_MEDITATION -> Log.w("data recieved","Meditation");
        case MindDataType.CODE_RAW -> Log.w("data recieved","Raw");
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
