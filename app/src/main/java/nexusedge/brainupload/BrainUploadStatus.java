package nexusedge.brainupload;

/**
 * Represents the status of the direct brain upload system.
 */
public enum BrainUploadStatus {
    /**
     * Brain upload system is offline.
     */
    OFFLINE,
    
    /**
     * Neural Data Transfer Protocol is initializing.
     */
    INITIALIZING_NDTP,
    
    /**
     * Information encoding system is activating.
     */
    ENCODING_SYSTEM_ACTIVATING,
    
    /**
     * Safety systems are being checked.
     */
    SAFETY_CHECK,
    
    /**
     * System is online and ready for uploads/downloads.
     */
    ONLINE,
    
    /**
     * Brain upload activation failed.
     */
    FAILED
}
