package com.example.wrappercore.controlMode;

import com.example.wrappercore.controlMode.events.controlSwitch.ControlModeTypes;
import com.example.wrappercore.controlMode.events.controlSwitch.ISwitchEventListener;
import com.example.wrappercore.controlMode.events.controlSwitch.SwitchEvent;
import com.example.wrappercore.controlMode.events.controlSwitch.SwitchEventHandler;
import headset.events.nskAlgo.algoBlink.AlgoBlinkEvent;
import headset.events.nskAlgo.algoBlink.IAlgoBlinkEventListener;

public class ControlModeManager implements IAlgoBlinkEventListener {

  private final SwitchEventHandler switchEventHandler = new SwitchEventHandler();
  private final ControlModeTypes lastControlModeType = ControlModeTypes.APP_CONTROL;

  private void switchMode() {
    ControlModeTypes controlModeType =
        lastControlModeType == ControlModeTypes.APP_CONTROL ? ControlModeTypes.WHEELCHAIR_CONTROL
            : ControlModeTypes.APP_CONTROL;
    switchEventHandler.fireEvent(new SwitchEvent(this, controlModeType));
  }

  public void addSwitchEventListener(ISwitchEventListener listener) {
    switchEventHandler.addSwitchEventListener(listener);
  }

  public void removeSwitchEventListener(ISwitchEventListener listener) {
    switchEventHandler.removeSwitchEventListener(listener);
  }

  @Override
  public void onBlink(AlgoBlinkEvent event) {
    //TODO: refine this logic
    if (event.getBlinkData().strength() > 20) {
      this.switchMode();
    }
  }
}
