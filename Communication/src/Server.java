import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public void MessageRespond(int port) {
		try(ServerSocket ssck = new ServerSocket(port);
			Socket sck=ssck.accept();
			BufferedReader is=new BufferedReader(new InputStreamReader(sck.getInputStream()));
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream()))){
			
			System.out.println("开始监听端口");
			
			String info;
			while(is.ready()==true) {
			while((info=is.readLine())!=null) {
				System.out.println(info);
			}}
			
			System.out.println("开始发送信息");
			os.write("这里是服务端");
			os.flush();
			
			//os.close();
			sck.shutdownOutput();
			
			System.out.println("结束");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
