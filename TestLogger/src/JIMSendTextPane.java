    import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;  
    import javax.swing.text.AbstractDocument;  
    import javax.swing.text.BoxView;  
    import javax.swing.text.ComponentView;  
    import javax.swing.text.Element;  
    import javax.swing.text.IconView;  
    import javax.swing.text.LabelView;  
    import javax.swing.text.ParagraphView;  
    import javax.swing.text.StyleConstants;  
    import javax.swing.text.StyledEditorKit;  
    import javax.swing.text.View;  
    import javax.swing.text.ViewFactory;  
      
    /** 
     * ����������ʵ�ֳ������ʶ����Զ����е� JTextPane ������ 
     * Java 7 ���°汾�� JTextPane ������ʵ���Զ����У��� 
     * �������ʶ�����Ч������ Java 7 ��ʼ���������ʾͲ����Զ� 
     * ���У����� JTextPane ��ʵ�ʿ�ȱ��ʹ�ù��������֡� 
     * ����ķ����Ƕ���� bug �ĽϺ��޸��� 
     * 
     * Created by dolphin on 15-2-3. 
     */  
public class JIMSendTextPane extends JTextPane {  
      
        // �ڲ���  
        // �����ڲ���ȫ������ʵ���Զ�ǿ������  
      
    	/*  StyledEditorKit ��ʵ���ṩ�˽��ı���Ϊ��ʽ�ı���Ĭ��ʵ��,���ṩ����ʽ�ı�����С������.
    	 *  �����ı���������һ������,��Ϊĳ�����͵��ı��ĵ��ĺ����ܵı༭��.
    	 */
        private class WarpEditorKit extends StyledEditorKit {  
      
            private ViewFactory defaultFactory = new WarpColumnFactory();  
      
            @Override  
            public ViewFactory getViewFactory() {  
                return defaultFactory;  
            }  
        }  
      
        /*  ViewFactoryһ����������һ�����ļ��������ͼ.����Ϊ���ܹ��Զ�����ͼ���ӳ�䵽�ĵ�ģ��.
         * 
         */
        
        private class WarpColumnFactory implements ViewFactory {  
      
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
      
        private class WarpLabelView extends LabelView {  
      
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
      
        // ����  
      
        // ���캯��  
        public JIMSendTextPane() {  
            super();  
            this.setEditorKit(new WarpEditorKit());  
        } 
        
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
        
        
        
        
        public static JTextPane setJTextPane() {
        	 	
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
        
        
        static public void main(String args[]) {
        	JFrame frame = new JFrame();
    		frame.setBounds(100, 100, 450, 300);
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		
    		JScrollPane scrollPane1 = new JScrollPane();
    	//	JTextPane textPane1 = JIMSendTextPane.setJTextPane();
    		JTextPane textPane1 = new JTextPane();
    		scrollPane1.setViewportView(textPane1);

    		
    		//JScrollPane scrollPane = new JScrollPane();
    		frame.getContentPane().add(scrollPane1, BorderLayout.CENTER);
    		frame.setVisible(true);
        }
    }  


