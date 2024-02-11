package headset.events.nskAlgo.AlgoSignalQuality;

import java.util.EventListener;

public interface ISignalQualityUpdateEventListener extends EventListener {

  void onSignalQualityUpdate(SignalQualityUpdateEvent event);
}
