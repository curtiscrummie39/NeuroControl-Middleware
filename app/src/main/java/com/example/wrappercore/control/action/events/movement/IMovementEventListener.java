package com.example.wrappercore.control.action.events.movement;

import java.util.EventListener;

public interface IMovementEventListener extends EventListener {

  void onMovementEvent(MovementEvent movementEvent);
}
