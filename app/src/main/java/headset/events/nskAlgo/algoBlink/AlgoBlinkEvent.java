package headset.events.nskAlgo.algoBlink;

import java.util.EventObject;

public class AlgoBlinkEvent extends EventObject {

  private final AlgoBlinkData blinkData;

  public AlgoBlinkEvent(Object source, AlgoBlinkData data) {
    super(source);
    this.blinkData = data;
  }

  public AlgoBlinkData getBlinkData() {
    return this.blinkData;
  }

  public String toString() {
    return "AlgoBlinkEvent { BlinkData: " + blinkData + "}";
  }

}
