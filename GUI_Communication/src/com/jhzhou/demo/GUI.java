package com.jhzhou.demo;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;


import com.jhzhou.util.FileMsg;
import com.jhzhou.util.Info;
import com.jhzhou.util.TransferPacket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class GUI {


	private Info BC = new Info("0",0,"0",3, 3,null,true);
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式


	private final BlockingQueue<Info> Outputqueue;
	private final Queue<Info> InputQueue;
	private final Map<Info, LinkedBlockingQueue<Info>> ReceiveThread;
	Exchanger<Info> exchanger ;
	private final Map<Info, Info> people;
	private final Queue<Info> ThreadRequest;
	private final Map<Info, ProgressBarDialog> FileQueue; 
	private JFrame frame;
	private JButton receivePacket;
	private JTextArea textArea;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	private void addMenber(Container box, Container menber) {	    
		box.add(menber);
		box.revalidate();
		box.repaint();
	}
	public void packetProcesse() throws InterruptedException {
		receivePacket.doClick();
	}

	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TEST window = new TEST();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 * @throws UnknownHostException 
	 */
	public GUI(BlockingQueue<Info> Outputqueue, Queue<Info> InputQueue, Map<Info, LinkedBlockingQueue<Info>> ThreadMap, Queue<Info> ThreadRequest) {
		this.Outputqueue = Outputqueue;
		this.InputQueue = InputQueue;
		this.ReceiveThread = ThreadMap;
		this.ThreadRequest = ThreadRequest;
		people = new HashMap<Info, Info>();
		FileQueue = new HashMap<Info, ProgressBarDialog>();
	}

	//	public TEST() throws UnknownHostException {
	//		initialize();
	//	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	public void initialize() throws IOException {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(448, 156, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lblNewLabel_1 = new JLabel();

		textArea = new JTextArea();
		textArea.setCaretPosition(textArea.getText().length()); //文本插入符位置
		textArea.setLineWrap(true);        //激活自动换行功能 
		textArea.setWrapStyleWord(true);  
		textArea.setEditable(false);


		JTextArea textArea_1 = new JTextArea();

		textArea_1.setLineWrap(true);        //激活自动换行功能 
		textArea_1.setWrapStyleWord(true);  

		JPanel panel = new JPanel();
		panel.setBorder(null);
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1);

		JButton btnNewButton_2 = new PButton("       添加新连接        ",
				new ImageIcon(new URL("file:\\"+new File("").getCanonicalPath()+"\\icon\\180.png")),"",0,0);
		btnNewButton_2.setBackground(new Color(190, 190, 190));

		//		JButton btnNewButton_2 = new JButton("new Button");


		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String IP = JOptionPane.showInputDialog(frame, "目标IP地址:", "创建连接", JOptionPane.PLAIN_MESSAGE);

				if(IP!=null) {
					people.put(new Info(IP, 2000, null, 3, 3, null, true),new Info(IP, 2000, IP, 3, 3, "", true));

					PButton Button;
					try {
						Button = new PButton(IP+':'+2000, 
								new ImageIcon(new URL("file:\\"+new File("").getCanonicalPath()+"\\icon\\177.png")),
								IP, 2000, 3);

						Button.setBackground(new Color(200, 200, 200));

						Button.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								BC.MSG = textArea.getText();

								BC = people.get(new Info(((PButton) e.getSource()).IP, 2000, null, 3, 3, null, true));

								((PButton) e.getSource()).setText(BC.NAME);

								textArea.setText((String) BC.MSG);
								lblNewLabel_1.setText(BC.NAME);
								frame.validate();
							}
						});

						Component Strut = Box.createVerticalStrut(20);
						panel.add(Strut);
						panel.add(Button);
						Button.doClick();
					} catch (MalformedURLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		});


		panel.add(btnNewButton_2);

		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut);

		JButton btnNewButton_3 = new PButton("           公共区             ", new ImageIcon(new URL("file:\\"+new File("").getCanonicalPath()+"\\icon\\182.png")), "", 0, 0);
		//		JButton btnNewButton_3 = new JButton("new Button");
		btnNewButton_3.setBackground(new Color(190, 190, 190));

		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_1.setText(btnNewButton_3.getText());
			}
		});

		//		btnNewButton_3.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				((PButton) e.getSource()).setText(people.get(new Info(((PButton) e.getSource()).IP, 2000, null, 3, null, true)).NAME);
		//				BC.MSG = textArea.getText();
		//				
		//				try {
		//					BC = people.get(new Info(InetAddress.getLocalHost().toString(), 2000, null, 3, null, true));
		//				} catch (UnknownHostException e1) {
		//					e1.printStackTrace();
		//				}
		//				textArea.setText((String) BC.MSG);
		//				frame.validate();
		//			
		//			}
		//		});


		panel.add(btnNewButton_3);
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_2);

		//"E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\177.png"
		//System.out.println(new URL("file:\\"+new File("").getCanonicalPath()+"\\icon\\177.png"));

		JButton btnNewButton_4 = new PButton(InetAddress.getLocalHost().getHostName(), 
				new ImageIcon(new URL("file:\\"+new File("").getCanonicalPath()+"\\icon\\177.png")),InetAddress.getLocalHost().toString(),2000,3);
		btnNewButton_4.setBackground(new Color(190, 190, 190));
		people.put(new Info(InetAddress.getLocalHost().toString(), 2000, null, 3, 3, null, true),new Info(InetAddress.getLocalHost().toString(), 
				2000, InetAddress.getLocalHost().getHostName(), 3, 3,"", true));


		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_1.setText(((PButton) e.getSource()).getText());
				((PButton) e.getSource()).setText(people.get(new Info(((PButton) e.getSource()).IP, 2000, null, 3, 3,null, true)).NAME);
				BC.MSG = textArea.getText();

				try {
					BC = people.get(new Info(InetAddress.getLocalHost().toString(), 2000, null, 3, 3,null, true));
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				textArea.setText((String) BC.MSG);
				frame.validate();

			}
		});


		panel.add(btnNewButton_4);

		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel_1, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setDividerSize(0);
		splitPane.setResizeWeight(0.7);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setBorder(null);
		splitPane_1.setDividerSize(0);
		splitPane_1.setResizeWeight(0.09);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);

		JPanel panel_11 = new JPanel();
		panel_11.setBorder(null);
		splitPane_1.setRightComponent(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(null);
		panel_11.add(panel_2, BorderLayout.SOUTH);

		JButton btnNewButton_5 =  new JButton("send");
		btnNewButton_5.setBackground(new Color(190, 190, 190));
		btnNewButton_5.setIcon(new ImageIcon(new URL("file:\\"+new File("").getCanonicalPath()+"\\icon\\164.png")));
		btnNewButton_5.setAlignmentY(Component.TOP_ALIGNMENT);


		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textArea.append('\n'+BC.NAME+" "+df.format(new Date())+":"+textArea_1.getText()+'\n');

				try {
					if(BC.IP.equals(InetAddress.getLocalHost().toString()) == false)
						while(Outputqueue.offer(new Info(BC.IP, BC.PORT, BC.NAME,BC.PID, BC.NO, textArea_1.getText(), true)) == false) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e1) {
								// TODO 自动生成的 catch 块
								e1.printStackTrace();
							}
						}
				} catch (UnknownHostException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

				textArea_1.setText(null);
			}
		});

		textArea_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER) {
					btnNewButton_5.doClick();
				}	
			}
		});
		panel_2.setLayout(new FlowLayout(FlowLayout.TRAILING, 0, 0));

		panel_2.add(btnNewButton_5);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(null);
		panel_11.add(scrollPane, BorderLayout.CENTER);

		textArea_1.setBorder(null);
		scrollPane.setViewportView(textArea_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		splitPane_1.setLeftComponent(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JButton btnNewButton_7 = new JButton(new ImageIcon(new URL("file:\\"+new File("").getCanonicalPath()+"\\icon\\225.png")));
		btnNewButton_7.setBackground(new Color(190, 190, 190));

		Component horizontalStrut_1 = Box.createHorizontalStrut(70);
		panel_2.add(horizontalStrut_1);
		PathDialog Dialog = new PathDialog(frame, true);

		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dialog.setModal(true);			
				Path file;
				if((file = Dialog.getState())!=null) {
					ThreadRequest.add(new Info(BC.IP, 1000, BC.NAME, 1, 1,file,true));
					Dialog.setModal(false);			

				}
			}
		});


		panel_3.add(btnNewButton_7);

		JScrollPane scrollPane_1 = new JScrollPane();              
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBorder(null);
		splitPane.setLeftComponent(scrollPane_1);


		textArea.setBorder(null);
		scrollPane_1.setViewportView(textArea);
		receivePacket = new JButton();
		receivePacket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Info value;
					Path path;
					ProgressBarDialog bar;
					while((value = InputQueue.poll()) !=null) {				
						switch(value.PID) {
						case 0: 
							//FileReceiveThread      
							//FileQueue
							int state = JOptionPane.showConfirmDialog(frame, 
									value.NAME+"\\"+value.IP+"向你发送大小为"+((FileMsg)value.MSG).FileSize+"字节文件:"+((FileMsg)value.MSG).FileName+"\n确认是否接收",
									"文件下载确认", 
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

							value.STATE = (state == 0 ? true:false);

							if(value.STATE) {
								//将PATH 添加刀value.msg
								PathDialog dialog = new PathDialog(frame, false); 
								dialog.setModal(true);
								path = dialog.getState();
								FileQueue.put(new Info(value.IP, value.PORT, value.NAME, 10, value.NO, null, value.STATE), 
										      new ProgressBarDialog(frame, 
												                    "下载进度", 
												                    value.NAME, 
												                    ((FileMsg)value.MSG).FileName, 
												                    ((FileMsg)value.MSG).FileSize, false));
								value.MSG = path;
							}					
							ReceiveThread.get(value).put(value);					
							break;
						case 1:    
							//FileTransferThread
							if(value.STATE == false) {	
								JOptionPane.showMessageDialog(frame, 
										((Path)value.MSG).toString()+"上传"+(value.STATE? "已确认":"被拒绝"), 
										"文件上传确认", 
										JOptionPane.INFORMATION_MESSAGE);	
							}
							else {		
								if(value.STATE) {
									FileQueue.put(new Info(value.IP, value.PORT, value.NAME, 11, value.NO, null, value.STATE), 
											new ProgressBarDialog(frame, 
													"上传进度", 
													value.NAME, 
													((Path)value.MSG).getFileName().toString(), 
													Files.size(((Path)value.MSG)), true));
								}											
							}		
							break;
						case 2: 
							//MsgReceiveThread
							Info info = people.get(new Info(value.IP, 2000, null, 3, 3,null, true));
							if(info == null) {
								final Info values = value;
								people.put(new Info(values.IP, 2000, null, 3, 3, null, true),new Info(values.IP, 2000, values.NAME, 3, 3,
	                            '\n'+values.NAME+" "+df.format(new Date())+":\n"+values.MSG+'\n', values.STATE));		

								JButton PButton = new PButton(value.NAME, 
										new ImageIcon(new URL("file:\\"+new File("").getCanonicalPath()+"\\icon\\177.png")),
										value.IP, value.PORT, ((Integer)value.PID).intValue());
								        PButton.setBackground(new Color(200, 200, 200));
								        PButton.addActionListener(new ActionListener() {
							    public void actionPerformed(ActionEvent e) {
//										((AbstractButton) e.getSource()).setText(people.get(new Info(values.IP, 2000, null, 3, 3, null, true)).NAME);
//
//										BC.MSG = textArea.getText();
//
//										BC = people.get(new Info(values.IP, 2000, null, 3, 3, null, true));
//										((AbstractButton) e.getSource()).setText(BC.NAME);
//										textArea.setText('\n'+values.NAME+" "+df.format(new Date())+":\n"+(String) BC.MSG+'\n');
//										lblNewLabel_1.setText(BC.NAME);
//										frame.validate();
//										
										
			
										BC.MSG = textArea.getText();
										BC = people.get(new Info(((PButton) e.getSource()).IP, 2000, null, 3, 3, null, true));
										((PButton) e.getSource()).setText(BC.NAME);
										textArea.setText((String) BC.MSG);
										lblNewLabel_1.setText(BC.NAME);
										frame.validate();

									}
								});

								Component ButtonStrut = Box.createVerticalStrut(20);
								panel.add(ButtonStrut);
								panel.add(PButton);
								frame.validate();

							}else if(info.IP.equals(BC.IP)) {		
								BC.NAME = value.NAME;
								BC.STATE = value.STATE;
								textArea.append('\n'+value.NAME+" "+df.format(new Date())+":\n"+(String) value.MSG+'\n');


							}else {
								info.MSG += '\n'+value.NAME+" "+df.format(new Date())+":\n"+(String) value.MSG+'\n';
								info.NAME = value.NAME;
								info.STATE = value.STATE;
							}		

							break;
						case 10:
							FileQueue.get(value).BardoCick(((TransferPacket)value.MSG).times, ((TransferPacket)value.MSG).speed);
							break;
						case 11:
							FileQueue.get(value).BardoCick(((TransferPacket)value.MSG).times, ((TransferPacket)value.MSG).speed);
							break;
						}
						//InputQueue.remove();
					}
				} catch (InterruptedException | IOException e1) {
					e1.printStackTrace();
				}	
			}

		});
		frame.setResizable(false);
		BC = people.get(new Info(InetAddress.getLocalHost().toString(), 2000, null, 3, 3,null, true));
		textArea.setText(df.format(new Date()));
		btnNewButton_4.doClick();
		textArea_1.setText("\r\n");
	}

	private class PButton extends JButton{
		public final String IP;
		public final int PORT;
		public final int PID;
		public PButton(String text, Icon icon, String IP, int PORT, int PID) {
			super(text, icon);
			this.IP = IP;
			this.PORT = PORT;
			this.PID = PID;
		}
	}

	private class PathDialog extends JDialog implements TreeSelectionListener, ActionListener {
		private Path state;
		private JTextField textField;
		private JButton btnNewButton_1 = new JButton("取消"),btnNewButton = new JButton("确认");
		PathDialog(JFrame owner,boolean MODE) throws IOException{
			super(owner, "文件另存为");
			
			JLabel lblNewLabel = new JLabel("Please select the installation folder");
			add(lblNewLabel);

			Component verticalStrut = Box.createVerticalStrut(20);
			add(verticalStrut);

			JLabel lblPath = new JLabel("Path:");
			add(lblPath);

			textField = new JTextField();
			add(this.textField);
			textField.setColumns(40);

			textField.addActionListener(this);

			JLabel lblDirectories = new JLabel("Directories:");
			add(lblDirectories);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

			
			
			
			
			
			
			Path start = Paths.get(FileSystemView.getFileSystemView() .getHomeDirectory().toString());
			FileVisitor visitor = MODE?(new FilesVisitor()):(new DirsVisitor());

			Files.walkFileTree(start, visitor);
			DefaultMutableTreeNode root =    MODE?(((FilesVisitor) visitor).getmapvalue(start.hashCode())):(((DirsVisitor) visitor).getmapvalue(start.hashCode()));
			JTree tree = new JTree(root);
			//			scrollPane.add(tree);

			
			tree.addTreeSelectionListener(this);

			
			
			
			
			
			
			
			
			add(scrollPane);

			scrollPane.setViewportView(tree);

			JPanel panel = new JPanel();
			panel.setAlignmentX(Component.LEFT_ALIGNMENT);
			panel.setAlignmentY(Component.TOP_ALIGNMENT);
			add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));


			Component horizontalStrut = Box.createHorizontalStrut(100);
			panel.add(horizontalStrut);


			panel.add(this.btnNewButton);
			btnNewButton.addActionListener(this);

			Component horizontalStrut_1 = Box.createHorizontalStrut(20);
			panel.add(horizontalStrut_1);


			panel.add(this.btnNewButton_1);	
			btnNewButton_1.addActionListener(this);
			setBounds(100, 100, 450, 300);
			setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setResizable(false);
		}
		public Path getState() {
			setVisible(true);
			return state;	
		}

		@Override
		public void valueChanged(TreeSelectionEvent e) {


			DefaultMutableTreeNode node = ((DefaultMutableTreeNode) ((JTree) e.getSource()).getLastSelectedPathComponent());

			this.textField.setText(((Path)node.getUserObject()).toString());
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(((JButton)e.getSource())==this.btnNewButton_1) {
				state = null;
				dispose();
			}
			if((((JButton)e.getSource())==this.btnNewButton)&&(this.textField.getText()!=null)) {
				try {
					state = Paths.get(this.textField.getText());
					this.dispose();
					//无法有效判断路径是否合法
				}catch(Exception errorPath) {
					JOptionPane.showMessageDialog(this, "为错误路径", "文件保存路径错误", JOptionPane.WARNING_MESSAGE);
				}
			}else {
				this.dispose();
			}
		}
	}
	private class DirsVisitor extends SimpleFileVisitor<Path>{

		Map<Integer, DefaultMutableTreeNode> map = new HashMap();
		public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) {
			if (Files.isDirectory(file)) {
				DefaultMutableTreeNode son = new DefaultMutableTreeNode(file);
				map.put(file.hashCode(), son);
				if(map.containsKey(file.getParent().hashCode())) {
					map.get(file.getParent().hashCode()).add(son);
				}
			}
			return FileVisitResult.CONTINUE;
		}
		public DefaultMutableTreeNode getmapvalue(int hashcode) {
			return map.get(hashcode);
		}
	}
	private class FilesVisitor extends SimpleFileVisitor<Path>{

		Map<Integer, DefaultMutableTreeNode> map = new HashMap();
		public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) {
			//if (Files.isDirectory(file)) {
			DefaultMutableTreeNode son = new DefaultMutableTreeNode(file);
			map.put(file.hashCode(), son);
			if(map.containsKey(file.getParent().hashCode())) {
				map.get(file.getParent().hashCode()).add(son);
			}
			//  }
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
		public DefaultMutableTreeNode getmapvalue(int hashcode) {
			return map.get(hashcode);
		}
	}
	private class ProgressBarDialog extends JDialog {
		private final JProgressBar progressBar;
		private final JLabel lblNewLabel_9;
		ProgressBarDialog(JFrame fream, String title, String guy, String filename, long filesize, boolean mode){
			super(fream,title);
			setBounds(100, 100, 480, 320);
			setDefaultCloseOperation(this.HIDE_ON_CLOSE);
			getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

			Component verticalGlue_1 = Box.createVerticalGlue();
			getContentPane().add(verticalGlue_1);

			JPanel panel = new JPanel();
			panel.setAlignmentY(Component.TOP_ALIGNMENT);
			panel.setAlignmentX(Component.LEFT_ALIGNMENT);
			getContentPane().add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

			Component rigidArea = Box.createRigidArea(new Dimension(20, 0));
			panel.add(rigidArea);

			JLabel lblNewLabel_5 = new JLabel((mode?"Receiver: ":"Sender: ")+guy);
			lblNewLabel_5.setAlignmentY(Component.TOP_ALIGNMENT);
			panel.add(lblNewLabel_5);

			Component horizontalGlue = Box.createHorizontalGlue();
			panel.add(horizontalGlue);

			JLabel lblNewLabel_6 = new JLabel("FileName: "+filename);
			lblNewLabel_6.setAlignmentY(Component.TOP_ALIGNMENT);
			panel.add(lblNewLabel_6);

			Component horizontalGlue_1 = Box.createHorizontalGlue();
			panel.add(horizontalGlue_1);

			JLabel lblNewLabel_7 = new JLabel("FileSize: "+filesize+"Bytes");
			lblNewLabel_7.setAlignmentY(Component.TOP_ALIGNMENT);
			panel.add(lblNewLabel_7);

			Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 0));
			panel.add(rigidArea_1);

			Component verticalStrut = Box.createVerticalStrut(10);
			getContentPane().add(verticalStrut);

			JPanel panel_1 = new JPanel();
			panel_1.setAlignmentY(Component.TOP_ALIGNMENT);
			panel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
			getContentPane().add(panel_1);
			panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

			Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 0));
			panel_1.add(rigidArea_2);

			progressBar = new JProgressBar();
			progressBar.setMaximum( (int) ((filesize>2147483646) ? filesize/1000 : filesize));
			progressBar.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					if(progressBar.getPercentComplete()==1)
						progressBar.setString("Transfer Completed!");
				}

			});
			progressBar.setStringPainted(true);
			progressBar.setAlignmentY(Component.TOP_ALIGNMENT);
			progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);
			panel_1.add(progressBar);

			Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 0));
			panel_1.add(rigidArea_3);

			JPanel panel_2 = new JPanel();
			getContentPane().add(panel_2);
			panel_2.setAlignmentY(Component.TOP_ALIGNMENT);
			panel_2.setAlignmentX(Component.LEFT_ALIGNMENT);
			panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

			Component horizontalGlue_4 = Box.createHorizontalGlue();
			panel_2.add(horizontalGlue_4);

			JLabel lblNewLabel_8 = new JLabel(mode?"UpLoard: ":"UpDownLoad: ");
			panel_2.add(lblNewLabel_8);

			lblNewLabel_9 = new JLabel();
			panel_2.add(lblNewLabel_9);

			Component horizontalGlue_5 = Box.createHorizontalGlue();
			panel_2.add(horizontalGlue_5);

			Component verticalStrut_1 = Box.createVerticalStrut(10);
			getContentPane().add(verticalStrut_1);

			JPanel panel_3 = new JPanel();
			panel_3.setAlignmentY(Component.TOP_ALIGNMENT);
			panel_3.setAlignmentX(Component.LEFT_ALIGNMENT);
			getContentPane().add(panel_3);
			panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

			Component horizontalGlue_3 = Box.createHorizontalGlue();
			panel_3.add(horizontalGlue_3);

			JButton btnNewButton = new JButton("Cancel");
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}

			});
			btnNewButton.setAlignmentY(Component.TOP_ALIGNMENT);
			panel_3.add(btnNewButton);

			Component horizontalGlue_2 = Box.createHorizontalGlue();
			panel_3.add(horizontalGlue_2);

			Component verticalGlue = Box.createVerticalGlue();
			getContentPane().add(verticalGlue);
			setVisible(true);
			setResizable(false);
		}
		public void BardoCick(int i,String label) {
			progressBar.setValue(i);
			lblNewLabel_9.setText(label);
		}
	}

}
