package examples;

import nexusedge.protocol.NexusEdgeDevice;
import nexusedge.protocol.NexusEdgePortingProtocol;
import nexusedge.protocol.PortingPhase;
import nexusedge.events.PortingEventListener;

/**
 * Example demonstrating the Nexus Edge 6G connectivity with SEP-1 protocol.
 * 
 * This example shows how to:
 * 1. Create a Nexus Edge device
 * 2. Execute the SEP-1 porting protocol
 * 3. Monitor porting progress through events
 * 4. Verify device operational status
 */
public class NexusEdge6GExample {
    
    public static void main(String[] args) {
        System.out.println("=== Nexus Edge 6G Service Porting Example ===\n");
        
        // Create a Nexus Edge device
        String deviceId = "NX-6G/B-1-001";
        String macAddress = "00:11:22:33:44:55";
        NexusEdgeDevice device = new NexusEdgeDevice(deviceId, macAddress);
        
        System.out.println("Created device: " + device);
        System.out.println();
        
        // Create the porting protocol
        NexusEdgePortingProtocol protocol = new NexusEdgePortingProtocol(device);
        
        // Add event listener to monitor progress
        protocol.addListener(new PortingEventListener() {
            @Override
            public void onPhaseChange(PortingPhase oldPhase, PortingPhase newPhase) {
                System.out.println("Phase transition: " + oldPhase + " -> " + newPhase);
            }
            
            @Override
            public void onPortingComplete(NexusEdgeDevice device) {
                System.out.println("\n=== PORTING COMPLETE ===");
                printDeviceStatus(device);
            }
        });
        
        // Execute the SEP-1 porting protocol
        System.out.println("Starting SEP-1 porting protocol...\n");
        String simCredentials = "SIM-CREDENTIAL-12345";
        boolean success = protocol.executePortingProtocol(simCredentials);
        
        if (success) {
            System.out.println("\n✓ Device successfully ported to 6G network!");
            System.out.println("\nFinal device state:");
            printDeviceStatus(device);
        } else {
            System.out.println("\n✗ Porting failed!");
            System.out.println("Device state: " + device.getState());
        }
    }
    
    private static void printDeviceStatus(NexusEdgeDevice device) {
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│ Nexus Edge Device Status                    │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│ Device ID:         " + padRight(device.getDeviceId(), 23) + "│");
        System.out.println("│ MAC Address:       " + padRight(device.getMacAddress(), 23) + "│");
        System.out.println("│ State:             " + padRight(device.getState().toString(), 23) + "│");
        System.out.println("│ Connectivity:      " + padRight(device.getConnectivityStatus(), 23) + "│");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│ Performance Metrics:                        │");
        System.out.println("│ Latency:           " + String.format("%.3f µs", device.getLatencyMicroseconds()) + padRight("", 14) + "│");
        System.out.println("│ Jitter:            " + String.format("%.3f µs", device.getJitterMicroseconds()) + padRight("", 14) + "│");
        System.out.println("│ Throughput:        " + String.format("%.2f Tbps", device.getThroughputTbps()) + padRight("", 11) + "│");
        System.out.println("│ Prediction Lead:   " + device.getPredictionLeadTimeSeconds() + " seconds" + padRight("", 12) + "│");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│ Subsystem Status:                           │");
        System.out.println("│ Authentication:    " + padRight(device.getAuthenticationStatus().toString(), 23) + "│");
        System.out.println("│ Synchronization:   " + padRight(device.getSynchronizationStatus().toString(), 23) + "│");
        System.out.println("│ Antenna:           " + padRight(device.getAntennaStatus().toString(), 23) + "│");
        System.out.println("│ BCI:               " + padRight(device.getBciStatus().toString(), 23) + "│");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│ Fully Operational: " + padRight(device.isFullyOperational() ? "YES" : "NO", 23) + "│");
        System.out.println("└─────────────────────────────────────────────┘");
    }
    
    private static String padRight(String s, int n) {
        if (s.length() >= n) {
            return s.substring(0, n);
        }
        return s + " ".repeat(n - s.length());
    }
}
