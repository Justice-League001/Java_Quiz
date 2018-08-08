package Client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private OutputStream os;
	private InputStream is;
	private Socket sck;
	
	private void socketconnet(String ip,int port) {
		try {
			sck=new Socket(ip,port);
			System.out.println("连接建立");
			os=sck.getOutputStream();
			//is=sck.getInputStream();				
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }
	private void file_read_send(String file) {
		byte buffer[]=new byte[32];		
		try (FileInputStream fr=new FileInputStream(file)){
			while(fr.read(buffer)!=-1) {
			os.write(buffer);
			}
			System.out.println("文件已发送");
			fr.close();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			System.out.println("文件已关闭");
		}
	}
	private void socket_close() {
		try {
			sck.close();
			System.out.println("连接断开");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Client c=new Client();
		//System.out.println("输入ip：");
		//System.out.println("输入port：");
		
		c.socketconnet("192.168.101.3",1820);	
		c.file_read_send("C:\\Users\\asus\\Desktop\\123.txt");
		c.socket_close();
	}

}
