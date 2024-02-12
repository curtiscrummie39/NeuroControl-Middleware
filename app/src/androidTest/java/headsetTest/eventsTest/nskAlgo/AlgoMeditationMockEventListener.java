package headsetTest.eventsTest.nskAlgo;

import headset.events.nskAlgo.algoMeditation.AlgoMeditationEvent;
import headset.events.nskAlgo.algoMeditation.IAlgoMeditationEventListener;

public class AlgoMeditationMockEventListener implements IAlgoMeditationEventListener {

  private int meditationCount = 0;
  private int lastMeditationValue = 0;

  @Override
  public void onMeditationUpdate(AlgoMeditationEvent event) {
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
