package designpatterns.EventObserver;

import java.util.EventListener;

public interface StateListener extends EventListener {
	public void handleEvent(StateEvent stateNow);
	public void handleEvent(StateEventT stateNow);
}
