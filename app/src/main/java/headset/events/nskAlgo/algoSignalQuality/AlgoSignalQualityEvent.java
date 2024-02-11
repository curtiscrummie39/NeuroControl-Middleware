package headset.events.nskAlgo.algoSignalQuality;


import java.util.EventObject;

public class AlgoSignalQualityEvent extends EventObject {

  private final AlgoSignalQualityData signalQuality;

  public AlgoSignalQualityEvent(Object source, AlgoSignalQualityData data) {
    super(source);
    this.signalQuality = data;
  }

  public AlgoSignalQualityData getSignalQualityData() {
    return this.signalQuality;
  }

  public String toString() {
    return "AlgoSignalQualityEvent { SignalQuality: " + signalQuality + "}";
  }
}