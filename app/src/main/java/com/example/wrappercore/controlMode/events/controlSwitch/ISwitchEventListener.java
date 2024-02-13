package com.example.wrappercore.controlMode.events.controlSwitch;

import java.util.EventListener;

public interface ISwitchEventListener extends EventListener {

  void onSwitchEvent(SwitchEvent event);
}
