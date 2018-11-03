import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {

	public static void main(String[] args) throws SecurityException, IOException {
		
		
		Logger log = Logger.getLogger("com.TestLogger");
		
		/*  Level 定义了一组可用于控制日志输出的标准日志记录级别.
		 *  记录级别对象被排序并且由有序整数指定.
		 *  级别:
		 *  SEVERE (TOP)
		 *  WARNING
		 *  INFO
		 *  CONFIG
		 *  FINE
		 *  FINER
		 *  FINEST (BASE)
		 *  特殊级别:
		 *  OFF 可用于关闭日志记录的特殊级别.
		 *  ALL 表示所有消息都应该被记录.
		 *  
		 *  Logger 用于记录特定系统或应用程序组件的消息.
 		 *  setLevel(Level new Level)
		 *  设置日志级别,指定记录器将记录那些消息级别. 消息级别低于此值将被丢弃.
		 *  addHandler(Handler handler) 添加日志处理程序以接收日志消息.
		 *  info() 记录INFO级别消息.
		 *  fine() 记录FINE级别消息.
		 */
		
		log.setLevel(Level.WARNING);
		
		/*  Handler 负责从Logger中获取日志信息,使用Formatter对记录进行格式化处理,并将信息导出.
		 *  可通过setLevel(Level.OFF)禁用,通过执行适当级别的setLevel重启.
		 *  
		 *  Formatter 格式化器提供对LogRecords进行格式化支持.
		 *  格式化器采用LogRecord并将其转换为字符串.
		 *  
		 *  ConsoleHandler 这个Handler将日志记录发布到System.err.
		 *                 默认情况下,SimpleFormatter用于生成简要摘要.
		 *  
		 *  FileHandler 简单的文件Handler.可以写入指定的文件,也可以写入一组旋转的文件.
		 * 
		 */
		
		//控制台控制器
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.WARNING);
		log.addHandler(consoleHandler);
		
		//文件控制器
		FileHandler fileHandler = new FileHandler("C:\\Users\\asus\\Desktop\\TestLogger\\8888g.xml");
		fileHandler.setLevel(Level.WARNING);
		log.addHandler(fileHandler);
		System.out.println(consoleHandler.getFormatter());
		log.warning("s");
		log.warning("b");
	}

}
