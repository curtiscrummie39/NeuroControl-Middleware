package headset.events.nskAlgo.algoAttention;

import java.util.EventListener;

public interface IAttentionDataUpdateEventListener extends EventListener {

  void onAttentionDataUpdate(AttentionDataUpdateEvent event);
}
