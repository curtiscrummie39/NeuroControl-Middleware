package headset.events.nskAlgo.algoAttention;

import java.util.EventListener;

public interface IAlgoAttentionEventListener extends EventListener {

  void onAttentionUpdate(AlgoAttentionEvent event);
}

