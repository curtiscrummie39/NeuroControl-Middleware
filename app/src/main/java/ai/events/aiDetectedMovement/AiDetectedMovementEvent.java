package ai.events.aiDetectedMovement;

import com.example.wrappercore.control.action.events.ActionEvent;
import java.time.LocalDateTime;

public class AiDetectedMovementEvent extends ActionEvent {


  private final LocalDateTime timestamp = LocalDateTime.now();

  public AiDetectedMovementEvent(Object source, int flag) {
    super(source, flag);
  }

  public String toString() {
    return "AI Movement AiDetectedMovementEvent{" +
        ", timestamp=" + timestamp +
        '}';
  }

}
