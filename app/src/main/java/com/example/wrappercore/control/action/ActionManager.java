package com.example.wrappercore.control.action;

import ai.events.aiDetectedMovement.AiDetectedMovementEvent;
import ai.events.aiDetectedMovement.IAiDetectedMovementEventListener;
import com.example.wrappercore.control.action.events.ActionEvent;
import com.example.wrappercore.control.action.events.movement.IMovementEventListener;
import com.example.wrappercore.control.action.events.movement.MovementEvent;
import com.example.wrappercore.control.action.events.movement.MovementEventHandler;
import com.example.wrappercore.control.action.events.movement.MovementTypes;
import com.example.wrappercore.control.mode.ModeManager;
import com.example.wrappercore.control.mode.events.controlSwitch.ControlModeTypes;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

//TODO: ask more about how the ai will interact with this class
public class ActionManager implements
    IAiDetectedMovementEventListener {

  private final MovementEventHandler movementEventHandler = new MovementEventHandler();
  private final ModeManager modeManager;
  private MovementEvent lastMovementEvent;
  private int changeToStopInMillis = 0;

  public ActionManager(ModeManager modeManager, int changeToStopInMillis) {
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

  public void addListener(IMovementEventListener listener) {
    movementEventHandler.addListener(listener);
  }

  public void removeListener(IMovementEventListener listener) {
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
