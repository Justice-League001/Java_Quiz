import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
	
	public void MessageInitiator() {
		
		try(BufferedWriter os = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream()));
		BufferedReader is = new BufferedReader(new InputStreamReader(sck.getInputStream()))){
			
			System.out.println("成功连接！");
			
			System.out.println("[对方  IP ]:"+sck.getInetAddress());
			System.out.println("[对方PORT]:"+sck.getPort());
			System.out.println("[对方 主机 ]:"+sck.getInetAddress().getHostName());
		
			System.out.println("[本地  IP ]:"+sck.getLocalAddress());
			System.out.println("[本地PORT]:"+sck.getLocalPort());
			System.out.println("[本地 主机 ]:"+sck.getLocalAddress().getHostName());
			
			System.out.println("输入短信(quit to EOF):");
			
			boolean GoOn=true;
			while(GoOn) {
				if(GoOn=send(enterStr(),os)) break;
				GoOn=receive(is);
			}
			
			send("连接中断",os);
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
	private boolean send(String info,BufferedWriter os) throws IOException  { 
			os.write(info);
			os.newLine();
			os.flush();
		    if(info=="EOF") return false;
		    return true;
	}
	private boolean receive(BufferedReader is) throws IOException{	
			String info;
			info=is.readLine();
			System.out.println(info);
			if(info=="EOF") return false;
			return true;
	}
	private String enterStr() {
		Scanner read=new Scanner(System.in);
		if(read.hasNextLine()) {
			String LineStr=read.nextLine();
			return LineStr;
		}
		else
			return "EOF";
	}
}
