package com.example.wrappercore.control.mode;

import com.example.wrappercore.control.mode.events.controlSwitch.ControlModeTypes;
import com.example.wrappercore.control.mode.events.controlSwitch.ISwitchEventListener;
import com.example.wrappercore.control.mode.events.controlSwitch.SwitchEvent;
import com.example.wrappercore.control.mode.events.controlSwitch.SwitchEventHandler;
import headset.events.nskAlgo.algoBlink.AlgoBlinkEvent;
import headset.events.nskAlgo.algoBlink.IAlgoBlinkEventListener;

public class ModeManager implements IAlgoBlinkEventListener {

  private final SwitchEventHandler switchEventHandler = new SwitchEventHandler();
  private final ControlModeTypes lastControlModeType = ControlModeTypes.APP_CONTROL;
  private final int blinkDetectionThreshold;

  public ModeManager(int blinkSensitivityThreshold) {
    this.blinkDetectionThreshold = blinkSensitivityThreshold;
  }

  private void switchMode() {
    ControlModeTypes controlModeType =
        lastControlModeType == ControlModeTypes.APP_CONTROL ? ControlModeTypes.WHEELCHAIR_CONTROL
            : ControlModeTypes.APP_CONTROL;
    switchEventHandler.fireEvent(new SwitchEvent(this, controlModeType));
  }

  public void addListener(ISwitchEventListener listener) {
    switchEventHandler.addListener(listener);
  }

  public void removeListener(ISwitchEventListener listener) {
    switchEventHandler.removeListener(listener);
  }

  @Override
  public void onBlink(AlgoBlinkEvent event) {
    //TODO: refine this logic
    if (event.getBlinkData().strength() > blinkDetectionThreshold) {
      this.switchMode();
    }
  }

  public ControlModeTypes getLastControlModeType() {
    return lastControlModeType;
  }
}
