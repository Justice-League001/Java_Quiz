package Client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
	private byte buffer[]=new byte[10];
	private Socket sck;
	private OutputStream os;
	private InputStream is;
	
	void socketconnet(String ip,int port) {
		while(sck==null) {
		try {	
			sck=new Socket(ip,port);
			os=sck.getOutputStream();
			is=sck.getInputStream();
					
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	 }
    }	
	void File_read_send(String file) {
		try {
			FileInputStream fr=new FileInputStream(file);
			while(fr.read(buffer)!=-1) {
			os.write(buffer);
			}
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	void Socket_close() {
		try {
			sck.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		client c=new client();
		//System.out.println("����ip��");
		c.socketconnet("192.168.101.3",1820);
		//System.out.println("����port��");
		c.File_read_send("C:\\Users\\asus\\Desktop\\123.txt");
		c.Socket_close();
	}

}
