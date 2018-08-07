import java.util.*;

public class QueueDemo {
	public static void printQ(Queue queue) {
		while(queue.peek()!=null) {
			System.out.print(queue.remove()+" ");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		Queue<Integer>queue=new LinkedList<Integer>();
		Random rand=new Random();
		for(int i=0; i<10; i++) {
			queue.offer(rand.nextInt(i+10));
		}
		printQ(queue);
		Queue<Character>qc=new LinkedList<Character>();
		for (char ch : "Brontosaurus".toCharArray()) {
			qc.offer(ch);
		}
		printQ(qc);
	}

}
