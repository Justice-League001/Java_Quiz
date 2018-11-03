/**
 * 
 */
package com.jhzhou.event;

import java.util.EventListener;

/**
 * @author asus
 *
 */
public interface SwitchListener extends EventListener {
	public void handleEvent(SwitchEvent switchEvent);
}
