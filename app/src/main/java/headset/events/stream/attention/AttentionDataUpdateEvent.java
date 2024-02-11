package headset.events.stream.attention;

import java.util.EventObject;

public class AttentionDataUpdateEvent extends EventObject {

  private final AttentionData attentionData;

  public AttentionDataUpdateEvent(Object source, AttentionData data) {
    super(source);
    this.attentionData = data;
  }

  public AttentionData getAttentionData() {
    return attentionData;
  }
}
