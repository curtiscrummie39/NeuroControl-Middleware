package headset.events.nskAlgo.AlgoBlink;


import java.util.EventListener;

public interface IBlinkEventListener extends EventListener {

  void onBlink(BlinkEvent event);
}
