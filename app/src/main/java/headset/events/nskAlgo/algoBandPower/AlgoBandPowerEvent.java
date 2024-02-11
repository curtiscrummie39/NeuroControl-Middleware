package headset.events.nskAlgo.algoBandPower;

import java.util.EventObject;

public class AlgoBandPowerEvent extends EventObject {

  private final AlgoBandPowerData bandPowerData;

  public AlgoBandPowerEvent(Object source, AlgoBandPowerData bandPowerData) {
    super(source);
    this.bandPowerData = bandPowerData;
  }

  public AlgoBandPowerData getBandPowerData() {
    return this.bandPowerData;
  }

  public String toString() {
    return "AlgoBandPowerEvent { BandPowerData: " + bandPowerData + "}";
  }
}
