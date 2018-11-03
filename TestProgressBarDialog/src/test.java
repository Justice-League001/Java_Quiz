import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JPanel;

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
		frame.setBounds(100, 100, 380, 220);
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(15);
		frame.getContentPane().add(verticalStrut);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel fileMsgLabel = new JLabel("New label");
		panel.add(fileMsgLabel);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		
		Component verticalStrut_1 = Box.createVerticalStrut(35);
		frame.getContentPane().add(verticalStrut_1);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panel_1.add(rigidArea);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		panel_1.add(progressBar);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_1.add(rigidArea_1);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue_1);
		
		JLabel progressLabel = new JLabel("ËÙÂÊ");
		panel_2.add(progressLabel);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_2.add(horizontalGlue_2);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		frame.getContentPane().add(verticalGlue_1);
		frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
		Component []k = panel_2.getComponents();
		Component []j = frame.getContentPane().getComponents();
		
		
		for(int i=0;  i<frame.getContentPane().getComponents().length; i++) {
			if(j[i].equals(panel_2) ) {
				System.out.println(i);
			}
		}
		
		for(int i=0;  i<panel_2.getComponents().length; i++) {
			if(k[i].equals(progressLabel) ) {
				System.out.println(i);
			}
		}
		
		
		
	}

}
