package headset.coreTgStream;

import headset.events.AttentionData;
import headset.events.MeditationData;
import headset.events.stream.StreamEventTypes;
import headset.events.stream.streamAttention.IStreamAttentionEventListener;
import headset.events.stream.streamAttention.StreamAttentionEvent;
import headset.events.stream.streamAttention.StreamAttentionEventHandler;
import headset.events.stream.streamEEG.IStreamEEGDataEventListener;
import headset.events.stream.streamEEG.StreamEEGData;
import headset.events.stream.streamEEG.StreamEEGDataEvent;
import headset.events.stream.streamEEG.StreamEEGDataEventHandler;
import headset.events.stream.streamMeditation.IStreamMeditationEventListener;
import headset.events.stream.streamMeditation.StreamMeditationEvent;
import headset.events.stream.streamMeditation.StreamMeditationEventHandler;
import headset.events.stream.streamRaw.IStreamRawDataEventListener;
import headset.events.stream.streamRaw.StreamRawData;
import headset.events.stream.streamRaw.StreamRawDataEvent;
import headset.events.stream.streamRaw.StreamRawDataEventHandler;
import java.util.EventListener;


public class CoreStreamEventsController {

  private final StreamAttentionEventHandler streamAttentionEventHandler;

  private final StreamMeditationEventHandler streamMeditationEventHandler;
  private final StreamRawDataEventHandler streamRawDataEventHandler;

  private final StreamEEGDataEventHandler streamEEGDataEventHandler;


  CoreStreamEventsController() {
    this.streamAttentionEventHandler = new StreamAttentionEventHandler();
    this.streamMeditationEventHandler = new StreamMeditationEventHandler();
    this.streamRawDataEventHandler = new StreamRawDataEventHandler();
    this.streamEEGDataEventHandler = new StreamEEGDataEventHandler();
  }

  public void fireEvent(StreamEventTypes type, Object data) {
    switch (type) {
      case EEG -> {
        int[] dataArr = (int[]) data;
        this.streamEEGDataEventHandler.fireEvent(
            new StreamEEGDataEvent(this,
                new StreamEEGData(dataArr[0], dataArr[1], dataArr[2], dataArr[3], dataArr[4],
                    dataArr[5], dataArr[6], dataArr[7])));
      }

      case RAW_DATA -> this.streamRawDataEventHandler.fireEvent(
          new StreamRawDataEvent(this, new StreamRawData((short[]) data)));

      case ATTENTION -> this.streamAttentionEventHandler.fireEvent(
          new StreamAttentionEvent(this, new AttentionData((int) data)));

      case MEDITATION -> this.streamMeditationEventHandler.fireEvent(
          new StreamMeditationEvent(this, new MeditationData((int) data)));

      default -> throw new IllegalArgumentException("Unknown event type: " + type);
    }
  }

  public void addEventListener(EventListener listener) {
    if (listener instanceof IStreamAttentionEventListener) {
      this.streamAttentionEventHandler.addListener((IStreamAttentionEventListener) listener);

    } else if (listener instanceof IStreamMeditationEventListener) {
      this.streamMeditationEventHandler.addListener(
          (IStreamMeditationEventListener) listener);

    } else if (listener instanceof IStreamEEGDataEventListener) {
      this.streamEEGDataEventHandler.addListener(
          (IStreamEEGDataEventListener) listener);

    } else if (listener instanceof IStreamRawDataEventListener) {
      this.streamRawDataEventHandler.addListener(
          (IStreamRawDataEventListener) listener);
    } else {
      throw new IllegalArgumentException("Unknown listener type: " + listener.getClass().getName());
    }
  }

  public void removeEventListener(EventListener listener) {
    if (listener instanceof IStreamAttentionEventListener) {
      this.streamAttentionEventHandler.removeListener((IStreamAttentionEventListener) listener);

    } else if (listener instanceof IStreamMeditationEventListener) {
      this.streamMeditationEventHandler.removeListener(
          (IStreamMeditationEventListener) listener);

    } else if (listener instanceof IStreamEEGDataEventListener) {
      this.streamEEGDataEventHandler.removeListener(
          (IStreamEEGDataEventListener) listener);

    } else if (listener instanceof IStreamRawDataEventListener) {
      this.streamRawDataEventHandler.removeListener(
          (IStreamRawDataEventListener) listener);

    } else {
      throw new IllegalArgumentException("Unknown listener type: " + listener.getClass().getName());
    }
  }

  public boolean containsListener(EventListener listener) {
    if (listener instanceof IStreamAttentionEventListener) {
      return this.streamAttentionEventHandler.containsListener(
          (IStreamAttentionEventListener) listener);

    } else if (listener instanceof IStreamMeditationEventListener) {
      return this.streamMeditationEventHandler.containsListener(
          (IStreamMeditationEventListener) listener);

    } else if (listener instanceof IStreamEEGDataEventListener) {
      return this.streamEEGDataEventHandler.containsListener(
          (IStreamEEGDataEventListener) listener);

    } else if (listener instanceof IStreamRawDataEventListener) {
      return this.streamRawDataEventHandler.containsListener(
          (IStreamRawDataEventListener) listener);

    } else {
      throw new IllegalArgumentException("Unknown listener type: " + listener.getClass().getName());
    }
  }
}
