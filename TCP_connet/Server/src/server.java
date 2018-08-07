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
	private byte buffer[]=new byte[3];
	private InputStream is;
	private OutputStream os;
	
	 void serversocket_connet(){
		 while(sck==null) {
		 try {
			 
			ssck=new ServerSocket(1820);
			sck=ssck.accept();
			is=sck.getInputStream();

		 } catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		 }
		}
	 }
	 void Fileread_write(String file) {
		 try {
			FileOutputStream fr=new FileOutputStream(file);
			while(is.read(buffer)!=-1) {
				fr.write(buffer);
			}
		} catch (FileNotFoundException  e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	 }
	public static void main(String[] args) {
		
		server s=new server();
		s.serversocket_connet();
		s.Fileread_write("C:\\Users\\asus\\Desktop\\123\\123.txt");
	}

}