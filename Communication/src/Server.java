import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public void MessageRespond(int port) {
		try(ServerSocket ssck = new ServerSocket(port);
			Socket sck=ssck.accept();
			BufferedReader is=new BufferedReader(new InputStreamReader(sck.getInputStream()));
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(sck.getOutputStream()))){
			
			System.out.println("��ʼ�����˿�");
			
			
			String info;
			while(is.ready()) {
			while((info=is.readLine())!=null) {
				System.out.println(info);
			}}
			
			info="�����Ƿ����\n";
			System.out.println("��ʼ������Ϣ");
			
			os.write(info);
			os.newLine();
			os.flush();
			
			sck.close();S
			System.out.println("����");
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
