package headsetTest.eventsTest.stream;

import headset.events.stream.streamBandPower.IStreamBandPowerEventListener;
import headset.events.stream.streamBandPower.StreamBandPowerEvent;

public class StreamBandPowerMockEventListener implements IStreamBandPowerEventListener {

  private int eegDataCount = 0;
  private int[] lastEegDataValue = new int[7];

  @Override
  public void onEEGDataUpdate(StreamBandPowerEvent event) {
    this.eegDataCount++;
    this.lastEegDataValue = new int[]{
        event.getEEGData().delta(),
        event.getEEGData().theta(),
        event.getEEGData().lowAlpha(),
        event.getEEGData().highAlpha(),
        event.getEEGData().lowBeta(),
        event.getEEGData().highBeta(),
        event.getEEGData().lowGamma(),
        event.getEEGData().midGamma()
    };
  }

  public int getEEGDataCount() {
    return this.eegDataCount;
  }

  public int[] getLastEEGDataValue() {
    return this.lastEegDataValue;
  }

}
