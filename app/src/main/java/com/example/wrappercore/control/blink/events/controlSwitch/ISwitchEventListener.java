package com.example.wrappercore.control.blink.events.controlSwitch;

import com.example.wrappercore.control.blink.events.IBlinkEventListener;

public interface ISwitchEventListener extends IBlinkEventListener {

  void onSwitchEvent(SwitchEvent event);
}
