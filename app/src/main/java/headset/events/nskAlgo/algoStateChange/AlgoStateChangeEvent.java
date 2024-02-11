package headset.events.nskAlgo.algoStateChange;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

public class AlgoStateChangeEvent extends EventObject {

  private static final Map<Integer, AlgoStateTypes> INTEGER_ALGO_STATE_TYPES_MAP = new HashMap<Integer, AlgoStateTypes>();
  private static final Map<Integer, AlgoStateChangeResons> INTEGER_ALGO_STATE_CHANGE_RESONS_MAP = new HashMap<Integer, AlgoStateChangeResons>();

  static {
    for (AlgoStateChangeResons type : AlgoStateChangeResons.values()) {
      INTEGER_ALGO_STATE_CHANGE_RESONS_MAP.put(type.ordinal(), type);
    }
    
    for (AlgoStateTypes type : AlgoStateTypes.values()) {
      INTEGER_ALGO_STATE_TYPES_MAP.put(type.ordinal(), type);
    }
  }

  private final AlgoStateTypes state;
  private final AlgoStateChangeResons reason;

  public AlgoStateChangeEvent(Object source, int state, int reason) {
    super(source);
    this.state = INTEGER_ALGO_STATE_TYPES_MAP.get(state);
    this.reason = INTEGER_ALGO_STATE_CHANGE_RESONS_MAP.get(reason);
  }

  public AlgoStateTypes getState() {
    return state;
  }

  public AlgoStateChangeResons getReason() {
    return reason;
  }


  public String toString() {
    return "AlgoStateChangeEvent { State: " + state + ", Reason: " + reason + "}";
  }
}
