package headset.events.nskAlgo.algoStateChange;


import java.util.EventListener;

public interface IAlgoStateChangeEventListener extends EventListener {

  void onAlgoStateChange(AlgoStateChangeEvent event);
}
