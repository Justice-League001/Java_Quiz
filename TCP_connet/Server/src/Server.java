import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private InputStream is;
	private OutputStream os;
	private Socket sck;
	private ServerSocket ssck;
	
	 void serversocket_connet(){
		 try {	
			ssck=new ServerSocket(1820);
			sck=ssck.accept();
			System.out.println("连接已经建立");
			is=sck.getInputStream();
		 } catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace(); 
		 }
	 }
	 void Fileread_write(String file) {
		 byte buffer[]=new byte[32];
		 try (FileOutputStream fr=new FileOutputStream(file)){
			System.out.println("已创建文件开始读写");
			while(is.read(buffer)!=-1) {
				fr.write(buffer);
			}
			
			System.out.println("文件读写结束");
			fr.close();

		} catch (FileNotFoundException  e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			System.out.println("文件已关闭");
		}
	 }
	private void server_socket_close() {
		try {
			sck.close();
			ssck.close();
			System.out.println("连接断开");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		Server s=new Server();
		s.serversocket_connet();
		s.Fileread_write("C:\\Users\\asus\\Desktop\\123\\123.txt");
		s.server_socket_close();
	}

}