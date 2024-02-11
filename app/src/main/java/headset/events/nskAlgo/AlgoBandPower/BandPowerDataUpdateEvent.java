package headset.events.nskAlgo.AlgoBandPower;

import java.util.EventObject;

public class BandPowerDataUpdateEvent extends EventObject {

  private final BandPowerData bandPowerData;

  public BandPowerDataUpdateEvent(Object source, BandPowerData bandPowerData) {
    super(source);
    this.bandPowerData = bandPowerData;
  }

  public BandPowerData getBandPowerData() {
    return this.bandPowerData;
  }
}
