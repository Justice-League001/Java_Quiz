import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	private ServerSocket ssck;
	private Socket sck;
	
	public Server(int port) {
		try {
			System.out.println("��ʼ�����˿�");
			ssck = new ServerSocket(port);
			sck=ssck.accept();
			System.out.println("��������!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void MessageRespond() {
		try(BufferedReader is=new BufferedReader(new InputStreamReader(sck.getInputStream()));
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream()))){
			
			System.out.println("�ɹ����ӣ�");
			
			System.out.println("[�Է�  IP ]:"+sck.getInetAddress());
			System.out.println("[�Է�PORT]:"+sck.getPort());
			System.out.println("[�Է� ���� ]:"+sck.getInetAddress().getHostName());
		
			System.out.println("[����  IP ]:"+sck.getLocalAddress());
			System.out.println("[����PORT]:"+sck.getLocalPort());
			System.out.println("[���� ���� ]:"+sck.getLocalAddress().getHostName());
		
			
			boolean GoOn=true;
			while(GoOn) {
				System.out.print(sck.getInetAddress()+":"+sck.getPort()+"��");
				
				if(!(GoOn=receive(is))) break;
				System.out.println("�������(quit to EOF):");
				GoOn=send(enterStr(),os);
			}
					
			receive(is);
			send("�����ж�",os);
				
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				ssck.close();
				sck.close();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	private boolean send(String info,BufferedWriter os) throws IOException  { 
		os.write(info);
		os.newLine();
		os.flush();
	    if(info.equals("EOF")) return false;
	    return true;
	    }
    private boolean receive(BufferedReader is) throws IOException{	
		String info;
		info=is.readLine();
		System.out.println(info);
		if(info.equals("EOF")) return false;
		return true;
		}
    private String enterStr() {
    	Scanner read=new Scanner(System.in);
		String LineStr="EOF";
		if(read.hasNextLine()) LineStr=read.nextLine();

		return LineStr;
	
	}
    
}
