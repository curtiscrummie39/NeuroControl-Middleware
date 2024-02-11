package headset.events.nskAlgo.algoAttention;

import headset.events.AttentionData;
import java.util.EventObject;

public class AlgoAttentionEvent extends EventObject {

  private final AttentionData attentionData;

  public AlgoAttentionEvent(Object source, AttentionData data) {
    super(source);
    this.attentionData = data;
  }

  public AttentionData getAttentionData() {
    return attentionData;
  }

  public String toString() {
    return "AlgoAttentionEvent { AttentionData: " + attentionData + "}";
  }
}
