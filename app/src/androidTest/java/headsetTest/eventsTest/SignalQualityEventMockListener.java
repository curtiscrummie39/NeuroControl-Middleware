package headsetTest.eventsTest;

import headset.events.nskAlgo.AlgoSignalQuality.ISignalQualityUpdateEventListener;
import headset.events.nskAlgo.AlgoSignalQuality.SignalQualityUpdateEvent;

public class SignalQualityEventMockListener implements ISignalQualityUpdateEventListener {

  private int signalQualityCount = 0;
  private int lastSignalQuality = 0;

  @Override
  public void onSignalQualityUpdate(SignalQualityUpdateEvent event) {
    this.signalQualityCount++;
    this.lastSignalQuality = event.getSignalQualityData().qualityLevel();
  }

  public int getSignalQualityCount() {
    return this.signalQualityCount;
  }

  public int getLastSignalQuality() {
    return this.lastSignalQuality;
  }

}
