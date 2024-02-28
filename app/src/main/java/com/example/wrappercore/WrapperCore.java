package com.example.wrappercore;

import ai.ModelController;
import android.bluetooth.BluetoothManager;
import com.example.wrappercore.control.ControlManager;
import headset.HeadsetController;
import java.util.EventListener;

public class WrapperCore {

  private final HeadsetController headsetController;
  private final ControlManager controlManager;
  private final ModelController modelController;

  //FIXME: put the correct PROD url
  private final String modelUrl = "https://learny-v1.onrender.com/api/v1/downloadModel";

  //FIXME: This constructor is not the production one it is for testing purposes only
  public WrapperCore() {
    this.controlManager = new ControlManager();
    this.headsetController = new HeadsetController();
    this.modelController = new ModelController(modelUrl);
    this.headsetController.addEventListener(controlManager.getModeManager());
    this.headsetController.addEventListener(this.modelController);
  }

  //FIXME: This constructor is missing the serial usb connection initialization
  public WrapperCore(BluetoothManager bluetoothManager, String macAddress) {
    this.controlManager = new ControlManager();
    this.headsetController = new HeadsetController(bluetoothManager, macAddress);
    this.modelController = new ModelController(modelUrl);
    this.headsetController.connect();
    this.headsetController.addEventListener(controlManager.getModeManager());
    this.headsetController.addEventListener(this.modelController);
  }

  public void addListener(EventListener listener) {
    controlManager.addListener(listener);
  }

  public void removeListener(EventListener listener) {
    controlManager.removeListener(listener);
  }

  //FIXME: This method is for testing purposes only
  public HeadsetController getMindWaveMobile2() {
    return headsetController;
  }

  //FIXME: This method is for testing purposes only
  public ControlManager getControlManager() {
    return controlManager;
  }

  //FIXME: This method is for testing purposes only
  public ModelController getModelController() {
    return modelController;
  }

}
