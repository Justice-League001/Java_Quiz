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
			
			System.out.println("成功连接");
			
			if(InfoComfirm(infoRead, infoWrite)){
				System.out.println("请求已确认");
				long totalBytesSend = SendFile(fileOut, fileIn);
				
				long totalBytesReceive = Long.parseLong(InfoReceive(infoRead));
				System.out.println("已发送"+totalBytesSend+"字节");
				System.out.println("IP:"+TARGET_IP+":"+TARGET_PORT+":"+"已接收"+totalBytesReceive+"字节");
				
				InfoSend(infoWrite, String.valueOf(totalBytesSend)); 
				}
			else 
				System.out.println("请求被拒绝");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private boolean InfoComfirm(BufferedReader infoRead, PrintWriter infoWrite) throws IOException{
		
		System.out.println("向对方发送文件确认");
		InfoSend(infoWrite,"FileName:"+LOCAL_FILE.getName()+"  FileSize:"+LOCAL_FILE.length());
		String info = InfoReceive(infoRead);
		info = info.equals("Y") ? "1" : "0"; 
		return info.equals("1") ? true : false; 
	}
	private long SendFile(OutputStream os,InputStream is) throws IOException {
		
		int b=-1;
		long totalBytesSend=0;
		while((b=is.read())!=-1) {
			os.write(b);
			totalBytesSend++;
		}
		os.flush();
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
