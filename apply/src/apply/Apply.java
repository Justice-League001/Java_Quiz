package apply;

import java.util.Arrays;

class Processor{
	public String name(){
		return getClass().getSimpleName();
//		����Դ�����и����Ļ�����ļ����ơ� 
//		����������������ģ��򷵻�һ�����ַ����� 
//		����ļ������Ǹ����ˡ�[]����������͵ļ����ơ� 
//		�ر����������Ϊ����������ļ������ǡ�[]���� 

	}
	Object process(Object input){
		return input;
	}
}
class Upcase extends Processor{
	String process(Object input){
		return ((String)input).toUpperCase();
		//���� String�����ַ�ת��Ϊ��д��ʹ��Ĭ�����Ի����Ĺ���
	}
}
class Downcase extends Processor{
	String process(Object input){
		return ((String)input).toLowerCase();
		//ʹ��Ĭ�����Ի����Ĺ��򽫴� String�����ַ�ת��ΪСд�� 
	}
}
class Splitter extends Processor{
	String process(Object input){
		return Arrays.toString(((String)input).split(""));
	}
}
public class Apply {
	public static void process(Processor p, Object s){
		System.out.println("Using Processor "+p.name());
		System.out.println(p.process(s));
	}
	public static String s=
			"Disagreement with beliefs is by definition incorrect";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		process(new Upcase(),s);
		process(new Downcase(), s);
		process(new Splitter(), s);
		char []x={'a','b','c'};
		System.out.println(x);
	}

}
