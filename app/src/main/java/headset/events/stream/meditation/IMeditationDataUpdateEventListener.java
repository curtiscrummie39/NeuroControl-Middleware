package headset.events.stream.meditation;

import java.util.EventListener;

public interface IMeditationDataUpdateEventListener extends EventListener {

  void onMeditationDataUpdate(MeditationDataUpdateEvent event);
}