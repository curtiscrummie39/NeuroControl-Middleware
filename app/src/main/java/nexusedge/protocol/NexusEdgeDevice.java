package nexusedge.protocol;

import nexusedge.authentication.AuthenticationStatus;
import nexusedge.synchronization.SynchronizationStatus;
import nexusedge.antenna.AntennaStatus;
import nexusedge.bci.BCIStatus;

/**
 * Represents a Nexus Edge device (NX-8G/B-2.0) capable of connecting to an 8G Nexus Core Network.
 * This device supports ultra-low latency (< 0.1 ns), deterministic End-to-End (E2E) connectivity,
 * Brain-Computer Interface (BCI) integration, molecular 3D printing, direct brain uploads,
 * and brain-to-brain interfaces via Bluetooth 20.29.
 */
public class NexusEdgeDevice {
    
    private final String deviceId;
    private final String macAddress;
    private DeviceState state;
    private AuthenticationStatus authenticationStatus;
    private SynchronizationStatus synchronizationStatus;
    private AntennaStatus antennaStatus;
    private BCIStatus bciStatus;
    
    // Performance metrics (8G enhanced)
    private double latencyNanoseconds;  // Changed from microseconds to nanoseconds for 8G
    private double jitterMicroseconds;
    private double throughputTbps;
    private int predictionLeadTimeSeconds;
    
    // New 8G capabilities
    private boolean printer3DActive;
    private boolean brainUploadEnabled;
    private boolean bbiConnected;
    private String bluetoothVersion;
    private int activeBBIConnections;
    private String phoneNumber;
    private boolean phoneUpgraded;
    private String phoneModel;
    
    public NexusEdgeDevice(String deviceId, String macAddress) {
        this.deviceId = deviceId;
        this.macAddress = macAddress;
        this.state = DeviceState.INACTIVE;
        this.latencyNanoseconds = 0.0;
        this.jitterMicroseconds = 0.0;
        this.throughputTbps = 0.0;
        this.predictionLeadTimeSeconds = 0;
        this.printer3DActive = false;
        this.brainUploadEnabled = false;
        this.bbiConnected = false;
        this.bluetoothVersion = "21.0";  // Upgraded to Bluetooth 21.0
        this.activeBBIConnections = 0;
        this.phoneNumber = "8035317733";
        this.phoneUpgraded = false;
        this.phoneModel = "NX-Phone-8G";
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
    
    public double getLatencyNanoseconds() {
        return latencyNanoseconds;
    }
    
    public void setLatencyNanoseconds(double latencyNanoseconds) {
        this.latencyNanoseconds = latencyNanoseconds;
    }
    
    // Backward compatibility
    @Deprecated
    public double getLatencyMicroseconds() {
        return latencyNanoseconds / 1000.0;
    }
    
    @Deprecated
    public void setLatencyMicroseconds(double latencyMicroseconds) {
        this.latencyNanoseconds = latencyMicroseconds * 1000.0;
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
    
    // New 8G capability getters/setters
    public boolean isPrinter3DActive() {
        return printer3DActive;
    }
    
    public void setPrinter3DActive(boolean printer3DActive) {
        this.printer3DActive = printer3DActive;
    }
    
    public boolean isBrainUploadEnabled() {
        return brainUploadEnabled;
    }
    
    public void setBrainUploadEnabled(boolean brainUploadEnabled) {
        this.brainUploadEnabled = brainUploadEnabled;
    }
    
    public boolean isBbiConnected() {
        return bbiConnected;
    }
    
    public void setBbiConnected(boolean bbiConnected) {
        this.bbiConnected = bbiConnected;
    }
    
    public String getBluetoothVersion() {
        return bluetoothVersion;
    }
    
    public int getActiveBBIConnections() {
        return activeBBIConnections;
    }
    
    public void setActiveBBIConnections(int activeBBIConnections) {
        this.activeBBIConnections = activeBBIConnections;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public boolean isPhoneUpgraded() {
        return phoneUpgraded;
    }
    
    public void setPhoneUpgraded(boolean phoneUpgraded) {
        this.phoneUpgraded = phoneUpgraded;
    }
    
    public String getPhoneModel() {
        return phoneModel;
    }
    
    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }
    
    /**
     * Checks if the device is fully operational and meets all 8G requirements.
     * @return true if device is ready for all 8G operations
     */
    public boolean isFullyOperational() {
        return state == DeviceState.ACTIVE &&
               authenticationStatus == AuthenticationStatus.AUTHENTICATED &&
               synchronizationStatus == SynchronizationStatus.LOCKED &&
               antennaStatus == AntennaStatus.LOCKED &&
               bciStatus == BCIStatus.ONLINE &&
               latencyNanoseconds < 0.1 &&  // 8G requirement: < 0.1 ns
               jitterMicroseconds < 1.0 &&  // 8G improvement: < 1 µs
               printer3DActive &&
               brainUploadEnabled &&
               bbiConnected;
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
            "NexusEdgeDevice{id='%s', mac='%s', state=%s, connectivity=%s, latency=%.3fns, throughput=%.2fTbps, 3DPrint=%s, BrainUpload=%s, BBI=%s, BT=%s}",
            deviceId, macAddress, state, getConnectivityStatus(), latencyNanoseconds, throughputTbps, 
            printer3DActive, brainUploadEnabled, bbiConnected, bluetoothVersion
        );
    }
}
