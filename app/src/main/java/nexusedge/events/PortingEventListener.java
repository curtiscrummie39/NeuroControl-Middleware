package nexusedge.events;

import nexusedge.protocol.NexusEdgeDevice;
import nexusedge.protocol.PortingPhase;

/**
 * Listener interface for receiving notifications about SEP-1 porting protocol events.
 */
public interface PortingEventListener {
    
    /**
     * Called when the porting protocol transitions from one phase to another.
     * @param oldPhase the previous phase
     * @param newPhase the new phase
     */
    void onPhaseChange(PortingPhase oldPhase, PortingPhase newPhase);
    
    /**
     * Called when the porting protocol completes successfully.
     * @param device the fully operational Nexus Edge device
     */
    void onPortingComplete(NexusEdgeDevice device);
}
