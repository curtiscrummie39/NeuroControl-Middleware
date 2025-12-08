package nexusedge.core;

import nexusedge.gnb.GnbNode;
import nexusedge.protocol.NexusEdgePortingProtocol;
import nexusedge.session.UrllcSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Main service core for Nexus 6G network management.
 * Manages External Interface Unit (EIU) provisioning, URLLC sessions,
 * and gNB (base station) status monitoring.
 */
public class NexusServiceCore {

  private NexusEdgePortingProtocol protocol;
  private UrllcSession urllcSession;
  private List<GnbNode> gnbNodes;
  private double serviceCredits; // NXC credits
  private double e2eLatencyMicroseconds;
  private double currentJitterMicroseconds;
  private double predictiveLeadTimeSeconds;

  /**
   * Creates a new Nexus Service Core instance.
   *
   * @param accountPublicKey Account public key for authentication
   * @param initialCredits   Initial service credits in NXC
   */
  public NexusServiceCore(String accountPublicKey, double initialCredits) {
    this.protocol = new NexusEdgePortingProtocol(accountPublicKey);
    this.urllcSession = new UrllcSession();
    this.gnbNodes = new ArrayList<>();
    this.serviceCredits = initialCredits;
    this.e2eLatencyMicroseconds = 0.0;
    this.currentJitterMicroseconds = 0.0;
    this.predictiveLeadTimeSeconds = 0.0;
  }

  /**
   * Gets the protocol instance.
   *
   * @return NexusEdgePortingProtocol instance
   */
  public NexusEdgePortingProtocol getProtocol() {
    return protocol;
  }

  /**
   * Gets the URLLC session.
   *
   * @return UrllcSession instance
   */
  public UrllcSession getUrllcSession() {
    return urllcSession;
  }

  /**
   * Gets the service credits balance.
   *
   * @return Current service credits in NXC
   */
  public double getServiceCredits() {
    return serviceCredits;
  }

  /**
   * Adds service credits.
   *
   * @param credits Amount of NXC to add
   */
  public void addServiceCredits(double credits) {
    this.serviceCredits += credits;
  }

  /**
   * Deducts service credits.
   *
   * @param credits Amount of NXC to deduct
   * @return true if successful, false if insufficient credits
   */
  public boolean deductServiceCredits(double credits) {
    if (this.serviceCredits >= credits) {
      this.serviceCredits -= credits;
      return true;
    }
    return false;
  }

  /**
   * Gets the end-to-end latency.
   *
   * @return E2E latency in microseconds
   */
  public double getE2eLatencyMicroseconds() {
    return e2eLatencyMicroseconds;
  }

  /**
   * Sets the end-to-end latency.
   *
   * @param e2eLatencyMicroseconds E2E latency in microseconds
   */
  public void setE2eLatencyMicroseconds(double e2eLatencyMicroseconds) {
    this.e2eLatencyMicroseconds = e2eLatencyMicroseconds;
  }

  /**
   * Gets the current jitter.
   *
   * @return Current jitter in microseconds
   */
  public double getCurrentJitterMicroseconds() {
    return currentJitterMicroseconds;
  }

  /**
   * Sets the current jitter.
   *
   * @param currentJitterMicroseconds Current jitter in microseconds
   */
  public void setCurrentJitterMicroseconds(double currentJitterMicroseconds) {
    this.currentJitterMicroseconds = currentJitterMicroseconds;
  }

  /**
   * Gets the predictive lead time.
   *
   * @return Predictive lead time in seconds
   */
  public double getPredictiveLeadTimeSeconds() {
    return predictiveLeadTimeSeconds;
  }

  /**
   * Sets the predictive lead time.
   *
   * @param predictiveLeadTimeSeconds Predictive lead time in seconds
   */
  public void setPredictiveLeadTimeSeconds(double predictiveLeadTimeSeconds) {
    this.predictiveLeadTimeSeconds = predictiveLeadTimeSeconds;
  }

  /**
   * Adds a gNB node to the network.
   *
   * @param gnbNode The gNB node to add
   */
  public void addGnbNode(GnbNode gnbNode) {
    this.gnbNodes.add(gnbNode);
  }

  /**
   * Gets all gNB nodes.
   *
   * @return List of gNB nodes
   */
  public List<GnbNode> getGnbNodes() {
    return new ArrayList<>(gnbNodes);
  }

  /**
   * Gets a specific gNB node by ID.
   *
   * @param nodeId Node identifier
   * @return GnbNode if found, null otherwise
   */
  public GnbNode getGnbNode(String nodeId) {
    return gnbNodes.stream()
        .filter(node -> node.getNodeId().equals(nodeId))
        .findFirst()
        .orElse(null);
  }

  /**
   * Removes a gNB node from the network.
   *
   * @param nodeId Node identifier
   * @return true if removed, false if not found
   */
  public boolean removeGnbNode(String nodeId) {
    return gnbNodes.removeIf(node -> node.getNodeId().equals(nodeId));
  }

  /**
   * Initiates a URLLC session if sufficient credits are available.
   *
   * @return true if session initiated, false otherwise
   */
  public boolean initiateUrllcSession() {
    if (serviceCredits >= urllcSession.getCostPerInterval() && !urllcSession.isActive()) {
      return urllcSession.initiate();
    }
    return false;
  }

  /**
   * Terminates the current URLLC session and deducts costs.
   *
   * @return Total cost deducted in NXC
   */
  public double terminateUrllcSession() {
    if (urllcSession.isActive()) {
      double totalCost = urllcSession.calculateTotalCost();
      urllcSession.terminate();
      deductServiceCredits(totalCost);
      return totalCost;
    }
    return 0.0;
  }

  /**
   * Connects to the Nexus 6G Core infrastructure.
   *
   * @return true if connection successful
   */
  public boolean connect() {
    return protocol.connect();
  }

  /**
   * Disconnects from the Nexus 6G Core infrastructure.
   */
  public void disconnect() {
    if (urllcSession.isActive()) {
      terminateUrllcSession();
    }
    protocol.disconnect();
  }

  /**
   * Gets the count of operational gNB nodes.
   *
   * @return Number of operational nodes
   */
  public int getOperationalGnbCount() {
    return (int) gnbNodes.stream().filter(GnbNode::isOperational).count();
  }

  /**
   * Updates network metrics based on gNB node data.
   * Calculates average latency across all operational nodes.
   */
  public void updateNetworkMetrics() {
    double avgLatency = gnbNodes.stream()
        .filter(GnbNode::isOperational)
        .mapToDouble(GnbNode::getLatencyMicroseconds)
        .average()
        .orElse(0.0);
    this.e2eLatencyMicroseconds = avgLatency;
  }

  /**
   * Checks if the service is ready to operate.
   *
   * @return true if protocol is connected and has at least one operational gNB
   */
  public boolean isServiceReady() {
    return protocol.isConnected() && getOperationalGnbCount() > 0;
  }
}
