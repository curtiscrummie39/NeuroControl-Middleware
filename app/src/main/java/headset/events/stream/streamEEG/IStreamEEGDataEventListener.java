package headset.events.stream.streamEEG;

import java.util.EventListener;

public interface IStreamEEGDataEventListener extends EventListener {

  void onEEGDataUpdate(StreamEEGDataEvent event);
}
