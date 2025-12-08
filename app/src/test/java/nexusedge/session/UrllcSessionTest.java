package nexusedge.session;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the UrllcSession class.
 */
public class UrllcSessionTest {

  private UrllcSession session;

  @Before
  public void setUp() {
    session = new UrllcSession();
  }

  @Test
  public void testDefaultConstructor() {
    assertNotNull(session);
    assertEquals(UrllcSession.SessionStatus.STANDBY, session.getStatus());
    assertEquals(0, session.getElapsedSessionTime());
    assertEquals(1.0, session.getCostPerInterval(), 0.001);
    assertEquals(10000, session.getIntervalDuration());
    assertFalse(session.isActive());
  }

  @Test
  public void testCustomConstructor() {
    UrllcSession customSession = new UrllcSession(2.5, 5000);
    assertEquals(2.5, customSession.getCostPerInterval(), 0.001);
    assertEquals(5000, customSession.getIntervalDuration());
  }

  @Test
  public void testInitiateSession() {
    assertTrue(session.initiate());
    assertEquals(UrllcSession.SessionStatus.ACTIVE, session.getStatus());
    assertTrue(session.isActive());
  }

  @Test
  public void testInitiateSessionWhenAlreadyActive() {
    session.initiate();
    assertFalse(session.initiate());
  }

  @Test
  public void testTerminateSession() throws InterruptedException {
    session.initiate();
    Thread.sleep(100); // Wait for some elapsed time
    
    long elapsedTime = session.terminate();
    assertEquals(UrllcSession.SessionStatus.TERMINATED, session.getStatus());
    assertTrue(elapsedTime >= 0);
    assertFalse(session.isActive());
  }

  @Test
  public void testTerminateSessionWhenNotActive() {
    long elapsedTime = session.terminate();
    assertEquals(0, elapsedTime);
  }

  @Test
  public void testResetSession() {
    session.initiate();
    session.terminate();
    
    session.reset();
    assertEquals(UrllcSession.SessionStatus.STANDBY, session.getStatus());
    assertEquals(0, session.getElapsedSessionTime());
  }

  @Test
  public void testElapsedSessionTimeWhileActive() throws InterruptedException {
    session.initiate();
    Thread.sleep(1100); // Wait for just over 1 second
    
    long elapsed = session.getElapsedSessionTime();
    assertTrue(elapsed >= 1);
  }

  @Test
  public void testElapsedSessionTimeAfterTermination() throws InterruptedException {
    session.initiate();
    Thread.sleep(100);
    session.terminate();
    
    long elapsedAfterTermination = session.getElapsedSessionTime();
    Thread.sleep(100);
    
    // Elapsed time should not change after termination
    assertEquals(elapsedAfterTermination, session.getElapsedSessionTime());
  }

  @Test
  public void testCalculateTotalCostForActiveSession() throws InterruptedException {
    UrllcSession testSession = new UrllcSession(1.0, 1000); // 1 NXC per second
    testSession.initiate();
    Thread.sleep(1500); // 1.5 seconds
    
    double cost = testSession.calculateTotalCost();
    assertTrue(cost >= 1.0 && cost <= 2.0); // Should be around 1.5 NXC
  }

  @Test
  public void testCalculateTotalCostForTerminatedSession() throws InterruptedException {
    UrllcSession testSession = new UrllcSession(1.0, 1000);
    testSession.initiate();
    Thread.sleep(1000);
    testSession.terminate();
    
    double cost = testSession.calculateTotalCost();
    assertTrue(cost >= 0.5 && cost <= 1.5);
  }

  @Test
  public void testCalculateTotalCostForStandbySession() {
    double cost = session.calculateTotalCost();
    assertEquals(0.0, cost, 0.001);
  }

  @Test
  public void testGetElapsedSessionTimeMillis() throws InterruptedException {
    session.initiate();
    Thread.sleep(100);
    
    long millisElapsed = session.getElapsedSessionTimeMillis();
    assertTrue(millisElapsed >= 100);
  }

  @Test
  public void testMultipleSessionCycles() throws InterruptedException {
    for (int i = 0; i < 3; i++) {
      session.reset();
      assertTrue(session.initiate());
      Thread.sleep(50);
      session.terminate();
      assertTrue(session.getElapsedSessionTime() >= 0);
    }
  }

  @Test
  public void testSessionStatusTransitions() {
    // STANDBY -> ACTIVE
    assertEquals(UrllcSession.SessionStatus.STANDBY, session.getStatus());
    session.initiate();
    assertEquals(UrllcSession.SessionStatus.ACTIVE, session.getStatus());
    
    // ACTIVE -> TERMINATED
    session.terminate();
    assertEquals(UrllcSession.SessionStatus.TERMINATED, session.getStatus());
    
    // TERMINATED -> STANDBY
    session.reset();
    assertEquals(UrllcSession.SessionStatus.STANDBY, session.getStatus());
  }
}
