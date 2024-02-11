package headsetTest.eventsTest;

import headset.events.nskAlgo.algoSignalQuality.AlgoSignalQualityEvent;
import headset.events.nskAlgo.algoSignalQuality.IAlgoSignalQualityEventListener;

public class SignalQualityEventMockListener implements IAlgoSignalQualityEventListener {

  private int signalQualityCount = 0;
  private int lastSignalQuality = 0;

  @Override
  public void onSignalQualityUpdate(AlgoSignalQualityEvent event) {
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
