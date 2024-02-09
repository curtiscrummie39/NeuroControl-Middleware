package headset.events.attention;

import java.util.EventListener;

public interface IAttentionDataUpdateEventListener extends EventListener {
  void onAttentionDataUpdate(AttentionDataUpdateEvent event);
}
