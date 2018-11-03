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
	 * GUI �ڲ�����
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
		 *  �б��
		 */
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1);
		
		/****************************************************************************************************************************************/
		
		/****************************************************************************************************************************************
		 * ���Ŀ�
		 */
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setDividerSize(0);
		splitPane.setResizeWeight(0.7);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		/****************************************************************************************************************************************/
		
		/******************************************************************************************************************************************
		 *  ��ʾ��
		 */
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel_1.add(lblNewLabel);
		
		/****************************************************************************************************************************************/
		
		
		/****************************************************************************************************************************************
		 *  ��ӡ��
		 */
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		panel_1.add(scrollPane);
		
		JTextPane textPane_1 = new JTextPane();
		
		textPane_1.setEditable(false);
		
		scrollPane.setViewportView(textPane_1);
		
		/****************************************************************************************************************************************/
		
		/*****************************************************************************************************************************************
		 *   ������
		 */

		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		/****************************************************************************************************************************************/
		
		/****************************************************************************************************************************************
		 * ����1��
		 */
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JButton btnNewButton_2 = new JButton("New button");
		panel_4.add(btnNewButton_2);
		
		JButton fileButton = new JButton("�ļ�");
		panel_4.add(fileButton);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_4.add(horizontalGlue);
		
		
		/*  �ļ�ѡ��Ի����¼�
		 *  
		 */
		
		fileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				JDialog Dialog = fileDialog(editTextPane);
				Dialog.setModalityType(ModalityType.APPLICATION_MODAL);   //??
			}
			
		});
		
	
		
		/****************************************************************************************************************************************/
		
		
		/****************************************************************************************************************************************
		 * �༭��
		 */
		
		editorScrollPane = new JScrollPane();
		panel_2.add(editorScrollPane);
		
		editTextPane = new JTextPane();
		
		editorScrollPane.setViewportView(editTextPane);
		editorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		editTextPane.setCaretPosition(editTextPane.getCaretPosition()); //�ı������λ��
		editTextPane.setEditable(true);
		editTextPane.setDropTarget(new DropTarget(editTextPane,DnDConstants.ACTION_MOVE, new DropTargetListener() {
			
			/**
		     * �����ļ����ַ���,����ֻ˵������ק����δ���ļ�����ʾ���ı�������
		     */
			
			 /**
		     *  DropTargetDragEvent ��ҷĿ����ҷ�¼�
		     *  getCurrentDataFlavors() ���ش����¼���ǰ������. 
		     *  getTransferable() �����뵱ǰ�϶���������������ݵ�Transferable����
		     *  
		     *  DataFlavor ������
		     *  DataFlavor.javaFileListFlavor java�ļ��б��� DataFlavor����.
		     *  DataFlavor.match() �Ƚ������������Ƿ���ͬ.
		     *  
		     *  Interface Transferable 
		     *  ��������ڴ�������ṩ���ݵ���Ľӿ�.
		     *  ���ڱ�ʾͨ������,���ƻ�ճ������������н���������.��Ҳ�����ϷŲ����Ա�ʾ����϶�,
		     *  �������Ϸŵ����. 
		     *  
		     *  
		     *  getTransferData() ����Ҫ��������ݶ���,���صĶ�����DataFlavor�ı�ʾ��(����?)����. 
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
		            		  * ȱ�ٲ���ͼƬ
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
		 * ����2��
		 */
		JPanel panel_3 = new JPanel();
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		JButton btnNewButton = new JButton("Send");
		
		panel_2.add(panel_3);
		
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		panel_3.add(horizontalGlue_1);
		panel_3.add(btnNewButton);
		
		
		// �༭��Enter�س�����
		editTextPane.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER)  btnNewButton.doClick();
			}
			
		});	
		
		
		
		/**************************************************************
		 * ���Ͱ�ť�¼�
		 */
		
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*  ��ȡ�༭��scrollPane�µ�JTextPane��StyledDocument(source), ��ӡ��scrollPane_1�µ�JTextPane��StyledDocument(target)
				 *  ����source�е�Ԫ������,������������Ԫ�ز���target
				 *  ����Ԫ��E����msgBuffer����
				 *  ����msgBuffer��������һ��msg��Ϊ���ݿ鲢���msgBuffer������
				 *  ���msg�������ײ��߳�
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
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				
    			
    			/*
    			 *  List<Object> msg = new ArrayList<Object>(msgBuffer.size());
    			 *  while(msg.add(msgBuffer.poll())) ;
    			 *  ���ʹ���δ�༭
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
		     * �����ļ����ַ���,����ֻ˵������ק����δ���ļ�����ʾ���ı�������
		     */
			
			 /**
		     *  DropTargetDragEvent ��ҷĿ����ҷ�¼�
		     *  getCurrentDataFlavors() ���ش����¼���ǰ������. 
		     *  getTransferable() �����뵱ǰ�϶���������������ݵ�Transferable����
		     *  
		     *  DataFlavor ������
		     *  DataFlavor.javaFileListFlavor java�ļ��б��� DataFlavor����.
		     *  DataFlavor.match() �Ƚ������������Ƿ���ͬ.
		     *  
		     *  Interface Transferable 
		     *  ��������ڴ�������ṩ���ݵ���Ľӿ�.
		     *  ���ڱ�ʾͨ������,���ƻ�ճ������������н���������.��Ҳ�����ϷŲ����Ա�ʾ����϶�,
		     *  �������Ϸŵ����. 
		     *  
		     *  
		     *  getTransferData() ����Ҫ��������ݶ���,���صĶ�����DataFlavor�ı�ʾ��(����?)����. 
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
		                 *  ���ݴ�����
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
         * ����������ʵ�ֳ������ʶ����Զ����е� JTextPane ������ 
         * Java 7 ���°汾�� JTextPane ������ʵ���Զ����У��� 
         * �������ʶ�����Ч������ Java 7 ��ʼ���������ʾͲ����Զ� 
         * ���У����� JTextPane ��ʵ�ʿ�ȱ��ʹ�ù��������֡� 
         * ����ķ����Ƕ���� bug �ĽϺ��޸��� 
         * 
         * Created by dolphin on 15-2-3. 
         * �����ڲ���ȫ������ʵ���Զ�ǿ������ 
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
    	
    	
    	/*  ViewFactoryһ����������һ�����ļ��������ͼ.����Ϊ���ܹ��Զ�����ͼ���ӳ�䵽�ĵ�ģ��.
         * 
         */
    	class WarpColumnFactory implements ViewFactory {  
    	      
            public View create(Element elem) {  
                String kind = elem.getName();  
                if (kind != null) {  
                    if (kind.equals(AbstractDocument.ContentElementName)) {  
                        return new WarpLabelView(elem);
                        //��ǩ��ͼ
                    } else if (kind.equals(AbstractDocument.ParagraphElementName)) {  
                        return new ParagraphView(elem);  
                        //������ͼ
                    } else if (kind.equals(AbstractDocument.SectionElementName)) {  
                        return new BoxView(elem, View.Y_AXIS); 
                        //������ͼ
                    } else if (kind.equals(StyleConstants.ComponentElementName)) {  
                        return new ComponentView(elem);  
                        //�����ͼ
                    } else if (kind.equals(StyleConstants.IconElementName)) {  
                        return new IconView(elem);  
                        //ͼƬ��ͼ
                    }  
                }  
      
                // default to text display  
                return new LabelView(elem);  
            }  
        }
    	
    	/*  StyledEditorKit ��ʵ���ṩ�˽��ı���Ϊ��ʽ�ı���Ĭ��ʵ��,���ṩ����ʽ�ı�����С������.
    	 *  �����ı���������һ������,��Ϊĳ�����͵��ı��ĵ��ĺ����ܵı༭��.
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
		 *   ·����
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
		 *  �ڵ�ѡ����
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
		dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
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
                		  * ȱ�ٲ���ͼƬ
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
