package com.example.wrappercore.controlMode.events.controlSwitch;

import java.util.ArrayList;

public class SwitchEventHandler {

  private final ArrayList<ISwitchEventListener> listeners = new ArrayList<ISwitchEventListener>();

  public void addSwitchEventListener(ISwitchEventListener listener) {
    listeners.add(listener);
  }

  public void removeSwitchEventListener(ISwitchEventListener listener) {
    listeners.remove(listener);
  }

  public void fireEvent(SwitchEvent event) {
    for (ISwitchEventListener listener : listeners) {
      listener.onSwitchEvent(event);
    }
  }


}
