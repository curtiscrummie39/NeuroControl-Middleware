package com.example.wrappercore;

import Wheelchair.WheelchairController;
import ai.ModelController;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.hardware.usb.UsbManager;
import com.example.wrappercore.control.ControlManager;
import com.example.wrappercore.control.IControlManagerEventListener;
import headset.HeadsetController;
import headset.events.IHeadsetListener;
import nexusedge.protocol.NexusEdgeDevice;
import nexusedge.protocol.NexusEdgePortingProtocol;
import nexusedge.events.PortingEventListener;
import java.io.IOException;
import java.util.EventListener;

public class WrapperCore {

  private final HeadsetController headsetController;
  private final ControlManager controlManager;
  private final ModelController modelController;
  private final WheelchairController wheelchairController;
  private final String deviceMacAddress;
  private NexusEdgePortingProtocol nexusEdgeProtocol;
  //NOTE: if you want to use your own ai-model
  //NOTE: change this to your own ai-model link in .tflite formate 
  //NOTE: and change the io vectors in ai component correspondingly.
  private final String modelUrl = "https://learny-v1.onrender.com/api/v1/downloadModel";


  public WrapperCore(BluetoothManager bluetoothManager, String macAddress)
      throws IOException {
    this.deviceMacAddress = macAddress;
    this.controlManager = new ControlManager();
    this.modelController = new ModelController(this.modelUrl);
    this.modelController.addListener(this.controlManager.getActionManager());
    this.headsetController = new HeadsetController(bluetoothManager, macAddress);
    this.headsetController.connect();
    this.headsetController.addEventListener(this.controlManager.getBlinkManager());
    this.headsetController.addEventListener(this.modelController);
  }

  //NOTE: this constructor is meant for the users who have the hardware (wheelchair) serial connection
  public WrapperCore(BluetoothManager bluetoothManager, String macAddress, UsbManager usbManager)
      throws IOException {
    this.deviceMacAddress = macAddress;
    this.controlManager = new ControlManager();
    this.wheelchairController = new WheelchairController(usbManager);
    this.modelController = new ModelController(this.modelUrl);
    this.modelController.addListener(this.controlManager.getActionManager());
    this.headsetController = new HeadsetController(bluetoothManager, macAddress);
    this.headsetController.connect();
    this.headsetController.addEventListener(this.controlManager.getBlinkManager());
    this.headsetController.addEventListener(this.modelController);
  }

  public void addListener(EventListener listener) {
    if (listener instanceof IControlManagerEventListener) {
      controlManager.addListener(listener);
    } else if (listener instanceof IHeadsetListener) {
      headsetController.addEventListener(listener);
    }
  }

  public void removeListener(EventListener listener) {
    if (listener instanceof IControlManagerEventListener) {
      controlManager.removeListener(listener);
    } else if (listener instanceof IHeadsetListener) {
      headsetController.removeEventListener(listener);
    } else if (listener instanceof PortingEventListener && nexusEdgeProtocol != null) {
      nexusEdgeProtocol.removeListener((PortingEventListener) listener);
    }
  }

  /**
   * Enables Nexus Edge 6G connectivity with SEP-1 protocol.
   * This provides ultra-low latency (< 1 µs), deterministic communication,
   * and enhanced BCI integration over 6G networks.
   * 
   * @param deviceId Unique identifier for the Nexus Edge device
   * @param simCredentials SIM/eSIM credentials for 6G authentication
   * @return true if porting succeeds and device is fully operational
   */
  public boolean enableNexusEdge6G(String deviceId, String simCredentials) {
    NexusEdgeDevice device = new NexusEdgeDevice(deviceId, deviceMacAddress);
    nexusEdgeProtocol = new NexusEdgePortingProtocol(device);
    return nexusEdgeProtocol.executePortingProtocol(simCredentials);
  }

  /**
   * Gets the Nexus Edge device if 6G connectivity is enabled.
   * @return the NexusEdgeDevice, or null if not enabled
   */
  public NexusEdgeDevice getNexusEdgeDevice() {
    return nexusEdgeProtocol != null ? nexusEdgeProtocol.getDevice() : null;
  }

  /**
   * Gets the Nexus Edge porting protocol if 6G connectivity is enabled.
   * @return the NexusEdgePortingProtocol, or null if not enabled
   */
  public NexusEdgePortingProtocol getNexusEdgeProtocol() {
    return nexusEdgeProtocol;
  }

  //NOTE: enable if you have hardware serial connection
  // public void makeWheelchairGoForward() {
  //   assert wheelchairController.forward():"Connection is null";
  // }

  // public void makeWheelchairGoLeft() {
  //   assert wheelchairController.left():"Connection is null";
  // }

  // public void makeWheelchairGoRight() {
  //   assert wheelchairController.right():"Connection is null";
  // }

  // public void makeWheelchairStop() {
  //   assert wheelchairController.stop():"Connection is null";
  // }
}
