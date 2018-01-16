package Frames;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.prism.Image;

import Filter.Filter;
import mypack.Csv;
import mypack.DataException;
import mypack.Database;
import mypack.Deserialize;
import mypack.Serialize;
import mypack.Server;
import mypack.Tokml;
import mypack.WifiSpots;
import mypack.Write_csv;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Stack;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.MatteBorder;

public class myFrame{

	private myFrame myframe;
    private JPanel p,panel_1;
	private JFrame frame;
	private Server server;
	//private Database mainDB;
	//private Stack<Database> filtDB;
	//private ArrayList<Filter> allFilter;
	//protected int filesCounter;
	//private NavigableMap<String,Long> pathToModifited = new TreeMap<String, Long>();
	private JLabel lblNumberOfMacs,lblNumberOfLines;
	//private ArrayList<String> allFolders = new ArrayList<String>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					myFrame window = new myFrame();
					new Thread(new RunModifed(window)).start();;
					window.frame.setVisible(true);
					window.setMyframe(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	    
	
	}

	public void setMyframe(myFrame m){
		myframe =m;	
	}
	
	/**
	 * Create the application.
	 */
	public myFrame()  {
		
		
		initialize();
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()   {
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1067, 810);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNumberOfLines = new JLabel("Number of lines: 0");
		lblNumberOfLines.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNumberOfLines.setBounds(30, 677, 304, 33);
		panel.add(lblNumberOfLines);
		
		lblNumberOfMacs = new JLabel("Number of Macs: 0");
		lblNumberOfMacs.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNumberOfMacs.setBounds(327, 677, 304, 33);
		panel.add(lblNumberOfMacs);
		
		JButton btnCreateCsv = new JButton("Create csv");
		btnCreateCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(getServer().filesCounter > 0){
					
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showSaveDialog(btnCreateCsv);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						Write_csv wc = new Write_csv(chooser.getSelectedFile().getName());
						String s = chooser.getSelectedFile().getPath();
						wc.writeGUIcsv(s.substring(0,server.cutLastinPath(s)), server.filtDB.peek(), frame);
					}
				}
				else
					JOptionPane.showMessageDialog(frame, "You need to add files first");
				
			}
		});
		btnCreateCsv.setBounds(845, 667, 192, 43);
		btnCreateCsv.setFont(new Font("Tahoma", Font.PLAIN, 28));
		panel.add(btnCreateCsv);
		
		JButton btnCreateKml = new JButton("Create KML");
		btnCreateKml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getServer().filesCounter > 0){
					
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showSaveDialog(btnCreateKml);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						Tokml kml = new Tokml(server.filtDB.peek());
						
						String s = chooser.getSelectedFile().getPath();
						try {
							kml.CreateKmlByFilter(chooser.getSelectedFile().getName(), frame, s.substring(0,server.cutLastinPath(s)));
						} catch (DataException e1) {
							
							e1.printStackTrace();
						}
						
					}
				}
				else
					JOptionPane.showMessageDialog(frame, "You need to add files first");
				
			}
		});
		btnCreateKml.setBounds(643, 667, 190, 43);
		btnCreateKml.setFont(new Font("Tahoma", Font.PLAIN, 28));
		panel.add(btnCreateKml);
		
		
		JButton btnAddFilter = new JButton("Add filter");
		btnAddFilter.setBounds(30, 590, 212, 40);
		btnAddFilter.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnAddFilter.setForeground(Color.BLACK);
		btnAddFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getServer().filesCounter > 0){
					frame.setVisible(false);
					FilterFrame f = new FilterFrame(myframe);
					f.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(frame, "You need to add files before");
			}
		});
		panel.add(btnAddFilter);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		panel_1.setBounds(30, 60, 212, 497);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		p = new JPanel();
		p.setBounds(288, 0, 761, 630);
		p.setLayout(null);
		
		panel.add(p);
		/////////////////////////////////////////////////////////////////////////////
		JLabel lblFilters = new JLabel("Filters:");
		lblFilters.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblFilters.setBounds(30, 25, 179, 33);
		panel.add(lblFilters);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 40));
		//menuBar.add(Box.createHorizontalGlue());
		frame.setJMenuBar(menuBar);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 28));
		menuBar.add(mnEdit);
		
		JMenuItem mntmAddFile = new JMenuItem("Add File");
		mntmAddFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				    "CSV files", "csv");
				chooser.setFileFilter(filter);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(mntmAddFile);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					
					getServer().addFile(chooser.getSelectedFile().getPath());
					getServer().display();
				}
				
			}
		});
		mntmAddFile.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		mnEdit.add(mntmAddFile);
		
		JMenuItem mntmAddFolder = new JMenuItem("Add Folder");
		mntmAddFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				    "CSV files", "csv");
				chooser.setFileFilter(filter);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(mntmAddFile);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					
					try 
					{
						getServer().allFolders.add(chooser.getSelectedFile().getPath());
						ArrayList<String>paths = server.getAllPaths(chooser.getSelectedFile().getPath());
						for (int i = 0; i < paths.size(); i++) {
							getServer().addFile(paths.get(i));
						}
						getServer().display();
					} 
					catch (DataException e1) {
						
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		
		mntmAddFolder.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		mnEdit.add(mntmAddFolder);
		
		JMenuItem mntmAddExistFilter = new JMenuItem("Add Exist Filter");
		mntmAddExistFilter.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		mntmAddExistFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(server.allFilter.size() > 0 && server.filesCounter >0){
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				    "Serializable Files", "ser");
				chooser.setFileFilter(filter);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(mntmAddExistFilter);
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					 String s = chooser.getSelectedFile().getPath();
					 System.out.println(s);
				     Deserialize des = new Deserialize(s);
					 Filter f= des.Read();
				     server.allFilter.add(f);
				     server.updateStack();
				     server.display();
				}
				}
				else
					JOptionPane.showMessageDialog(frame, "You need to add files and filters before");
			}
		});
		mnEdit.add(mntmAddExistFilter);
		
		JMenuItem mntmConnectToServer = new JMenuItem("Connect to server");
		mntmConnectToServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				SQLServer_Frame sqlFrame = new SQLServer_Frame(myframe);
				sqlFrame.setVisible(true);
				
			}
		});
		mntmConnectToServer.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		mnEdit.add(mntmConnectToServer);
		
		JMenu mnAlgo = new JMenu("Algo");
		mnAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 28));
		menuBar.add(mnAlgo);
		
		JMenuItem mntmAlgo_1 = new JMenuItem("Algo 2");
		mntmAlgo_1.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		mntmAlgo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(server.filesCounter >0){
				Algo2_frame algo2=new Algo2_frame (server.mainDB);
				algo2.setVisible(true);}
				else
					JOptionPane.showMessageDialog(frame, "You need to add files first");
			}
		});
		mnAlgo.add(mntmAlgo_1);
		
		JMenuItem mntmAlgo = new JMenuItem("Algo 1");
		mntmAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		mntmAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(server.filesCounter >0){
				Algo1_frame Algo1=new Algo1_frame(server.mainDB);
				Algo1.setVisible(true);}
				else
					JOptionPane.showMessageDialog(frame, "You need to add files first");
			}
		});
		mnAlgo.add(mntmAlgo);
		
		server = new Server(lblNumberOfMacs,lblNumberOfLines,p,panel_1);
		
		getServer().display();
		
		
	}

	public Server getServer() {
		return server;
	}
}
