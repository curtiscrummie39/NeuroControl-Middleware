package com.example.wrappercore.control;

import com.example.wrappercore.control.action.ActionManager;
import com.example.wrappercore.control.action.events.movement.IMovementEventListener;
import com.example.wrappercore.control.mode.ModeManager;
import com.example.wrappercore.control.mode.events.controlSwitch.ISwitchEventListener;
import java.util.EventListener;

public class ControlManager {

  private final ModeManager modeManager;

  private final ActionManager actionManager;

  public ControlManager() {
    modeManager = new ModeManager(20);
    actionManager = new ActionManager(modeManager, 5000);
  }

  public void addListener(EventListener listener) {
    if (listener instanceof IMovementEventListener) {
      actionManager.addListener((IMovementEventListener) listener);
    } else if (listener instanceof ISwitchEventListener) {
      modeManager.addListener((ISwitchEventListener) listener);
    } else {
      throw new IllegalArgumentException("Unknown listener type");
    }
  }

  public void removeListener(EventListener listener) {
    if (listener instanceof IMovementEventListener) {
      actionManager.removeListener((IMovementEventListener) listener);
    } else if (listener instanceof ISwitchEventListener) {
      modeManager.removeListener((ISwitchEventListener) listener);
    } else {
      throw new IllegalArgumentException("Unknown listener type");
    }
  }

  public ModeManager getModeManager() {
    return modeManager;
  }
}
