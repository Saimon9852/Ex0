package mypack;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Algo1_frame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Algo1_frame frame;
	private JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Algo1_frame frame = new Algo1_frame(null);
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
	public Algo1_frame(Database db) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(197, 68, 223, 22);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblMac = new JLabel("Enter Mac Address for Router approximation");
		lblMac.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMac.setBounds(26, 39, 366, 16);
		contentPane.add(lblMac);

		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String mac=textField.getText();
				if(!macValidate.Validate(mac)){
					JOptionPane.showMessageDialog(frame, "Not a Mac");
				}else{
					MacLoc_1 Loc=new MacLoc_1(10,db);
					WifiSpot s=Loc.algorithm(mac);
					String Lat=s.getLatitude();
					String Lon=s.getLongtitude();
					String Alt=s.getAltitude();
					lblNewLabel.setText("LAT= "+Lat+" LON= "+Lon+" Alt= "+Alt);
				}
			}
		});
		btnEnter.setBounds(323, 103, 97, 25);
		contentPane.add(btnEnter);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(26, 175, 377, 35);
		contentPane.add(lblNewLabel);
	}
}
