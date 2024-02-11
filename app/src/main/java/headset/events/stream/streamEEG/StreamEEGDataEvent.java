package headset.events.stream.streamEEG;

import java.util.EventObject;

public class StreamEEGDataEvent extends EventObject {

  private final StreamEEGData eegData;

  public StreamEEGDataEvent(Object source, StreamEEGData data) {
    super(source);
    this.eegData = data;
  }

  public StreamEEGData getRawData() {
    return this.eegData;
  }

  public String toString() {
    return "StreamEEGDataEvent { eegData=" + this.eegData + "}";
  }
}
