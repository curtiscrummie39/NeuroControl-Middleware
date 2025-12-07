package nexusedge.synchronization;

/**
 * Represents the synchronization status of a Nexus Edge device.
 */
public enum SynchronizationStatus {
    /**
     * Synchronization has not been initiated.
     */
    NOT_STARTED,
    
    /**
     * Clock locking is in progress.
     */
    LOCKING,
    
    /**
     * Device clock is locked to network timing.
     */
    LOCKED,
    
    /**
     * Synchronization failed.
     */
    FAILED
}
