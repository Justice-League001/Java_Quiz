package designpatterns.EventObserver;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;



public class State {
	private Vector stateListenerList = new Vector();
	private final BlockingQueue<StateEvent> EventQueue;
	private Boolean Switch = false;
	
	public State(BlockingQueue<StateEvent> EventQueue) {
		this.EventQueue = EventQueue;
	}
	public void addListener(Object listener) {
		stateListenerList.add(listener);
	}
	
	synchronized protected void open() {
		System.out.println(Thread.currentThread().getName()+"进入");
		if(Switch == false) {
			Switch = true;
		}
		else {
			System.out.println(Thread.currentThread().getName()+"失败退出");
			return ;
		}
		StateEvent SE = new StateEvent(this, Thread.currentThread().getName());
		SE.setState(true);
		
		EventQueue.offer(SE);
		System.out.println(Thread.currentThread().getName()+"退出");
		//notifyListeners(SE);
	}
//	protected void close() {
//		synchronized(Switch) {
//			if(Switch == false)
//				Switch = true;
//			else
//				return ;
//			}
//			
//		StateEventT SE = new StateEventT(this);
//		SE.setState(false);
//		//notifyListeners(SE);
//	}
	
	public void notifyListeners(StateEvent SE) throws InterruptedException {
		// TODO Auto-generated method stub
		if(Thread.currentThread().getName().equalsIgnoreCase("main")==false) return ;
		Iterator iterator = stateListenerList.iterator();
		while(iterator.hasNext()) {
			StateListener SL = (StateListener) iterator.next();
			SL.handleEvent(SE);
		}
		Thread.sleep(10);
		Switch = false;
	}
//	private void notifyListeners(StateEventT SE) {
//		// TODO Auto-generated method stub
//		Iterator iterator = stateListenerList.iterator();
//		while(iterator.hasNext()) {
//			StateListener SL = (StateListener) iterator.next();
//			SL.handleEvent(SE);
//		}
//	}
}
