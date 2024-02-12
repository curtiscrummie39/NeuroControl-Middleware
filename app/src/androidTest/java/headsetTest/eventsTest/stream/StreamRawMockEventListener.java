package headsetTest.eventsTest.stream;

import headset.events.stream.streamRaw.IStreamRawDataEventListener;
import headset.events.stream.streamRaw.StreamRawDataEvent;

public class StreamRawMockEventListener implements IStreamRawDataEventListener {

  private int rawCount = 0;
  private short[] lastRawValue = new short[512];

  @Override
  public void onRawDataUpdate(StreamRawDataEvent event) {
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
