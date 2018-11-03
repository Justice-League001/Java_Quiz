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
     * 该类是真正实现超长单词都能自动换行的 JTextPane 的子类 
     * Java 7 以下版本的 JTextPane 本身都能实现自动换行，对 
     * 超长单词都能有效，但从 Java 7 开始读超长单词就不能自动 
     * 换行，导致 JTextPane 的实际宽度变大，使得滚动条出现。 
     * 下面的方法是对这个 bug 的较好修复。 
     * 
     * Created by dolphin on 15-2-3. 
     */  
public class JIMSendTextPane extends JTextPane {  
      
        // 内部类  
        // 以下内部类全都用于实现自动强制折行  
      
    	/*  StyledEditorKit 此实现提供了将文本视为样式文本的默认实现,并提供了样式文本的最小操作集.
    	 *  这是文本组件所需的一组事物,作为某种类型的文本文档的合理功能的编辑器.
    	 */
        private class WarpEditorKit extends StyledEditorKit {  
      
            private ViewFactory defaultFactory = new WarpColumnFactory();  
      
            @Override  
            public ViewFactory getViewFactory() {  
                return defaultFactory;  
            }  
        }  
      
        /*  ViewFactory一个工厂创建一部分文件主题的视图.这是为了能够自定义视图如何映射到文档模型.
         * 
         */
        
        private class WarpColumnFactory implements ViewFactory {  
      
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
      
        // 本类  
      
        // 构造函数  
        public JIMSendTextPane() {  
            super();  
            this.setEditorKit(new WarpEditorKit());  
        } 
        
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


