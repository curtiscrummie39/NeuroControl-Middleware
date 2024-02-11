package headset.events.nskAlgo.algoSignalQuality;

import java.util.EventListener;

public interface IAlgoSignalQualityEventListener extends EventListener {

  void onSignalQualityUpdate(AlgoSignalQualityEvent event);
}
