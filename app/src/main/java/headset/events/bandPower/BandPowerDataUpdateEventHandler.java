package headset.events.bandPower;


import headset.event.IEventHandler;
import java.util.ArrayList;

public class BandPowerDataUpdateEventHandler implements
    IEventHandler<IBandPowerDataUpdateEventListener, BandPowerDataUpdateEvent> {

  private final ArrayList<IBandPowerDataUpdateEventListener> listeners = new ArrayList<IBandPowerDataUpdateEventListener>();

  public void addListener(IBandPowerDataUpdateEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IBandPowerDataUpdateEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IBandPowerDataUpdateEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(BandPowerDataUpdateEvent event) {
    for (IBandPowerDataUpdateEventListener listener : listeners) {
      listener.onBandPowerDataUpdate(event);
    }
  }
}
