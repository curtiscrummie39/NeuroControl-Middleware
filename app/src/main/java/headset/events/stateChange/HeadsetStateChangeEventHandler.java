package headset.events.stateChange;

import android.util.Log;
import java.util.ArrayList;
import headset.event.IEventHandler;

public class HeadsetStateChangeEventHandler implements
    IEventHandler<IHeadsetStateChangeEventListener, HeadsetStateChangeEvent> {

  private final ArrayList<IHeadsetStateChangeEventListener> listeners = new ArrayList<IHeadsetStateChangeEventListener>();

  public void addListener(IHeadsetStateChangeEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IHeadsetStateChangeEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IHeadsetStateChangeEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(HeadsetStateChangeEvent event) {
    for (IHeadsetStateChangeEventListener listener : listeners) {
      Log.i("State Change Event",event.getState().name());
      listener.onHeadsetStateChange(event);
    }
  }
}
