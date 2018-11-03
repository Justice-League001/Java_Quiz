package com.jhzhou.thread;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Dialog.ModalityType;

import javax.swing.Box;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class GUI {

	private class Attachment extends Component{
		Path file;
		Attachment(Path file){
			this.file = file;
		}
	}
	private JFrame frame;
	private JTextPane editTextPane;
	
	/*************************************************************************************************************************
	 * GUI 内部缓存
	 */
	
	private final LinkedList<Object> msgBuffer = new LinkedList<Object>();
	
	
	/***************************************************************************************************************************/
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JScrollPane editorScrollPane;
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		
		
		
		/*****************************************************************************************************************************************
		 *  列表框
		 */
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1);
		
		/****************************************************************************************************************************************/
		
		/****************************************************************************************************************************************
		 * 核心框
		 */
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setDividerSize(0);
		splitPane.setResizeWeight(0.7);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		/****************************************************************************************************************************************/
		
		/******************************************************************************************************************************************
		 *  显示框
		 */
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel_1.add(lblNewLabel);
		
		/****************************************************************************************************************************************/
		
		
		/****************************************************************************************************************************************
		 *  打印区
		 */
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		panel_1.add(scrollPane);
		
		JTextPane textPane_1 = new JTextPane();
		
		textPane_1.setEditable(false);
		
		scrollPane.setViewportView(textPane_1);
		
		/****************************************************************************************************************************************/
		
		/*****************************************************************************************************************************************
		 *   工作框
		 */

		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		/****************************************************************************************************************************************/
		
		/****************************************************************************************************************************************
		 * 功能1区
		 */
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JButton btnNewButton_2 = new JButton("New button");
		panel_4.add(btnNewButton_2);
		
		JButton fileButton = new JButton("文件");
		panel_4.add(fileButton);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_4.add(horizontalGlue);
		
		
		/*  文件选择对话框事件
		 *  
		 */
		
		fileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				JDialog Dialog = fileDialog(editTextPane);
				Dialog.setModalityType(ModalityType.APPLICATION_MODAL);   //??
			}
			
		});
		
	
		
		/****************************************************************************************************************************************/
		
		
		/****************************************************************************************************************************************
		 * 编辑区
		 */
		
		editorScrollPane = new JScrollPane();
		panel_2.add(editorScrollPane);
		
		editTextPane = new JTextPane();
		
		editorScrollPane.setViewportView(editTextPane);
		editorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		editTextPane.setCaretPosition(editTextPane.getCaretPosition()); //文本插入符位置
		editTextPane.setEditable(true);
		editTextPane.setDropTarget(new DropTarget(editTextPane,DnDConstants.ACTION_MOVE, new DropTargetListener() {
			
			/**
		     * 拖入文件或字符串,这里只说明能拖拽，并未打开文件并显示到文本区域中
		     */
			
			 /**
		     *  DropTargetDragEvent 拖曳目标拖曳事件
		     *  getCurrentDataFlavors() 返回触发事件当前数据流. 
		     *  getTransferable() 返回与当前拖动操作相关联的数据的Transferable对象
		     *  
		     *  DataFlavor 数据流
		     *  DataFlavor.javaFileListFlavor java文件列表流 DataFlavor子类.
		     *  DataFlavor.match() 比较数据流类型是否相同.
		     *  
		     *  Interface Transferable 
		     *  定义可用于传输操作提供数据的类的接口.
		     *  用于表示通过剪切,复制或粘贴到剪贴板进行交换的数据.它也用于拖放操作以表示组件拖动,
		     *  并将其拖放到组件. 
		     *  
		     *  
		     *  getTransferData() 返回要传输的数据对象,返回的对象由DataFlavor的表示类(子类?)定义. 
		     *  
		     */
		    public void dragEnter(DropTargetDragEvent dtde) {
		      //  DataFlavor[] dataFlavors = dtde.getCurrentDataFlavors();
		      //if(dataFlavors.match(DataFlavor.javaFileListFlavor))   	
		        if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
		            try { 	
		              //  Transferable tr = dtde.getTransferable();        	
		              //  Object obj = tr.getTransferData(DataFlavor.javaFileListFlavor);
		              //   List<File> files = (List<File>)obj;
		            	
		            	 List<File> files = (List<File>)((Transferable)dtde.getTransferable()).getTransferData(DataFlavor.javaFileListFlavor);
		            	 for(int i=0; i<files.size(); i++) {
		            		 StringBuilder str = new StringBuilder("<html>");
		            		 File f = files.get(i);
		            		 str.append(f.getName());
		            		 str.append("<br>");
		            		 str.append(f.length());
		            		 str.append("B");
		            		 str.append("</html>");       		 
		            		 JLabel jl = new JLabel(str.toString());
		            		 /*
		            		  * 缺少插入图片
		            		  */
		            		 jl.add(new Attachment(f.toPath()));
		            		 ((JTextPane) ((DropTarget)dtde.getSource()).getComponent()).insertComponent(jl);	            		 
		            	 }
		               
		                
		            } catch (UnsupportedFlavorException ex) {

		            } catch (IOException ex) {

		            }
		        }
		    }
		    public void dragOver(DropTargetDragEvent dtde) {}
		    public void dropActionChanged(DropTargetDragEvent dtde) {}
		    public void dragExit(DropTargetEvent dte) {}
		    public void drop(DropTargetDropEvent dtde) {}
			
		}, true));
		
		
		
		/****************************************************************************************************************************************/
		
		/****************************************************************************************************************************************
		 * 功能2区
		 */
		JPanel panel_3 = new JPanel();
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		JButton btnNewButton = new JButton("Send");
		
		panel_2.add(panel_3);
		
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		panel_3.add(horizontalGlue_1);
		panel_3.add(btnNewButton);
		
		
		// 编辑区Enter回车发送
		editTextPane.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER)  btnNewButton.doClick();
			}
			
		});	
		
		
		
		/**************************************************************
		 * 发送按钮事件
		 */
		
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*  获取编辑区scrollPane下的JTextPane的StyledDocument(source), 打印区scrollPane_1下的JTextPane的StyledDocument(target)
				 *  解析source中的元素类型,并将解析出的元素插入target
				 *  并将元素E插入msgBuffer缓冲
				 *  根据msgBuffer缓冲生成一个msg作为数据块并清空msgBuffer缓冲区
				 *  最后将msg传输至底层线程
				 */			
			
				try {
		    		StyledDocument source = ((JTextPane) editorScrollPane.getViewport().getView()).getStyledDocument();
		    		StyledDocument target = ((JTextPane) scrollPane.getViewport().getView()).getStyledDocument();
		    		Object strBuff = null;
		    		
		    		int k = 0 ;	
	    			for(int i = 0; i < source.getLength(); i++) {
	    				Element ele = source.getCharacterElement(i);
		        		if(ele.getName().equals("icon")) {
		        			
				   			target.insertString( target.getLength(), (String) (strBuff = source.getText(k, i-k)), null);
				   			msgBuffer.add(strBuff);
				   			
		        			((JTextPane) scrollPane.getViewport().getView()).insertIcon((Icon)(strBuff = StyleConstants.getIcon(ele.getAttributes())));
		        			msgBuffer.add(strBuff);
		        			
		        			k=i+1;
		        		}	
		    		}
	    			
	    			if(k < source.getLength()) {
		    			target.insertString(target.getLength(), (String) (strBuff = source.getText(k, source.getLength()-k)), null); 
		    			msgBuffer.add(strBuff);
	    			}
	    			source.remove(0, source.getLength());
				} catch (BadLocationException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				
    			
    			/*
    			 *  List<Object> msg = new ArrayList<Object>(msgBuffer.size());
    			 *  while(msg.add(msgBuffer.poll())) ;
    			 *  发送代码未编辑
    			 */
    			
    			
			}
			
		});
		
		/***************************************************************/
		/****************************************************************************************************************************************/
	}
	private JSplitPane coreFramework() {
		
		JSplitPane splitPane = new JSplitPane();
		
		splitPane.setBorder(null);
		splitPane.setDividerSize(0);
		splitPane.setResizeWeight(0.7);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		return splitPane;
	}
	
	private JPanel listBox() {
		
		JPanel panel = new JPanel();
		JLabel lblNewLabel_1 = new JLabel("New label");
		
		panel.add(lblNewLabel_1);
		panel.setBorder(null);
		
		return panel;
	}
	
	private JPanel displayFrame() {
		JPanel panel = new JPanel();
		JLabel lblNewLabel = new JLabel("New label");
		
		panel.setBorder(null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(lblNewLabel);
		
		return panel;
	}
	
	private JPanel workFrame() {
		JPanel panel = new JPanel();
		
		panel.setBorder(null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		return panel;
	}
	
	private JScrollPane printArea() {
		
		JScrollPane scrollPane = new JScrollPane();
		JTextPane textPane = setJTextPane();	
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(null);
		textPane.setBorder(null);
		textPane.setEditable(false);	
		scrollPane.setViewportView(textPane);
		
		return scrollPane;
	}

	private JPanel functionArea1() {
		
		JPanel panel = new JPanel();
		JButton btnNewButton_1 = new JButton("New button");
		JButton btnNewButton_2 = new JButton("New button");
		Component horizontalGlue = Box.createHorizontalGlue();
		
		panel.setBorder(null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));	
		panel.add(btnNewButton_1);	
		panel.add(btnNewButton_2);	
		panel.add(horizontalGlue);
		
		return panel;
	}
	
	private JPanel functionArea2() {
		JPanel panel = new JPanel();
		JButton btnNewButton = new JButton("New button");
		JButton btnNewButton_1 = new JButton("New button");
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		
		panel.setBorder(null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(horizontalGlue_1);
		panel.add(btnNewButton);
		panel.add(btnNewButton_1);
		
		return panel;
	}
	
	private JScrollPane editorArea() {

		
		JScrollPane scrollPane = new JScrollPane();	
		JTextPane textPane = setJTextPane();
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(null);
		textPane.setBorder(null);
		textPane.setEditable(true);
        textPane.setDropTarget(new DropTarget(textPane,DnDConstants.ACTION_MOVE, new DropTargetListener() {
			
			/**
		     * 拖入文件或字符串,这里只说明能拖拽，并未打开文件并显示到文本区域中
		     */
			
			 /**
		     *  DropTargetDragEvent 拖曳目标拖曳事件
		     *  getCurrentDataFlavors() 返回触发事件当前数据流. 
		     *  getTransferable() 返回与当前拖动操作相关联的数据的Transferable对象
		     *  
		     *  DataFlavor 数据流
		     *  DataFlavor.javaFileListFlavor java文件列表流 DataFlavor子类.
		     *  DataFlavor.match() 比较数据流类型是否相同.
		     *  
		     *  Interface Transferable 
		     *  定义可用于传输操作提供数据的类的接口.
		     *  用于表示通过剪切,复制或粘贴到剪贴板进行交换的数据.它也用于拖放操作以表示组件拖动,
		     *  并将其拖放到组件. 
		     *  
		     *  
		     *  getTransferData() 返回要传输的数据对象,返回的对象由DataFlavor的表示类(子类?)定义. 
		     *  
		     */
		    public void dragEnter(DropTargetDragEvent dtde) {
		      //  DataFlavor[] dataFlavors = dtde.getCurrentDataFlavors();
		      //if(dataFlavors.match(DataFlavor.javaFileListFlavor))   	
		        if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
		            try { 	
		              //  Transferable tr = dtde.getTransferable();        	
		              //  Object obj = tr.getTransferData(DataFlavor.javaFileListFlavor);
		              //   List<File> files = (List<File>)obj;
		            	
		            	 List<File> files = (List<File>)((Transferable)dtde.getTransferable())
		            			               .getTransferData(DataFlavor.javaFileListFlavor);
		            	    
		                /*
		                 *  数据待处理
		                 */
		                
		            } catch (UnsupportedFlavorException ex) {

		            } catch (IOException ex) {

		            }
		        }
		    }
		    public void dragOver(DropTargetDragEvent dtde) {}
		    public void dropActionChanged(DropTargetDragEvent dtde) {}
		    public void dragExit(DropTargetEvent dte) {}
		    public void drop(DropTargetDropEvent dtde) {}
			
		}, true));
			
		scrollPane.setViewportView(textPane);
		
		return scrollPane;
	}
	private JTextPane setJTextPane() {
    	/** 
         * 该类是真正实现超长单词都能自动换行的 JTextPane 的子类 
         * Java 7 以下版本的 JTextPane 本身都能实现自动换行，对 
         * 超长单词都能有效，但从 Java 7 开始读超长单词就不能自动 
         * 换行，导致 JTextPane 的实际宽度变大，使得滚动条出现。 
         * 下面的方法是对这个 bug 的较好修复。 
         * 
         * Created by dolphin on 15-2-3. 
         * 以下内部类全都用于实现自动强制折行 
         */  	
    	
    	class WarpLabelView extends LabelView {  
  	      
            public WarpLabelView(Element elem) {  
                super(elem);  
            }  
      
            @Override  
            public float getMinimumSpan(int axis) {  
                switch (axis) {  
                    case View.X_AXIS:  
                        return 0;  
                    case View.Y_AXIS:  
                        return super.getMinimumSpan(axis);  
                    default:  
                        throw new IllegalArgumentException("Invalid axis: " + axis);  
                }  
            }  
        } 
    	
    	
    	/*  ViewFactory一个工厂创建一部分文件主题的视图.这是为了能够自定义视图如何映射到文档模型.
         * 
         */
    	class WarpColumnFactory implements ViewFactory {  
    	      
            public View create(Element elem) {  
                String kind = elem.getName();  
                if (kind != null) {  
                    if (kind.equals(AbstractDocument.ContentElementName)) {  
                        return new WarpLabelView(elem);
                        //标签视图
                    } else if (kind.equals(AbstractDocument.ParagraphElementName)) {  
                        return new ParagraphView(elem);  
                        //段落试图
                    } else if (kind.equals(AbstractDocument.SectionElementName)) {  
                        return new BoxView(elem, View.Y_AXIS); 
                        //盒子视图
                    } else if (kind.equals(StyleConstants.ComponentElementName)) {  
                        return new ComponentView(elem);  
                        //组件视图
                    } else if (kind.equals(StyleConstants.IconElementName)) {  
                        return new IconView(elem);  
                        //图片视图
                    }  
                }  
      
                // default to text display  
                return new LabelView(elem);  
            }  
        }
    	
    	/*  StyledEditorKit 此实现提供了将文本视为样式文本的默认实现,并提供了样式文本的最小操作集.
    	 *  这是文本组件所需的一组事物,作为某种类型的文本文档的合理功能的编辑器.
    	 */

    	class WarpEditorKit extends StyledEditorKit {  
    	      
             private ViewFactory defaultFactory = new WarpColumnFactory();  
       
             @Override  
             public ViewFactory getViewFactory() {  
                 return defaultFactory;  
             }  
         }
    	
    	JTextPane TextPane = new JTextPane();
    	TextPane.setEditorKit(new WarpEditorKit());
    	return TextPane;
    	
    }
	
	private JDialog fileDialog(JTextPane pane) {
		JDialog dialog = new JDialog(frame);
		dialog.setBounds(100, 100, 400, 350);

		
		
//		setBounds(100, 100, 450, 300);
//		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		dialog.setDefaultCloseOperation(dialog.DISPOSE_ON_CLOSE);
		dialog.setResizable(false);
		
		
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
		dialog.getContentPane().add(inputPanel, BorderLayout.NORTH);
		
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
		dialog.getContentPane().add(nodePanel, BorderLayout.CENTER);
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
		dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
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
				try {
		    		if(Files.exists(p = Paths.get(pathField.getText()))) {
		     			 StringBuilder str = new StringBuilder("<html>");
                		 str.append(p.getFileName());
                 		 str.append("<br>");
		         		 str.append(Files.size(p));
                		 str.append("B");
                		 str.append("</html>");       		 
                		 JLabel jl = new JLabel(str.toString());
                		 /*
                		  * 缺少插入图片
                		  */
                		 jl.add(new Attachment(p));
		    		     pane.insertComponent(jl);
		    		}
					
		    		dialog.setModalityType(ModalityType.MODELESS);
	    			dialog.dispose();
		    		
	     		} catch (IOException e1) {
	    			e1.printStackTrace();
	    		}
			}
			
		});
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
			
		});
		/*********************************************************************/
		
		/********************************************************************************************************************************/
		
		dialog.setVisible(true);
		
		return dialog;
	}
	
	private JDialog transferProgressBar(boolean mode,String guy, String file, long size) {
		
		JDialog transferDialog = new JDialog(frame, mode? "Download File":"Upload File");
		
		transferDialog.setBounds(100, 100, 380, 220);
		transferDialog.setResizable(false);
		transferDialog.getContentPane().setLayout(new BoxLayout(transferDialog.getContentPane(), BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(15);
		transferDialog.getContentPane().add(verticalStrut);
		
		JPanel panel = new JPanel();
		transferDialog.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		StringBuilder str = new StringBuilder("<html>");
		 str.append(mode?"Sender:":"Receiver:");
		 str.append(guy);
		 str.append("<br>");
		 str.append("FileName:");
		 str.append(file);
		 str.append("<br>");
		 str.append("FileSize:");
		 str.append(size);
		 str.append('B');
		 str.append("</html>");       		 

		JLabel fileMsgLabel = new JLabel(str.toString());
		panel.add(fileMsgLabel);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		
		Component verticalStrut_1 = Box.createVerticalStrut(35);
		transferDialog.getContentPane().add(verticalStrut_1);
		
		JPanel panel_1 = new JPanel();
		transferDialog.getContentPane().add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panel_1.add(rigidArea);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		panel_1.add(progressBar);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_1.add(rigidArea_1);
		
		JPanel panel_2 = new JPanel();
		transferDialog.getContentPane().add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue_1);
		
		JLabel progressLabel = new JLabel("0KB/s");
		panel_2.add(progressLabel);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue_2);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		transferDialog.getContentPane().add(verticalGlue_1);
		transferDialog.setDefaultCloseOperation(transferDialog.HIDE_ON_CLOSE);		
		
		transferDialog.setVisible(true);
		
		return transferDialog;
	}
}
