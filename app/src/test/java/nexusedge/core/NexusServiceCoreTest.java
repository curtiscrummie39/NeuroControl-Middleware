package nexusedge.core;

import nexusedge.gnb.GnbNode;
import nexusedge.session.UrllcSession;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the NexusServiceCore class.
 */
public class NexusServiceCoreTest {

  private NexusServiceCore serviceCore;
  private static final String VALID_PUBLIC_KEY = "bc1q4dpjsdcf8pwnduvqermyw5wrzyaqdpttjg3858";
  private static final double INITIAL_CREDITS = 2000000.0;

  @Before
  public void setUp() {
    serviceCore = new NexusServiceCore(VALID_PUBLIC_KEY, INITIAL_CREDITS);
  }

  @Test
  public void testConstructor() {
    assertNotNull(serviceCore);
    assertNotNull(serviceCore.getProtocol());
    assertNotNull(serviceCore.getUrllcSession());
    assertEquals(INITIAL_CREDITS, serviceCore.getServiceCredits(), 0.001);
    assertEquals(0, serviceCore.getGnbNodes().size());
  }

  @Test
  public void testAddServiceCredits() {
    serviceCore.addServiceCredits(1000.0);
    assertEquals(INITIAL_CREDITS + 1000.0, serviceCore.getServiceCredits(), 0.001);
  }

  @Test
  public void testDeductServiceCreditsSuccess() {
    assertTrue(serviceCore.deductServiceCredits(100.0));
    assertEquals(INITIAL_CREDITS - 100.0, serviceCore.getServiceCredits(), 0.001);
  }

  @Test
  public void testDeductServiceCreditsInsufficientFunds() {
    assertFalse(serviceCore.deductServiceCredits(INITIAL_CREDITS + 1.0));
    assertEquals(INITIAL_CREDITS, serviceCore.getServiceCredits(), 0.001);
  }

  @Test
  public void testSetAndGetE2eLatency() {
    serviceCore.setE2eLatencyMicroseconds(0.9);
    assertEquals(0.9, serviceCore.getE2eLatencyMicroseconds(), 0.001);
  }

  @Test
  public void testSetAndGetCurrentJitter() {
    serviceCore.setCurrentJitterMicroseconds(3.57);
    assertEquals(3.57, serviceCore.getCurrentJitterMicroseconds(), 0.001);
  }

  @Test
  public void testSetAndGetPredictiveLeadTime() {
    serviceCore.setPredictiveLeadTimeSeconds(1.2);
    assertEquals(1.2, serviceCore.getPredictiveLeadTimeSeconds(), 0.001);
  }

  @Test
  public void testAddGnbNode() {
    GnbNode node = new GnbNode("ALPHA-001", "Industrial Zone 4", GnbNode.GnbStatus.OPTIMAL);
    serviceCore.addGnbNode(node);
    
    assertEquals(1, serviceCore.getGnbNodes().size());
    assertEquals("ALPHA-001", serviceCore.getGnbNodes().get(0).getNodeId());
  }

  @Test
  public void testAddMultipleGnbNodes() {
    serviceCore.addGnbNode(new GnbNode("ALPHA-001", "Industrial Zone 4", GnbNode.GnbStatus.OPTIMAL));
    serviceCore.addGnbNode(new GnbNode("BETA-002", "Medical Research Wing", GnbNode.GnbStatus.NOMINAL));
    serviceCore.addGnbNode(new GnbNode("GAMMA-003", "Remote Farming Sector", GnbNode.GnbStatus.HIGH_LOAD));
    
    assertEquals(3, serviceCore.getGnbNodes().size());
  }

  @Test
  public void testGetGnbNodeById() {
    GnbNode node = new GnbNode("ALPHA-001", "Industrial Zone 4", GnbNode.GnbStatus.OPTIMAL);
    serviceCore.addGnbNode(node);
    
    GnbNode retrieved = serviceCore.getGnbNode("ALPHA-001");
    assertNotNull(retrieved);
    assertEquals("ALPHA-001", retrieved.getNodeId());
  }

