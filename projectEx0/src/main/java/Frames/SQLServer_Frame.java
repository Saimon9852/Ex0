package main.java.Frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.mypack.Database;
import main.java.mypack.Read_fSQL;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class SQLServer_Frame extends JFrame {

	private JPanel contentPane;
	private JTextField txtPass;
	private JTextField txtUser;
	private JTextField txtURL;
	private JTextField txtIp;
	private JTextField txtTable;
	private JTextField txtTableSch;
	private SQLServer_Frame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SQLServer_Frame frame = new SQLServer_Frame(null);
					
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
	public SQLServer_Frame(myFrame mframe) {
		
		setBounds(100, 100, 836, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPass = new JTextField();
		txtPass.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPass.setBounds(15, 69, 236, 39);
		contentPane.add(txtPass);
		txtPass.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(15, 28, 141, 33);
		contentPane.add(lblPassword);
		
		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtUser.setBounds(299, 69, 218, 39);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(299, 28, 115, 33);
		contentPane.add(lblUser);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(299, 160, 115, 33);
		contentPane.add(lblIp);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(567, 28, 141, 33);
		contentPane.add(lblPort);
		
		txtURL = new JTextField();
		txtURL.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtURL.setColumns(10);
		txtURL.setBounds(567, 69, 223, 39);
		contentPane.add(txtURL);
		
		txtIp = new JTextField();
		txtIp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtIp.setColumns(10);
		txtIp.setBounds(299, 201, 218, 39);
		contentPane.add(txtIp);
		
		txtTable = new JTextField();
		txtTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtTable.setBounds(15, 201, 236, 39);
		contentPane.add(txtTable);
		txtTable.setColumns(10);
		
		txtTableSch = new JTextField();
		txtTableSch.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtTableSch.setBounds(572, 201, 218, 39);
		contentPane.add(txtTableSch);
		txtTableSch.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Table");
		lblNewLabel.setBounds(15, 160, 115, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblTableSchema = new JLabel("Table Schema:");
		lblTableSchema.setBounds(567, 160, 187, 33);
		contentPane.add(lblTableSchema);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!txtPass.getText().equals("") && !txtUser.getText().equals("") &&!txtURL.getText().equals("") && !txtIp.getText().equals("")
						&&!txtTable.getText().equals("") && !txtTableSch.getText().equals(""))
				{
					Read_fSQL mysql = new Read_fSQL(mframe.getServer().mainDB,txtPass.getText(),txtUser.getText(),txtURL.getText(),txtIp.getText(),txtTable.getText(),txtTableSch.getText());
					mysql.READ();
					mframe.getServer().filesCounter++;
					mframe.getServer().updateStack();
					mframe.getServer().display();
					dispose();
				}
				else
					JOptionPane.showMessageDialog(frame, "All fields can not be empty");
			}
		});
		btnConnect.setBounds(572, 283, 218, 41);
		contentPane.add(btnConnect);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(330, 283, 218, 41);
		contentPane.add(btnCancel);
	}
}
