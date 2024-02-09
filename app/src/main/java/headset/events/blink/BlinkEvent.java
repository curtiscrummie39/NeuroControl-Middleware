package headset.events.blink;

import java.util.EventObject;

public class BlinkEvent extends EventObject {
  private final BlinkData blinkData;

  public BlinkEvent(Object source, BlinkData data) {
    super(source);
    this.blinkData = data;
  }

  public BlinkData getBlinkData() {
    return this.blinkData;
  }


}
