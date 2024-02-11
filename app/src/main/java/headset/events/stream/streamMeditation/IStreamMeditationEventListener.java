package headset.events.stream.streamMeditation;

import java.util.EventListener;

public interface IStreamMeditationEventListener extends EventListener {

  void onMeditationUpdate(StreamMeditationEvent event);
}