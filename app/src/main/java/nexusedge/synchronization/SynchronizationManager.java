package nexusedge.synchronization;

/**
 * Manages Phase 2: Microsecond Synchronization and Deterministic Link.
 * Handles clock lock protocol, TSN channel reservation, and ISAC calibration.
 */
public class SynchronizationManager {
    
    private SynchronizationStatus status;
    private double jitterMicroseconds;
    private boolean tsnChannelReserved;
    private boolean isacCalibrated;
    
    public SynchronizationManager() {
        this.status = SynchronizationStatus.NOT_STARTED;
        this.jitterMicroseconds = 0.0;
        this.tsnChannelReserved = false;
        this.isacCalibrated = false;
    }
    
    /**
     * Initiates clock lock protocol with the serving gNB.
     * Synchronizes device clock to sub-nanosecond atomic clock reference.
     * @return true if synchronization succeeds and jitter is below 10 µs
     */
    public boolean performClockLock() {
        this.status = SynchronizationStatus.LOCKING;
        
        // Simulate clock synchronization
        this.jitterMicroseconds = simulateJitterMeasurement();
        
        if (jitterMicroseconds < 10.0) {
            this.status = SynchronizationStatus.LOCKED;
            return true;
        }
        
        this.status = SynchronizationStatus.FAILED;
        return false;
    }
    
    /**
     * Requests Time-Sensitive Networking (TSN) channel reservation from 6G Core.
     * Guarantees deterministic < 1 µs packet delivery window.
     * @return true if channel is successfully reserved
     */
    public boolean reserveTsnChannel() {
        if (status != SynchronizationStatus.LOCKED) {
            return false;
        }
        
        // Simulate TSN channel reservation
        this.tsnChannelReserved = true;
        return true;
    }
    
    /**
     * Performs ISAC (Integrated Sensing & Communication) calibration.
     * Scans immediate local environment to establish Digital Twin baseline.
     * @return true if calibration succeeds
     */
    public boolean calibrateIsac() {
        if (status != SynchronizationStatus.LOCKED || !tsnChannelReserved) {
            return false;
        }
        
        // Simulate ISAC baseline calibration
        this.isacCalibrated = true;
        return true;
    }
    
    /**
     * Simulates jitter measurement in microseconds.
     * In production, this would be an actual timing measurement.
     * @return measured jitter in microseconds
     */
    private double simulateJitterMeasurement() {
        // Simulate a jitter value below the 10 µs threshold
        return 5.0 + (Math.random() * 3.0); // 5-8 µs range
    }
    
    public SynchronizationStatus getStatus() {
        return status;
    }
    
    public double getJitterMicroseconds() {
        return jitterMicroseconds;
    }
    
    public boolean isTsnChannelReserved() {
        return tsnChannelReserved;
    }
    
    public boolean isIsacCalibrated() {
        return isacCalibrated;
    }
    
    /**
     * Checks if Phase 2 is complete.
     * @return true if all Phase 2 steps are completed
     */
    public boolean isPhaseComplete() {
        return status == SynchronizationStatus.LOCKED &&
               jitterMicroseconds < 10.0 &&
               tsnChannelReserved &&
               isacCalibrated;
    }
}
