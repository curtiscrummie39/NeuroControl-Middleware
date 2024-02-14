package com.example.wrappercore.control.action.events.movement;

import com.example.wrappercore.control.action.events.IActionEventListener;

public interface IAppMovementListener extends IActionEventListener {

  void onMovementEvent(MovementEvent movementEvent);
}
