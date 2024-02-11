package headsetTest.eventsTest;

import headset.events.stream.stateChange.HeadsetStateChangeEvent;
import headset.events.stream.stateChange.HeadsetStateTypes;
import headset.events.stream.stateChange.IHeadsetStateChangeEventListener;

public class HeadsetStateEventMockListener implements IHeadsetStateChangeEventListener {

  private int stateChangeCount = 0;
  private HeadsetStateTypes lastState = HeadsetStateTypes.TEST;

  @Override
  public void onHeadsetStateChange(HeadsetStateChangeEvent event) {
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
