package wheelchair;

import ai.events.aiDetectedMovement.AiDetectedMovementEvent;
import ai.events.aiDetectedMovement.AiDetectedMovementEventHandler;
import headset.events.stream.streamRaw.IStreamRawDataEventListener;
import headset.events.stream.streamRaw.StreamRawDataEvent;

public class WheelchairController implements IWheelchairMovementListener  {


  private final AiDetectedMovementEventHandler aiDetectedMovementEventHandler = new AiDetectedMovementEventHandler();

  public WheelchairController(String remoteWheelchairUrl) {
    this.model = new Wheelchair(remoteWheelchairUrl);
  }

  @Override
  public void onRawDataUpdate(StreamRawDataEvent event) {
//    float[] result = model.runInference(new float[][]{{0, (float) event.getRawData().rawData()[0]}});
    float[] result = model.runInference(new float[][]{{1, 2}});
    aiDetectedMovementEventHandler.fireEvent(new AiDetectedMovementEvent(this, (int) result[0]));
  }

}
