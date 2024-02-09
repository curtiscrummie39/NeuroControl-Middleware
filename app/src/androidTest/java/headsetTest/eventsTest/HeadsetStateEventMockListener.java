package headsetTest.eventsTest;

import headset.events.stateChange.IHeadsetStateChangeEventListener;
import headset.events.stateChange.HeadsetStateTypes;

public class HeadsetStateEventMockListener implements IHeadsetStateChangeEventListener {

  private int stateChangeCount=0;
  private HeadsetStateTypes lastState = HeadsetStateTypes.TEST;

  @Override
  public void onHeadsetStateChange(headset.events.stateChange.HeadsetStateChangeEvent event) {
    this.stateChangeCount++;
    this.lastState = event.getState();
  }

  public int getStateChangeCount() {
    return this.stateChangeCount;
  }

  public HeadsetStateTypes getLastState() {
    return this.lastState;
  }

}
