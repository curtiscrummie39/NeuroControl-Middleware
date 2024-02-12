package headsetTest.eventsTest.nskAlgo;

import headset.events.nskAlgo.algoBlink.AlgoBlinkEvent;
import headset.events.nskAlgo.algoBlink.IAlgoBlinkEventListener;

public class BlinkEventMockListener implements IAlgoBlinkEventListener {

  private int blinkCount = 0;
  private int lastBlinkStrength = 0;

  @Override
  public void onBlink(AlgoBlinkEvent event) {
    this.blinkCount++;
    this.lastBlinkStrength = event.getBlinkData().strength();
  }

  public int getBlinkCount() {
    return this.blinkCount;
  }

  public int getLastBlinkStrength() {
    return this.lastBlinkStrength;
  }
}
