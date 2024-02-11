package headsetTest.eventsTest;

import headset.events.stream.meditation.IMeditationDataUpdateEventListener;
import headset.events.stream.meditation.MeditationDataUpdateEvent;

public class MeditationEventMockListener implements IMeditationDataUpdateEventListener {

  private int meditationCount = 0;
  private int lastMeditationValue = 0;

  @Override
  public void onMeditationDataUpdate(MeditationDataUpdateEvent event) {
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
