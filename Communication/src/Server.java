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
	
	ServerSocket ssck;
	Socket sck;
	
	public Server(int port) {
		try {
			System.out.println("开始监听端口");
			ssck = new ServerSocket(port);
			sck=ssck.accept();
			System.out.println("发现请求!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void MessageRespond() {
		try(BufferedReader is=new BufferedReader(new InputStreamReader(sck.getInputStream()));
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream()))){
			
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
				if(GoOn=receive(is))break;
				GoOn=send(enterStr(),os);
			}
					
			receive(is);
			send("连接中断",os);
			
			os.close();
			is.close();
			sck.close();
			ssck.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
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
