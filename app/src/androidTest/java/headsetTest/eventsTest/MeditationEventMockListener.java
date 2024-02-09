package headsetTest.eventsTest;

import headset.events.meditation.IMeditationDataUpdateEventListener;

public class MeditationEventMockListener implements IMeditationDataUpdateEventListener {

  private int meditationCount = 0;
  private int lastMeditationValue = 0;

  @Override
  public void onMeditationDataUpdate(headset.events.meditation.MeditationDataUpdateEvent event) {
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
