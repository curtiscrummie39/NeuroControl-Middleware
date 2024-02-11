package headset.events.stream.attention;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class AttentionDataUpdateEventHandler implements
    IEventHandler<IAttentionDataUpdateEventListener, AttentionDataUpdateEvent> {

  private final ArrayList<IAttentionDataUpdateEventListener> listeners = new ArrayList<IAttentionDataUpdateEventListener>();

  public void addListener(IAttentionDataUpdateEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IAttentionDataUpdateEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IAttentionDataUpdateEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(AttentionDataUpdateEvent event) {
    for (IAttentionDataUpdateEventListener listener : listeners) {
      listener.onAttentionDataUpdate(event);
    }
  }
}
