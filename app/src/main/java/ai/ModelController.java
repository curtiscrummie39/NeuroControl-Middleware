package ai;

import ai.events.aiDetectedMovement.AiDetectedMovementEvent;
import ai.events.aiDetectedMovement.AiDetectedMovementEventHandler;
import ai.events.aiDetectedMovement.IAiDetectedMovementEventListener;
import headset.events.stream.streamRaw.IStreamRawDataEventListener;
import headset.events.stream.streamRaw.StreamRawDataEvent;

public class ModelController implements IStreamRawDataEventListener {

  private final Model model;

  private final AiDetectedMovementEventHandler aiDetectedMovementEventHandler = new AiDetectedMovementEventHandler();

  public ModelController(String remoteModelUrl) {
    this.model = new Model(remoteModelUrl);
  }

  @Override
  public void onRawDataUpdate(StreamRawDataEvent event) {
    //FIXME: this is a dummy implementation
    //       the real implementation require the model to take the headset raw data as input and return some sort of a
    //       agreed upon flag {o: for incorrect detection of action, 1: for correct detection of action } that will be
    //       used to trigger the appropriate action
//    float[] result = model.runInference(new float[][]{{0, (float) event.getRawData().rawData()[0]}});
    float[] result = model.runInference(new float[][]{{1, 2}});
    if (result[0] > 0.5) {
      aiDetectedMovementEventHandler.fireEvent(new AiDetectedMovementEvent(this, (int) result[0]));
    }
  }

  public void addListener(IAiDetectedMovementEventListener listener) {
    aiDetectedMovementEventHandler.addListener(listener);
  }

  public void removeListener(IAiDetectedMovementEventListener listener) {
    aiDetectedMovementEventHandler.removeListener(listener);
  }
}
