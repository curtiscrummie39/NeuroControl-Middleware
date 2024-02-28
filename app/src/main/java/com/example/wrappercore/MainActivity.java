package com.example.wrappercore;

import ai.Model;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import java.util.Iterator;

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

    Log.i("Test Android USB", "Device List: " + deviceList.size());

    Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
    while (deviceIterator.hasNext()) {

      UsbDevice device = deviceIterator.next();

      String actionString = this.getPackageName() + ".action.USB_PERMISSION";

      PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new
          Intent(actionString), -1);
      manager.requestPermission(device, mPermissionIntent);

      manager.requestPermission(device, mPermissionIntent);
      String Model = device.getDeviceName();

      int DeviceID = device.getDeviceId();
      int Vendor = device.getVendorId();
      int Product = device.getProductId();
      int Class = device.getDeviceClass();
      int Subclass = device.getDeviceSubclass();

      Log.i("Test Android USB",
          "Device Name: " + Model + " Device ID: " + DeviceID + " Vendor: " + Vendor + " Product: " + Product
              + " Class: " + Class + " Subclass: " + Subclass);

    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

//    modelTest();
    
    getDetail();

    Log.e("Test Android FS", getApplicationContext().getFilesDir().getAbsolutePath());

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);

    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    binding.fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action " + String.valueOf(result[0]), Snackbar.LENGTH_LONG)
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