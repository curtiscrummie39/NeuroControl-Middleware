package headset.events.blink;


import java.util.EventListener;

public interface IBlinkEventListener extends EventListener {

  void onBlink(BlinkEvent event);
}
