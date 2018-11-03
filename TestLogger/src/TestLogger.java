import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {

	public static void main(String[] args) throws SecurityException, IOException {
		
		
		Logger log = Logger.getLogger("com.TestLogger");
		
		/*  Level ������һ������ڿ�����־����ı�׼��־��¼����.
		 *  ��¼�����������������������ָ��.
		 *  ����:
		 *  SEVERE (TOP)
		 *  WARNING
		 *  INFO
		 *  CONFIG
		 *  FINE
		 *  FINER
		 *  FINEST (BASE)
		 *  ���⼶��:
		 *  OFF �����ڹر���־��¼�����⼶��.
		 *  ALL ��ʾ������Ϣ��Ӧ�ñ���¼.
		 *  
		 *  Logger ���ڼ�¼�ض�ϵͳ��Ӧ�ó����������Ϣ.
 		 *  setLevel(Level new Level)
		 *  ������־����,ָ����¼������¼��Щ��Ϣ����. ��Ϣ������ڴ�ֵ��������.
		 *  addHandler(Handler handler) �����־��������Խ�����־��Ϣ.
		 *  info() ��¼INFO������Ϣ.
		 *  fine() ��¼FINE������Ϣ.
		 */
		
		log.setLevel(Level.WARNING);
		
		/*  Handler �����Logger�л�ȡ��־��Ϣ,ʹ��Formatter�Լ�¼���и�ʽ������,������Ϣ����.
		 *  ��ͨ��setLevel(Level.OFF)����,ͨ��ִ���ʵ������setLevel����.
		 *  
		 *  Formatter ��ʽ�����ṩ��LogRecords���и�ʽ��֧��.
		 *  ��ʽ��������LogRecord������ת��Ϊ�ַ���.
		 *  
		 *  ConsoleHandler ���Handler����־��¼������System.err.
		 *                 Ĭ�������,SimpleFormatter�������ɼ�ҪժҪ.
		 *  
		 *  FileHandler �򵥵��ļ�Handler.����д��ָ�����ļ�,Ҳ����д��һ����ת���ļ�.
		 * 
		 */
		
		//����̨������
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.WARNING);
		log.addHandler(consoleHandler);
		
		//�ļ�������
		FileHandler fileHandler = new FileHandler("C:\\Users\\asus\\Desktop\\TestLogger\\8888g.xml");
		fileHandler.setLevel(Level.WARNING);
		log.addHandler(fileHandler);
		System.out.println(consoleHandler.getFormatter());
		log.warning("s");
		log.warning("b");
	}

}
