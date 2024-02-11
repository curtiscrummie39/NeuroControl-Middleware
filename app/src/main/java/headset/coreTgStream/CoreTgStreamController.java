package headset.coreTgStream;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import com.neurosky.connection.TgStreamReader;
import java.util.Objects;

public class CoreTgStreamController {

  private CoreTgStreamHandler coreTgStreamHandler;
  private BluetoothDevice bluetoothDevice;
  private TgStreamReader tgStreamReader;

  public CoreTgStreamController(BluetoothManager bluetoothManager, String deviceName) {
    BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
    this.bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceName);
    this.coreTgStreamHandler = new CoreTgStreamHandler();
  }

  public void connect() {
    if (Objects.isNull(this.tgStreamReader)) {
      this.tgStreamReader = new TgStreamReader(this.bluetoothDevice, this.coreTgStreamHandler);
      this.coreTgStreamHandler = new CoreTgStreamHandler(this.tgStreamReader);
      tgStreamReader.connect();
    }
  }

  private void restart() {
    if (Objects.nonNull(this.tgStreamReader) && this.tgStreamReader.isBTConnected()) {
      this.tgStreamReader.stop();
      this.tgStreamReader.close();
      this.tgStreamReader = null;
      this.connect();
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
}
