
interface Selector{
	boolean end();
	Object current();
	void next();
}

public class Sequence {
	private Object []items;
	private int next=0;
	public Sequence(int size) {items=new Object[size];}
	public void add(Object x) {
		if(next<items.length)
			items[next++]=x;
	}
	private class SequenceSelector implements Selector{
		private int i=0;
		@Override
		public boolean end() {
			// TODO �Զ����ɵķ������
			return i==items.length;
		}

		@Override
		public Object current() {
			// TODO �Զ����ɵķ������
			return items[i];
		}

		@Override
		public void next() {
			// TODO �Զ����ɵķ������
			if(i<items.length) i++;
		}
	}
	public Selector selector() {
		return new SequenceSelector();
	}
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		Sequence sequence=new Sequence(10);
		for (int i = 0; i < sequence.items.length; i++) {
			sequence.add(Integer.toString(i));
		}
		Selector selector=sequence.selector();
		while(!selector.end()) {
			System.out.println(selector.current()+"");
			selector.next();
		}
	}
}