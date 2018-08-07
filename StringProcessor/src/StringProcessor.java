import java.util.Arrays;

import interfaceprocessor.Processor;
import interfaceprocessor.Apply;

public abstract class StringProcessor implements Processor{
	public String name(){
		return getClass().getSimpleName();
	}
	public abstract String process(Object input);
	public static String s=
			"Disagreement with beliefs is by definition incorrect";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Apply.process(new Upcase(),s);
		Apply.process(new Downcase(), s);
		Apply.process(new Splitter(), s);
	}
}
class Upcase extends StringProcessor{
	public String process(Object input){
		return ((String)input).toUpperCase();
		//���� String�����ַ�ת��Ϊ��д��ʹ��Ĭ�����Ի����Ĺ���
	}
}
class Downcase extends StringProcessor{
	public String process(Object input){
		return ((String)input).toLowerCase();
		//ʹ��Ĭ�����Ի����Ĺ��򽫴� String�����ַ�ת��ΪСд�� 
	}
}
class Splitter extends StringProcessor{
	public String process(Object input){
		return Arrays.toString(((String)input).split(" "));
	}
}