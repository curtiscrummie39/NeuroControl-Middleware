package headset.events.stream.meditation;

import headset.event.IEventHandler;
import java.util.ArrayList;

public class MeditationDataUpdateEventHandler implements
    IEventHandler<IMeditationDataUpdateEventListener, MeditationDataUpdateEvent> {

  private final ArrayList<IMeditationDataUpdateEventListener> listeners = new ArrayList<IMeditationDataUpdateEventListener>();

  public void addListener(IMeditationDataUpdateEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IMeditationDataUpdateEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IMeditationDataUpdateEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(MeditationDataUpdateEvent event) {
    for (IMeditationDataUpdateEventListener listener : listeners) {
      listener.onMeditationDataUpdate(event);
    }
  }

}
