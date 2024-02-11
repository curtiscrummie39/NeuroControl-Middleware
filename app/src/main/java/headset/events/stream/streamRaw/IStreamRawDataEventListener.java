package headset.events.stream.streamRaw;

import java.util.EventListener;

public interface IStreamRawDataEventListener extends EventListener {

  void onRawDataUpdate(StreamRawDataEvent event);
}
