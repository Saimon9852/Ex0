package mypack;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;
import javax.swing.SpringLayout;
import javax.swing.JLabel;

public class FilterFrame extends JFrame {

	private JPanel contentPane;
    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterFrame frame = new FilterFrame(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FilterFrame(JFrame parent) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JButton button = new JButton("\u05D4\u05D5\u05E1\u05E3 \u05E1\u05D9\u05E0\u05D5\u05DF");
		sl_contentPane.putConstraint(SpringLayout.NORTH, button, 828, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, button, 257, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, button, 901, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, button, 496, SpringLayout.WEST, contentPane);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u05D1\u05D9\u05D8\u05D5\u05DC");
		sl_contentPane.putConstraint(SpringLayout.NORTH, button_1, 828, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, button_1, 26, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, button_1, 901, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, button_1, 242, SpringLayout.WEST, contentPane);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				dispose();
				parent.setVisible(true);
			}
		});
		contentPane.add(button_1);
		
		JLabel label = new JLabel("\u05E1\u05D9\u05E0\u05D5\u05DF \u05DC\u05E4\u05D9 \u05D6\u05DE\u05DF:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label, 64, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, label, -99, SpringLayout.EAST, contentPane);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u05E1\u05D9\u05E0\u05D5\u05DF \u05DC\u05E4\u05D9 \u05DE\u05D9\u05E7\u05D5\u05DD");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label_1, 142, SpringLayout.SOUTH, label);
		sl_contentPane.putConstraint(SpringLayout.EAST, label_1, -97, SpringLayout.EAST, contentPane);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u05E1\u05D9\u05E0\u05D5\u05DF \u05DC\u05E4\u05D9 \u05D6\u05DE\u05DF:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label_2, 160, SpringLayout.SOUTH, label_1);
		sl_contentPane.putConstraint(SpringLayout.WEST, label_2, 0, SpringLayout.WEST, label);
		contentPane.add(label_2);
	}
}