  @Test
  public void testGetGnbNodeByIdNotFound() {
    GnbNode retrieved = serviceCore.getGnbNode("NON-EXISTENT");
    assertNull(retrieved);
  }

  @Test
  public void testRemoveGnbNode() {
    GnbNode node = new GnbNode("ALPHA-001", "Industrial Zone 4", GnbNode.GnbStatus.OPTIMAL);
    serviceCore.addGnbNode(node);
    
    assertTrue(serviceCore.removeGnbNode("ALPHA-001"));
    assertEquals(0, serviceCore.getGnbNodes().size());
  }

  @Test
  public void testRemoveGnbNodeNotFound() {
    assertFalse(serviceCore.removeGnbNode("NON-EXISTENT"));
  }

  @Test
  public void testGetOperationalGnbCount() {
    serviceCore.addGnbNode(new GnbNode("ALPHA-001", "Industrial Zone 4", GnbNode.GnbStatus.OPTIMAL));
    serviceCore.addGnbNode(new GnbNode("BETA-002", "Medical Research Wing", GnbNode.GnbStatus.NOMINAL));
    serviceCore.addGnbNode(new GnbNode("GAMMA-003", "Remote Farming Sector", GnbNode.GnbStatus.HIGH_LOAD));
    serviceCore.addGnbNode(new GnbNode("DELTA-004", "Offline Zone", GnbNode.GnbStatus.OFFLINE));
    
    assertEquals(3, serviceCore.getOperationalGnbCount());
  }

  @Test
  public void testUpdateNetworkMetrics() {
    serviceCore.addGnbNode(new GnbNode("ALPHA-001", "Zone 1", GnbNode.GnbStatus.OPTIMAL, 0.9, 78));
    serviceCore.addGnbNode(new GnbNode("BETA-002", "Zone 2", GnbNode.GnbStatus.NOMINAL, 1.2, 45));
    serviceCore.addGnbNode(new GnbNode("GAMMA-003", "Zone 3", GnbNode.GnbStatus.HIGH_LOAD, 3.5, 92));
    
    serviceCore.updateNetworkMetrics();
    
    double expectedAvg = (0.9 + 1.2 + 3.5) / 3.0;
    assertEquals(expectedAvg, serviceCore.getE2eLatencyMicroseconds(), 0.001);
  }

  @Test
  public void testUpdateNetworkMetricsWithOfflineNodes() {
    serviceCore.addGnbNode(new GnbNode("ALPHA-001", "Zone 1", GnbNode.GnbStatus.OPTIMAL, 1.0, 50));
    serviceCore.addGnbNode(new GnbNode("BETA-002", "Zone 2", GnbNode.GnbStatus.OFFLINE, 999.0, 0));
    
    serviceCore.updateNetworkMetrics();
    
    // Should only consider operational node
    assertEquals(1.0, serviceCore.getE2eLatencyMicroseconds(), 0.001);
  }

  @Test
  public void testConnect() {
    assertTrue(serviceCore.connect());
    assertTrue(serviceCore.getProtocol().isConnected());
  }

  @Test
  public void testDisconnect() {
    serviceCore.connect();
    serviceCore.disconnect();
    assertFalse(serviceCore.getProtocol().isConnected());
  }

  @Test
  public void testInitiateUrllcSession() {
    assertTrue(serviceCore.initiateUrllcSession());
    assertTrue(serviceCore.getUrllcSession().isActive());
  }

  @Test
  public void testInitiateUrllcSessionInsufficientCredits() {
    NexusServiceCore poorCore = new NexusServiceCore(VALID_PUBLIC_KEY, 0.5);
    assertFalse(poorCore.initiateUrllcSession());
  }

