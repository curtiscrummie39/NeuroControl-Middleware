package ai;

import ai.events.aiDetectedMovement.AiDetectedMovementEvent;
import ai.events.aiDetectedMovement.AiDetectedMovementEventHandler;
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
//    float[] result = model.runInference(new float[][]{{0, (float) event.getRawData().rawData()[0]}});
    float[] result = model.runInference(new float[][]{{1, 2}});
    aiDetectedMovementEventHandler.fireEvent(new AiDetectedMovementEvent(this, (int) result[0]));
  }

}
