package headsetTest.eventsTest.nskAlgo;

import headset.events.stream.streamAttention.IStreamAttentionEventListener;
import headset.events.stream.streamAttention.StreamAttentionEvent;

public class StreamAttentionMockEventListener implements IStreamAttentionEventListener {

  private int attentionCount = 0;
  private int lastAttentionValue = 0;

  @Override
  public void onAttentionUpdate(StreamAttentionEvent event) {
    this.attentionCount++;
    this.lastAttentionValue = event.getAttentionData().attention();
  }

  public int getAttentionCount() {
    return this.attentionCount;
  }

  public int getLastAttentionValue() {
    return this.lastAttentionValue;
  }

}
