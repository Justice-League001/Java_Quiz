package com.jhzhou.event;

import java.util.EventObject;

public class SwitchEvent extends EventObject{
	
	public SwitchEvent(Switch source) {
		super(source);
	}
}
