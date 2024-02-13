package com.example.wrappercore.control.action.events.movement;

public class MovementEventHandler {

  private IAppMovementListener appMovementListener;
  private IWheelchairMovementListener wheelchairMovementListener;

  public void addListener(IMovementEventListener listener) {
    if (listener instanceof IAppMovementListener) {
      appMovementListener = (IAppMovementListener) listener;
    } else if (listener instanceof IWheelchairMovementListener) {
      wheelchairMovementListener = (IWheelchairMovementListener) listener;
    }
  }

  public void removeListener(IMovementEventListener listener) {
    if (listener instanceof IAppMovementListener) {
      appMovementListener = null;
    } else if (listener instanceof IWheelchairMovementListener) {
      wheelchairMovementListener = null;
    }
  }

  public void fireAppMovementEvent(MovementEvent movementEvent) {
    if (appMovementListener != null) {
      appMovementListener.onMovementEvent(movementEvent);
    } else {
      throw new IllegalStateException("No listener found for MovementEvent in App.");
    }
  }

  public void fireWheelchairMovementEvent(MovementEvent movementEvent) {
    if (wheelchairMovementListener != null) {
      wheelchairMovementListener.onMovementEvent(movementEvent);
    } else {
      throw new IllegalStateException("No listener found for MovementEvent in Wheelchair.");
    }
  }


}
