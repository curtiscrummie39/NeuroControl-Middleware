package headset.events.nskAlgo.algoMeditation;

import java.util.EventListener;

public interface IAlgoMeditationEventListener extends EventListener {

  void onMeditationUpdate(AlgoMeditationEvent event);
}