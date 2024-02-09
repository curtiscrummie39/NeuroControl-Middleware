package headset.events.raw;

import java.util.EventListener;

public interface IRawDataUpdateEventListener extends EventListener {

  void onRawDataUpdate(RawDataUpdateEvent event);
}
