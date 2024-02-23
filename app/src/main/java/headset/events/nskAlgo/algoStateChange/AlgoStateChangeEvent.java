package headset.events.nskAlgo.algoStateChange;

import headset.events.nskAlgo.NskAlgoEvent;
import java.util.HashMap;
import java.util.Map;

public class AlgoStateChangeEvent extends NskAlgoEvent {

  private static final Map<Integer, AlgoState> INTEGER_ALGO_STATE_TYPES_MAP = new HashMap<Integer, AlgoState>();
  private static final Map<Integer, AlgoStateChangeReason> INTEGER_ALGO_STATE_CHANGE_REASONS_MAP = new HashMap<Integer, AlgoStateChangeReason>();

  static {
    for (AlgoStateChangeReason type : AlgoStateChangeReason.values()) {
      INTEGER_ALGO_STATE_CHANGE_REASONS_MAP.put(type.ordinal(), type);
    }

    for (AlgoState type : AlgoState.values()) {
      INTEGER_ALGO_STATE_TYPES_MAP.put(type.ordinal(), type);
    }
  }

  private final AlgoState state;
  private final AlgoStateChangeReason reason;

  public AlgoStateChangeEvent(Object source, int state, int reason) {
    super(source);
    this.state = INTEGER_ALGO_STATE_TYPES_MAP.get(state);
    this.reason = INTEGER_ALGO_STATE_CHANGE_REASONS_MAP.get(reason);
  }

  public AlgoState getState() {
    return state;
  }

  public AlgoStateChangeReason getReason() {
    return reason;
  }

  public String toString() {
    return super.toString() + "AlgoStateChangeEvent { State: " + state + ", Reason: " + reason
        + "}";
  }
}
