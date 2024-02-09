package headsetTest.eventsTest;

import headset.events.raw.IRawDataUpdateEventListener;

public class RawEventMockListener implements IRawDataUpdateEventListener {

  private int rawCount = 0;
  private short[] lastRawValue = new short[512];

  @Override
  public void onRawDataUpdate(headset.events.raw.RawDataUpdateEvent event) {
    this.rawCount++;
    this.lastRawValue = event.getRawData().rawData();
  }

  public int getRawCount() {
    return this.rawCount;
  }

  public short[] getLastRawValue() {
    return this.lastRawValue;
  }

}
