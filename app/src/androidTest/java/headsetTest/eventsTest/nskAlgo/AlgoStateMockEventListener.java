package headsetTest.eventsTest.nskAlgo;

import headset.events.nskAlgo.algoStateChange.AlgoStateChangeEvent;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeReasons;
import headset.events.nskAlgo.algoStateChange.AlgoStateTypes;
import headset.events.nskAlgo.algoStateChange.IAlgoStateChangeEventListener;

public class AlgoStateMockEventListener implements IAlgoStateChangeEventListener {

  private int stateChangeCount = 0;
  private AlgoStateTypes lastState;

  private AlgoStateChangeReasons lastReason;

  @Override
  public void onAlgoStateChange(AlgoStateChangeEvent event) {
    this.stateChangeCount++;
    this.lastState = event.getState();
    this.lastReason = event.getReason();
  }

  public int getStateChangeCount() {
    return this.stateChangeCount;
  }

  public AlgoStateTypes getLastState() {
    return this.lastState;
  }

  public AlgoStateChangeReasons getLastReason() {
    return this.lastReason;
  }

}
