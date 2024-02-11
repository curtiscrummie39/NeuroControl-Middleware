package headset.events.nskAlgo.AlgoBandPower;

import java.util.EventListener;

public interface IBandPowerDataUpdateEventListener extends EventListener {

  void onBandPowerDataUpdate(BandPowerDataUpdateEvent event);
}
