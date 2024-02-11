package headset.events.stream.raw;

import java.util.EventListener;

public interface IRawDataUpdateEventListener extends EventListener {

  void onRawDataUpdate(RawDataUpdateEvent event);
}
