
public class Client {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		if("0".equalsIgnoreCase(args[0])) {
			Request c=new Request("192.168.101.3", 185);
			c.SendMessage();
			c.close();
		}
		else if("1".equalsIgnoreCase(args[0]) ) {
			Server s=new Server();
			s.MessageReceive(185);
		}
	}

}
