package headset.events.stream.streamAttention;

import java.util.EventListener;

public interface IStreamAttentionEventListener extends EventListener {

  void onAttentionUpdate(StreamAttentionEvent event);
}
