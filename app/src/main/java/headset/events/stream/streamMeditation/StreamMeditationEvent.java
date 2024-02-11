package headset.events.stream.streamMeditation;

import headset.events.MeditationData;
import java.util.EventObject;

public class StreamMeditationEvent extends EventObject {

  private final MeditationData meditationData;

  public StreamMeditationEvent(Object source, MeditationData data) {
    super(source);
    this.meditationData = data;
  }

  public MeditationData getMeditationData() {
    return this.meditationData;
  }

  public String toString() {
    return "StreamMeditationEvent { meditationData=" + this.meditationData + "}";
  }
}
