package nexusedge.protocol;

/**
 * Represents the phases of the SEP-1 porting protocol.
 */
public enum PortingPhase {
    /**
     * Porting has not started.
     */
    NOT_STARTED,
    
    /**
     * Phase 1: Physical Handshake and Identity Verification.
     */
    PHASE_1,
    
    /**
     * Phase 2: Microsecond Synchronization and Deterministic Link.
     */
    PHASE_2,
    
    /**
     * Phase 3: Advanced Antenna System Configuration.
     */
    PHASE_3,
    
    /**
     * Phase 4: Bio-Digital Interface (BCI) Onboarding.
     */
    PHASE_4,
    
    /**
     * All phases complete - device fully operational.
     */
    COMPLETE
}
