package headset.events.stream.stateChange;


import java.util.EventListener;

public interface IHeadsetStateChangeEventListener extends EventListener {

  void onHeadsetStateChange(HeadsetStateChangeEvent event);
}
