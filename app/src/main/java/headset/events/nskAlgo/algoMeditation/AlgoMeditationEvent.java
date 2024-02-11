package headset.events.nskAlgo.algoMeditation;

import headset.events.MeditationData;
import java.util.EventObject;

public class AlgoMeditationEvent extends EventObject {

  private final MeditationData meditationData;

  public AlgoMeditationEvent(Object source, MeditationData data) {
    super(source);
    this.meditationData = data;
  }

  public MeditationData getMeditationData() {
    return this.meditationData;
  }

  public String toString() {
    return "AlgoMeditationEvent { MeditationData: " + meditationData + "}";
  }
}
