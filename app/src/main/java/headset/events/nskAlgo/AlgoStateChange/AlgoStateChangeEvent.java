package headset.events.nskAlgo.AlgoStateChange;

import java.util.EventObject;

public class AlgoStateChangeEvent extends EventObject {

  private final AlgoStateTypes state;
  private final AlgoStateChangeResons reason;

  public AlgoStateChangeEvent(Object source, AlgoStateTypes state, AlgoStateChangeResons reason) {
    super(source);
    this.state = state;
    this.reason = reason;
  }

  public AlgoStateTypes getState() {
    return state;
  }

  public AlgoStateChangeResons getReason() {
    return reason;
  }
}
