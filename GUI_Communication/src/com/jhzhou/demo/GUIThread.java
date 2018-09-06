package com.jhzhou.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class GUIThread {

	private JFrame frame;
	private int xx, yy;
	private boolean isDraging = false;
	JLabel lblNewLabel = new JLabel("New label");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIThread window = new GUIThread();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("100");
	}

	/**
	 * Create the application.
	 */
	public GUIThread() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setUndecorated(true);		
		frame.setOpacity(0.76f);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBounds(448, 156, 1280, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setShape(new RoundRectangle2D.Double(0.0, 0.0, frame.getWidth()+1, frame.getHeight()+1, 26.0, 26.0));	
		
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
				
				JPanel xx = new JPanel();
				xx.setBackground(new Color(190, 190, 190));
				JLabel xxxx = new JLabel("192.168.1.195XXX");
				xx.setLayout(new BoxLayout(xx, BoxLayout.X_AXIS));
				JButton xxx = new JButton();
				xxx.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\177.png"));
				xxx.setBackground(new Color(200, 200, 200));
				xxx.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						lblNewLabel.setText(xxxx.getText());
					}
				});
				
				xx.add(xxx);
				xx.add(xxxx);		
				
				addMenber(panel_8, xx);
				
				Component xxxxx = Box.createVerticalStrut(20);
				panel_8.add(xxxxx);
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
		panel_10.add(btnNewButton_1);
		btnNewButton_1.setIcon(new ImageIcon("E:\\repo\\pro\\Java_Quiz\\GUI_Communication\\icon\\177.png"));
		btnNewButton_1.setBackground(new Color(200, 200, 200));
		
		JLabel lblNewLabel_2 = new JLabel("DESKTOP-FGHV4AU");
		panel_10.add(lblNewLabel_2);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		panel_8.add(verticalStrut_4);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setText(lblNewLabel_2.getText());
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
		
		
		JTextArea textArea = new JTextArea();
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
		
				textArea.append(textArea_1.getText());
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
	}
	
	void addMenber(Container box, Container menber) {
	
		    
		box.add(menber);
		//box.add(Box.createHorizontalStrut(20));
		box.revalidate();
        box.repaint();
	}

}


class JCircleButton extends JButton{
	  public JCircleButton() {
	       
	        // 获取按钮的最佳大小
	        Dimension size = getPreferredSize();
//	        size.width = size.height = Math.max(size.width, size.height);
	        size.width = size.height = 128;
	        setPreferredSize(size);
	       
	        setContentAreaFilled(false);
	        this.setBorderPainted(false); // 不绘制边框
	        this.setFocusPainted(false); // 不绘制焦点状态
	    }
	 
	    // 画圆的按钮的背景和标签
	    protected void paintComponent(Graphics g) {
	 
	        if (getModel().isArmed()) {
	            g.setColor(Color.lightGray); // 点击时高亮
	        } else {
	            g.setColor(getBackground());
	        }
	        // fillOval方法画一个矩形的内切椭圆，并且填充这个椭圆，
	        // 当矩形为正方形时，画出的椭圆便是圆
	        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
	 
	        super.paintComponent(g);
	    }
	 
	    // 用简单的弧画按钮的边界。
	    protected void paintBorder(Graphics g) {
	        g.setColor(Color.white);
	        // drawOval方法画矩形的内切椭圆，但不填充。只画出一个边界
	        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	    }
	 
	    // shape对象用于保存按钮的形状，有助于侦听点击按钮事件
	    Shape shape;
	 
	    public boolean contains(int x, int y) {
	 
	        if ((shape == null) || (!shape.getBounds().equals(getBounds()))) {
	            // 构造一个椭圆形对象
	            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
	        }
	        // 判断鼠标的x、y坐标是否落在按钮形状内。
	        return shape.contains(x, y);
	    }
}