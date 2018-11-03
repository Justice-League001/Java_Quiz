import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTree;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.ScrollPaneConstants;

public class test {

	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
//		setBounds(100, 100, 450, 300);
//		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setResizable(false);
		Path files = null;   //调用方法内部变量
		
		
		JPanel inputPanel, nodePanel, buttonPanel;
		JLabel TipsLabel, pathIndicateLabel, nodeIndicateLabel;
		JTextField pathField;
		JScrollPane scrollPane;
		JTree tree;
		JButton confirmButton, cancelButton;
		Component horizontalStrut;
		
		/********************************************************************************************************************************
		 *   路径区
		 */	
		
		
		inputPanel = new JPanel();
		frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
		inputPanel.setLayout(new BorderLayout(0, 0));
		
		TipsLabel = new JLabel("Tips!");
		TipsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inputPanel.add(TipsLabel, BorderLayout.NORTH);
		
		pathIndicateLabel = new JLabel("Path:");
		inputPanel.add(pathIndicateLabel, BorderLayout.WEST);
		
		pathField = new JTextField();
		inputPanel.add(pathField, BorderLayout.SOUTH);
		pathField.setColumns(20);
		
		/********************************************************************************************************************************/
		
		/*********************************************************************************************************************************
		 *  节点选择区
		 */
		
		nodePanel = new JPanel();
		frame.getContentPane().add(nodePanel, BorderLayout.CENTER);
		nodePanel.setLayout(new BorderLayout(0, 0));
		
		nodeIndicateLabel = new JLabel("Directories");
		nodePanel.add(nodeIndicateLabel, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		nodePanel.add(scrollPane, BorderLayout.CENTER);
		
		/*  Interface FileVisitor<T>  文件访问者 该接口实现提供给Files.walkFileTree方法来访问文件树中的每个文件.
		 *  Files.walkFileTree根据方法的返回值FileVisitResult决定是否继续遍历.
		 *  CONTINUE 继续.
		 *  SKIP_SIBLINGS 继续, 但忽略当前节点的所有兄弟节点直接返回上一层.
		 *  SKIP_SUBTREE 继续, 但忽略子目录, 但子文件还会访问.
		 *  TERMINATE 终止遍历.
		 *  FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs)  定义访问一个目录之前执行的动作.
		 *  FileVisitResult postVisitDirectory(T dir, IOException exc) 定义访问一个目录后执行的动作.
		 *  FileVisitResult visitFile(T file, BasicFileAttributes attrs) 定义正在访问一个文件时的执行动作.
		 *  FileVisitResult visitFileFailed(T file, IOException exc) 定义访问一个文件失败时的执行动作.
		 *  
		 *  class SimpleFileVisitor FileVisitor<T>实现类 该类具有默认行为访问所有文件并重新抛出I/O错误.
		 *  使用该类无需实现接口所有方法,只需实现感兴趣的方法.
		 *  
		 */
		
		Path file = Paths.get("C:\\Users\\asus\\Desktop\\哈哈"); //临时变量
		tree = new JTree(new SimpleFileVisitor<Path>() {
			
			/*  遍历指定路径下的所有文件(目录)并同时建立文件树
			 *  当前元素为目录时执行preVisitDirectory():建立树节点使用当前路径为参数, 根据当前file生成hashCode作为键和生成树节点作为值存入Map,
			 *  并根据当前file获得其父目录hashCode, 在Map中寻找其父节点 , 建立继承关系.
			 *  当前元素为文件时执行visitFile():建立树节点使用当前路径为参数, 根据当前file生成hashCode作为键和生成树节点作为值存入Map,
			 *  并根据当前file获得其父目录hashCode, 在Map中寻找其父节点 , 建立继承关系.
			 *  getNode():Files.walkFileTree()遍历指定路径, 返回Map.
			 */
			
			Map<Integer, DefaultMutableTreeNode> map = new HashMap();
			public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) {
				//if (Files.isDirectory(file)) 
				DefaultMutableTreeNode son = new DefaultMutableTreeNode(file);
				map.put(file.hashCode(), son);
				if(map.containsKey(file.getParent().hashCode())) {
					map.get(file.getParent().hashCode()).add(son);
				}
				return FileVisitResult.CONTINUE;
			}
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)throws IOException{
				//if (Files.isExecutable(file)) {
				DefaultMutableTreeNode son = new DefaultMutableTreeNode(file);
				map.put(file.hashCode(), son);
				if(map.containsKey(file.getParent().hashCode())) {
					map.get(file.getParent().hashCode()).add(son);
				}
				// }
			return FileVisitResult.CONTINUE;
			}
			public TreeNode getNode(Path start) {
				try {
					Files.walkFileTree(start, this);
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				return map.get(start.hashCode());
			}	

		}.getNode(file));		
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO 自动生成的方法存根
				DefaultMutableTreeNode node = ((DefaultMutableTreeNode) ((JTree) e.getSource()).getLastSelectedPathComponent());

				pathField.setText(((Path)node.getUserObject()).toString());
			}
			
		});
		scrollPane.setViewportView(tree);
				
		/********************************************************************************************************************************
		 *  按钮区
		 */
		
		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		confirmButton = new JButton("确认");
		buttonPanel.add(confirmButton);
		
		
		horizontalStrut = Box.createHorizontalStrut(30);
		buttonPanel.add(horizontalStrut);
		
		cancelButton = new JButton("取消");
		buttonPanel.add(cancelButton);
		
		/*********************************************************************
		 * 按钮事件
		 * 确认按钮
		 * 取消按钮
		 */
		
		confirmButton.addActionListener(new ActionListener() {
			Path p;
			@Override
			public void actionPerformed(ActionEvent e) {	
				if(Files.exists(p = Paths.get(pathField.getText())) == false) 
					/*
					 * 
					 */
				
				System.out.println(files);
				//this.dispose();
				/*
				 * 待修改
				 */
			}
			
		});
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//this.dispose();
			}
			
		});
		/*********************************************************************/
		
		/********************************************************************************************************************************/
	}
	
}
