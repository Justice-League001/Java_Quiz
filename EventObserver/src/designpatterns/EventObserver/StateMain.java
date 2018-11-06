package designpatterns.EventObserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StateMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BlockingQueue<StateEvent> EventQueue = new LinkedBlockingQueue<StateEvent>();
		State stateTest = new State(EventQueue);
		StateListenerT listener = new StateListenerT();
		stateTest.addListener(listener);
	
		
		Thread a = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0; i<5; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stateTest.open();}
			}
			
		});
		
		Thread b = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0; i<5; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					stateTest.open();}
			}
			
		});
		a.start();
		b.start();
		while(true) {
			try {
				StateEvent E = EventQueue.take();
				stateTest.notifyListeners(E);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
class StateListenerT implements StateListener{

	@Override
	public void handleEvent(StateEvent stateNow) {
		// TODO Auto-generated method stub
		System.out.println(stateNow.getState());
		System.out.println(Thread.currentThread().getName());
		System.out.println(stateNow.source);
	}

	@Override
	public void handleEvent(StateEventT stateNow) {
		// TODO Auto-generated method stub
		System.out.println(stateNow.getState());
	}
	
}
