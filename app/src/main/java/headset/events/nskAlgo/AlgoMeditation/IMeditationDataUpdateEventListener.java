package headset.events.nskAlgo.AlgoMeditation;

import java.util.EventListener;

public interface IMeditationDataUpdateEventListener extends EventListener {

  void onMeditationDataUpdate(MeditationDataUpdateEvent event);
}