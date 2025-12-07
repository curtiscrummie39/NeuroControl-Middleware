package nexusedge.protocol;

import nexusedge.authentication.AuthenticationStatus;
import nexusedge.synchronization.SynchronizationStatus;
import nexusedge.antenna.AntennaStatus;
import nexusedge.bci.BCIStatus;

/**
 * Represents a Nexus Edge device (NX-6G/B-1) capable of connecting to a 6G Nexus Core Network.
 * This device supports ultra-low latency (< 1 µs), deterministic End-to-End (E2E) connectivity,
 * and Brain-Computer Interface (BCI) integration.
 */
public class NexusEdgeDevice {
    
    private final String deviceId;
    private final String macAddress;
    private DeviceState state;
    private AuthenticationStatus authenticationStatus;
    private SynchronizationStatus synchronizationStatus;
    private AntennaStatus antennaStatus;
    private BCIStatus bciStatus;
    
    // Performance metrics
    private double latencyMicroseconds;
    private double jitterMicroseconds;
    private double throughputTbps;
    private int predictionLeadTimeSeconds;
    
    public NexusEdgeDevice(String deviceId, String macAddress) {
        this.deviceId = deviceId;
        this.macAddress = macAddress;
        this.state = DeviceState.INACTIVE;
        this.latencyMicroseconds = 0.0;
        this.jitterMicroseconds = 0.0;
        this.throughputTbps = 0.0;
        this.predictionLeadTimeSeconds = 0;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public String getMacAddress() {
        return macAddress;
    }
    
    public DeviceState getState() {
        return state;
    }
    
    public void setState(DeviceState state) {
        this.state = state;
    }
    
    public AuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus;
    }
    
    public void setAuthenticationStatus(AuthenticationStatus authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }
    
    public SynchronizationStatus getSynchronizationStatus() {
        return synchronizationStatus;
    }
    
    public void setSynchronizationStatus(SynchronizationStatus synchronizationStatus) {
        this.synchronizationStatus = synchronizationStatus;
    }
    
    public AntennaStatus getAntennaStatus() {
        return antennaStatus;
    }
    
    public void setAntennaStatus(AntennaStatus antennaStatus) {
        this.antennaStatus = antennaStatus;
    }
    
    public BCIStatus getBciStatus() {
        return bciStatus;
    }
    
    public void setBciStatus(BCIStatus bciStatus) {
        this.bciStatus = bciStatus;
    }
    
    public double getLatencyMicroseconds() {
        return latencyMicroseconds;
    }
    
    public void setLatencyMicroseconds(double latencyMicroseconds) {
        this.latencyMicroseconds = latencyMicroseconds;
    }
    
    public double getJitterMicroseconds() {
        return jitterMicroseconds;
    }
    
    public void setJitterMicroseconds(double jitterMicroseconds) {
        this.jitterMicroseconds = jitterMicroseconds;
    }
    
    public double getThroughputTbps() {
        return throughputTbps;
    }
    
    public void setThroughputTbps(double throughputTbps) {
        this.throughputTbps = throughputTbps;
    }
    
    public int getPredictionLeadTimeSeconds() {
        return predictionLeadTimeSeconds;
    }
    
    public void setPredictionLeadTimeSeconds(int predictionLeadTimeSeconds) {
        this.predictionLeadTimeSeconds = predictionLeadTimeSeconds;
    }
    
    /**
     * Checks if the device is fully operational and meets all requirements.
     * @return true if device is ready for BCI operations
     */
    public boolean isFullyOperational() {
        return state == DeviceState.ACTIVE &&
               authenticationStatus == AuthenticationStatus.AUTHENTICATED &&
               synchronizationStatus == SynchronizationStatus.LOCKED &&
               antennaStatus == AntennaStatus.LOCKED &&
               bciStatus == BCIStatus.ONLINE &&
               latencyMicroseconds < 1.0 &&
               jitterMicroseconds < 10.0;
    }
    
    /**
     * Gets the connectivity status indicator color.
     * @return "GREEN" for fully operational, "YELLOW" for degraded, "RED" for disconnected
     */
    public String getConnectivityStatus() {
        if (isFullyOperational()) {
            return "GREEN";
        } else if (state == DeviceState.ACTIVE) {
            return "YELLOW";
        } else {
            return "RED";
        }
    }
    
    @Override
    public String toString() {
        return String.format(
            "NexusEdgeDevice{id='%s', mac='%s', state=%s, connectivity=%s, latency=%.3fµs, throughput=%.2fTbps}",
            deviceId, macAddress, state, getConnectivityStatus(), latencyMicroseconds, throughputTbps
        );
    }
}
