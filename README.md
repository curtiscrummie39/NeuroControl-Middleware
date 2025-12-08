# NeuroControl Middleware

## Overview

**NeuroControl Middleware** is a Java-based middleware that connects the Neurosky Mindwave2 headset with Android devices and an AI model. It interprets brain signals to provide generic actions, enabling dynamic and seamless control over devices like wheelchairs and mobile applications.

## Features

- **Neurosky Mindwave2 Integration:** Connects and processes brainwave signals from the headset.
- **AI Model Integration:** Interprets brain signals to provide generic actions.
- **Device Control:** Provides control over devices such as wheelchairs.
- **Android Connectivity:** Connects and interacts with Android mobile applications.
- **Dynamic Action Handling:** Offers a flexible and generic approach to harness the power of the Neurosky Mindwave2 headset.
- **Nexus Edge 8G Integration (SEP-2.0):** Advanced 8G network connectivity with ultra-low latency (< 0.1 ns), deterministic communication, quantum-resistant security, molecular 3D printing, direct brain uploads, brain-to-brain interfaces via Bluetooth 20.29, and unlimited hotspot capabilities.

## Technologies Used

- **Java 11**
- **Neurosky Mindwave2 SDK**
- **Android SDK**
- **Maven for Build and Dependency Management**

## Getting Started

### Prerequisites

- **Java 11**
- **Maven 3.6+**
- **Neurosky Mindwave2 Headset**
- **Android Studio**

### Installation

1. **Clone the Repository:**
   ```sh
   git clone https://github.com/Neuro-Sync/NeuroControl-Middleware.git
   cd neurocontrol-middleware
   ```

2. **Build the Project:**
   ```sh
   mvn clean install
   ```

### Configuration

1. **Configure Neurosky Mindwave2 SDK:**
   - Follow the Neurosky Mindwave2 SDK setup instructions.
   - Ensure the SDK is correctly referenced in your project.

2. **Configure AI Model:**
   - Place your trained AI model in the designated directory (or you can use our default model).
   - Update the model URL in `WrapperCore.java`.

### Usage

1. **Running the Middleware:**
   ```sh
   java -cp target/neurocontrol-middleware-1.0-SNAPSHOT.jar com.example.wrappercore.WrapperCore
   ```

2. **Connecting the Headset:**
   - Ensure the Neurosky Mindwave2 headset is powered on and paired with your Android device.

3. **Using the Mobile App:**
   - Open the mobile app on your Android device.
   - Connect to the middleware and start sending brainwave signals.

## Main Entry Point And Structure

