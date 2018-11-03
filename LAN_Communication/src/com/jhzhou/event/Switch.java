package com.jhzhou.event;


import java.util.Iterator;
import java.util.Vector;



public class Switch {
	private Vector<SwitchListener> switchListenerList = new Vector<SwitchListener>();
	private Boolean state = false;
	public void addListener(SwitchListener listener) {
		switchListenerList.add(listener);
	}
	public void receive() {
    	synchronized(state) { 
	    	if(state == false) 
	    		state = Boolean.valueOf(true);
	    	else
	    		return ;
    	}
    	notifyListeners(new SwitchEvent(this));
	}
	public void create() {
		synchronized(state) { 
	    	if(state == false) 
	    		state = Boolean.valueOf(true);
	    	else
	    		return ;
    	}
    	notifyListeners(new CreateEvent(this));
	}
	protected void setState() {
		state = Boolean.valueOf(false);
	}
	private void notifyListeners(SwitchEvent switchEvent) {
		state = true;
		Iterator<SwitchListener> iterator = switchListenerList.iterator();
		while(iterator.hasNext()) {
			SwitchListener switchListener = iterator.next();
			switchListener.handleEvent(switchEvent);
		}
		
	}
}
