package headset.events.nskAlgo.AlgoMeditation;

import java.util.EventObject;

public class MeditationDataUpdateEvent extends EventObject {

  private final MeditationData meditationData;

  public MeditationDataUpdateEvent(Object source, MeditationData data) {
    super(source);
    this.meditationData = data;
  }

  public MeditationData getMeditationData() {
    return this.meditationData;
  }
}
