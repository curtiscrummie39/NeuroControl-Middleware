package headset.events.bandPower;

import java.util.EventListener;

public interface IBandPowerDataUpdateEventListener extends EventListener {

  void onBandPowerDataUpdate(BandPowerDataUpdateEvent event);
}
