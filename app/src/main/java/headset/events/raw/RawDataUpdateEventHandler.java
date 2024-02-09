package headset.events.raw;

import headset.event.IEventHandler;
import java.util.ArrayList;

public class RawDataUpdateEventHandler implements
    IEventHandler<IRawDataUpdateEventListener, RawDataUpdateEvent> {

  private final ArrayList<IRawDataUpdateEventListener> listeners = new ArrayList<IRawDataUpdateEventListener>();

  public void addListener(IRawDataUpdateEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IRawDataUpdateEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IRawDataUpdateEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(RawDataUpdateEvent event) {
    for (IRawDataUpdateEventListener listener : listeners) {
      listener.onRawDataUpdate(event);
    }
  }
}
