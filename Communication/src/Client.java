
public class Client {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		if("0".equalsIgnoreCase(args[0])) {
			Request c=new Request();
			c.MessageInitiator("192.168.101.3", 185);
			//c.close();
			
		}
		else if("1".equalsIgnoreCase(args[0]) ) {
			Server s=new Server();
			s.MessageRespond(185);
		}
	}

}
