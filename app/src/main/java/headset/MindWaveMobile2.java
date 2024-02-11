package headset;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import com.neurosky.connection.TgStreamReader;
import headset.coreTgStream.CoreTgStreamHandler;
import headset.events.stream.stateChange.IHeadsetStateChangeEventListener;
import java.util.EventListener;
import java.util.Objects;


public class MindWaveMobile2 {

  private CoreTgStreamHandler coreTgStreamHandler;
  private BluetoothDevice bluetoothDevice;
  private TgStreamReader tgStreamReader;

  //FIXME: This constructor is not used anywhere in the code just for testing purpose
  public MindWaveMobile2() {
    this.coreTgStreamHandler = new CoreTgStreamHandler();
  }

  public MindWaveMobile2(BluetoothManager bluetoothManager, String deviceName) {
    BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
    this.bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceName);
    this.coreTgStreamHandler = new CoreTgStreamHandler();
  }

  public void restart() {
    if (Objects.nonNull(this.tgStreamReader) && this.tgStreamReader.isBTConnected()) {
      this.tgStreamReader.stop();
      this.tgStreamReader.close();
      this.tgStreamReader = null;
      this.connect();
    }
  }

  public void connect() {
    if (Objects.isNull(this.tgStreamReader)) {
      this.tgStreamReader = new TgStreamReader(this.bluetoothDevice, this.coreTgStreamHandler);
      this.coreTgStreamHandler = new CoreTgStreamHandler(this.tgStreamReader);
      tgStreamReader.connect();
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

  public void addEventListener(EventListener listener) {
    if (listener instanceof IHeadsetStateChangeEventListener) {
      this.coreTgStreamHandler.addHeadsetStateChangeEventListener(
          (IHeadsetStateChangeEventListener) listener);
    } else {
      this.coreTgStreamHandler.getCoreNskAlgoSdk().getEventsHandler().addEventListener(listener);
    }
  }

  public void removeEventListener(EventListener listener) {
    if (listener instanceof IHeadsetStateChangeEventListener) {
      this.coreTgStreamHandler.removeHeadsetStateChangeEventListener(
          (IHeadsetStateChangeEventListener) listener);
    } else {
      this.coreTgStreamHandler.getCoreNskAlgoSdk().getEventsHandler().removeEventListener(listener);
    }
  }

  public boolean containsListener(EventListener listener) {
    if (listener instanceof IHeadsetStateChangeEventListener) {
      return this.coreTgStreamHandler.containsHeadsetStateChangeEventListener(
          (IHeadsetStateChangeEventListener) listener);
    } else {
      return this.coreTgStreamHandler.getCoreNskAlgoSdk().getEventsHandler()
          .containsListener(listener);
    }
  }

  //FIXME: This method is not used anywhere in the code just for testing purpose
  public CoreTgStreamHandler getCoreTgStreamHandler() {
    return this.coreTgStreamHandler;
  }

}
