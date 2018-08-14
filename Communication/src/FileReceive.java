import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FileReceive {
	private final File LOCAL_FILE;
	private String TARGET_IP;
	private final int LOCAL_PORT;
	
	FileReceive(int PORT,String PATH){
			LOCAL_FILE = new File(PATH);
			LOCAL_PORT = PORT;
	}	
	public void start() {
		try (ServerSocket connection = new ServerSocket(LOCAL_PORT);
			 Socket remote = connection.accept();
			 InputStream fileIn = new BufferedInputStream(remote.getInputStream());
			 OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(LOCAL_FILE));
			 BufferedReader infoRead = new BufferedReader(new InputStreamReader(remote.getInputStream()));
			 PrintWriter infoWrite = new PrintWriter(remote.getOutputStream())){
			
			if(InfoComfirm(infoRead, infoWrite)){
				System.out.println("开始接收文件");
				long totalBytesReceive = ReceiveFile(fileOut, fileIn);
				
				InfoSend(infoWrite,totalBytesReceive+"");
				long totalBytesSend = Long.parseLong(InfoReceive(infoRead));
				
				System.out.println("已接收"+totalBytesReceive+"字节");
				System.out.println("IP:"+remote.getInetAddress()
						+":"+remote.getPort()+":"+"已发送"+totalBytesSend+"字节");
				}
			else 
				System.out.println("拒绝接收文件");
				
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
    private boolean InfoComfirm(BufferedReader infoRead, PrintWriter infoWrite) throws IOException{
		
    	System.out.println("接收文件信息");
    	System.out.println(InfoReceive(infoRead));
		
		String info = EnterStr();
		InfoSend(infoWrite,info);
		info = info.equals("Y") ? "1" : "0"; 
		return info.equals("1") ? true : false; 
	}
	private long ReceiveFile(OutputStream os,InputStream is) throws IOException {
		int b=-1;
		long totalBytesReceive=0;
		while(totalBytesReceive != 607) {
			b=is.read();
			os.write(b);
			totalBytesReceive++;
		}
		os.flush();
		return totalBytesReceive;
	}
	private void InfoSend(PrintWriter write,String info) {
		write.println(info);
		write.flush();
	}
	private String InfoReceive(BufferedReader read) throws IOException {
		String info = read.readLine();
		return info;
	}
	private String EnterStr() {
		Scanner read=new Scanner(System.in);
		String LineStr="EOF";
		if(read.hasNextLine()) LineStr=read.nextLine();

		return LineStr;
	}
}

