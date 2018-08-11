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

	private Socket sck;
	
	public Request(String ip, int port) {
		try {
			sck=new Socket(ip,port);		
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void MessageInitiator(String ip, int port) {
		
//		try(Socket sck = new Socket(ip, port);	
//			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream()));
//			BufferedReader is = new BufferedReader(new InputStreamReader(sck.getInputStream()));){
		
		try(BufferedWriter os = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream()));
		BufferedReader is = new BufferedReader(new InputStreamReader(sck.getInputStream()))){
			
			String info="这里是客户端";
			send(info,os);
			receive(is);	
			
			
			os.close();
			is.close();
			sck.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void send(String info,BufferedWriter os) throws IOException  {    
				os.write(info);
				os.newLine();
				os.flush();		
	}
	private void receive(BufferedReader is) throws IOException{				
			String info;
			while((info=is.readLine())!=null) {
				System.out.println(info);
			}
	}
}
