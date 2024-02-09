package headsetTest.eventsTest;

import headset.events.blink.BlinkEvent;
import headset.events.blink.IBlinkEventListener;

public class BlinkEventMockListener implements IBlinkEventListener {

  private int blinkCount = 0;
  private int lastBlinkStrength = 0;

  @Override
  public void onBlink(BlinkEvent event) {
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
