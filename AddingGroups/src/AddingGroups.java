import java.util.Collections;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;

public class AddingGroups {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		Collection<Integer>collection=
				new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		//Arrays.asList()�������Ԫ���б�ת��ΪList����;
		Integer[]moreInts= {6,7,8,9,10};
		collection.addAll(Arrays.asList(moreInts));
		Collections.addAll(collection, 11,12,13,14,15);
		//Collections.addAll()��һ�������һ��Ԫ���б���ӽ�һ��Collection����
		Collections.addAll(collection, moreInts);
		List<Integer>list=Arrays.asList(16,17,18,19,20);
		list.set(1, 99);
	}

}
