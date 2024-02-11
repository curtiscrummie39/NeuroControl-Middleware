package headset.events.nskAlgo.algoBandPower;

import java.util.EventListener;

public interface IAlgoBandPowerEventListener extends EventListener {

  void onBandPowerUpdate(AlgoBandPowerEvent event);
}
