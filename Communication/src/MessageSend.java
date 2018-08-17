import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MessageSend {
//	private static final int NETWORK_BUFFER_SIZE =  128 * 1024;
//  private static final int DISK_BUFFER_SIZE = 64 * 1024;

	private Socket sck;
	
	MessageSend(String ip, int port) {
		try {
			sck=new Socket(ip,port);		
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void start() {
		
		try(BufferedWriter os = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream()));
		BufferedReader is = new BufferedReader(new InputStreamReader(sck.getInputStream()))){
			
			System.out.println("�ɹ����ӣ�");
			
			System.out.println("[�Է�  IP ]:"+sck.getInetAddress());
			System.out.println("[�Է�PORT]:"+sck.getPort());
			System.out.println("[�Է� ���� ]:"+sck.getInetAddress().getHostName());
		
			System.out.println("[����  IP ]:"+sck.getLocalAddress());
			System.out.println("[����PORT]:"+sck.getLocalPort());
			System.out.println("[���� ���� ]:"+sck.getLocalAddress().getHostName());
			
			
			boolean GoOn=true;
			while(GoOn) {
				System.out.println("Please Input Short Message(quit to EOF):");
				
				if(!(GoOn=Send(EnterStr(),os))) break;
				System.out.print(sck.getInetAddress()+":"+sck.getPort()+"��");
				GoOn=Receive(is);
			}
			
			Send("�����ж�",os);
			Receive(is);
			
		 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				sck.close();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	private boolean Send(String info,BufferedWriter os) throws IOException  { 
			os.write(info);
			os.newLine();
			os.flush();
		    if(info.equals("EOF")) return false;
		    return true;
	}
	private boolean Receive(BufferedReader is) throws IOException{	
			String info;
			info=is.readLine();
			System.out.println(info);
			if(info.equals("EOF")) return false;
			return true;
	}
	private String EnterStr() {
		Scanner read=new Scanner(System.in);
		String LineStr="EOF";
		if(read.hasNextLine()) LineStr=read.nextLine();

		return LineStr;
	}
}
