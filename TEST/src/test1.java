

public class test1 { 
	Boolean state = false;
	
	synchronized public void pp(String s) throws InterruptedException {
		
			System.out.println(s);
			synchronized(state) {
				if(state.booleanValue() == false)
					state = true;	
				else {
					return ;
				}
			}
			p2(s);
	
	}
	private void p2(String s) throws InterruptedException {
		System.out.println(s);
		Thread.sleep(1);
		Boolean state = false;
	}
	
  public static void main(String[] args) { 
	
	  test1 k = new test1();
	  Thread a = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			try {
				k.pp("a");
				System.out.println("1");
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		  
	  });
	 
	  Thread b = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				try {
					k.pp("b");
					System.out.println("2");
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			  
		  });
	  Thread c = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				try {
					k.pp("c");
					System.out.println("3");
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			  
		  });
	  a.start();
	  b.start();
	  c.start();
  } 
}

