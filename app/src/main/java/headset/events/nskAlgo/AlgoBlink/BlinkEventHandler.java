package headset.events.nskAlgo.AlgoBlink;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class BlinkEventHandler implements IEventHandler<IBlinkEventListener, BlinkEvent> {

  private final ArrayList<IBlinkEventListener> listeners = new ArrayList<>();

  public void addListener(IBlinkEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IBlinkEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IBlinkEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(BlinkEvent event) {
    for (IBlinkEventListener listener : listeners) {
      listener.onBlink(event);
    }
  }
}
