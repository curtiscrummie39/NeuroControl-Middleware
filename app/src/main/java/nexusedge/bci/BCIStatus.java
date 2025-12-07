package nexusedge.bci;

/**
 * Represents the BCI (Brain-Computer Interface) system status.
 */
public enum BCIStatus {
    /**
     * BCI system is offline.
     */
    OFFLINE,
    
    /**
     * BCI pipeline is opening.
     */
    OPENING_PIPELINE,
    
    /**
     * BCI predictive model is loading.
     */
    LOADING_MODEL,
    
    /**
     * BCI system is fully online and operational.
     */
    ONLINE,
    
    /**
     * BCI onboarding failed.
     */
    FAILED
}
