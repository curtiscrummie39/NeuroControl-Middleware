package nexusedge.authentication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for AuthenticationManager class (Phase 1).
 */
public class AuthenticationManagerTest {
    
    private AuthenticationManager authManager;
    
    @Before
    public void setUp() {
        authManager = new AuthenticationManager();
    }
    
    @Test
    public void testInitialState() {
        assertEquals(AuthenticationStatus.NOT_STARTED, authManager.getStatus());
        assertNull(authManager.getSessionKey());
        assertFalse(authManager.isEdgeAiActivated());
        assertFalse(authManager.isPhaseComplete());
    }
    
    @Test
    public void testPerformSimAuthentication_Success() {
        boolean result = authManager.performSimAuthentication("VALID-CREDENTIALS");
        
        assertTrue(result);
        assertEquals(AuthenticationStatus.AUTHENTICATED, authManager.getStatus());
    }
    
    @Test
    public void testPerformSimAuthentication_Failure_NullCredentials() {
        boolean result = authManager.performSimAuthentication(null);
        
        assertFalse(result);
        assertEquals(AuthenticationStatus.FAILED, authManager.getStatus());
    }
    
    @Test
    public void testPerformSimAuthentication_Failure_EmptyCredentials() {
        boolean result = authManager.performSimAuthentication("");
        
        assertFalse(result);
        assertEquals(AuthenticationStatus.FAILED, authManager.getStatus());
    }
    
    @Test
    public void testPerformQuantumResistantKeyExchange_Success() {
        authManager.performSimAuthentication("VALID-CREDENTIALS");
        
        String sessionKey = authManager.performQuantumResistantKeyExchange();
        
        assertNotNull(sessionKey);
        assertTrue(sessionKey.startsWith("QR-KEY-"));
        assertEquals(sessionKey, authManager.getSessionKey());
    }
    
    @Test
    public void testPerformQuantumResistantKeyExchange_NotAuthenticated() {
        String sessionKey = authManager.performQuantumResistantKeyExchange();
        
        assertNull(sessionKey);
        assertNull(authManager.getSessionKey());
    }
    
    @Test
    public void testActivateEdgeAI_Success() {
        authManager.performSimAuthentication("VALID-CREDENTIALS");
        authManager.performQuantumResistantKeyExchange();
        
        boolean result = authManager.activateEdgeAI();
        
        assertTrue(result);
        assertTrue(authManager.isEdgeAiActivated());
    }
    
    @Test
    public void testActivateEdgeAI_NotAuthenticated() {
        boolean result = authManager.activateEdgeAI();
        
        assertFalse(result);
        assertFalse(authManager.isEdgeAiActivated());
    }
    
    @Test
    public void testActivateEdgeAI_NoSessionKey() {
        authManager.performSimAuthentication("VALID-CREDENTIALS");
        // Skip key exchange
        
        boolean result = authManager.activateEdgeAI();
        
        assertFalse(result);
        assertFalse(authManager.isEdgeAiActivated());
    }
    
    @Test
    public void testIsPhaseComplete_AllStepsCompleted() {
        authManager.performSimAuthentication("VALID-CREDENTIALS");
        authManager.performQuantumResistantKeyExchange();
        authManager.activateEdgeAI();
        
        assertTrue(authManager.isPhaseComplete());
    }
    
    @Test
    public void testIsPhaseComplete_MissingSteps() {
        authManager.performSimAuthentication("VALID-CREDENTIALS");
        // Skip key exchange and AI activation
        
        assertFalse(authManager.isPhaseComplete());
    }
    
    @Test
    public void testCompletePhase1Workflow() {
        // Step 1: SIM authentication
        boolean authResult = authManager.performSimAuthentication("VALID-CREDENTIALS");
        assertTrue(authResult);
        assertEquals(AuthenticationStatus.AUTHENTICATED, authManager.getStatus());
        
        // Step 2: Quantum-resistant key exchange
        String sessionKey = authManager.performQuantumResistantKeyExchange();
        assertNotNull(sessionKey);
        
        // Step 3: Edge AI activation
        boolean aiResult = authManager.activateEdgeAI();
        assertTrue(aiResult);
        
        // Verify phase completion
        assertTrue(authManager.isPhaseComplete());
    }
}
