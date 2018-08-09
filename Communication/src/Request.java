import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Request {
	protected final int NETWORK_BUFFER_SIZE;
	protected final int DISK_BUFFER_SIZE; 
	protected final IO_info c;
	
	public Request(String ip, int port) {	
		NETWORK_BUFFER_SIZE =  128 * 1024;
		DISK_BUFFER_SIZE = 64 * 1024;
		c=connet(ip,port);	
	}
	
	public Request(int NETWORK_BUFFER_SIZE,int DISK_BUFFER_SIZE,String ip,int port) {
		this.NETWORK_BUFFER_SIZE = NETWORK_BUFFER_SIZE;
		this.DISK_BUFFER_SIZE = DISK_BUFFER_SIZE;
		c=connet(ip,port);
	}
	
	class IO_info{
		final Socket sck;
		final InputStream is;
		final OutputStream os;
		IO_info(Socket sck,InputStream is,OutputStream os){
			this.sck=sck;
			this.is=is;
			this.os=os;
		}
	}
	protected IO_info connet(String ip, int port) {
		try (Socket sck = new Socket(ip, port);
				InputStream is = new BufferedInputStream(sck.getInputStream(),NETWORK_BUFFER_SIZE);
				OutputStream os = new BufferedOutputStream(sck.getOutputStream(),DISK_BUFFER_SIZE)){
			
			System.out.println("建立连接");
			return new IO_info(sck,is,os);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected void close() {
		try {
			c.is.close();
		//	c.os.close();
			c.sck.close();
			System.out.println("连接断开");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void SendMessage() {
		try {
//		try(Socket sck = new Socket(ip, port);
//				InputStream is = new BufferedInputStream(sck.getInputStream(),NETWORK_BUFFER_SIZE);
//				OutputStream os = new BufferedOutputStream(sck.getOutputStream(),DISK_BUFFER_SIZE)){
			
//			(BufferedReader reader= new BufferedReader(new InputStreamReader(c.is)))
			
			System.out.println("开始发送");
			String str="这里是客户端";
			c.os.write(str.getBytes());
			//c.os.flush();
			System.out.println("发送结束");
			

//			String info;
//			while((info=reader.readLine())!=null) {
//				System.out.print(info);
//			}
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
