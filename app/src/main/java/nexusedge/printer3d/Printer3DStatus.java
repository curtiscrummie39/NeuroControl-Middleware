package nexusedge.printer3d;

/**
 * Represents the status of the molecular 3D printer system.
 */
public enum Printer3DStatus {
    /**
     * 3D printer system is offline.
     */
    OFFLINE,
    
    /**
     * Molecular harvester is initializing.
     */
    INITIALIZING,
    
    /**
     * Calibrating quantum assembly chamber.
     */
    CALIBRATING,
    
    /**
     * System is ready to print.
     */
    READY,
    
    /**
     * Currently printing an item.
     */
    PRINTING,
    
    /**
     * 3D printer activation failed.
     */
    FAILED
}
