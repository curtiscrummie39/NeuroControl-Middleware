package headset.events.stream.streamRaw;

import java.util.EventObject;

public class StreamRawDataEvent extends EventObject {

  private final StreamRawData rawData;

  public StreamRawDataEvent(Object source, StreamRawData data) {
    super(source);
    this.rawData = data;
  }

  public StreamRawData getRawData() {
    return this.rawData;
  }

  public String toString() {
    return "StreamRawDataEvent { rawData=" + this.rawData + "}";
  }
}
