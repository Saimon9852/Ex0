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
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Algo2_frame extends JFrame {

	private JPanel contentPane;
	private JTextField textField_Scan;
	private JTextField textField_MAC1;
	private JTextField textField_MAC2;
	private JTextField textField_MAC3;
	private JTextField textField_Signal1;
	private JTextField textField_Signal2;
	private JTextField textField_Signal3;
	private Algo2_frame frame;
	private JRadioButton rdbtnScan;
	private JRadioButton rdbtnSinglas;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Algo2_frame frame = new Algo2_frame(null);
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
	public Algo2_frame(Database db) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_Scan = new JTextField();
		textField_Scan.setBounds(12, 75, 408, 22);
		contentPane.add(textField_Scan);
		textField_Scan.setColumns(10);

		JLabel lblNewLabel = new JLabel("Enter Scan");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(12, 51, 116, 16);
		contentPane.add(lblNewLabel);

		JRadioButton rdbtnScan = new JRadioButton("Scan");
		rdbtnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnScan.isSelected()){
					textField_Scan.setEnabled(true);
					textField_MAC1.setEnabled(false);
					textField_MAC2.setEnabled(false);
					textField_MAC3.setEnabled(false);
					textField_Signal1.setEnabled(false);
					textField_Signal2.setEnabled(false);
					textField_Signal3.setEnabled(false);
					rdbtnSinglas.setSelected(false);

				}
			}
		});
		rdbtnScan.setBounds(293, 19, 127, 25);
		contentPane.add(rdbtnScan);

		textField_MAC1 = new JTextField();
		textField_MAC1.setBounds(12, 133, 116, 22);
		contentPane.add(textField_MAC1);
		textField_MAC1.setColumns(10);

		textField_MAC2 = new JTextField();
		textField_MAC2.setBounds(12, 171, 116, 22);
		contentPane.add(textField_MAC2);
		textField_MAC2.setColumns(10);

		textField_MAC3 = new JTextField();
		textField_MAC3.setBounds(12, 218, 116, 22);
		contentPane.add(textField_MAC3);
		textField_MAC3.setColumns(10);

		textField_Signal1 = new JTextField();
		textField_Signal1.setBounds(147, 133, 56, 22);
		contentPane.add(textField_Signal1);
		textField_Signal1.setColumns(10);

		textField_Signal2 = new JTextField();
		textField_Signal2.setColumns(10);
		textField_Signal2.setBounds(147, 171, 56, 22);
		contentPane.add(textField_Signal2);

		textField_Signal3 = new JTextField();
		textField_Signal3.setColumns(10);
		textField_Signal3.setBounds(147, 218, 56, 22);
		contentPane.add(textField_Signal3);

		JLabel lblEnterMacs = new JLabel("Enter Macs");
		lblEnterMacs.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEnterMacs.setBounds(12, 110, 116, 16);
		contentPane.add(lblEnterMacs);

		JLabel lblEnterSignals = new JLabel("Enter Signals");
		lblEnterSignals.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEnterSignals.setBounds(140, 110, 116, 16);
		contentPane.add(lblEnterSignals);

		JRadioButton rdbtnSinglas = new JRadioButton("Macs");
		rdbtnSinglas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnSinglas.isSelected()){
					textField_Scan.setEnabled(false);
					textField_MAC1.setEnabled(true);
					textField_MAC2.setEnabled(true);
					textField_MAC3.setEnabled(true);
					textField_Signal1.setEnabled(true);
					textField_Signal2.setEnabled(true);
					textField_Signal3.setEnabled(true);
					rdbtnScan.setSelected(false);
				}

			}
		});
		rdbtnSinglas.setBounds(293, 49, 127, 25);
		contentPane.add(rdbtnSinglas);

		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnSinglas.isSelected()){
					String mac1=textField_MAC1.getText();
					String mac2=textField_MAC2.getText();
					String mac3=textField_MAC3.getText();
					String Signal1=textField_Signal1.getText();
					String Signal2=textField_Signal2.getText();
					String Signal3=textField_Signal3.getText();
					if((macValidate.Validate(mac1)||mac1==null)&&
							(macValidate.Validate(mac3)||mac3==null)&&
						(macValidate.Validate(mac2)||mac2==null)){
						
						WifiSpots s=new WifiSpots();
						WifiSpot a=new WifiSpot();
						if(mac1!=null){
							a.setMac(mac1);
							a.setRssi(Signal1);
							s.spots.add(a);

						}
						if(mac2!=null){
							WifiSpot b=new WifiSpot();
							b.setMac(mac2);
							b.setRssi(Signal2);
							s.spots.add(b);

						}
						if(mac3!=null){
							WifiSpot c=new WifiSpot();
							c.setMac(mac3);
							c.setRssi(Signal3);
							s.spots.add(c);
						}
						if(s.getSpots().isEmpty()){
							JOptionPane.showMessageDialog(frame, "Not Data");
						}else{
							MacLoc_2 loc=new MacLoc_2(db,5,s);
							loc.setWscans();
							lblNewLabel_1.setText("LAT = "+s.getLatitude()+"\n"+" LON = "
							+s.getLongtitude() +"\n"+"ALT = "+s.getAltitude());
						}
						
						
						
						
					}else{
						JOptionPane.showMessageDialog(frame, "Not a Mac");
					}

				}else if(rdbtnScan.isSelected()){

				}
			}
		});
		btnEnter.setBounds(323, 217, 97, 25);
		contentPane.add(btnEnter);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(215, 136, 205, 68);
		contentPane.add(lblNewLabel_1);
	}
}
