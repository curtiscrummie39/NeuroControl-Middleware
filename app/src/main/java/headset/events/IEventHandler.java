package headset.event;

import java.util.EventListener;
import java.util.EventObject;

public interface IEventHandler<T extends EventListener, U extends EventObject> {

  public void addListener(T listener);

  public void removeListener(T listener);

  public boolean containsListener(T listener);

  public void fireEvent(U event);

}
