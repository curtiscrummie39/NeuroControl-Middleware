package headsetTest.eventsTest;

import headset.events.attention.AttentionDataUpdateEvent;
import headset.events.attention.IAttentionDataUpdateEventListener;

public class AttentionEventMockListener implements IAttentionDataUpdateEventListener {

  private int attentionCount = 0;
  private int lastAttentionValue = 0;

  @Override
  public void onAttentionDataUpdate(AttentionDataUpdateEvent event) {
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
