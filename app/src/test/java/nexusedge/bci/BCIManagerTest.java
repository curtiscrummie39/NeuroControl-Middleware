package nexusedge.bci;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for BCIManager class (Phase 4).
 */
public class BCIManagerTest {
    
    private BCIManager bciManager;
    
    @Before
    public void setUp() {
        bciManager = new BCIManager();
    }
    
    @Test
    public void testInitialState() {
        assertEquals(BCIStatus.OFFLINE, bciManager.getStatus());
        assertFalse(bciManager.isNeuralPipelineOpen());
        assertFalse(bciManager.isPredictiveModelLoaded());
        assertFalse(bciManager.isHapticVrPipelineReady());
        assertEquals(0, bciManager.getPredictionLeadTimeSeconds());
        assertFalse(bciManager.isPhaseComplete());
    }
    
    @Test
    public void testOpenNeuralDataPipeline_Success() {
        boolean result = bciManager.openNeuralDataPipeline();
        
        assertTrue(result);
        assertEquals(BCIStatus.OPENING_PIPELINE, bciManager.getStatus());
        assertTrue(bciManager.isNeuralPipelineOpen());
    }
    
    @Test
    public void testLoadPredictiveModel_Success() {
        bciManager.openNeuralDataPipeline();
        
        boolean result = bciManager.loadPredictiveModel();
        
        assertTrue(result);
        assertEquals(BCIStatus.LOADING_MODEL, bciManager.getStatus());
        assertTrue(bciManager.isPredictiveModelLoaded());
        assertTrue(bciManager.getPredictionLeadTimeSeconds() >= 1);
    }
    
    @Test
    public void testLoadPredictiveModel_PipelineNotOpen() {
        boolean result = bciManager.loadPredictiveModel();
        
        assertFalse(result);
        assertFalse(bciManager.isPredictiveModelLoaded());
    }
    
    @Test
    public void testPrepareHapticVrPipeline_Success() {
        bciManager.openNeuralDataPipeline();
        bciManager.loadPredictiveModel();
        
        boolean result = bciManager.prepareHapticVrPipeline();
        
        assertTrue(result);
        assertEquals(BCIStatus.ONLINE, bciManager.getStatus());
        assertTrue(bciManager.isHapticVrPipelineReady());
    }
    
    @Test
    public void testPrepareHapticVrPipeline_ModelNotLoaded() {
        boolean result = bciManager.prepareHapticVrPipeline();
        
        assertFalse(result);
        assertFalse(bciManager.isHapticVrPipelineReady());
    }
    
    @Test
    public void testIsPhaseComplete_AllStepsCompleted() {
        bciManager.openNeuralDataPipeline();
        bciManager.loadPredictiveModel();
        bciManager.prepareHapticVrPipeline();
        
        assertTrue(bciManager.isPhaseComplete());
    }
    
    @Test
    public void testIsPhaseComplete_MissingSteps() {
        bciManager.openNeuralDataPipeline();
        // Skip model loading and haptic/VR
        
        assertFalse(bciManager.isPhaseComplete());
    }
    
    @Test
    public void testCompletePhase4Workflow() {
        // Step 1: Neural data pipeline
        boolean pipelineResult = bciManager.openNeuralDataPipeline();
        assertTrue(pipelineResult);
        assertTrue(bciManager.isNeuralPipelineOpen());
        
        // Step 2: Predictive model loading
        boolean modelResult = bciManager.loadPredictiveModel();
        assertTrue(modelResult);
        assertTrue(bciManager.isPredictiveModelLoaded());
        assertEquals(2, bciManager.getPredictionLeadTimeSeconds());
        
        // Step 3: Haptic/VR pipeline
        boolean hapticResult = bciManager.prepareHapticVrPipeline();
        assertTrue(hapticResult);
        assertTrue(bciManager.isHapticVrPipelineReady());
        assertEquals(BCIStatus.ONLINE, bciManager.getStatus());
        
        // Verify phase completion
        assertTrue(bciManager.isPhaseComplete());
    }
    
    @Test
    public void testPredictionLeadTime() {
        bciManager.openNeuralDataPipeline();
        bciManager.loadPredictiveModel();
        
        int leadTime = bciManager.getPredictionLeadTimeSeconds();
        
        assertTrue("Lead time should be at least 1 second", leadTime >= 1);
        assertTrue("Lead time should be at most 2 seconds", leadTime <= 2);
    }
}
