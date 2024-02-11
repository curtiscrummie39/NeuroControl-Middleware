package headset.events.stream.streamAttention;

import headset.events.AttentionData;
import java.util.EventObject;

public class StreamAttentionEvent extends EventObject {

  private final AttentionData attentionData;

  public StreamAttentionEvent(Object source, AttentionData data) {
    super(source);
    this.attentionData = data;
  }

  public AttentionData getAttentionData() {
    return attentionData;
  }

  public String toString() {
    return "StreamAttentionEvent { attentionData=" + this.attentionData + "}";
  }
}
