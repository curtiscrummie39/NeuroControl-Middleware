package headsetTest.eventsTest.stream;

import headset.events.stream.streamMeditation.IStreamMeditationEventListener;
import headset.events.stream.streamMeditation.StreamMeditationEvent;

public class StreamMeditationMockEventListener implements IStreamMeditationEventListener {

  private int meditationCount = 0;
  private int lastMeditationValue = 0;

  @Override
  public void onMeditationUpdate(StreamMeditationEvent event) {
    this.meditationCount++;
    this.lastMeditationValue = event.getMeditationData().meditation();
  }

  public int getMeditationCount() {
    return this.meditationCount;
  }

  public int getLastMeditationValue() {
    return this.lastMeditationValue;
  }

}
