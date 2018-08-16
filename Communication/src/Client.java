import java.util.Scanner;

public class Client {
	private static int Menu() {
		System.out.println("1.SendMessage\t                    \t2.ReceiveMessage\t");
		System.out.println("3.FileSend\t                       \t4.FileReceive\t");
		System.out.println("5.close\t");
		return  Integer.parseInt(EnterStr());
	}
	private static String InfoEnter(int m) {
		if(m == 1) System.out.println("Please Input The IP Address(Enter to quit):");
		else if(m == 2) System.out.println("Please Input The File Directory To Be Sent(Enter to quit):");
		else System.out.println("Please Input The File Directory To Be Receive(Enter to quit):");
		return EnterStr();
	}
	private static String EnterStr() {

		Scanner read = new Scanner(System.in);
		String LineStr = null;
		if(read.hasNextLine())  
			LineStr = read.nextLine();
		return LineStr;
	}
	public static void main(String[] args) {
		boolean menu = true;
		while(menu) {
			switch(Menu()) {
			case 1:
				new MessageSend(InfoEnter(1),185).start();
				break;
			case 2:
				new MessageReceive(185).start();
				break;
			case 3:
				new FileTransfer(InfoEnter(1),186,InfoEnter(2)).start();
				break;
			case 4:
				new FileReceive(186,InfoEnter(3)).start();
				break;
			case 5:
				menu = false;
				System.out.println("End procedure");
				break;
			default:
				System.out.println("Input Error");
			}
		}
	}

}
