package com.example.wrappercore;

import ai.Model;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.wrappercore.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

  private AppBarConfiguration appBarConfiguration;
  private ActivityMainBinding binding;

  private float[] result = new float[]{4};

  private void modelTest() {
    Model modelHandler = new Model("https://learny-v1.onrender.com/api/v1/downloadModel");

    float[][] inputData = new float[][]{{1, 2}};

    result = modelHandler.runInference(inputData);
//    result = new float[]{3};

  }

  public void getDetail() {
    UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
    HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
    UsbDevice mDevice = null;
    for (UsbDevice device : deviceList.values()) {
      if (device.getDeviceId() == 1002) {
        mDevice = device;
      }
      assert mDevice != null;
      Log.i("Test_Android_USB", "test dev" + mDevice.getDeviceId());
      Log.i("Test_Android_USB",
          "Device Name: " + device.getDeviceName() + " Device ID: " + device.getDeviceId() + " Vendor: "
              + device.getVendorId() + " Product: " + device.getProductId());

    }
    if (mDevice != null) {
      Log.i("Test_Android_USB_1", "Device: " + mDevice.getVendorId());
//      UsbInterface usbInterface = mDevice.getInterface(0);
//      UsbEndpoint endpoint = usbInterface.getEndpoint(0);
//      UsbDeviceConnection connection = manager.openDevice(mDevice);
//      byte[] DATA = "1".getBytes();
//      boolean forceClaim = true;
//
//      connection.claimInterface(mDevice.getInterface(0), true);
//      connection.bulkTransfer(endpoint, DATA, DATA.length, 0);

// Initialize UsbRequest
//      UsbRequest usbRequest = new UsbRequest();
//      usbRequest.initialize(connection, endpoint);

// Prepare the data to send asynchronously
//
//// Wrap the data in a ByteBuffer
//      ByteBuffer buffer = ByteBuffer.allocate(dataToSend.length);
//      buffer.put(dataToSend);
//      buffer.flip(); // Prepare the buffer for reading
//
//// Queue the asynchronous request
//      new Thread(new Runnable() {
//        @Override
//        public void run() {
//          if (usbRequest.queue(buffer, buffer.limit())) {
//            // Wait for the result
//            UsbRequest response = connection.requestWait();
//            if (response == usbRequest) {
//              // Request completed successfully
//              // You can handle the response if needed
//              Log.i("Test_Android_USB", "Request completed successfully");
//              Log.i("Test_Android_USB", "Response: " + Arrays.toString(buffer.array()));
//            } else {
//              Log.e("Test_Android_USB", "Error sending request");
//            }
//          } else {
//            Log.e("Test_Android_USB", "Error queueing request");
//          }
//
//// Release the USB interface when done
//          connection.releaseInterface(usbInterface);
//        }
//      }).start();
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

//    modelTest();

    getDetail();

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);

    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    binding.fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action ", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onSupportNavigateUp() {
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    return NavigationUI.navigateUp(navController, appBarConfiguration)
        || super.onSupportNavigateUp();
  }
}