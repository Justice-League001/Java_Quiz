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
		Path files = null;   //���÷����ڲ�����
		
		
		JPanel inputPanel, nodePanel, buttonPanel;
		JLabel TipsLabel, pathIndicateLabel, nodeIndicateLabel;
		JTextField pathField;
		JScrollPane scrollPane;
		JTree tree;
		JButton confirmButton, cancelButton;
		Component horizontalStrut;
		
		/********************************************************************************************************************************
		 *   ·����
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
		 *  �ڵ�ѡ����
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
		
		/*  Interface FileVisitor<T>  �ļ������� �ýӿ�ʵ���ṩ��Files.walkFileTree�����������ļ����е�ÿ���ļ�.
		 *  Files.walkFileTree���ݷ����ķ���ֵFileVisitResult�����Ƿ��������.
		 *  CONTINUE ����.
		 *  SKIP_SIBLINGS ����, �����Ե�ǰ�ڵ�������ֵܽڵ�ֱ�ӷ�����һ��.
		 *  SKIP_SUBTREE ����, ��������Ŀ¼, �����ļ��������.
		 *  TERMINATE ��ֹ����.
		 *  FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs)  �������һ��Ŀ¼֮ǰִ�еĶ���.
		 *  FileVisitResult postVisitDirectory(T dir, IOException exc) �������һ��Ŀ¼��ִ�еĶ���.
		 *  FileVisitResult visitFile(T file, BasicFileAttributes attrs) �������ڷ���һ���ļ�ʱ��ִ�ж���.
		 *  FileVisitResult visitFileFailed(T file, IOException exc) �������һ���ļ�ʧ��ʱ��ִ�ж���.
		 *  
		 *  class SimpleFileVisitor FileVisitor<T>ʵ���� �������Ĭ����Ϊ���������ļ��������׳�I/O����.
		 *  ʹ�ø�������ʵ�ֽӿ����з���,ֻ��ʵ�ָ���Ȥ�ķ���.
		 *  
		 */
		
		Path file = Paths.get("C:\\Users\\asus\\Desktop\\����"); //��ʱ����
		tree = new JTree(new SimpleFileVisitor<Path>() {
			
			/*  ����ָ��·���µ������ļ�(Ŀ¼)��ͬʱ�����ļ���
			 *  ��ǰԪ��ΪĿ¼ʱִ��preVisitDirectory():�������ڵ�ʹ�õ�ǰ·��Ϊ����, ���ݵ�ǰfile����hashCode��Ϊ�����������ڵ���Ϊֵ����Map,
			 *  �����ݵ�ǰfile����丸Ŀ¼hashCode, ��Map��Ѱ���丸�ڵ� , �����̳й�ϵ.
			 *  ��ǰԪ��Ϊ�ļ�ʱִ��visitFile():�������ڵ�ʹ�õ�ǰ·��Ϊ����, ���ݵ�ǰfile����hashCode��Ϊ�����������ڵ���Ϊֵ����Map,
			 *  �����ݵ�ǰfile����丸Ŀ¼hashCode, ��Map��Ѱ���丸�ڵ� , �����̳й�ϵ.
			 *  getNode():Files.walkFileTree()����ָ��·��, ����Map.
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
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				return map.get(start.hashCode());
			}	

		}.getNode(file));		
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO �Զ����ɵķ������
				DefaultMutableTreeNode node = ((DefaultMutableTreeNode) ((JTree) e.getSource()).getLastSelectedPathComponent());

				pathField.setText(((Path)node.getUserObject()).toString());
			}
			
		});
		scrollPane.setViewportView(tree);
				
		/********************************************************************************************************************************
		 *  ��ť��
		 */
		
		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		confirmButton = new JButton("ȷ��");
		buttonPanel.add(confirmButton);
		
		
		horizontalStrut = Box.createHorizontalStrut(30);
		buttonPanel.add(horizontalStrut);
		
		cancelButton = new JButton("ȡ��");
		buttonPanel.add(cancelButton);
		
		/*********************************************************************
		 * ��ť�¼�
		 * ȷ�ϰ�ť
		 * ȡ����ť
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
				 * ���޸�
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
