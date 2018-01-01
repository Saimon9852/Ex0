package mypack;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FilterFrame extends JFrame {

	private JPanel contentPane;
    private FilterFrame frame;
    private JRadioButton keyRadbtn;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private boolean isKey = false,isKey2 = false,isNone=false;
    private JTextField secTxt1;
    private JTextField secTxt2;
    private JTextField secTxt4;
    private JTextField secTxt3;
    private Filter finalFilter = null;
   

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
	public FilterFrame(myFrame parent) {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 783);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button_1 = new JButton("\u05D1\u05D9\u05D8\u05D5\u05DC");
		button_1.setBounds(15, 616, 216, 73);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				dispose();
				parent.getFrame().setVisible(true);
			}
		});
		contentPane.add(button_1);
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(20, 148, 580, 436);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(26, 149, 236, 39);
		panel1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(334, 149, 236, 39);
		panel1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(26, 88, 115, 33);
		panel1.add(lblNewLabel);
		
		JLabel label = new JLabel("New label");
		label.setBounds(334, 88, 115, 33);
		panel1.add(label);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(26, 244, 115, 33);
		panel1.add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(26, 305, 236, 39);
		panel1.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(334, 244, 115, 33);
		panel1.add(lblNewLabel_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(334, 305, 236, 39);
		panel1.add(textField_3);
		textField_3.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(26, 149, 236, 39);
		setCombo(comboBox);
		
		JRadioButton timeFiltebtn = new JRadioButton("Time Filter");
		timeFiltebtn.setBounds(15, 80, 171, 41);
		JRadioButton locFiltbtn = new JRadioButton("Location Filter");
		locFiltbtn.setBounds(192, 80, 211, 41);
		
		timeFiltebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				locFiltbtn.setSelected(false);
				keyRadbtn.setSelected(false);
				if(isKey == true){
				 panel1.add(textField_2);
				 panel1.add(textField_3);
				 panel1.add(textField);
				 panel1.remove(comboBox);
				  isKey = false;
				}
				else
					panel1.add(textField_3);
				
				 textField.setText("00/00/0000");
				 textField_1.setText("00:00");
				 textField_2.setText("00/00/0000");
				 textField_3.setText("00:00");
				 label.setText("With time:");
				 lblNewLabel.setText("From Date:");
				 lblNewLabel_1.setText("To Data:");
				 lblNewLabel_2.setText("With time:");
			}
		});
		contentPane.add(timeFiltebtn);
		
		
		locFiltbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeFiltebtn.setSelected(false);
				panel1.remove(textField_3);
				panel1.revalidate();
				 panel1.repaint();
				keyRadbtn.setSelected(false);
				if(isKey == true){
					 panel1.add(textField_2);
					 panel1.add(textField);
					 panel1.remove(comboBox);
					  isKey = false;
				}
					 textField.setText("0");
					 textField_1.setText("0");
					 textField_2.setText("0");
					 label.setText("Lon:");
					 lblNewLabel.setText("Lat:");
					 lblNewLabel_1.setText("Radius:");
					 lblNewLabel_2.setText("");
					 
			}
		});
		contentPane.add(locFiltbtn);
		
		keyRadbtn = new JRadioButton("Key word filter");
		keyRadbtn.setBounds(15, 19, 217, 41);
		keyRadbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			     
				 timeFiltebtn.setSelected(false);
				 locFiltbtn.setSelected(false);
				 panel1.remove(textField_2);
				 panel1.remove(textField_3);
				 panel1.remove(textField);
				 panel1.add(comboBox);
				 panel1.revalidate();
				 panel1.repaint();
				 isKey = true;
				 textField_1.setText("");
				 label.setText("The word:");
				 lblNewLabel.setText("Key:");
				 lblNewLabel_1.setText(" ");
				 lblNewLabel_2.setText(" ");
				 
			}
		});
		contentPane.add(keyRadbtn);
		
		JPanel panel = new JPanel();
		panel.setBounds(668, 148, 589, 436);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel secLbl1 = new JLabel("New label");
		secLbl1.setBounds(26, 87, 115, 33);
		panel.add(secLbl1);
		
		JLabel secLbl2 = new JLabel("New label");
		secLbl2.setBounds(334, 87, 115, 33);
		panel.add(secLbl2);
		
		secTxt1 = new JTextField();
		secTxt1.setColumns(10);
		secTxt1.setBounds(26, 148, 236, 39);
		panel.add(secTxt1);
		
		secTxt2 = new JTextField();
		secTxt2.setColumns(10);
		secTxt2.setBounds(334, 148, 236, 39);
		panel.add(secTxt2);
		
		secTxt4 = new JTextField();
		secTxt4.setColumns(10);
		secTxt4.setBounds(334, 304, 236, 39);
		panel.add(secTxt4);
		
		secTxt3 = new JTextField();
		secTxt3.setColumns(10);
		secTxt3.setBounds(26, 304, 236, 39);
		panel.add(secTxt3);
		
		JLabel secLbl3 = new JLabel("New label");
		secLbl3.setBounds(26, 243, 115, 33);
		panel.add(secLbl3);
		
		JLabel secLbl4 = new JLabel("New label");
		secLbl4.setBounds(334, 243, 115, 33);
		panel.add(secLbl4);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(26, 148, 236, 39);
		setCombo(comboBox_1);
		//panel.add(comboBox_1);
		
		JRadioButton rdTime2 = new JRadioButton("Time Filter");
		JRadioButton rdbtnNone = new JRadioButton("None");
		JRadioButton rdLoc2 = new JRadioButton("Location Filter");
		JRadioButton rdKey2 = new JRadioButton("Key word filter");
		rdTime2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdLoc2.setSelected(false);
				rdKey2.setSelected(false);
				rdbtnNone.setSelected(false);
			
				if(isKey2 == true){
					 panel.add(secTxt3);
					 panel.add(secTxt4);
					 panel.add(secTxt1);
					 panel.remove(comboBox_1);
					  isKey2 = false;
				}
				else if(isNone = true)
				{
					 panel.add(secTxt1);
					 panel.add(secTxt2);
					 panel.add(secTxt3);
					 panel.add(secTxt4);
					 panel.add(secLbl1);
					 panel.add(secLbl2);
					 panel.add(secLbl3);
					 panel.add(secLbl4);
					 
					 isNone = false;
				}
				else
					panel.add(secTxt4);
				 secTxt1.setText("00/00/0000");
				 secTxt2.setText("00:00");
				 secTxt3.setText("00/00/0000");
				 secTxt4.setText("00:00");
				 secLbl2.setText("With time:");
				 secLbl1.setText("From Date:");
				 secLbl3.setText("To Data:");
				 secLbl4.setText("With time:");
				
			}
		});
		
		rdKey2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				 rdTime2.setSelected(false);
				 rdLoc2.setSelected(false);
				 rdbtnNone.setSelected(false);
				 if(isNone == true){
					 panel.add(comboBox_1);
					 panel.add(secLbl1);
					 panel.add(secLbl2);
					 panel.add(secTxt2);
					 isNone = false;
				 }
				 panel.remove(secTxt1);
				 panel.remove(secTxt3);
				 panel.remove(secTxt4);
				 panel.add(comboBox_1);
				 panel.revalidate();
				 panel.repaint();
				 isKey2 = true;
				 secTxt2.setText("");
				 secLbl2.setText("The word:");
				 secLbl1.setText("Key:");
				 secLbl3.setText(" ");
				 secLbl4.setText(" ");
			}
		});
		
		rdbtnNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdTime2.setSelected(false);
				rdKey2.setSelected(false);
				rdLoc2.setSelected(false);
				isNone = true;
				isKey2 = false;
				panel.removeAll();
				panel.revalidate();
				panel.repaint();
			}
		});
		
		rdLoc2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdTime2.setSelected(false);
				rdKey2.setSelected(false);
				rdbtnNone.setSelected(false);
				panel.remove(secTxt4);
				if(isKey2 == true){
					 panel.add(secTxt3);
					// panel.add(secTxt4);
					 panel.add(secTxt1);
					 panel.remove(comboBox_1);
					  isKey2 = false;
				}
				else if(isNone = true)
				{
					 panel.add(secTxt1);
					 panel.add(secTxt2);
					 panel.add(secTxt3);
					 panel.add(secLbl1);
					 panel.add(secLbl2);
					 panel.add(secLbl3);
					 panel.add(secLbl4);
					 isNone = false;
				}
				panel.revalidate();
				panel.repaint();
				
					 secTxt1.setText("0");
					 secTxt2.setText("0");
					 secTxt3.setText("0");
					 secLbl2.setText("Lon:");
					 secLbl1.setText("Lat:");
					 secLbl3.setText("Radius:");
					 secLbl4.setText("");
			}
		});
		
		rdTime2.setBounds(696, 80, 171, 41);
		contentPane.add(rdTime2);
		
		
		rdLoc2.setBounds(696, 19, 211, 41);
		contentPane.add(rdLoc2);
		
		
		rdKey2.setBounds(975, 19, 217, 41);
		contentPane.add(rdKey2);
		
		
		rdbtnNone.setBounds(975, 80, 251, 41);
		contentPane.add(rdbtnNone);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((String)comboBox_2.getSelectedItem()).equals("NOT") || ((String)comboBox_2.getSelectedItem()).equals("None"))
					isNone = true;
				    rdbtnNone.setSelected(true);
				    rdLoc2.setSelected(false);
				    rdTime2.setSelected(false);
				    rdKey2.setSelected(false);
				    panel.removeAll();
			}
		});
		comboBox_2.setBounds(601, 370, 65, 41);
		comboBox_2.addItem("None");
		comboBox_2.addItem("AND");
		comboBox_2.addItem("NOT");
		comboBox_2.addItem("OR");
		contentPane.add(comboBox_2);
		
		JButton button = new JButton("\u05D4\u05D5\u05E1\u05E3 \u05E1\u05D9\u05E0\u05D5\u05DF");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Filter f1= null,f2 = null;
				boolean isF1 =false,isF2 = false;
				if(timeFiltebtn.isSelected()){
					boolean dataValid = isValid(textField.getText(),'/',2);
					boolean timeValid = isValid(textField_1.getText(),':',1);
					boolean dataValid2 = isValid(textField_2.getText(),'/',2);
					boolean timeValid2 = isValid(textField_3.getText(),':',1);
					if(dataValid && timeValid && dataValid2 && timeValid2){
						String from = textField.getText() + " " + textField_1.getText();
						String to = textField_2.getText() + " " + textField_3.getText();
						f1 = new TimeFilter(from,to);
						isF1 = true;
					}
					else
						JOptionPane.showMessageDialog(frame, "Wrong format for date filter");
				}
				else if(keyRadbtn.isSelected()){
					
					if(textField_1.getText() != ""){
						String keyWord = (String)comboBox.getSelectedItem();
						String filtWord = textField_1.getText();
						f1 = new IdFilter(keyWord,filtWord);
						isF1 = true;
					}
					else
						JOptionPane.showMessageDialog(frame, "Wrong format for key filter");
				}
				else if(locFiltbtn.isSelected()){
					boolean isLat = isValid(textField.getText(),'.',1);
					boolean isLon = isValid(textField_1.getText(),'.',1);
					boolean isRadius = isValid(textField_2.getText(),'.',1);
					if(isLat&&isLon&&isRadius){
						double lat = Double.parseDouble(textField.getText());
						double lon = Double.parseDouble(textField_1.getText());
						double rad = Double.parseDouble(textField_2.getText());
						f1 = new LocationFilter(lon,lat,rad);	
						isF1 = true;
					}
					else
						JOptionPane.showMessageDialog(frame, "Wrong format for location filter");
				}
				else
					JOptionPane.showMessageDialog(frame, "You must choose one of the filter buttons");
				
				
				if(rdTime2.isSelected()){
					boolean dataValid = isValid(secTxt1.getText(),'/',2);
					boolean timeValid = isValid(secTxt2.getText(),':',1);
					boolean dataValid2 = isValid(secTxt3.getText(),'/',2);
					boolean timeValid2 = isValid(secTxt4.getText(),':',1);
					if(dataValid && timeValid && dataValid2 && timeValid2){
						String from = secTxt1.getText() + " " + secTxt2.getText();
						String to = secTxt3.getText() + " " + secTxt4.getText();
						f2 = new TimeFilter(from,to);
						isF2 = true;
					}
					else
						JOptionPane.showMessageDialog(frame, "Wrong format for date filter");
				}
				else if(rdLoc2.isSelected()){
					boolean isLat = isValid(secTxt1.getText(),'.',1);
					boolean isLon = isValid(secTxt2.getText(),'.',1);
					boolean isRadius = isValid(secTxt3.getText(),'.',1);
					if(isLat&&isLon&&isRadius){
						double lat = Double.parseDouble(secTxt1.getText());
						double lon = Double.parseDouble(secTxt2.getText());
						double rad = Double.parseDouble(secTxt3.getText());
						f2 = new LocationFilter(lon,lat,rad);	
						isF2 = true;
					}
					else
						JOptionPane.showMessageDialog(frame, "Wrong format for location filter");
				}
				else if(rdKey2.isSelected()){
					if(secTxt2.getText() != ""){
						String keyWord = (String)comboBox_1.getSelectedItem();
						String filtWord = secTxt2.getText();
						f2 = new IdFilter(keyWord,filtWord);
						isF2 = true;
					}
					else
						JOptionPane.showMessageDialog(frame, "Wrong format for key filter");
				}
				else if(rdbtnNone.isSelected()){
					isF2 = true;
				}
				else
					JOptionPane.showMessageDialog(frame, "You must choose one of the filter buttons");
				
				if(isF2&&isF1){
					if(rdbtnNone.isSelected()){
						if(((String)comboBox_2.getSelectedItem()).equals("NOT")){
							  finalFilter = new Not_Filter(f1);
							  parent.getFrame().setVisible(true);
							  parent.addFilter(finalFilter);
							  dispose();
						}
						else{
							 finalFilter = f1;
							 System.out.println(finalFilter);
							 parent.getFrame().setVisible(true);
							 parent.addFilter(finalFilter);
							 dispose();
						}
					}
					else{
						if(((String)comboBox_2.getSelectedItem()).equals("OR")){
							finalFilter = new Or_Filter(f1,f2);
							 parent.getFrame().setVisible(true);
							 parent.addFilter(finalFilter);
							 dispose();
							 
						}
						else if(((String)comboBox_2.getSelectedItem()).equals("AND")){
							finalFilter = new And_Filter(f1,f2);
							 parent.getFrame().setVisible(true);
							 parent.addFilter(finalFilter);
							 dispose();
						}
						else
							JOptionPane.showMessageDialog(frame, "To use secound filter you must choose AND/OR");
		
					}
				}
				
				
			}
		});
		button.setBounds(281, 616, 239, 73);
		contentPane.add(button);
	
		
	}
	
	
	private void setCombo(JComboBox comb){
		String[] headers = {"Time","ID","Lat","Lon","Alt"};
		for (int i = 0; i < headers.length; i++) {
			  comb.addItem(headers[i]);
		}
	}
	
	private boolean isValid(String s,char c,int exist){
		int counter=0;
		for (int i = 0; i < s.length(); i++) {
			  if(s.charAt(i) == c)
				  counter++;
			  else{
				  if(!Character.isDigit(s.charAt(i)))
					  return false;
			  }
		}
		
		return exist == counter;
	}
	
	
}

