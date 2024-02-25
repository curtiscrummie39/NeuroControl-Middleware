package com.example.wrappercore;

import ai.ModelController;
import android.bluetooth.BluetoothManager;
import com.example.wrappercore.control.ControlManager;
import headset.MindWaveMobile2;
import java.util.EventListener;

public class WrapperCore {

  private final MindWaveMobile2 mindWaveMobile2;
  private final ControlManager controlManager;
  private final ModelController modelController;

  //FIXME: put the correct PROD url
  private final String modelUrl = "https://learny-v1.onrender.com/api/v1/downloadModel";

  //FIXME: This constructor is not the production one it is for testing purposes only
  public WrapperCore() {
    this.controlManager = new ControlManager();
    this.mindWaveMobile2 = new MindWaveMobile2();
    this.modelController = new ModelController(modelUrl);
    this.mindWaveMobile2.addEventListener(controlManager.getModeManager());
    this.mindWaveMobile2.addEventListener(this.modelController);
  }

  //FIXME: This constructor is missing the serial usb connection initialization
  public WrapperCore(BluetoothManager bluetoothManager, String macAddress) {
    this.controlManager = new ControlManager();
    this.mindWaveMobile2 = new MindWaveMobile2(bluetoothManager, macAddress);
    this.modelController = new ModelController(modelUrl);
    this.mindWaveMobile2.connect();
    this.mindWaveMobile2.addEventListener(controlManager.getModeManager());
    this.mindWaveMobile2.addEventListener(this.modelController);
  }

  public void addListener(EventListener listener) {
    controlManager.addListener(listener);
  }

  public void removeListener(EventListener listener) {
    controlManager.removeListener(listener);
  }

  //FIXME: This method is for testing purposes only
  public MindWaveMobile2 getMindWaveMobile2() {
    return mindWaveMobile2;
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
