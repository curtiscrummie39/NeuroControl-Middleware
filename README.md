# NeuroControl Middleware

## Overview

**NeuroControl Middleware** is a Java-based middleware that connects the Neurosky Mindwave2 headset with Android devices and an AI model. It interprets brain signals to provide generic actions, enabling dynamic and seamless control over devices like wheelchairs and mobile applications.

## Features

- **Neurosky Mindwave2 Integration:** Connects and processes brainwave signals from the headset.
- **AI Model Integration:** Interprets brain signals to provide generic actions.
- **Device Control:** Provides control over devices such as wheelchairs.
- **Android Connectivity:** Connects and interacts with Android mobile applications.
- **Dynamic Action Handling:** Offers a flexible and generic approach to harness the power of the Neurosky Mindwave2 headset.
- **Nexus 6G Core Service:** Ultra-Reliable Low-Latency Communication (URLLC) with 6G network infrastructure support via SEP-1 protocol.

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
- **To customize the serial message see the wheelchair component**

## Nexus 6G Core Service

The middleware now includes support for Nexus 6G Core infrastructure, enabling Ultra-Reliable Low-Latency Communication (URLLC) for neural-controlled devices.

### Key Capabilities

- **SEP-1 Protocol**: Standard Edge Protocol for 6G connectivity
- **URLLC Sessions**: Deterministic link control with sub-microsecond latency
- **gNB Monitoring**: Real-time tracking of 6G base stations
- **NXC Credits**: Service credit management system
- **Network Metrics**: E2E latency, jitter, and predictive lead time

### Quick Start

```java
import nexusedge.core.NexusServiceCore;
import nexusedge.gnb.GnbNode;

// Initialize with account key and credits
NexusServiceCore service = new NexusServiceCore(
    "bc1q4dpjsdcf8pwnduvqermyw5wrzyaqdpttjg3858",
    2000000.0  // 2 million NXC
);

// Add 6G base stations
service.addGnbNode(new GnbNode(
    "ALPHA-001", 
    "Industrial Zone 4",
    GnbNode.GnbStatus.OPTIMAL, 
    0.9, 
    78
));

// Connect and start URLLC session
service.connect();
service.initiateUrllcSession();
```

For complete documentation, see [app/src/main/java/nexusedge/README.md](app/src/main/java/nexusedge/README.md).

For a working example, run:
```bash
java -cp .:app/src/main/java examples.NexusServiceCoreExample
```

### Contributing

Contributions are welcome! Please submit a pull request or open an issue to discuss what you would like to change.

### License

This project is licensed under the MIT License - see the [LICENSE](https://choosealicense.com/licenses/mit/) file for details.

### Acknowledgements

- Neurosky for their Mindwave2 headset and SDK.
