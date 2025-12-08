package nexusedge.synchronization;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for SynchronizationManager class (Phase 2).
 */
public class SynchronizationManagerTest {
    
    private SynchronizationManager syncManager;
    
    @Before
    public void setUp() {
        syncManager = new SynchronizationManager();
    }
    
    @Test
    public void testInitialState() {
        assertEquals(SynchronizationStatus.NOT_STARTED, syncManager.getStatus());
        assertEquals(0.0, syncManager.getJitterMicroseconds(), 0.001);
        assertFalse(syncManager.isTsnChannelReserved());
        assertFalse(syncManager.isIsacCalibrated());
        assertFalse(syncManager.isPhaseComplete());
    }
    
    @Test
    public void testPerformClockLock_Success() {
        boolean result = syncManager.performClockLock();
        
        assertTrue(result);
        assertEquals(SynchronizationStatus.LOCKED, syncManager.getStatus());
        assertTrue(syncManager.getJitterMicroseconds() < 10.0);
    }
    
    @Test
    public void testReserveTsnChannel_Success() {
        syncManager.performClockLock();
        
        boolean result = syncManager.reserveTsnChannel();
        
        assertTrue(result);
        assertTrue(syncManager.isTsnChannelReserved());
    }
    
    @Test
    public void testReserveTsnChannel_NotLocked() {
        boolean result = syncManager.reserveTsnChannel();
        
        assertFalse(result);
        assertFalse(syncManager.isTsnChannelReserved());
    }
    
    @Test
    public void testCalibrateIsac_Success() {
        syncManager.performClockLock();
        syncManager.reserveTsnChannel();
        
        boolean result = syncManager.calibrateIsac();
        
        assertTrue(result);
        assertTrue(syncManager.isIsacCalibrated());
    }
    
    @Test
    public void testCalibrateIsac_NotLocked() {
        boolean result = syncManager.calibrateIsac();
        
        assertFalse(result);
        assertFalse(syncManager.isIsacCalibrated());
    }
    
    @Test
    public void testCalibrateIsac_NoTsnChannel() {
        syncManager.performClockLock();
        // Skip TSN reservation
        
        boolean result = syncManager.calibrateIsac();
        
        assertFalse(result);
        assertFalse(syncManager.isIsacCalibrated());
    }
    
    @Test
    public void testIsPhaseComplete_AllStepsCompleted() {
        syncManager.performClockLock();
        syncManager.reserveTsnChannel();
        syncManager.calibrateIsac();
        
        assertTrue(syncManager.isPhaseComplete());
    }
    
    @Test
    public void testIsPhaseComplete_MissingSteps() {
        syncManager.performClockLock();
        // Skip TSN and ISAC
        
        assertFalse(syncManager.isPhaseComplete());
    }
    
    @Test
    public void testCompletePhase2Workflow() {
        // Step 1: Clock lock
        boolean lockResult = syncManager.performClockLock();
        assertTrue(lockResult);
        assertEquals(SynchronizationStatus.LOCKED, syncManager.getStatus());
        
        // Step 2: TSN channel reservation
        boolean tsnResult = syncManager.reserveTsnChannel();
        assertTrue(tsnResult);
        assertTrue(syncManager.isTsnChannelReserved());
        
        // Step 3: ISAC calibration
        boolean isacResult = syncManager.calibrateIsac();
        assertTrue(isacResult);
        assertTrue(syncManager.isIsacCalibrated());
        
        // Verify phase completion
        assertTrue(syncManager.isPhaseComplete());
    }
    
    @Test
    public void testJitterMeasurement() {
        syncManager.performClockLock();
        
        double jitter = syncManager.getJitterMicroseconds();
        
        assertTrue("Jitter should be positive", jitter > 0);
        assertTrue("Jitter should be below 10 µs", jitter < 10.0);
    }
}
