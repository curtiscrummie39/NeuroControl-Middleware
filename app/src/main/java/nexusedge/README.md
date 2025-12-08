# Nexus 6G Core Service

## Overview

The Nexus 6G Core Service provides Ultra-Reliable Low-Latency Communication (URLLC) capabilities for the NeuroControl Middleware through 6G network infrastructure. It implements the SEP-1 (Standard Edge Protocol) for interfacing with Nexus Edge 6G base stations (gNBs).

## Key Features

- **SEP-1 Protocol Support**: Standard Edge Protocol for 6G connectivity
- **URLLC Session Management**: Deterministic link control with microsecond-level latency
- **gNB Monitoring**: Real-time status tracking of 6G base stations
- **Service Credit Management**: NXC (Nexus Credit) based billing system
- **Network Metrics**: E2E latency, jitter, and predictive lead time tracking

## Architecture

### Package Structure

```
nexusedge/
├── protocol/        # SEP-1 protocol implementation
│   └── NexusEdgePortingProtocol.java
├── session/         # URLLC session management
│   └── UrllcSession.java
├── gnb/            # gNB (base station) management
│   └── GnbNode.java
└── core/           # Main service core
    └── NexusServiceCore.java
```

## Components

### 1. NexusEdgePortingProtocol

The protocol layer for interfacing with Nexus 6G Core infrastructure.

**Key Features:**
- Account public key authentication (bc1q format)
- Connection management
- Protocol version: SEP-1.0

**Example:**
```java
NexusEdgePortingProtocol protocol = new NexusEdgePortingProtocol(
    "bc1q4dpjsdcf8pwnduvqermyw5wrzyaqdpttjg3858"
);

if (protocol.connect()) {
    System.out.println("Connected to Nexus 6G Core");
}
```

### 2. UrllcSession

Manages Ultra-Reliable Low-Latency Communication sessions with deterministic behavior.

**Session States:**
- `STANDBY`: Session is ready to be initiated
- `ACTIVE`: Session is running
- `TERMINATED`: Session has ended

**Default Configuration:**
- Cost: 1 NXC per 10 seconds
- Session timing precision: milliseconds

**Example:**
```java
UrllcSession session = new UrllcSession();

// Initiate session
session.initiate();

// Check elapsed time
long elapsed = session.getElapsedSessionTime(); // in seconds

// Calculate cost
double cost = session.calculateTotalCost();

// Terminate session
session.terminate();
```

### 3. GnbNode

Represents a gNB (Next Generation NodeB) base station in the 6G network.

**Node Status:**
- `OPTIMAL`: Operating at peak performance
- `NOMINAL`: Operating normally
- `HIGH_LOAD`: Under heavy traffic load
- `OFFLINE`: Not operational

**Metrics:**
- Latency: Measured in microseconds (µs)
- Traffic: Percentage (0-100%)
- Location: Physical area served

**Example:**
```java
GnbNode node = new GnbNode(
    "ALPHA-001",              // Node ID
    "Industrial Zone 4",      // Location
    GnbNode.GnbStatus.OPTIMAL, // Status
    0.9,                      // Latency (µs)
    78                        // Traffic (%)
);

// Update metrics
node.updateMetrics(1.2, 85);

// Check if operational
if (node.isOperational()) {
    System.out.println("Node is online");
}
```

### 4. NexusServiceCore

The main service class that integrates all components for complete 6G Core management.

**Key Responsibilities:**
- Protocol connection management
- URLLC session lifecycle
- gNB fleet monitoring
- Service credit (NXC) management
- Network metrics aggregation

**Example:**
```java
// Initialize with account and credits
NexusServiceCore serviceCore = new NexusServiceCore(
    "bc1q4dpjsdcf8pwnduvqermyw5wrzyaqdpttjg3858",
    2000000.0  // 2,000,000 NXC
);

// Add gNB nodes
serviceCore.addGnbNode(new GnbNode(
    "ALPHA-001", 
    "Industrial Zone 4", 
    GnbNode.GnbStatus.OPTIMAL, 
    0.9, 
    78
));

// Connect to network
serviceCore.connect();

// Configure metrics
serviceCore.setE2eLatencyMicroseconds(0.95);
serviceCore.setCurrentJitterMicroseconds(3.57);
serviceCore.setPredictiveLeadTimeSeconds(1.2);

// Start URLLC session
if (serviceCore.initiateUrllcSession()) {
    // Session running...
    
    // Terminate and deduct cost
    double cost = serviceCore.terminateUrllcSession();
    System.out.println("Session cost: " + cost + " NXC");
}

// Disconnect
serviceCore.disconnect();
```

