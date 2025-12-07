package nexusedge.antenna;

/**
 * Represents the antenna system status of a Nexus Edge device.
 */
public enum AntennaStatus {
    /**
     * Antenna systems are offline.
     */
    OFFLINE,
    
    /**
     * Antenna systems are initializing.
     */
    INITIALIZING,
    
    /**
     * Antenna systems are calibrating.
     */
    CALIBRATING,
    
    /**
     * Antenna beam is locked to optimal path.
     */
    LOCKED,
    
    /**
     * Antenna configuration failed.
     */
    FAILED
}
