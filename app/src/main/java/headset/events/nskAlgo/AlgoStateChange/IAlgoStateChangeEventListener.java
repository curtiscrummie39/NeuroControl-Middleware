package headset.events.nskAlgo.AlgoStateChange;


import java.util.EventListener;

public interface IAlgoStateChangeEventListener extends EventListener {

  void onAlgoStateChange(AlgoStateChangeEvent event);
}
