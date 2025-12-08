package nexusedge.authentication;

/**
 * Represents the authentication status of a Nexus Edge device.
 */
public enum AuthenticationStatus {
    /**
     * Authentication has not been initiated.
     */
    NOT_STARTED,
    
    /**
     * Authentication is in progress.
     */
    IN_PROGRESS,
    
    /**
     * Device has been successfully authenticated.
     */
    AUTHENTICATED,
    
    /**
     * Authentication failed.
     */
    FAILED
}