  @Test
  public void testTerminateUrllcSession() throws InterruptedException {
    serviceCore.initiateUrllcSession();
    Thread.sleep(100);
    
    double initialCredits = serviceCore.getServiceCredits();
    double cost = serviceCore.terminateUrllcSession();
    
    assertTrue(cost > 0);
    assertTrue(serviceCore.getServiceCredits() < initialCredits);
    assertFalse(serviceCore.getUrllcSession().isActive());
  }

  @Test
  public void testTerminateUrllcSessionWhenNotActive() {
    double cost = serviceCore.terminateUrllcSession();
    assertEquals(0.0, cost, 0.001);
  }

  @Test
  public void testDisconnectTerminatesActiveSession() throws InterruptedException {
    serviceCore.initiateUrllcSession();
    Thread.sleep(100);
    
    double initialCredits = serviceCore.getServiceCredits();
    serviceCore.disconnect();
    
    assertFalse(serviceCore.getUrllcSession().isActive());
    assertTrue(serviceCore.getServiceCredits() < initialCredits);
  }

  @Test
  public void testIsServiceReady() {
    assertFalse(serviceCore.isServiceReady()); // Not connected, no nodes
    
    serviceCore.connect();
    assertFalse(serviceCore.isServiceReady()); // Connected but no nodes
    
    serviceCore.addGnbNode(new GnbNode("ALPHA-001", "Zone 1", GnbNode.GnbStatus.OPTIMAL));
    assertTrue(serviceCore.isServiceReady()); // Connected with operational node
  }

  @Test
  public void testIsServiceReadyWithOfflineNodes() {
    serviceCore.connect();
    serviceCore.addGnbNode(new GnbNode("OFFLINE-001", "Zone 1", GnbNode.GnbStatus.OFFLINE));
    
    assertFalse(serviceCore.isServiceReady()); // No operational nodes
  }

  @Test
  public void testGetGnbNodesReturnsNewList() {
    serviceCore.addGnbNode(new GnbNode("ALPHA-001", "Zone 1", GnbNode.GnbStatus.OPTIMAL));
    
    List<GnbNode> nodes1 = serviceCore.getGnbNodes();
    List<GnbNode> nodes2 = serviceCore.getGnbNodes();
    
    assertNotSame(nodes1, nodes2); // Should return different list instances
  }

  @Test
  public void testCompleteWorkflow() throws InterruptedException {
    // Set up network infrastructure
    serviceCore.addGnbNode(new GnbNode("ALPHA-001", "Industrial Zone 4", 
                                       GnbNode.GnbStatus.OPTIMAL, 0.9, 78));
    serviceCore.addGnbNode(new GnbNode("BETA-002", "Medical Research Wing", 
                                       GnbNode.GnbStatus.NOMINAL, 1.2, 45));
    serviceCore.addGnbNode(new GnbNode("GAMMA-003", "Remote Farming Sector", 
                                       GnbNode.GnbStatus.HIGH_LOAD, 3.5, 92));
    
    // Configure network metrics
    serviceCore.setE2eLatencyMicroseconds(1.0);
    serviceCore.setCurrentJitterMicroseconds(3.57);
    serviceCore.setPredictiveLeadTimeSeconds(1.2);
    
    // Connect to network
    assertTrue(serviceCore.connect());
    assertTrue(serviceCore.isServiceReady());
    
    // Start URLLC session
    double creditsBefore = serviceCore.getServiceCredits();
    assertTrue(serviceCore.initiateUrllcSession());
    
    Thread.sleep(100);
    
    // Verify session is running
    assertTrue(serviceCore.getUrllcSession().isActive());
    assertTrue(serviceCore.getUrllcSession().getElapsedSessionTime() >= 0);
    
    // Terminate session
    double cost = serviceCore.terminateUrllcSession();
    assertTrue(cost > 0);
    assertEquals(creditsBefore - cost, serviceCore.getServiceCredits(), 0.001);
    
    // Disconnect
    serviceCore.disconnect();
    assertFalse(serviceCore.getProtocol().isConnected());
  }
}
