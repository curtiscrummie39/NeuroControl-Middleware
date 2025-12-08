package nexusedge.protocol;

/**
 * Represents the operational state of a Nexus Edge device.
 */
public enum DeviceState {
    /**
     * Device is not initialized or powered off.
     */
    INACTIVE,
    
    /**
     * Device is in the process of connecting and initializing.
     */
    INITIALIZING,
    
    /**
     * Device is performing authentication and handshake.
     */
    AUTHENTICATING,
    
    /**
     * Device is synchronizing with network timing.
     */
    SYNCHRONIZING,
    
    /**
     * Device is configuring antenna systems.
     */
    CONFIGURING_ANTENNA,
    
    /**
     * Device is onboarding BCI interfaces.
     */
    ONBOARDING_BCI,
    
    /**
     * Device is fully operational and ready for use.
     */
    ACTIVE,
    
    /**
     * Device is operating in degraded mode (reduced performance).
     */
    DEGRADED,
    
    /**
     * Device has encountered an error.
     */
    ERROR
}
