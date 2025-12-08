package nexusedge.gnb;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the GnbNode class.
 */
public class GnbNodeTest {

  private GnbNode gnbNode;

  @Before
  public void setUp() {
    gnbNode = new GnbNode("ALPHA-001", "Industrial Zone 4", GnbNode.GnbStatus.OPTIMAL);
  }

  @Test
  public void testBasicConstructor() {
    assertNotNull(gnbNode);
    assertEquals("ALPHA-001", gnbNode.getNodeId());
    assertEquals("Industrial Zone 4", gnbNode.getLocation());
    assertEquals(GnbNode.GnbStatus.OPTIMAL, gnbNode.getStatus());
    assertEquals(0.0, gnbNode.getLatencyMicroseconds(), 0.001);
    assertEquals(0, gnbNode.getTrafficPercentage());
  }

  @Test
  public void testFullConstructor() {
    GnbNode fullNode = new GnbNode("BETA-002", "Medical Research Wing", 
                                    GnbNode.GnbStatus.NOMINAL, 1.2, 45);
    assertEquals("BETA-002", fullNode.getNodeId());
    assertEquals("Medical Research Wing", fullNode.getLocation());
    assertEquals(GnbNode.GnbStatus.NOMINAL, fullNode.getStatus());
    assertEquals(1.2, fullNode.getLatencyMicroseconds(), 0.001);
    assertEquals(45, fullNode.getTrafficPercentage());
  }

  @Test
  public void testSetStatus() {
    gnbNode.setStatus(GnbNode.GnbStatus.HIGH_LOAD);
    assertEquals(GnbNode.GnbStatus.HIGH_LOAD, gnbNode.getStatus());
  }

  @Test
  public void testSetLatency() {
    gnbNode.setLatencyMicroseconds(0.9);
    assertEquals(0.9, gnbNode.getLatencyMicroseconds(), 0.001);
  }

  @Test
  public void testSetTrafficPercentage() {
    gnbNode.setTrafficPercentage(78);
    assertEquals(78, gnbNode.getTrafficPercentage());
  }

  @Test
  public void testTrafficPercentageBoundaryMin() {
    gnbNode.setTrafficPercentage(-10);
    assertEquals(0, gnbNode.getTrafficPercentage());
  }

  @Test
  public void testTrafficPercentageBoundaryMax() {
    gnbNode.setTrafficPercentage(150);
    assertEquals(100, gnbNode.getTrafficPercentage());
  }

  @Test
  public void testTrafficPercentageInConstructor() {
    GnbNode nodeUnderflow = new GnbNode("TEST-001", "Test", GnbNode.GnbStatus.OPTIMAL, 1.0, -5);
    assertEquals(0, nodeUnderflow.getTrafficPercentage());
    
    GnbNode nodeOverflow = new GnbNode("TEST-002", "Test", GnbNode.GnbStatus.OPTIMAL, 1.0, 150);
    assertEquals(100, nodeOverflow.getTrafficPercentage());
  }

  @Test
  public void testIsOperational() {
    assertTrue(gnbNode.isOperational());
    
    gnbNode.setStatus(GnbNode.GnbStatus.NOMINAL);
    assertTrue(gnbNode.isOperational());
    
    gnbNode.setStatus(GnbNode.GnbStatus.HIGH_LOAD);
    assertTrue(gnbNode.isOperational());
    
    gnbNode.setStatus(GnbNode.GnbStatus.OFFLINE);
    assertFalse(gnbNode.isOperational());
  }

  @Test
  public void testUpdateMetrics() {
    gnbNode.updateMetrics(3.5, 92);
    assertEquals(3.5, gnbNode.getLatencyMicroseconds(), 0.001);
    assertEquals(92, gnbNode.getTrafficPercentage());
  }

  @Test
  public void testUpdateMetricsWithBoundaryValues() {
    gnbNode.updateMetrics(0.5, -10);
    assertEquals(0.5, gnbNode.getLatencyMicroseconds(), 0.001);
    assertEquals(0, gnbNode.getTrafficPercentage());
    
    gnbNode.updateMetrics(10.0, 150);
    assertEquals(10.0, gnbNode.getLatencyMicroseconds(), 0.001);
    assertEquals(100, gnbNode.getTrafficPercentage());
  }

  @Test
  public void testToString() {
    String expected = "GnbNode{nodeId='ALPHA-001', location='Industrial Zone 4', " +
                      "status=OPTIMAL, latencyMicroseconds=0.0, trafficPercentage=0}";
    assertEquals(expected, gnbNode.toString());
  }

  @Test
  public void testAllStatusValues() {
    gnbNode.setStatus(GnbNode.GnbStatus.OPTIMAL);
    assertEquals(GnbNode.GnbStatus.OPTIMAL, gnbNode.getStatus());
    
    gnbNode.setStatus(GnbNode.GnbStatus.NOMINAL);
    assertEquals(GnbNode.GnbStatus.NOMINAL, gnbNode.getStatus());
    
    gnbNode.setStatus(GnbNode.GnbStatus.HIGH_LOAD);
    assertEquals(GnbNode.GnbStatus.HIGH_LOAD, gnbNode.getStatus());
    
    gnbNode.setStatus(GnbNode.GnbStatus.OFFLINE);
    assertEquals(GnbNode.GnbStatus.OFFLINE, gnbNode.getStatus());
  }

  @Test
  public void testHighLoadScenario() {
    GnbNode highLoadNode = new GnbNode("GAMMA-003", "Remote Farming Sector", 
                                       GnbNode.GnbStatus.HIGH_LOAD, 3.5, 92);
    assertTrue(highLoadNode.isOperational());
    assertEquals(3.5, highLoadNode.getLatencyMicroseconds(), 0.001);
    assertEquals(92, highLoadNode.getTrafficPercentage());
  }
}
