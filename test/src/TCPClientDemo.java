import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class TCPClientDemo {
 
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
        //1.����TCP����
		String ip="192.168.101.3";   //��������ip��ַ
		int port=10003;        //�˿ں�
		Socket sck=new Socket(ip,port);
		//2.��������
		OutputStream os=sck.getOutputStream();   //�����
		String content="����һ��java�ͻ���";
		
		byte[] bstream=content.getBytes();
		//System.out.println(new String(bstream));
		
		os.write(bstream);
		//3.�ر�����
		sck.close();
	}
 
}
