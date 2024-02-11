package headset.events.stream.raw;

import java.util.EventObject;

public class RawDataUpdateEvent extends EventObject {

  private final RawData rawData;

  public RawDataUpdateEvent(Object source, RawData data) {
    super(source);
    this.rawData = data;
  }

  public RawData getRawData() {
    return this.rawData;
  }
}
