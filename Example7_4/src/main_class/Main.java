package main_class;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=0, m=0, t=1000;
		try{
			m = Integer.parseInt("8888");
			n = Integer.parseInt("ab89");
			t = 7777;
		}
		catch(NumberFormatException e){
			System.out.println("�����쳣��"+e.getMessage());
		}
		System.out.println("n="+n+",m="+m+",t="+t);
		try{
			System.out.println("�����׳�I/O�쳣��");
			throw new java.io.IOException("���ǹ����");}
		catch(java.io.IOException e){
			System.out.println("�����쳣��"+e.getMessage());
			
		}
	}
}
