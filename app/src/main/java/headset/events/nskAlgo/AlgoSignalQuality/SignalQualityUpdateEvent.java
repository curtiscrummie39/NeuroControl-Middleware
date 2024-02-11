package headset.events.nskAlgo.AlgoSignalQuality;


import java.util.EventObject;

public class SignalQualityUpdateEvent extends EventObject {

  private final SignalQualityData signalQuality;

  public SignalQualityUpdateEvent(Object source, SignalQualityData data) {
    super(source);
    this.signalQuality = data;
  }

  public SignalQualityData getSignalQualityData() {
    return this.signalQuality;
  }
}