package headset;

import android.bluetooth.BluetoothManager;
import headset.coreTgStream.CoreTgStreamController;


public class MindWaveMobile2 {

  private final CoreTgStreamController coreTgStreamReader;


  public MindWaveMobile2(BluetoothManager bluetoothManager, String deviceName) {
    this.coreTgStreamReader = new CoreTgStreamController(bluetoothManager, deviceName);
  }

  public void connect() {
    this.coreTgStreamReader.connect();
  }

  public void disconnect() {
    this.coreTgStreamReader.disconnect();
  }

  public void changeBluetoothDevice(BluetoothManager bluetoothManager, String deviceName) {
    this.coreTgStreamReader.changeBluetoothDevice(bluetoothManager, deviceName);
  }

//
//
//  public void addEventListener(EventListener listener) {
//    if (listener instanceof IHeadsetStateChangeEventListener) {
//      this.coreTgStreamHandler.addHeadsetStateChangeEventListener(
//          (IHeadsetStateChangeEventListener) listener);
//    } else {
//      this.coreTgStreamHandler.getCoreNskAlgoSdk().getEventsHandler().addEventListener(listener);
//    }
//  }
//
//  public void removeEventListener(EventListener listener) {
//    if (listener instanceof IHeadsetStateChangeEventListener) {
//      this.coreTgStreamHandler.removeHeadsetStateChangeEventListener(
//          (IHeadsetStateChangeEventListener) listener);
//    } else {
//      this.coreTgStreamHandler.getCoreNskAlgoSdk().getEventsHandler().removeEventListener(listener);
//    }
//  }
//
//  public boolean containsListener(EventListener listener) {
//    if (listener instanceof IHeadsetStateChangeEventListener) {
//      return this.coreTgStreamHandler.containsHeadsetStateChangeEventListener(
//          (IHeadsetStateChangeEventListener) listener);
//    } else {
//      return this.coreTgStreamHandler.getCoreNskAlgoSdk().getEventsHandler()
//          .containsListener(listener);
//    }
//  }
}
