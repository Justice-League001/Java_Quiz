import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
	private ServerSocket ssck;
	private Socket sck;
	private byte buffer[]=new byte[20];
	private InputStream is;
	private OutputStream os;
	
	 void serversocket_connet(){
		 while(sck==null) {
		 try {
			ssck=new ServerSocket(182);
			while(true) {
				sck=ssck.accept();
				is=sck.getInputStream();
				String str="";
			    while(is.read(buffer)!=-1) {
						 str+=new String(buffer);
					 }
				System.out.println(str);
			}
			
			//os=sck.getOutputStream();
		 } catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		 }
		}
	 }
	 void message_confirm() {
		 String str="";
		 try {
			while(is.read(buffer)!=-1) {
				 str+=new String(buffer);
			 }
			System.out.println(str);
			//os.write("�����:�����Ƿ����".getBytes());
			/*
			while(is.read(buffer)!=-1) {
				 System.out.print(new String(buffer));
			 }
			System.out.println();
			os.write("�����:�յ�".getBytes());*/
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	 }
	 void Fileread_write(String file) {
		 try {
			FileOutputStream fr=new FileOutputStream(file);
			while(is.read(buffer)!=-1) {
				fr.write(buffer);
			}
		} catch (FileNotFoundException  e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	 }
	public static void main(String[] args) {
		
		server s=new server();
		s.serversocket_connet();
		s.message_confirm();
		//s.Fileread_write("C:\\Users\\asus\\Desktop\\123\\123.txt");
	}

}