package headsetTest.eventsTest.nskAlgo;

import headset.events.nskAlgo.algoStateChange.AlgoStateChangeEvent;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeReason;
import headset.events.nskAlgo.algoStateChange.AlgoState;
import headset.events.nskAlgo.algoStateChange.IAlgoStateChangeEventListener;

public class AlgoStateMockEventListener implements IAlgoStateChangeEventListener {

  private int stateChangeCount = 0;
  private AlgoState lastState;

  private AlgoStateChangeReason lastReason;

  @Override
  public void onAlgoStateChange(AlgoStateChangeEvent event) {
    this.stateChangeCount++;
    this.lastState = event.getState();
    this.lastReason = event.getReason();
  }

  public int getStateChangeCount() {
    return this.stateChangeCount;
  }

  public AlgoState getLastState() {
    return this.lastState;
  }

  public AlgoStateChangeReason getLastReason() {
    return this.lastReason;
  }

}
