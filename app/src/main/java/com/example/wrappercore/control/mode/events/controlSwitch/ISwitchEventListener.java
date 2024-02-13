package com.example.wrappercore.control.mode.events.controlSwitch;

import java.util.EventListener;

public interface ISwitchEventListener extends EventListener {

  void onSwitchEvent(SwitchEvent event);
}
