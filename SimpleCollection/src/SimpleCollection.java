import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.TreeSet;

public class SimpleCollection {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		Set<Integer>c=new TreeSet<Integer>();
		for (int i = 0; i < 10; i++) {
			c.add(i);
		}
		for (Integer integer : c) {
			System.out.print(integer+"��");
		}
	}

}
