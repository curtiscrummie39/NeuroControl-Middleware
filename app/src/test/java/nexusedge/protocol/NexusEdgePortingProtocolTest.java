package nexusedge.protocol;

import nexusedge.authentication.AuthenticationStatus;
import nexusedge.synchronization.SynchronizationStatus;
import nexusedge.antenna.AntennaStatus;
import nexusedge.bci.BCIStatus;
import nexusedge.events.PortingEventListener;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for NexusEdgePortingProtocol class (SEP-1 orchestrator).
 */
public class NexusEdgePortingProtocolTest {
    
    private NexusEdgeDevice device;
    private NexusEdgePortingProtocol protocol;
    
    @Before
    public void setUp() {
        device = new NexusEdgeDevice("NX-6G/B-1-001", "00:11:22:33:44:55");
        protocol = new NexusEdgePortingProtocol(device);
    }
    
    @Test
    public void testProtocolConstructor() {
        assertNotNull(protocol);
        assertEquals(device, protocol.getDevice());
        assertEquals(PortingPhase.NOT_STARTED, protocol.getCurrentPhase());
        assertNotNull(protocol.getAuthenticationManager());
        assertNotNull(protocol.getSynchronizationManager());
        assertNotNull(protocol.getAntennaManager());
        assertNotNull(protocol.getBciManager());
    }
    
    @Test
    public void testExecutePortingProtocol_Success() {
        boolean result = protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        assertTrue(result);
        assertEquals(DeviceState.ACTIVE, device.getState());
        assertEquals(PortingPhase.COMPLETE, protocol.getCurrentPhase());
        assertTrue(device.isFullyOperational());
    }
    
    @Test
    public void testExecutePortingProtocol_Phase1Success() {
        protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        assertEquals(AuthenticationStatus.AUTHENTICATED, device.getAuthenticationStatus());
        assertTrue(protocol.getAuthenticationManager().isPhaseComplete());
    }
    
    @Test
    public void testExecutePortingProtocol_Phase2Success() {
        protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        assertEquals(SynchronizationStatus.LOCKED, device.getSynchronizationStatus());
        assertTrue(protocol.getSynchronizationManager().isPhaseComplete());
        assertTrue(device.getJitterMicroseconds() < 10.0);
    }
    
    @Test
    public void testExecutePortingProtocol_Phase3Success() {
        protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        assertEquals(AntennaStatus.LOCKED, device.getAntennaStatus());
        assertTrue(protocol.getAntennaManager().isPhaseComplete());
        assertTrue(device.getThroughputTbps() > 0.0);
    }
    
    @Test
    public void testExecutePortingProtocol_Phase4Success() {
        protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        assertEquals(BCIStatus.ONLINE, device.getBciStatus());
        assertTrue(protocol.getBciManager().isPhaseComplete());
        assertTrue(device.getPredictionLeadTimeSeconds() >= 1);
        assertTrue(device.getLatencyMicroseconds() < 1.0);
    }
    
    @Test
    public void testExecutePortingProtocol_FailureInvalidCredentials() {
        boolean result = protocol.executePortingProtocol(null);
        
        assertFalse(result);
        assertEquals(DeviceState.ERROR, device.getState());
        assertFalse(device.isFullyOperational());
    }
    
    @Test
    public void testEventListeners() {
        final boolean[] phaseChangeCalled = {false};
        final boolean[] portingCompleteCalled = {false};
        
        PortingEventListener listener = new PortingEventListener() {
            @Override
            public void onPhaseChange(PortingPhase oldPhase, PortingPhase newPhase) {
                phaseChangeCalled[0] = true;
            }
            
            @Override
            public void onPortingComplete(NexusEdgeDevice device) {
                portingCompleteCalled[0] = true;
            }
        };
        
        protocol.addListener(listener);
        protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        assertTrue("Phase change event should be called", phaseChangeCalled[0]);
        assertTrue("Porting complete event should be called", portingCompleteCalled[0]);
    }
    
    @Test
    public void testRemoveListener() {
        final int[] callCount = {0};
        
        PortingEventListener listener = new PortingEventListener() {
            @Override
            public void onPhaseChange(PortingPhase oldPhase, PortingPhase newPhase) {
                callCount[0]++;
            }
            
            @Override
            public void onPortingComplete(NexusEdgeDevice device) {
                callCount[0]++;
            }
        };
        
        protocol.addListener(listener);
        protocol.removeListener(listener);
        protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        assertEquals("No events should be received after removal", 0, callCount[0]);
    }
    
    @Test
    public void testDevicePerformanceMetrics() {
        protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        assertTrue("Latency should be below 1 µs", device.getLatencyMicroseconds() < 1.0);
        assertTrue("Jitter should be below 10 µs", device.getJitterMicroseconds() < 10.0);
        assertTrue("Throughput should be in Tbps range", device.getThroughputTbps() > 0.0);
        assertTrue("Prediction lead time should be 1-2 seconds", 
                   device.getPredictionLeadTimeSeconds() >= 1 && 
                   device.getPredictionLeadTimeSeconds() <= 2);
    }
    
    @Test
    public void testDeviceConnectivityStatus() {
        protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        assertEquals("GREEN", device.getConnectivityStatus());
    }
    
    @Test
    public void testCompleteProtocolWorkflow() {
        // Execute the complete SEP-1 protocol
        boolean result = protocol.executePortingProtocol("VALID-CREDENTIALS");
        
        // Verify overall success
        assertTrue("Protocol execution should succeed", result);
        assertEquals("Device should be active", DeviceState.ACTIVE, device.getState());
        assertEquals("Protocol should be complete", PortingPhase.COMPLETE, protocol.getCurrentPhase());
        
        // Verify all phases are complete
        assertTrue("Phase 1 should be complete", protocol.getAuthenticationManager().isPhaseComplete());
        assertTrue("Phase 2 should be complete", protocol.getSynchronizationManager().isPhaseComplete());
        assertTrue("Phase 3 should be complete", protocol.getAntennaManager().isPhaseComplete());
        assertTrue("Phase 4 should be complete", protocol.getBciManager().isPhaseComplete());
        
        // Verify device is fully operational
        assertTrue("Device should be fully operational", device.isFullyOperational());
    }
}