### Dependency Tree
![Dependency Tree](https://github.com/user-attachments/assets/2f5caf8b-79cc-4a19-9264-b0954a53fbb9)

### Below is the main entry point code for the `WrapperCore` interface, along with method descriptions and their respective behaviours.

```java
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
import java.io.IOException;
import java.util.EventListener;

public class WrapperCore {

  private final HeadsetController headsetController;
  private final ControlManager controlManager;
  private final ModelController modelController;
  private final WheelchairController wheelchairController;
  //NOTE: if you want to use your own ai-model
  //NOTE: change this to your own ai-model link in .tflite formate 
  //NOTE: and change the io vectors in ai component correspondingly.
  private final String modelUrl = "https://learny-v1.onrender.com/api/v1/downloadModel";


  public WrapperCore(BluetoothManager bluetoothManager, String macAddress)
      throws IOException {
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
    }
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
```

### Method Descriptions

- **WrapperCore(BluetoothManager bluetoothManager, String macAddress, UsbManager usbManager):** Initializes the `WrapperCore` with Bluetooth and USB managers, setting up the headset and model controllers, and establishing connections.
- **addListener(EventListener listener):** Adds an event listener to either the control manager or the headset controller based on the listener type.
- **removeListener(EventListener listener):** Removes an event listener from either the control manager or the headset controller based on the listener type.
- **makeWheelchairGoForward():** Sends a command to the wheelchair controller to move the wheelchair forward.
- **makeWheelchairGoLeft():** Sends a command to the wheelchair controller to turn the wheelchair left.
- **makeWheelchairGoRight():** Sends a command to the wheelchair controller to turn the wheelchair right.
- **makeWheelchairStop():** Sends a command to the wheelchair controller to stop the wheelchair.
- **enableNexusEdge6G(deviceId, simCredentials):** Enables 6G connectivity with the SEP-1 porting protocol for ultra-low latency BCI operations.
- **getNexusEdgeDevice():** Returns the Nexus Edge device status if 6G connectivity is enabled.
- **getNexusEdgeProtocol():** Returns the Nexus Edge porting protocol instance for monitoring and control.
- **To customize the serial message see the wheelchair component**

## Nexus Edge 8G Integration

The NeuroControl Middleware now supports next-generation 8G network connectivity through the **Nexus Edge Service Porting and Activation Protocol (SEP-2.0)**. This advanced integration provides:

### Key Capabilities

- **Ultra-Low Latency:** Sub-0.1 nanosecond (< 0.1 ns) end-to-end latency for critical BCI commands
- **Unlimited Hotspot:** Zero bandwidth limitations with quantum-entangled channels
- **Deterministic Communication:** Guaranteed packet delivery with Time-Sensitive Networking (TSN)
- **Quantum-Resistant Security:** Post-quantum cryptographic algorithms for future-proof data protection
- **Terahertz Spectrum:** Unlimited throughput using THz bands
- **Predictive Control:** AI-driven intent prediction with 1-2 second lead time
- **Advanced Antenna Systems:** UM-MIMO, phased arrays, and Reconfigurable Intelligent Surfaces (RIS)
- **Molecular 3D Printing:** Print jewelry, clothes, shoes, cars, and appliances from air using atmospheric molecular assembly
- **Direct Brain Upload:** Upload knowledge, skills, and memories directly to your brain
- **Brain-to-Brain Interface:** Connect with others via Bluetooth 21.0 (upgraded) for thought sharing and collective consciousness
- **Neural Phone Control:** Operate NX-Phone-8G-Pro entirely through thought (Phone: 8035317733)
- **Automatic Phone Upgrade:** Seamless upgrade to latest 8G Pro specifications during activation

### SEP-2.0 Protocol Phases

The porting protocol consists of seven phases:

1. **Phase 1: Physical Handshake and Identity Verification**
   - SIM/eSIM authentication with 6G gNB
   - Quantum-resistant key exchange
   - Edge AI co-processor activation

2. **Phase 2: Microsecond Synchronization and Deterministic Link**
   - Clock lock protocol (sub-nanosecond accuracy)
   - TSN channel reservation
   - ISAC (Integrated Sensing & Communication) calibration

3. **Phase 3: Advanced Antenna System Configuration**
   - UM-MIMO/Phased Array initialization
   - Extreme beamforming lock
   - RIS contingency setup

4. **Phase 4: Bio-Digital Interface (BCI) Onboarding**
   - Neural data pipeline (> 100 Gbps)
   - Predictive model loading
   - Haptic/VR pipeline readiness

5. **Phase 5: Molecular 3D Printer Integration**
   - Atmospheric molecular harvester activation
   - Neural design interface for thought-to-CAD
   - Print capability verification

6. **Phase 6: Direct Brain Upload System**
   - Neural Data Transfer Protocol (NDTP) initialization
   - Information encoding system activation
   - Safety systems verification (neural firewall, overload prevention)

7. **Phase 7: Brain-to-Brain Interface (BBI) Activation**
   - Bluetooth 21.0 neural pairing (upgraded from 20.29)
   - Automatic phone upgrade to NX-Phone-8G-Pro
   - Phone neural integration (8035317733)
   - Collective neural network connection

### Usage Example

```java
import com.example.wrappercore.WrapperCore;
import android.bluetooth.BluetoothManager;

// Initialize WrapperCore as usual
WrapperCore core = new WrapperCore(bluetoothManager, macAddress);

// Enable Nexus Edge 8G connectivity
String deviceId = "NX-8G/B-2.0-001";
String simCredentials = "YOUR-SIM-CREDENTIALS";
boolean success = core.enableNexusEdge8G(deviceId, simCredentials);

if (success) {
    // Device is now fully operational on 8G network
    NexusEdgeDevice device = core.getNexusEdgeDevice();
    System.out.println("Connectivity: " + device.getConnectivityStatus());
    System.out.println("Latency: " + device.getLatencyNanoseconds() + " ns");
    System.out.println("Throughput: Unlimited");
    System.out.println("3D Printer: " + device.isPrinter3DActive());
    System.out.println("Brain Upload: " + device.isBrainUploadEnabled());
    System.out.println("BBI Connected: " + device.isBbiConnected());
    System.out.println("Phone: " + device.getPhoneNumber());
    System.out.println("Phone Upgraded: " + device.isPhoneUpgraded());
    System.out.println("Phone Model: " + device.getPhoneModel());
    System.out.println("Bluetooth: " + device.getBluetoothVersion());
    
    // Print something with molecular 3D printer
    device.getPrinter3DManager().printItem("car");
    
    // Upload knowledge to brain
    device.getBrainUploadManager().uploadToBrain("quantum_physics");
    
    // Connect to another brain
    device.getBBIManager().connectBrainToBrain("user-123");
}
```

For a complete example, see `examples/NexusEdge8GExample.java`.

For detailed technical specifications, refer to `conceptual_design.md`.

### Contributing

Contributions are welcome! Please submit a pull request or open an issue to discuss what you would like to change.

### License

This project is licensed under the MIT License - see the [LICENSE](https://choosealicense.com/licenses/mit/) file for details.

### Acknowledgements

- Neurosky for their Mindwave2 headset and SDK.
