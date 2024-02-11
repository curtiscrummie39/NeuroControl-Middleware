package headset.events.stream.attention;

import java.util.EventListener;

public interface IAttentionDataUpdateEventListener extends EventListener {

  void onAttentionDataUpdate(AttentionDataUpdateEvent event);
}
