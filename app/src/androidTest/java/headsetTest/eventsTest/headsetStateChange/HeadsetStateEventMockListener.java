package headsetTest.eventsTest.headsetStateChange;

import headset.events.headsetStateChange.HeadsetStateChangeEvent;
import headset.events.headsetStateChange.HeadsetStateTypes;
import headset.events.headsetStateChange.IHeadsetStateChangeEventListener;

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
