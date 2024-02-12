package headsetTest.eventsTest;

import java.util.EventObject;

public class TestEvent extends EventObject {

  public TestEvent(Object source) {
    super(source);
  }

  public String toString() {
    return "TestEvent {}";
  }

}
