package headsetTest.eventsTest;

import headset.events.bandPower.IBandPowerDataUpdateEventListener;

public class BandPowerEventMockListener implements IBandPowerDataUpdateEventListener{

  private int bandPowerCount = 0;
  private float[] lastBandPowerValues = new float[5];

  @Override
  public void onBandPowerDataUpdate(headset.events.bandPower.BandPowerDataUpdateEvent event) {
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
