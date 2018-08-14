import java.util.Scanner;

public class Client {
	
	private static String enterStr(boolean h_p) {
		if(h_p) System.out.print("IP:");
		else System.out.print("PORT:");
		
		Scanner read=new Scanner(System.in);
		String LineStr = null;
		if(read.hasNextLine())  
			LineStr=read.nextLine();
		return LineStr;
	}
	public static void main(String[] args) {
		if("0".equals(args[0])) {
			//Request c=new Request(Client.enterStr(true), Integer.parseInt(Client.enterStr(false)));
			FileTransfer a = new FileTransfer("192.168.1.195",185,
					"C:\\Users\\asus\\Desktop\\123\\123.txt");
			a.start();
			//c.MessageInitiator();
			
		}
		else if("1".equals(args[0]) ) {
		//	Server s=new Server(Integer.parseInt(Client.enterStr(false)));
		//s.MessageRespond();
			FileReceive b = new FileReceive(185,"C:\\Users\\asus\\Desktop\\123\\11\\123.txt");
			b.start();
		}
	}

}
