package com.example.wrappercore;

import android.bluetooth.BluetoothManager;
import com.example.wrappercore.controlMode.ControlModeManager;
import com.example.wrappercore.controlMode.events.controlSwitch.ISwitchEventListener;
import headset.MindWaveMobile2;
import java.util.EventListener;

public class WrapperCore {

  private final ControlModeManager controlModeManager;

  private final MindWaveMobile2 mindWaveMobile2;

  //FIXME: This constructor is not the production one it is for testing purposes only
  public WrapperCore() {
    this.controlModeManager = new ControlModeManager();
    this.mindWaveMobile2 = new MindWaveMobile2();
    this.mindWaveMobile2.addEventListener(this.controlModeManager);
  }

  //FIXME: This constructor is missing the serial usb connection initialization
  public WrapperCore(BluetoothManager bluetoothManager, String macAddress) {
    this.controlModeManager = new ControlModeManager();
    this.mindWaveMobile2 = new MindWaveMobile2(bluetoothManager, macAddress);
    this.mindWaveMobile2.connect();
    this.mindWaveMobile2.addEventListener(this.controlModeManager);
  }

  public void addListener(EventListener listener) {
    if (listener instanceof ISwitchEventListener) {
      controlModeManager.addSwitchEventListener((ISwitchEventListener) listener);
    } else {
      throw new IllegalArgumentException("Listener not supported");
    }
  }

  public void removeListener(EventListener listener) {
    if (listener instanceof ISwitchEventListener) {
      controlModeManager.removeSwitchEventListener((ISwitchEventListener) listener);
    } else {
      throw new IllegalArgumentException("Listener not supported");
    }
  }


}
