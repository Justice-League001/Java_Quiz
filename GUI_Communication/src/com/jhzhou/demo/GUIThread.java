package com.jhzhou.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.jhzhou.util.ConnectionStream;
import com.jhzhou.util.FileMsg;
import com.jhzhou.util.Info;




public class GUIThread {

	private JFrame frame;
	private JButton receivePacket;
	private JTextArea textArea;
	private JLabel lblNewLabel;
	private Info BC = new Info("0",0,"0",3,null,true);
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	
	
	private final BlockingQueue<Info> Outputqueue;
	private final Queue<Info> InputQueue;
	private final Map<Info, SynchronousQueue<Info>> ReceiveThread;
	Exchanger<Info> exchanger ;
	private final Map<Info, Info> people;
	private final Queue<Info> ThreadRequest;
	
	private int xx, yy;
	private boolean isDraging = false;
	
	
	
	
    private class Mydialog extends JDialog implements TreeSelectionListener, ActionListener {
	    private Path state;
		private JTextField textField;
		private JButton btnNewButton_1 = new JButton("取消"),btnNewButton = new JButton("确认");
		Mydialog(JFrame owner) throws IOException{
			super(owner, "文件另存为");
			frame = owner;
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
			
			Path start = Paths.get("C:/Users/asus/Desktop");
			FileVisitor visitor = new Visitor();
			Files.walkFileTree(start, visitor);
			DefaultMutableTreeNode root = ((Visitor) visitor).getmapvalue(start.hashCode());
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
//	private class Foldername{
//		public final Path file;
//		Foldername(Path file){
//			this.file = file;
//		}
//		public String toString() {
//			return file.getName(file.getNameCount()-1).toString();
//		}
//		
//	}
	private class Visitor extends SimpleFileVisitor<Path>{

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
	
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUIThread window = new GUIThread();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		System.out.println("100");
//	}

	/**
	 * Create the application.
	 */
	public GUIThread(BlockingQueue<Info> Outputqueue, Queue<Info> InputQueue, Map<Info, SynchronousQueue<Info>> ThreadMap, Queue<Info> ThreadRequest) {
		this.Outputqueue = Outputqueue;
		this.InputQueue = InputQueue;
		this.ReceiveThread = ThreadMap;
		this.ThreadRequest = ThreadRequest;
		people = new HashMap<Info, Info>();
	}
//	public GUIThread() {
//		
//		//frame.setVisible(true);
//		initialize();
//		
//	}

	/**
	 * Initialize the contents of the frame.
	 * @throws UnknownHostException 
	 */
	public void initialize() throws UnknownHostException {
		frame = new JFrame();
		frame.setUndecorated(true);		
		frame.setOpacity(0.76f);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBounds(448, 156, 1280, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setShape(new RoundRectangle2D.Double(0.0, 0.0, frame.getWidth()+1, frame.getHeight()+1, 26.0, 26.0));	
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(221, 220, 221));
		panel.setBorder(null);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
			
		
		//Frame
		JPanel panel_7 = new JPanel();
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		Component verticalStrut_1 = Box.createVerticalStrut(60);
		
		panel_7.setBackground(new Color(221, 220, 221));
		panel.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));	
		panel_7.add(horizontalGlue_2);
		panel_7.add(verticalStrut_1);

		JButton ButtonMinimize = new JButton();
		JButton ButtonClose = new JButton();
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		

		ButtonMinimize.setBackground(new Color(221, 220, 221));
		ButtonMinimize.setBorder(null);
		ButtonMinimize.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\minimize.png"));	

		ButtonClose.setBackground(new Color(221, 220, 221));
		ButtonClose.setBorder(null);
		ButtonClose.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\close1.png"));
		

		panel_7.add(ButtonMinimize);
		panel_7.add(horizontalStrut_5);
		panel_7.add(ButtonClose);
		panel_7.add(horizontalStrut_6);
					

		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});
		ButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		
		
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(221, 220, 221));
		panel.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(25);
		panel_5.add(verticalStrut);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(192, 192, 193));
		panel.add(panel_6);
		
		lblNewLabel = new JLabel();
		panel_6.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(53, 64, 80));
		panel_1.setBorder(null);
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(new Color(53, 64, 80));
		panel_8.setBorder(null);
		panel_1.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBackground(new Color(190, 190, 190));
		panel_8.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.X_AXIS));
		//Component verticalStrut_3 = Box.createVerticalStrut(20);
		//panel_8.add(verticalStrut_3);
		JButton btnNewButton_2 = new JButton();
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_11.add(btnNewButton_2);
		btnNewButton_2.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\180.png"));
		btnNewButton_2.setBackground(new Color(200, 200, 200));
		
		JLabel lblNewLabel_3 = new JLabel("添加成员XX");
		panel_11.add(lblNewLabel_3);
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String IP = JOptionPane.showInputDialog(frame, "目标IP地址:", "创建连接", JOptionPane.PLAIN_MESSAGE);

				if(IP!=null) {
					people.put(new Info(IP, 2000, null, 3, null, true),new Info(IP, 2000, IP, 3, "", true));
	
				    JPanel ButtonPanel = new JPanel();
				    ButtonPanel.setBackground(new Color(190, 190, 190));
				    JLabel ButtonLabel = new JLabel(IP+":2000");
				    ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.X_AXIS));
			     	JButton PButton = new JButton();
			     	PButton.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\177.png"));
			     	PButton.setBackground(new Color(200, 200, 200));
			     	PButton.addActionListener(new ActionListener() {
			     		public void actionPerformed(ActionEvent e) {
			     			ButtonLabel.setText(people.get(new Info(IP, 2000, null, 3, null, true)).NAME);

					     	BC.MSG = textArea.getText();
						
				     		BC = people.get(new Info(IP, 2000, null, 3, null, true));
						
				    		textArea.setText((String) BC.MSG);
					    	lblNewLabel.setText(BC.NAME);
				    		frame.validate();
				    	}
			    	});
				
			     	 ButtonPanel.add(PButton);
			     	 ButtonPanel.add(ButtonLabel);		
				
			      addMenber(panel_8, ButtonPanel);
			      Component Strut = Box.createVerticalStrut(20);
			      panel_8.add(Strut);
			      }
				}
			});
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel_8.add(verticalStrut_3);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(190, 190, 190));
		panel_8.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		
		
		
		
		//Menber
		JButton btnNewButton = new JButton();
		panel_9.add(btnNewButton);
		btnNewButton.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\182.png"));
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton.setVerticalAlignment(SwingConstants.CENTER);
		btnNewButton.setBackground(new Color(200, 200, 200));
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		
		JLabel lblNewLabel_1 = new JLabel("公共区额XX");
		panel_9.add(lblNewLabel_1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setText(lblNewLabel_1.getText());
			}
		});
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel_8.add(verticalStrut_2);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(new Color(190, 190, 190));
		panel_8.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.X_AXIS));
		JButton btnNewButton_1 = new JButton();
		
		people.put(new Info(InetAddress.getLocalHost().toString(), 2000, null, 3, null, true),new Info(InetAddress.getLocalHost().toString(), 
				                                                                                       2000, 
				                                                                                       InetAddress.getLocalHost().getHostName(), 3, "", true));
		
	
		
		panel_10.add(btnNewButton_1);
		btnNewButton_1.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\177.png"));
		btnNewButton_1.setBackground(new Color(200, 200, 200));
		
		JLabel lblNewLabel_2 = new JLabel(people.get(new Info(InetAddress.getLocalHost().toString(), 2000, null, 3, null, true)).NAME);
		panel_10.add(lblNewLabel_2);
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		panel_8.add(verticalStrut_4);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setText(lblNewLabel_2.getText());
				BC.MSG = textArea.getText();
				
				try {
					BC = people.get(new Info(InetAddress.getLocalHost().toString(), 2000, null, 3, null, true));
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				textArea.setText((String) BC.MSG);
				frame.validate();
			
			}
		});
		
		
		
		
		Component horizontalStrut = Box.createHorizontalStrut(250);
		panel_1.add(horizontalStrut);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(1);
		splitPane.setResizeWeight(0.7);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		splitPane.setBorder(null);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		splitPane.setLeftComponent(scrollPane);
		
		
		textArea = new JTextArea();
		textArea.setBackground(new Color(240, 240, 240));
		scrollPane.setRowHeaderView(textArea);
		
		
		
		JPanel panel_3 = new JPanel();
		splitPane.setRightComponent(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		panel_3.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		
		textArea_1.setBackground(new Color(240, 240, 240));
		scrollPane_1.setViewportView(textArea_1);
		
		
		
		//Function
		JPanel panel_4 = new JPanel();
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);

		panel_4.setBackground(new Color(240, 240, 240));
		scrollPane_1.setColumnHeaderView(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		panel_4.add(horizontalStrut_7);

		JButton btnNewButton_3 = new JButton();
		JButton btnNewButton_4 = new JButton();
		JButton btnNewButton_5 = new JButton();
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Mydialog dialog = new Mydialog(frame);
					dialog.setModal(true);
					
					Path file;
					if((file = dialog.getState())!=null) {
						
						ThreadRequest.add(new Info(BC.IP, 2000, null, 1, file,true));
						
					}
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		
		btnNewButton_3.setBorder(null);
		btnNewButton_3.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\288.png"));
		btnNewButton_4.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\163.png"));
		btnNewButton_4.setBorder(null);
		btnNewButton_5.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\225.png"));
		btnNewButton_5.setBorder(null);
	
		panel_4.add(btnNewButton_3);
		panel_4.add(horizontalStrut_3);
		panel_4.add(btnNewButton_4);
		panel_4.add(horizontalStrut_2);
		panel_4.add(btnNewButton_5);
		panel_4.add(horizontalStrut_1);
		
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(240, 240, 240));
		panel_3.add(panel_2);
		panel_2.setBorder(null);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue);
		
		JButton btnNewButton_6 = new JButton("send");
		
		btnNewButton_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnNewButton_6);
		
		Component rigidArea = Box.createRigidArea(new Dimension(30, 0));
		panel_2.add(rigidArea);
		
		
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				textArea.append('\n'+BC.NAME+" "+df.format(new Date())+":"+textArea_1.getText()+'\n');
				
				
				while(Outputqueue.offer(new Info(BC.IP, BC.PORT, BC.NAME, BC.PID, textArea_1.getText(), true)) == false) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
				
				textArea_1.setText(null);
			}
		});
		
		textArea_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER) {
					btnNewButton_6.doClick();
				}	
			}
		});
		
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				isDraging = true;
				xx = e.getX();
				yy = e.getY();
			}
			public void mouseReleased(MouseEvent e) {
				isDraging = false;
			}
		});
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isDraging) {
					int left = frame.getLocation().x;
					int top = frame.getLocation().y;
					frame.setLocation(left + e.getX() - xx, top + e.getY() - yy);
				}
			}
		});
		
		 receivePacket = new JButton();
		 receivePacket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				Info value;
				while((value = InputQueue.poll()) !=null) {
					switch(value.PID) {
					case 0:                             
						//FileReceiveThread                     
						int state = JOptionPane.showConfirmDialog(frame, 
								value.NAME+"\\"+value.IP+"向你发送大小为"+((FileMsg)value.MSG).FileSize+"字节文件:"+((FileMsg)value.MSG).FileName+"\n确认是否接收",
								"文件下载确认", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						value.STATE = (state == 0 ? true:false);
						ReceiveThread.get(value).put(value);		
						break;
					case 1:                                           
						//FileTransferThread
						JOptionPane.showMessageDialog(frame, 
								                      ((Path)value.MSG).toString()+"上传"+(value.STATE? "已确认":"被拒绝"), 
								                      "文件上传确认", 
								                      JOptionPane.INFORMATION_MESSAGE);
						break;
					case 2:
						//MsgReceiveThread
						Info info = people.get(value);
						if(info == null) {
							final Info values = value;
							people.put(new Info(values.IP, 2000, null, 3, null, true),new Info(values.IP, 2000, values.NAME, 3, values.MSG, values.STATE));
							
						    JPanel ButtonPanel = new JPanel();
						    ButtonPanel.setBackground(new Color(190, 190, 190));
						    JLabel ButtonLabel = new JLabel(values.NAME);
						    ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.X_AXIS));
					     	JButton PButton = new JButton();
					     	PButton.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\177.png"));
					     	PButton.setBackground(new Color(200, 200, 200));
					     	PButton.addActionListener(new ActionListener() {
					     		public void actionPerformed(ActionEvent e) {
					     			ButtonLabel.setText(people.get(new Info(values.IP, 2000, null, 3, null, true)).NAME);

							     	BC.MSG = textArea.getText();
								
						     		BC = people.get(new Info(values.IP, 2000, null, 3, null, true));
								
						    		textArea.setText((String) BC.MSG);
							    	lblNewLabel.setText(BC.NAME);
						    		frame.validate();
						    	}
					    	});
						
					     	ButtonPanel.add(PButton);
					     	ButtonPanel.add(ButtonLabel);		
						
					      addMenber(panel_8, ButtonPanel);
					      Component ButtonStrut = Box.createVerticalStrut(20);
					      panel_8.add(ButtonStrut);
					      
						}else if(info.IP.equals(BC.IP)) {
							BC.NAME = info.NAME;
							BC.STATE = info.STATE;
							textArea.append('\n'+value.NAME+" "+df.format(new Date())+":"+(String) value.MSG+'\n');
							
						}else {
							Info guy = people.get(info);
							guy.MSG += '\n'+value.NAME+" "+df.format(new Date())+":"+(String) value.MSG+'\n';
							guy.NAME = info.NAME;
							guy.STATE = info.STATE;
						}
						
						
						break;
					case 10:
						JOptionPane.showMessageDialog(frame, value.MSG, "文件上传速度", JOptionPane.INFORMATION_MESSAGE);
						break;
					case 11:
						JOptionPane.showMessageDialog(frame, value.MSG, "文件下载 速度", JOptionPane.INFORMATION_MESSAGE);
						break;
						}
					InputQueue.remove();
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}	
			}
			 
		 });
		 
		 BC = people.get(new Info(InetAddress.getLocalHost().toString(), 2000, null, 3, null, true));
		 textArea.setText(df.format(new Date()));
		 btnNewButton_1.doClick();
		 textArea_1.setText("\r\n");
		 btnNewButton_6.doClick();
		 
	}
	
	void addMenber(Container box, Container menber) {	    
		box.add(menber);
		box.revalidate();
        box.repaint();
	}
	public void packetProcesse() throws InterruptedException {
		receivePacket.doClick();
	}

}