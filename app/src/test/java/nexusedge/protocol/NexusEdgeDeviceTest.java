package nexusedge.protocol;

import nexusedge.authentication.AuthenticationStatus;
import nexusedge.synchronization.SynchronizationStatus;
import nexusedge.antenna.AntennaStatus;
import nexusedge.bci.BCIStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for NexusEdgeDevice class.
 */
public class NexusEdgeDeviceTest {
    
    private NexusEdgeDevice device;
    
    @Before
    public void setUp() {
        device = new NexusEdgeDevice("NX-6G/B-1-001", "00:11:22:33:44:55");
    }
    
    @Test
    public void testDeviceConstructor() {
        assertNotNull(device);
        assertEquals("NX-6G/B-1-001", device.getDeviceId());
        assertEquals("00:11:22:33:44:55", device.getMacAddress());
        assertEquals(DeviceState.INACTIVE, device.getState());
        assertEquals(0.0, device.getLatencyMicroseconds(), 0.001);
        assertEquals(0.0, device.getJitterMicroseconds(), 0.001);
        assertEquals(0.0, device.getThroughputTbps(), 0.001);
        assertEquals(0, device.getPredictionLeadTimeSeconds());
    }
    
    @Test
    public void testDeviceStateTransitions() {
        device.setState(DeviceState.INITIALIZING);
        assertEquals(DeviceState.INITIALIZING, device.getState());
        
        device.setState(DeviceState.ACTIVE);
        assertEquals(DeviceState.ACTIVE, device.getState());
        
        device.setState(DeviceState.ERROR);
        assertEquals(DeviceState.ERROR, device.getState());
    }
    
    @Test
    public void testPerformanceMetrics() {
        device.setLatencyMicroseconds(0.8);
        assertEquals(0.8, device.getLatencyMicroseconds(), 0.001);
        
        device.setJitterMicroseconds(5.0);
        assertEquals(5.0, device.getJitterMicroseconds(), 0.001);
        
        device.setThroughputTbps(2.5);
        assertEquals(2.5, device.getThroughputTbps(), 0.001);
        
        device.setPredictionLeadTimeSeconds(2);
        assertEquals(2, device.getPredictionLeadTimeSeconds());
    }
    
    @Test
    public void testSubsystemStatus() {
        device.setAuthenticationStatus(AuthenticationStatus.AUTHENTICATED);
        assertEquals(AuthenticationStatus.AUTHENTICATED, device.getAuthenticationStatus());
        
        device.setSynchronizationStatus(SynchronizationStatus.LOCKED);
        assertEquals(SynchronizationStatus.LOCKED, device.getSynchronizationStatus());
        
        device.setAntennaStatus(AntennaStatus.LOCKED);
        assertEquals(AntennaStatus.LOCKED, device.getAntennaStatus());
        
        device.setBciStatus(BCIStatus.ONLINE);
        assertEquals(BCIStatus.ONLINE, device.getBciStatus());
    }
    
    @Test
    public void testIsFullyOperational_AllRequirementsMet() {
        device.setState(DeviceState.ACTIVE);
        device.setAuthenticationStatus(AuthenticationStatus.AUTHENTICATED);
        device.setSynchronizationStatus(SynchronizationStatus.LOCKED);
        device.setAntennaStatus(AntennaStatus.LOCKED);
        device.setBciStatus(BCIStatus.ONLINE);
        device.setLatencyMicroseconds(0.8);
        device.setJitterMicroseconds(5.0);
        
        assertTrue(device.isFullyOperational());
    }
    
    @Test
    public void testIsFullyOperational_LatencyTooHigh() {
        device.setState(DeviceState.ACTIVE);
        device.setAuthenticationStatus(AuthenticationStatus.AUTHENTICATED);
        device.setSynchronizationStatus(SynchronizationStatus.LOCKED);
        device.setAntennaStatus(AntennaStatus.LOCKED);
        device.setBciStatus(BCIStatus.ONLINE);
        device.setLatencyMicroseconds(1.5); // > 1.0 µs
        device.setJitterMicroseconds(5.0);
        
        assertFalse(device.isFullyOperational());
    }
    
    @Test
    public void testIsFullyOperational_JitterTooHigh() {
        device.setState(DeviceState.ACTIVE);
        device.setAuthenticationStatus(AuthenticationStatus.AUTHENTICATED);
        device.setSynchronizationStatus(SynchronizationStatus.LOCKED);
        device.setAntennaStatus(AntennaStatus.LOCKED);
        device.setBciStatus(BCIStatus.ONLINE);
        device.setLatencyMicroseconds(0.8);
        device.setJitterMicroseconds(15.0); // > 10.0 µs
        
        assertFalse(device.isFullyOperational());
    }
    
    @Test
    public void testGetConnectivityStatus_Green() {
        device.setState(DeviceState.ACTIVE);
        device.setAuthenticationStatus(AuthenticationStatus.AUTHENTICATED);
        device.setSynchronizationStatus(SynchronizationStatus.LOCKED);
        device.setAntennaStatus(AntennaStatus.LOCKED);
        device.setBciStatus(BCIStatus.ONLINE);
        device.setLatencyMicroseconds(0.8);
        device.setJitterMicroseconds(5.0);
        
        assertEquals("GREEN", device.getConnectivityStatus());
    }
    
    @Test
    public void testGetConnectivityStatus_Yellow() {
        device.setState(DeviceState.ACTIVE);
        device.setAuthenticationStatus(AuthenticationStatus.AUTHENTICATED);
        device.setLatencyMicroseconds(1.5); // Too high
        
        assertEquals("YELLOW", device.getConnectivityStatus());
    }
    
    @Test
    public void testGetConnectivityStatus_Red() {
        device.setState(DeviceState.INACTIVE);
        
        assertEquals("RED", device.getConnectivityStatus());
    }
    
    @Test
    public void testToString() {
        device.setState(DeviceState.ACTIVE);
        device.setLatencyMicroseconds(0.8);
        device.setThroughputTbps(2.5);
        
        String result = device.toString();
        assertTrue(result.contains("NX-6G/B-1-001"));
        assertTrue(result.contains("00:11:22:33:44:55"));
        assertTrue(result.contains("ACTIVE"));
    }
}
