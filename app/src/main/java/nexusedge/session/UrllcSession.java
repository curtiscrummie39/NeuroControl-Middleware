package nexusedge.session;

/**
 * Represents a URLLC (Ultra-Reliable Low-Latency Communication) session
 * in the Nexus 6G Core infrastructure.
 */
public class UrllcSession {

  /**
   * Session status enumeration.
   */
  public enum SessionStatus {
    STANDBY,
    ACTIVE,
    TERMINATED
  }

  private SessionStatus status;
  private long sessionStartTime;
  private long elapsedSessionTime;
  private double costPerInterval; // NXC per interval
  private long intervalDuration; // Duration in milliseconds

  /**
   * Creates a new URLLC session in STANDBY state.
   */
  public UrllcSession() {
    this.status = SessionStatus.STANDBY;
    this.sessionStartTime = 0;
    this.elapsedSessionTime = 0;
    this.costPerInterval = 1.0; // 1 NXC per interval
    this.intervalDuration = 10000; // 10 seconds in milliseconds
  }

  /**
   * Creates a new URLLC session with custom cost configuration.
   *
   * @param costPerInterval Cost in NXC per interval
   * @param intervalDuration Duration of each interval in milliseconds
   */
  public UrllcSession(double costPerInterval, long intervalDuration) {
    this.status = SessionStatus.STANDBY;
    this.sessionStartTime = 0;
    this.elapsedSessionTime = 0;
    this.costPerInterval = costPerInterval;
    this.intervalDuration = intervalDuration;
  }

  /**
   * Gets the current session status.
   *
   * @return Current session status
   */
  public SessionStatus getStatus() {
    return status;
  }

  /**
   * Gets the elapsed session time in seconds.
   *
   * @return Elapsed time in seconds
   */
  public long getElapsedSessionTime() {
    if (status == SessionStatus.ACTIVE) {
      return (System.currentTimeMillis() - sessionStartTime) / 1000;
    }
    return elapsedSessionTime / 1000;
  }

  /**
   * Gets the elapsed session time in milliseconds.
   *
   * @return Elapsed time in milliseconds
   */
  public long getElapsedSessionTimeMillis() {
    if (status == SessionStatus.ACTIVE) {
      return System.currentTimeMillis() - sessionStartTime;
    }
    return elapsedSessionTime;
  }

  /**
   * Gets the cost per interval.
   *
   * @return Cost per interval in NXC
   */
  public double getCostPerInterval() {
    return costPerInterval;
  }

  /**
   * Gets the interval duration.
   *
   * @return Interval duration in milliseconds
   */
  public long getIntervalDuration() {
    return intervalDuration;
  }

  /**
   * Initiates the URLLC session.
   *
   * @return true if session was initiated, false if already active
   */
  public boolean initiate() {
    if (status != SessionStatus.STANDBY) {
      return false;
    }
    this.status = SessionStatus.ACTIVE;
    this.sessionStartTime = System.currentTimeMillis();
    this.elapsedSessionTime = 0;
    return true;
  }

  /**
   * Terminates the URLLC session.
   *
   * @return Elapsed time in seconds when session was terminated
   */
  public long terminate() {
    if (status == SessionStatus.ACTIVE) {
      this.elapsedSessionTime = System.currentTimeMillis() - sessionStartTime;
      this.status = SessionStatus.TERMINATED;
    }
    return elapsedSessionTime / 1000;
  }

  /**
   * Resets the session to STANDBY state.
   */
  public void reset() {
    this.status = SessionStatus.STANDBY;
    this.sessionStartTime = 0;
    this.elapsedSessionTime = 0;
  }

  /**
   * Calculates the total cost for the current session.
   *
   * @return Total cost in NXC
   */
  public double calculateTotalCost() {
    long totalTimeMillis = (status == SessionStatus.ACTIVE) 
        ? (System.currentTimeMillis() - sessionStartTime) 
        : elapsedSessionTime;
    
    double intervals = (double) totalTimeMillis / intervalDuration;
    return intervals * costPerInterval;
  }

  /**
   * Checks if the session is active.
   *
   * @return true if session is active, false otherwise
   */
  public boolean isActive() {
    return status == SessionStatus.ACTIVE;
  }
}
