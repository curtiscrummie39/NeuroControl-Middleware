package nexusedge.gnb;

/**
 * Represents a gNB (Next Generation NodeB) base station in the Nexus 6G Core network.
 * Each gNB serves a specific geographic area and maintains network metrics.
 */
public class GnbNode {

  /**
   * Status of the gNB node.
   */
  public enum GnbStatus {
    OPTIMAL,
    NOMINAL,
    HIGH_LOAD,
    OFFLINE
  }

  private String nodeId;
  private String location;
  private GnbStatus status;
  private double latencyMicroseconds;
  private int trafficPercentage;

  /**
   * Creates a new gNB node.
   *
   * @param nodeId   Unique identifier for the node (e.g., "ALPHA-001")
   * @param location Physical location or area served
   * @param status   Current operational status
   */
  public GnbNode(String nodeId, String location, GnbStatus status) {
    this.nodeId = nodeId;
    this.location = location;
    this.status = status;
    this.latencyMicroseconds = 0.0;
    this.trafficPercentage = 0;
  }

  /**
   * Creates a new gNB node with full parameters.
   *
   * @param nodeId               Unique identifier for the node
   * @param location             Physical location or area served
   * @param status               Current operational status
   * @param latencyMicroseconds  Current latency in microseconds
   * @param trafficPercentage    Current traffic load percentage (0-100)
   */
  public GnbNode(String nodeId, String location, GnbStatus status, 
                 double latencyMicroseconds, int trafficPercentage) {
    this.nodeId = nodeId;
    this.location = location;
    this.status = status;
    this.latencyMicroseconds = latencyMicroseconds;
    this.trafficPercentage = Math.min(100, Math.max(0, trafficPercentage));
  }

  /**
   * Gets the node identifier.
   *
   * @return Node ID
   */
  public String getNodeId() {
    return nodeId;
  }

  /**
   * Gets the location.
   *
   * @return Location description
   */
  public String getLocation() {
    return location;
  }

  /**
   * Gets the current status.
   *
   * @return Node status
   */
  public GnbStatus getStatus() {
    return status;
  }

  /**
   * Sets the node status.
   *
   * @param status New status
   */
  public void setStatus(GnbStatus status) {
    this.status = status;
  }

  /**
   * Gets the current latency in microseconds.
   *
   * @return Latency in microseconds
   */
  public double getLatencyMicroseconds() {
    return latencyMicroseconds;
  }

  /**
   * Sets the latency.
   *
   * @param latencyMicroseconds Latency in microseconds
   */
  public void setLatencyMicroseconds(double latencyMicroseconds) {
    this.latencyMicroseconds = latencyMicroseconds;
  }

  /**
   * Gets the traffic percentage.
   *
   * @return Traffic percentage (0-100)
   */
  public int getTrafficPercentage() {
    return trafficPercentage;
  }

  /**
   * Sets the traffic percentage.
   *
   * @param trafficPercentage Traffic percentage (0-100)
   */
  public void setTrafficPercentage(int trafficPercentage) {
    this.trafficPercentage = Math.min(100, Math.max(0, trafficPercentage));
  }

  /**
   * Checks if the node is operational.
   *
   * @return true if status is not OFFLINE
   */
  public boolean isOperational() {
    return status != GnbStatus.OFFLINE;
  }

  /**
   * Updates the metrics for this gNB node.
   *
   * @param latencyMicroseconds New latency value
   * @param trafficPercentage   New traffic percentage
   */
  public void updateMetrics(double latencyMicroseconds, int trafficPercentage) {
    this.latencyMicroseconds = latencyMicroseconds;
    this.trafficPercentage = Math.min(100, Math.max(0, trafficPercentage));
  }

  @Override
  public String toString() {
    return "GnbNode{" +
        "nodeId='" + nodeId + '\'' +
        ", location='" + location + '\'' +
        ", status=" + status +
        ", latencyMicroseconds=" + latencyMicroseconds +
        ", trafficPercentage=" + trafficPercentage +
        '}';
  }
}
