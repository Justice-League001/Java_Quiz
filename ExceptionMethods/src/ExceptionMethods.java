
public class ExceptionMethods extends Exception{
	public ExceptionMethods(String string) {
		// TODO �Զ����ɵĹ��캯�����
		super(string);
	}
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {
			throw new Exception("My Exception");
		}catch(Exception e) {
			System.out.println("Caught Exception");
			System.out.println("getMessage():"+e.getMessage());
			System.out.println
			("getLocalizedMessage():"+e.getLocalizedMessage());
			System.out.println("toString():"+e);
			System.out.println("printStackTrace():");
			e.printStackTrace(System.out);
		}
		try {
			throw new ExceptionMethods("My Exception1");
		}catch(Exception e) {
			System.out.println("Caught Exception");
			System.out.println("getMessage():"+e.getMessage());
			System.out.println
			("getLocalizedMessage():"+e.getLocalizedMessage());
			System.out.println("toString():"+e);
			System.out.println("printStackTrace():");
			e.printStackTrace(System.out);
		}
	}

}
