package com.example.wrappercore.control;

import com.example.wrappercore.control.action.ActionManager;
import com.example.wrappercore.control.action.events.IActionEventListener;
import com.example.wrappercore.control.blink.BlinkManager;
import com.example.wrappercore.control.blink.events.IBlinkEventListener;
import java.util.EventListener;

public class ControlManager {

  private final BlinkManager modeManager;

  private final ActionManager actionManager;

  public ControlManager() {
    modeManager = new BlinkManager(0, 5, 3);
    actionManager = new ActionManager(modeManager, 5000);
  }

  public void addListener(EventListener listener) {
    if (listener instanceof IActionEventListener) {
      actionManager.addListener((IActionEventListener) listener);
    } else if (listener instanceof IBlinkEventListener) {
      modeManager.addListener((IBlinkEventListener) listener);
    } else {
      throw new IllegalArgumentException("Invalid Listener Type");
    }
  }

  public void removeListener(EventListener listener) {
    if (listener instanceof IActionEventListener) {
      actionManager.removeListener((IActionEventListener) listener);
    } else if (listener instanceof IBlinkEventListener) {
      modeManager.removeListener((IBlinkEventListener) listener);
    } else {
      throw new IllegalArgumentException("Invalid Listener Type");
    }
  }

  public BlinkManager getModeManager() {
    return modeManager;
  }
}
