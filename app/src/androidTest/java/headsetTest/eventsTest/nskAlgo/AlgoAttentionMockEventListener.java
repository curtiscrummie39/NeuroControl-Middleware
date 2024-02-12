package headsetTest.eventsTest.nskAlgo;

import headset.events.nskAlgo.algoAttention.AlgoAttentionEvent;
import headset.events.nskAlgo.algoAttention.IAlgoAttentionEventListener;

public class AlgoAttentionMockEventListener implements IAlgoAttentionEventListener {

  private int attentionCount = 0;
  private int lastAttentionValue = 0;

  @Override
  public void onAttentionUpdate(AlgoAttentionEvent event) {
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
