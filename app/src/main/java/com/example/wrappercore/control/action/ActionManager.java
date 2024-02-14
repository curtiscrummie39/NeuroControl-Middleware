package com.example.wrappercore.control.action;

import ai.events.aiDetectedMovement.AiDetectedMovementEvent;
import ai.events.aiDetectedMovement.IAiDetectedMovementEventListener;
import com.example.wrappercore.control.action.events.ActionEvent;
import com.example.wrappercore.control.action.events.IActionEventListener;
import com.example.wrappercore.control.action.events.movement.MovementEvent;
import com.example.wrappercore.control.action.events.movement.MovementEventHandler;
import com.example.wrappercore.control.action.events.movement.MovementTypes;
import com.example.wrappercore.control.blink.BlinkManager;
import com.example.wrappercore.control.blink.events.controlSwitch.ControlModeTypes;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

//TODO: ask more about how the ai will interact with this class
public class ActionManager implements
    IAiDetectedMovementEventListener {

  private final MovementEventHandler movementEventHandler = new MovementEventHandler();
  private final BlinkManager modeManager;
  private MovementEvent lastMovementEvent;
  private int changeToStopInMillis = 0;

  public ActionManager(BlinkManager modeManager, int changeToStopInMillis) {
    this.modeManager = modeManager;
    this.changeToStopInMillis = changeToStopInMillis;
    checkMovement();
  }

  private void checkMovement() {
    new Timer().scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (lastMovementEvent != null
            && Timestamp.valueOf(lastMovementEvent.getTimestamp().toString()).getTime()
            + changeToStopInMillis
            <= System.currentTimeMillis()
            && lastMovementEvent.getMovementType() != MovementTypes.STOP) {
          fireEvent(new ActionEvent(this, MovementTypes.STOP.ordinal()));
        }
      }
    }, 0, changeToStopInMillis);
  }

  public void addListener(IActionEventListener listener) {
    movementEventHandler.addListener(listener);
  }

  public void removeListener(IActionEventListener listener) {
    movementEventHandler.removeListener(listener);
  }

  @Override
  public void onAiDetectedMovementEvent(AiDetectedMovementEvent aiDetectedMovementEvent) {
    fireEvent(aiDetectedMovementEvent);
  }

  private void fireEvent(ActionEvent event) {
    lastMovementEvent = new MovementEvent(this, event.getFlag());
    if (modeManager.getLastControlModeType() == ControlModeTypes.WHEELCHAIR_CONTROL) {
      movementEventHandler.fireWheelchairMovementEvent(lastMovementEvent);
    } else if (modeManager.getLastControlModeType() == ControlModeTypes.APP_CONTROL) {
      movementEventHandler.fireAppMovementEvent(lastMovementEvent);
    }
  }

}
