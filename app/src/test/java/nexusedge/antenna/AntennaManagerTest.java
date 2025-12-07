package nexusedge.antenna;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for AntennaManager class (Phase 3).
 */
public class AntennaManagerTest {
    
    private AntennaManager antennaManager;
    
    @Before
    public void setUp() {
        antennaManager = new AntennaManager();
    }
    
    @Test
    public void testInitialState() {
        assertEquals(AntennaStatus.OFFLINE, antennaManager.getStatus());
        assertFalse(antennaManager.isUmMimoInitialized());
        assertFalse(antennaManager.isBeamformingLocked());
        assertFalse(antennaManager.isRisActive());
        assertEquals(0.0, antennaManager.getThroughputTbps(), 0.001);
        assertFalse(antennaManager.isPhaseComplete());
    }
    
    @Test
    public void testInitializeUmMimo_Success() {
        boolean result = antennaManager.initializeUmMimo();
        
        assertTrue(result);
        assertEquals(AntennaStatus.CALIBRATING, antennaManager.getStatus());
        assertTrue(antennaManager.isUmMimoInitialized());
    }
    
    @Test
    public void testLockBeamforming_Success() {
        antennaManager.initializeUmMimo();
        
        boolean result = antennaManager.lockBeamforming();
        
        assertTrue(result);
        assertEquals(AntennaStatus.LOCKED, antennaManager.getStatus());
        assertTrue(antennaManager.isBeamformingLocked());
        assertTrue(antennaManager.getThroughputTbps() > 0.0);
    }
    
    @Test
    public void testLockBeamforming_NotInitialized() {
        boolean result = antennaManager.lockBeamforming();
        
        assertFalse(result);
        assertFalse(antennaManager.isBeamformingLocked());
    }
    
    @Test
    public void testActivateRis_Success() {
        antennaManager.initializeUmMimo();
        antennaManager.lockBeamforming();
        
        boolean result = antennaManager.activateRis();
        
        assertTrue(result);
        assertTrue(antennaManager.isRisActive());
    }
    
    @Test
    public void testActivateRis_NotLocked() {
        boolean result = antennaManager.activateRis();
        
        assertFalse(result);
        assertFalse(antennaManager.isRisActive());
    }
    
    @Test
    public void testIsPhaseComplete_AllStepsCompleted() {
        antennaManager.initializeUmMimo();
        antennaManager.lockBeamforming();
        antennaManager.activateRis();
        
        assertTrue(antennaManager.isPhaseComplete());
    }
    
    @Test
    public void testIsPhaseComplete_MissingSteps() {
        antennaManager.initializeUmMimo();
        // Skip beamforming and RIS
        
        assertFalse(antennaManager.isPhaseComplete());
    }
    
    @Test
    public void testCompletePhase3Workflow() {
        // Step 1: UM-MIMO initialization
        boolean initResult = antennaManager.initializeUmMimo();
        assertTrue(initResult);
        assertTrue(antennaManager.isUmMimoInitialized());
        
        // Step 2: Beamforming lock
        boolean beamResult = antennaManager.lockBeamforming();
        assertTrue(beamResult);
        assertTrue(antennaManager.isBeamformingLocked());
        assertEquals(AntennaStatus.LOCKED, antennaManager.getStatus());
        
        // Step 3: RIS activation
        boolean risResult = antennaManager.activateRis();
        assertTrue(risResult);
        assertTrue(antennaManager.isRisActive());
        
        // Verify phase completion
        assertTrue(antennaManager.isPhaseComplete());
    }
    
    @Test
    public void testThroughputMeasurement() {
        antennaManager.initializeUmMimo();
        antennaManager.lockBeamforming();
        
        double throughput = antennaManager.getThroughputTbps();
        
        assertTrue("Throughput should be positive", throughput > 0.0);
        assertTrue("Throughput should be in Tbps range", throughput >= 1.0);
    }
}
