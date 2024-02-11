package headset.events.nskAlgo.algoBlink;


import java.util.EventListener;

public interface IAlgoBlinkEventListener extends EventListener {

  void onBlink(AlgoBlinkEvent event);
}
