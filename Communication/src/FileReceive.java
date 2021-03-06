import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;





public class FileReceive {
	private Path SaveDirPath;
	private final int LOCAL_PORT;
	private long FILE_SIZE;
	
	FileReceive(int PORT,String PATH){
			SaveDirPath = Paths.get(PATH);
			LOCAL_PORT = PORT;
			System.out.println("Start listening port:"+PORT+"...");
	}	
	public void start() {
		try (ServerSocket connection = new ServerSocket(LOCAL_PORT);
			 Socket remote = connection.accept();
			 InputStream fileIn = new BufferedInputStream(remote.getInputStream());	 
			 BufferedReader infoRead = new BufferedReader(new InputStreamReader(remote.getInputStream()));
			 PrintWriter infoWrite = new PrintWriter(remote.getOutputStream())){
			
			
			System.out.println("IS_CONNECTED:"+remote.isConnected());
			System.out.println("[LOCAL_IP   ]:"+remote.getLocalAddress());
			System.out.println("[LOCAL_PORT ]:"+LOCAL_PORT);
			System.out.println("[LOCAL_NAME ]:"+remote.getLocalAddress().getHostName());
			System.out.println("[TARGET_IP  ]:"+remote.getInetAddress());
			System.out.println("[TARGET_PORT]:"+remote.getPort());
			System.out.println("[TARGET_NAME]:"+remote.getInetAddress().getHostName());
			
			
			if(InfoComfirm(infoRead, infoWrite)){
				System.out.println("Info_Comfirm_:1");
				
				double timeUsed = System.currentTimeMillis();
				long totalBytesReceive = ReceiveFile(fileIn);		
				timeUsed = (System.currentTimeMillis()-timeUsed)/1000;
				
				System.out.printf("Have_Receive\t:%d Bytes\nTime Used\t:%f Sec\nAverage Speed\t:%f KB/S\n"
						,totalBytesReceive,timeUsed,((double)totalBytesReceive/1000.)/timeUsed);
				}
			else 
				System.out.println("Info_Comfirm_:0");
				
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
    private boolean InfoComfirm(BufferedReader infoRead, PrintWriter infoWrite) throws IOException{
		
    	System.out.println("\n\tInfo_Comfirm_Receive\n");
 
    	String str = InfoReceive(infoRead);
    	SaveDirPath = SaveDirPath.resolve(str);
    	
    	System.out.println("File_Name:"+str); 
    	System.out.println("File_Size:"+(FILE_SIZE = Long.parseLong(InfoReceive(infoRead)))+" Bytes");
    	
    	System.out.print("FileComfirm(Receive to (y or Y):");
    	
		String info = EnterStr();
		InfoSend(infoWrite,info);
		info = info.toUpperCase().equals("Y") ? "1" : "0"; 
		return info.equals("1") ? true : false; 
	}
	private long ReceiveFile(InputStream is) throws IOException {
		int b = -1;
		long totalBytesReceive = 0;
		
		long Times = FILE_SIZE / 36 ; 
		int k=0;

		
		OutputStream os = new BufferedOutputStream(new FileOutputStream(SaveDirPath.toString()));
		while((b = is.read()) != -1) {
			os.write(b);
			totalBytesReceive++;
			
			if(Times == 0 || totalBytesReceive % Times == 0 && k<36 ) {
				System.out.print("▋");
				k++;
			}
		}
		for(; k<30; k++) System.out.print("▋");
		if(totalBytesReceive / FILE_SIZE == 1) System.out.println("已下载100%");
		else System.out.println("已下载100%\nWaring:文件大小不符");
		
		os.flush();
		os.close();

					
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

