package com.example.wrappercore;

import android.bluetooth.BluetoothManager;
import com.example.wrappercore.control.ControlManager;
import headset.MindWaveMobile2;
import java.util.EventListener;

public class WrapperCore {

  private final MindWaveMobile2 mindWaveMobile2;
  private final ControlManager controlManager;

  //FIXME: This constructor is not the production one it is for testing purposes only
  public WrapperCore() {
    this.controlManager = new ControlManager();
    this.mindWaveMobile2 = new MindWaveMobile2();
    this.mindWaveMobile2.addEventListener(controlManager.getModeManager());
  }

  //FIXME: This constructor is missing the serial usb connection initialization
  public WrapperCore(BluetoothManager bluetoothManager, String macAddress) {
    this.controlManager = new ControlManager();
    this.mindWaveMobile2 = new MindWaveMobile2(bluetoothManager, macAddress);
    this.mindWaveMobile2.connect();
    this.mindWaveMobile2.addEventListener(controlManager.getModeManager());
  }

  public void addListener(EventListener listener) {
    controlManager.addListener(listener);
  }

  public void removeListener(EventListener listener) {
    controlManager.removeListener(listener);
  }

}
