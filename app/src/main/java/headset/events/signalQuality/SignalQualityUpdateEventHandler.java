package headset.events.signalQuality;

import java.util.ArrayList;
import headset.event.IEventHandler;

public class SignalQualityUpdateEventHandler implements
    IEventHandler<ISignalQualityUpdateEventListener, SignalQualityUpdateEvent> {

  private final ArrayList<ISignalQualityUpdateEventListener> listeners = new ArrayList<ISignalQualityUpdateEventListener>();

  public void addListener(ISignalQualityUpdateEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(ISignalQualityUpdateEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(ISignalQualityUpdateEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(SignalQualityUpdateEvent event) {
    for (ISignalQualityUpdateEventListener listener : listeners) {
      listener.onSignalQualityUpdate(event);
    }
  }
}
