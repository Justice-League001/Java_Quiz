package designpatterns.EventObserver;

import java.util.EventObject;

public class StateEvent extends EventObject {

	private boolean state = false;
	public final String source;
	public StateEvent(Object source, String sc) {
		super(source);
		this.source = sc;
		// TODO Auto-generated constructor stub
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public boolean getState() {
		return this.state;
	}

}
class StateEventT extends EventObject {

	private boolean state = false;
	public StateEventT(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public boolean getState() {
		return this.state;
	}

}