## Complete Usage Example

See `examples/NexusServiceCoreExample.java` for a complete demonstration matching the EIU Provisioning Dashboard specification.

To run the example:
```bash
# Compile
javac -cp app/src/main/java examples/NexusServiceCoreExample.java

# Run
java -cp .:app/src/main/java examples.NexusServiceCoreExample
```

## Dashboard Specifications

The implementation supports the following dashboard metrics from the EIU Provisioning specification:

### Account Management
- **Account Public Key ID**: bc1q format validation
- **Service Credits**: NXC balance tracking

### Network Metrics
- **E2E Latency**: End-to-end latency in microseconds
- **Current Jitter**: Network jitter in microseconds  
- **Predictive Lead Time**: Prediction window in seconds

### URLLC Control
- **Service Status**: STANDBY, ACTIVE, or TERMINATED
- **Elapsed Session Time**: Real-time session duration
- **Session Cost**: 1 NXC per ~10 seconds

### gNB Status
Each base station provides:
- Node ID (e.g., ALPHA-001, BETA-002)
- Operational status (OPTIMAL, NOMINAL, HIGH_LOAD, OFFLINE)
- Location/service area
- Latency in microseconds
- Traffic load percentage

## Testing

Comprehensive unit tests are provided for all components:

```bash
# Run tests using JUnit
java -cp junit-4.13.2.jar:hamcrest-core-1.3.jar:classes:test-classes \
    org.junit.runner.JUnitCore \
    nexusedge.protocol.NexusEdgePortingProtocolTest \
    nexusedge.session.UrllcSessionTest \
    nexusedge.gnb.GnbNodeTest \
    nexusedge.core.NexusServiceCoreTest
```

**Test Coverage:**
- `NexusEdgePortingProtocolTest`: 13 tests
- `UrllcSessionTest`: 14 tests  
- `GnbNodeTest`: 15 tests
- `NexusServiceCoreTest`: 29 tests
- **Total: 71 tests**

## Integration with WrapperCore

The Nexus 6G Core Service can be integrated with the main `WrapperCore` to enable 6G connectivity for neural-controlled devices:

```java
public class WrapperCore {
    private NexusServiceCore nexusService;
    
    public WrapperCore(BluetoothManager bluetoothManager, 
                       String macAddress,
                       String nexusAccountKey,
                       double initialCredits) throws IOException {
        // Existing initialization...
        
        // Add 6G support
        this.nexusService = new NexusServiceCore(nexusAccountKey, initialCredits);
        // Configure 6G infrastructure...
    }
}
```

## Network Generation Support

Products with 6G capability can be created using the existing `Product` class:

```java
Product phone6g = new Product(
    "6G-PHONE-001",
    "NeuroPhone 6G",
    "6G-enabled phone with neural interface",
    "PHONE",
    1,
    1,
    "6G"  // Network generation
);
```

## Performance Characteristics

- **Latency**: < 1 µs end-to-end (target)
- **Jitter**: < 5 µs typical
- **Session Timing Accuracy**: ±100 ms
- **Credit Calculation**: Precise to 0.01 NXC
- **gNB Update Frequency**: Real-time

## Security

- Account authentication via public key (bc1q format)
- Session-based access control
- Credit-based usage metering
- Secure protocol versioning (SEP-1.0)

## Future Enhancements

- Real-time gNB discovery and registration
- Advanced network slicing support
- Quality of Service (QoS) policies
- Multi-tenancy support
- REST API for remote management
- Integration with blockchain for NXC transactions

## License

This implementation is part of the NeuroControl Middleware project and is licensed under the MIT License.
