package nexusedge.bbi;

/**
 * Represents the status of the Brain-to-Brain Interface (BBI) system.
 */
public enum BBIStatus {
    /**
     * BBI system is offline.
     */
    OFFLINE,
    
    /**
     * Bluetooth 20.29 neural pairing in progress.
     */
    PAIRING,
    
    /**
     * Phone neural integration activating.
     */
    PHONE_INTEGRATING,
    
    /**
     * Connecting to collective neural network.
     */
    NETWORK_CONNECTING,
    
    /**
     * BBI is fully online and connected.
     */
    ONLINE,
    
    /**
     * BBI activation failed.
     */
    FAILED
}
