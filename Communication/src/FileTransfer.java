import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class FileTransfer {
	private final File LOCAL_FILE;
	private final String TARGET_IP;
	private final int TARGET_PORT;
	
	FileTransfer(String IP,int PORT,String PATH){
		LOCAL_FILE = new File(PATH);
		TARGET_PORT = PORT;
		TARGET_IP = IP;
	}
	
	public void start() {
		try(Socket connection = new Socket(TARGET_IP,TARGET_PORT);
			InputStream fileIn = new BufferedInputStream(new FileInputStream(LOCAL_FILE));
			OutputStream fileOut = new BufferedOutputStream(connection.getOutputStream());
			BufferedReader infoRead = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter infoWrite = new PrintWriter(connection.getOutputStream())){
			
			
			System.out.println("IS_CONNECTED_\t:"+connection.isConnected());
			System.out.println("[LOCAL_IP\t]:"+connection.getLocalAddress());
			System.out.println("[LOCAL_PORT\t]:"+connection.getLocalPort());
			System.out.println("[LOCAL_NAME\t]:"+connection.getLocalAddress().getHostName());
			System.out.println("[TARGET_IP\t]:"+connection.getInetAddress());
			System.out.println("[TARGET_PORT\t]:"+connection.getPort());
			System.out.println("[TARGET_NAME\t]:"+connection.getInetAddress().getHostName());
			
			
			
			if(InfoComfirm(infoRead, infoWrite)){
				System.out.println("Info_Comfirm_\t:1");
				long totalBytesSend = SendFile(fileOut, fileIn);
						
				System.out.println("Have_Sent\t:"+totalBytesSend+"times");		
				}
			else 
				System.out.println("Info_Comfirm_\t:0");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private boolean InfoComfirm(BufferedReader infoRead, PrintWriter infoWrite) throws IOException{
		
		System.out.println("\n\tInfo_Comfirm_Send");
		InfoSend(infoWrite,LOCAL_FILE.getName());
		InfoSend(infoWrite,LOCAL_FILE.length()+"");
		String info = InfoReceive(infoRead);
		info = info.equals("Y") ? "1" : "0"; 
		return info.equals("1") ? true : false; 
	}
	private long SendFile(OutputStream os,InputStream is) throws IOException {
		
		int b = -1;
		long totalBytesSend = 0;
		while((b = is.read()) != -1) {
			os.write(b);
			totalBytesSend++;
		}
		os.flush();
		is.close();
		return totalBytesSend; 
	}
	private void InfoSend(PrintWriter write,String info) {
		write.println(info);
		write.flush();
	}
	private String InfoReceive(BufferedReader read) throws IOException {
		String info = read.readLine();
		return info;
	}
	
}
