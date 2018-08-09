import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	protected final int NETWORK_BUFFER_SIZE;
	protected final int DISK_BUFFER_SIZE; 
	
	public Server() {
		NETWORK_BUFFER_SIZE =  128 * 1024;
		DISK_BUFFER_SIZE = 64 * 1024;
	}
	public Server(int NETWORK_BUFFER_SIZE,int DISK_BUFFER_SIZE) {
		this.NETWORK_BUFFER_SIZE = NETWORK_BUFFER_SIZE;
		this.DISK_BUFFER_SIZE = DISK_BUFFER_SIZE;
	}
	
	public void MessageReceive(int port) {
		try(ServerSocket ssck = new ServerSocket(port);
				Socket sck=ssck.accept();
			    InputStream is = new BufferedInputStream(sck.getInputStream());
				OutputStream os = new BufferedOutputStream(sck.getOutputStream());){
			
			
			BufferedReader reader= new BufferedReader(new InputStreamReader(is));
			System.out.println("��ʼ�����˿�");
			
			String info;
			while((info=reader.readLine())!=null) {
				System.out.println(info);
			}
			System.out.println("����");
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
