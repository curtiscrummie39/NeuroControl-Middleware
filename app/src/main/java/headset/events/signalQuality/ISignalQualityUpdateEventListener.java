package headset.events.signalQuality;

import java.util.EventListener;

public interface ISignalQualityUpdateEventListener extends EventListener {
  void onSignalQualityUpdate(SignalQualityUpdateEvent event);
}
