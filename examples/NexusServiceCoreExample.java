package examples;

import nexusedge.core.NexusServiceCore;
import nexusedge.gnb.GnbNode;
import nexusedge.session.UrllcSession;

/**
 * Example demonstrating the Nexus 6G Core Service functionality
 * as described in the EIU Provisioning Dashboard specification.
 */
public class NexusServiceCoreExample {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("=== NEXUS SERVICE CORE ===");
    System.out.println("External Interface Unit (EIU) Provisioning Dashboard\n");

    // Account Public Key ID from the dashboard
    String accountPublicKey = "bc1q4dpjsdcf8pwnduvqermyw5wrzyaqdpttjg3858";
    
    // Initialize Nexus Service Core with 2,000,000 NXC credits
    NexusServiceCore serviceCore = new NexusServiceCore(accountPublicKey, 2000000.0);
    
    System.out.println("Account Public Key ID:");
    System.out.println(serviceCore.getProtocol().getAccountPublicKey());
    System.out.println();
    
    System.out.println("NXC SERVICE CREDIT");
    System.out.println(String.format("%.0f NXC", serviceCore.getServiceCredits()));
    System.out.println();

    // Configure network metrics
    serviceCore.setE2eLatencyMicroseconds(0.95);
    serviceCore.setCurrentJitterMicroseconds(3.57);
    serviceCore.setPredictiveLeadTimeSeconds(1.2);
    
    System.out.println("E2E Latency");
    System.out.println(String.format("< %.2f µs", serviceCore.getE2eLatencyMicroseconds()));
    System.out.println();
    
    System.out.println("Current Jitter");
    System.out.println(String.format("%.2f µs", serviceCore.getCurrentJitterMicroseconds()));
    System.out.println();
    
    System.out.println("Predictive Lead Time");
    System.out.println(String.format("%.1f s", serviceCore.getPredictiveLeadTimeSeconds()));
    System.out.println();

    // Set up gNB (base station) nodes
    System.out.println("Nexus 6G Core Status (Serving gNBs)");
    System.out.println("-----------------------------------");
    
    GnbNode alpha = new GnbNode("ALPHA-001", "Industrial Zone 4", 
                                 GnbNode.GnbStatus.OPTIMAL, 0.9, 78);
    serviceCore.addGnbNode(alpha);
    System.out.println("ALPHA-001");
    System.out.println("  Status: " + alpha.getStatus());
    System.out.println("  Location: " + alpha.getLocation());
    System.out.println("  Latency: " + alpha.getLatencyMicroseconds() + " µs");
    System.out.println("  Traffic: " + alpha.getTrafficPercentage() + "%");
    System.out.println();
    
    GnbNode beta = new GnbNode("BETA-002", "Medical Research Wing", 
                                GnbNode.GnbStatus.NOMINAL, 1.2, 45);
    serviceCore.addGnbNode(beta);
    System.out.println("BETA-002");
    System.out.println("  Status: " + beta.getStatus());
    System.out.println("  Location: " + beta.getLocation());
    System.out.println("  Latency: " + beta.getLatencyMicroseconds() + " µs");
    System.out.println("  Traffic: " + beta.getTrafficPercentage() + "%");
    System.out.println();
    
    GnbNode gamma = new GnbNode("GAMMA-003", "Remote Farming Sector", 
                                 GnbNode.GnbStatus.HIGH_LOAD, 3.5, 92);
    serviceCore.addGnbNode(gamma);
    System.out.println("GAMMA-003");
    System.out.println("  Status: " + gamma.getStatus());
    System.out.println("  Location: " + gamma.getLocation());
    System.out.println("  Latency: " + gamma.getLatencyMicroseconds() + " µs");
    System.out.println("  Traffic: " + gamma.getTrafficPercentage() + "%");
    System.out.println();

    // Connect to Nexus 6G Core infrastructure
    System.out.println("Connecting to Nexus 6G Core infrastructure...");
    boolean connected = serviceCore.connect();
    System.out.println("Connection status: " + (connected ? "CONNECTED" : "FAILED"));
    System.out.println();

    // URLLC Deterministic Link Control
    System.out.println("=== URLLC Deterministic Link Control ===");
    UrllcSession session = serviceCore.getUrllcSession();
    System.out.println("Service Status: " + session.getStatus());
    System.out.println("Elapsed Session Time: " + session.getElapsedSessionTime() + " seconds");
    System.out.println();

    // Initiate URLLC Session
    System.out.println("INITIATE URLLC SESSION (Cost: 1 NXC / ~10s)");
    if (serviceCore.initiateUrllcSession()) {
      System.out.println("Session initiated successfully!");
      System.out.println("Service Status: " + session.getStatus());
      System.out.println();
      
      // Simulate running session for 15 seconds
      System.out.println("Running session for 15 seconds...");
      for (int i = 0; i < 15; i++) {
        Thread.sleep(1000);
        System.out.println("Elapsed Session Time: " + session.getElapsedSessionTime() + " seconds");
      }
      System.out.println();
      
      // Calculate and display cost
      double estimatedCost = session.calculateTotalCost();
      System.out.println(String.format("Estimated session cost: %.2f NXC", estimatedCost));
      System.out.println();
      
      // Terminate session
      System.out.println("Terminating URLLC session...");
      double actualCost = serviceCore.terminateUrllcSession();
      System.out.println(String.format("Session terminated. Cost: %.2f NXC", actualCost));
      System.out.println(String.format("Remaining credits: %.0f NXC", serviceCore.getServiceCredits()));
      System.out.println("Service Status: " + session.getStatus());
    } else {
      System.out.println("Failed to initiate session (insufficient credits or already active)");
    }
    System.out.println();

    // Update network metrics based on gNB data
    serviceCore.updateNetworkMetrics();
    System.out.println("Updated E2E Latency (calculated from gNBs): " + 
                       String.format("%.2f µs", serviceCore.getE2eLatencyMicroseconds()));
    
    // Disconnect from service
    System.out.println();
    System.out.println("Disconnecting from Nexus 6G Core...");
    serviceCore.disconnect();
    System.out.println("Disconnected.");
  }
}
