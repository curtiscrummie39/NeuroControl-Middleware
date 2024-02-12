package headsetTest.eventsTest.nskAlgo;

import headset.events.nskAlgo.algoBandPower.AlgoBandPowerEvent;
import headset.events.nskAlgo.algoBandPower.IAlgoBandPowerEventListener;

public class AlgoBandPowerMockEventListener implements IAlgoBandPowerEventListener {

  private final float[] lastBandPowerValues = new float[5];
  private int bandPowerCount = 0;

  @Override
  public void onBandPowerUpdate(AlgoBandPowerEvent event) {
    this.bandPowerCount++;
    this.lastBandPowerValues[0] = event.getBandPowerData().delta();
    this.lastBandPowerValues[1] = event.getBandPowerData().theta();
    this.lastBandPowerValues[2] = event.getBandPowerData().alpha();
    this.lastBandPowerValues[3] = event.getBandPowerData().beta();
    this.lastBandPowerValues[4] = event.getBandPowerData().gamma();
  }

  public int getBandPowerCount() {
    return this.bandPowerCount;
  }

  public float[] getLastBandPowerValue() {
    return this.lastBandPowerValues;
  }

}
