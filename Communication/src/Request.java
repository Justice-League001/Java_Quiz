import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Request {
//	private static final int NETWORK_BUFFER_SIZE =  128 * 1024;
//  private static final int DISK_BUFFER_SIZE = 64 * 1024;

	
	public void MessageInitiator(String ip, int port) {
		try(Socket sck = new Socket(ip, port);	
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream()));
			BufferedReader is = new BufferedReader(new InputStreamReader(sck.getInputStream()));){
		
			
			System.out.println("��ʼ����");
			String info="�����ǿͻ���";
			os.write(info);
			os.flush();
			System.out.println("���ͽ���");
			
			sck.shutdownOutput();
			
			while((info=is.readLine())!=null) {
				System.out.print(info);
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
