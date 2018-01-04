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
	private Database mainDB;
	private Stack<Database> filtDB;
	private ArrayList<Filter> allFilter;
	protected int filesCounter;
	private NavigableMap<String,Long> pathToModifited = new TreeMap<String, Long>();
	private JLabel lblNumberOfMacs,lblNumberOfLines;
	private ArrayList<String> allFolders = new ArrayList<String>();
	

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
		filtDB = new Stack<Database>();
		allFilter = new ArrayList<Filter>();
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
				
				if(filesCounter > 0){
					
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showSaveDialog(btnCreateCsv);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						Write_csv wc = new Write_csv(chooser.getSelectedFile().getName());
						String s = chooser.getSelectedFile().getPath();
						wc.writeGUIcsv(s.substring(0,cutLastinPath(s)), filtDB.peek(), frame);
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
				if(filesCounter > 0){
					
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showSaveDialog(btnCreateKml);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						Tokml kml = new Tokml(filtDB.peek());
						
						String s = chooser.getSelectedFile().getPath();
						try {
							kml.CreateKmlByFilter(chooser.getSelectedFile().getName(), frame, s.substring(0,cutLastinPath(s)));
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
				if(filesCounter > 0){
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
		display();
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
					
					addFile(chooser.getSelectedFile().getPath());
					display();
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
						allFolders.add(chooser.getSelectedFile().getPath());
						ArrayList<String>paths = getAllPaths(chooser.getSelectedFile().getPath());
						for (int i = 0; i < paths.size(); i++) {
							addFile(paths.get(i));
						}
						display();
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
				if(allFilter.size() > 0 && filesCounter >0){
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
				     allFilter.add(f);
				     updateStack();
				     display();
				}
				}
				else
					JOptionPane.showMessageDialog(frame, "You need to add files and filters before");
			}
		});
		mnEdit.add(mntmAddExistFilter);
		
		JMenu mnAlgo = new JMenu("Algo");
		mnAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 28));
		menuBar.add(mnAlgo);
		
		JMenuItem mntmAlgo_1 = new JMenuItem("Algo 2");
		mntmAlgo_1.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		mntmAlgo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(filesCounter >0){
				Algo2_frame algo2=new Algo2_frame (mainDB);
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
				if(filesCounter >0){
				Algo1_frame Algo1=new Algo1_frame(mainDB);
				Algo1.setVisible(true);}
				else
					JOptionPane.showMessageDialog(frame, "You need to add files first");
			}
		});
		mnAlgo.add(mntmAlgo);
		
		
		
	}
	
	
	private void displayFilters() throws IOException{
		
		JLabel lbl =null;
		panel_1.removeAll();
		for (int i = 0; i < filtDB.size(); i++) {
			  if(i==0) lbl = new JLabel("Datebase");
			  else lbl = new JLabel("Filter" + i);
			  lbl.setBounds(50, 10+ i*40, 100, 20);
			  if(i>0){
				  lbl.setToolTipText(allFilter.get(i-1).toString());
			  }
			  panel_1.add(lbl);
			  
			  if(i>0){
				  JButton importFilter = new JButton("Import");
				  
				  importFilter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				  importFilter.setBounds(120, 10 + i*40, 80, 22);
				  importFilter.setName(Integer.toString(i));
				  importFilter.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							JFileChooser chooser = new JFileChooser();
							int returnVal = chooser.showSaveDialog(importFilter);
							if(returnVal == JFileChooser.APPROVE_OPTION) {
								String s = chooser.getSelectedFile().getPath();
								
								Serialize ser = new Serialize(s);
								int a = Integer.parseInt(importFilter.getName());
								ser.Write(allFilter.get(a-1));
							}
						}
					});
				  
				  panel_1.add(importFilter);
			  }
			  
			  lbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
			  if(i == allFilter.size()){
				  JButton undo = new JButton("X");
				  undo.setBounds(20, 10 + i*40, 20, 20);
				  undo.setForeground(Color.RED);
				  undo.setBorder(BorderFactory.createEmptyBorder());
				  undo.setOpaque(false);
				  undo.setContentAreaFilled(false);
				  undo.setFont(new Font("Tahoma", Font.PLAIN, 16));
				  undo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(allFilter.size() > 0)allFilter.remove(allFilter.size() -1);
							else {
								filesCounter =0;
								allFolders = new ArrayList<String>();
								pathToModifited = new TreeMap<String, Long>();
								
							}
							filtDB.pop();
							display();
						}
					});
				  panel_1.add(undo);
			  }
			  panel_1.revalidate();
			  panel_1.repaint();
		}
		
		if(filesCounter ==0){
			panel_1.revalidate();
			panel_1.repaint();
		}
	}
	
	public void addFilter(Filter f){
		allFilter.add(f);
		Database hold = filtDB.peek();
		ArrayList<WifiSpots> newlist = new ArrayList<WifiSpots>();
		for (int i = 0; i < hold.getDB().size() ; i++) {
			  if(f.Filt(hold.getDB().get(i))){
				  newlist.add(hold.getDB().get(i));
			  }
		}
		Database newDb = new Database(newlist);
		filtDB.push(newDb);
		display();
		//displayFilters();
	}
	
	private void display(){
		
		Database hold = null;
		String[] columns = {"Time","ID","Lat","Lon","Alt","#WiFi networks","SSID1","MAC1","Frequancy1","Signal1","SSID2","MAC2","Frequancy2","Signal2","SSID3"
				 ,"MAC3","Frequancy3","Signal3","SSID4","MAC4","Frequancy4","Signal4","SSID5","MAC5","Frequancy5","Signal5"
				,"SSID6","MAC6","Frequancy6","Signal6","SSID7","MAC7","Frequancy7","Signal7"
				,"SSID8","MAC8","Frequancy8","Signal8","SSID9,MAC9","Frequancy9","Signal9","SSID10","MAC10","Frequancy10","Signal10"};
		
		int rows = 0;
		int macs =0;
		if(filesCounter !=0){
			hold = filtDB.peek();
			rows = hold.getDB().size();
			macs = hold.getMacim().size();
		}
		String[][]  data = new String[rows][46];
		
		for (int i = 0; i < rows; i++) {
			  data[i][0] = hold.getDB().get(i).getFirstSeen();
			  data[i][1] = hold.getDB().get(i).getID();
			  data[i][2] = hold.getDB().get(i).getLatitude();
			  data[i][3] = hold.getDB().get(i).getLongtitude();
			  data[i][4] = hold.getDB().get(i).getAltitude();
			  data[i][5] = String.valueOf(hold.getDB().get(i).getSpots().size());
			  
			  int w = 6;
			  for (int j = 0; j < hold.getDB().get(i).getSpots().size(); j++) {
				   
				   data[i][w] = hold.getDB().get(i).getSpots().get(j).getSsid();w++;
				   data[i][w] = hold.getDB().get(i).getSpots().get(j).getMac();w++;
				   data[i][w] = hold.getDB().get(i).getSpots().get(j).getChanel();w++;
				   data[i][w] = hold.getDB().get(i).getSpots().get(j).getRssi();w++;	 
			}
		}
		
		lblNumberOfLines.setText("Number of Lines: "+ rows);
		lblNumberOfMacs.setText("Number of Macs: "+ macs);
		JTable table = new JTable(data, columns);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFillsViewportHeight(true);
		scrollPane.setBounds(0, 0, 764, 630);//////////////////////////////////////////////////////////////////
		p.removeAll();
		p.add(scrollPane);
		
		try {
			displayFilters();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> getAllPaths(String path)  throws DataException{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> paths=new ArrayList<String>();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()){
				
					String str = listOfFiles[i].getPath();
					paths.add(str);
				
			}

		} 
		if(paths.size()==0){
			throw new DataException("No currect input files");
		}
		return paths;
	}
	
	private void updateStack(){
		
		filtDB = new Stack<Database>();
		filtDB.push(mainDB);
		for (int i = 0; i < allFilter.size(); i++) {
			  Database peek = filtDB.peek();
			  ArrayList<WifiSpots> newarray = new ArrayList<WifiSpots>();
			  for (int j = 0; j < peek.getDB().size(); j++) {
				   if(allFilter.get(i).Filt(peek.getDB().get(j))){
					   newarray.add(peek.getDB().get(j));
				   }
			  }
			  Database newdb = new Database(newarray);
			  filtDB.push(newdb);
		}
	}
	
	private int cutLastinPath(String s){
		int index = -1;
		for (int i = 0; i < s.length(); i++) {
			  if(s.charAt(i) == '\\')
					  index =i;
		}
		return index;
	}
	
	protected void fileWasUpdated(){
		System.out.println("Hi from file");
		for(String key : pathToModifited.keySet()){
			    String path = key;
				File file = new File(path);
				
				if(file.lastModified() != pathToModifited.get(key)){ 
					int size = mainDB.getDB().size();
					for (int i = 0; i < size; i++) {
						  if(mainDB.getDB().get(i).getFrom().equals(path)){
							  mainDB.getDB().remove(i);
							  i--;
							  size = mainDB.getDB().size();
						  }
					}
					pathToModifited.put(key, file.lastModified());
					ArrayList<WifiSpots> newFile = readAgain(path);
					mainDB.addToDB(newFile);
					updateStack();
					display();
			
				}
		}
	}
	
	protected void folderWasUpdated(){
		
		for (int i = 0; i < allFolders.size(); i++) {
			File folder = new File(allFolders.get(i));
			File[] listOfFiles = folder.listFiles();
			for (int j = 0; j < listOfFiles.length; j++) {
				  String pathfile = listOfFiles[j].getPath();
				  boolean found = false;
				  for(String key : pathToModifited.keySet()){
					   String s = key;
					   if(s.equals(pathfile))
					   {
						   found = true;
						   break;
					   }
				  }
				  
				  
				  
				  if(!found){
					  addFile(pathfile);
					  updateStack();
					  display();
				  }
			}
		}
	}
	
	private ArrayList<WifiSpots> readAgain(String path){
		  Csv myfileC=new Csv();
		  if(myfileC.hasRightFormat(path)){
			   
		     try {
		    	 Csv myfile = new Csv(path);
		    	 return myfile.getLittleDB();
			 
			 }
			 catch (DataException e1) {
				e1.printStackTrace();
			 }    
		  }
		  else{
			  Database b = new Database(path,"WifiSpots");
			  return b.getDB();
		  }
		return null;
	}
	
	private void addFile(String path){
		  Csv myfileC=new Csv();
		  File file = new File(path);
		  if(myfileC.hasRightFormat(path)){
			   
		     try {
		    	 Csv myfile = new Csv(path);
			     if(filesCounter == 0)
			     	{
			    	 	mainDB = new Database(myfile.getLittleDB());
			    	 	filtDB.push(mainDB);
			    	    pathToModifited.put(path, file.lastModified());
			         }
				 else {
					
					 mainDB.addToDB(myfile.getLittleDB());
					 pathToModifited.put(path, file.lastModified());
					 if(allFilter.size() > 0) updateStack();
					 else{filtDB.pop(); filtDB.push(mainDB);}
			      }
			 }
			 catch (DataException e1) {
				e1.printStackTrace();
			 }    
		  }
		  else{
			  if(filesCounter == 0){ mainDB = new Database(path,"WifiSpots");filtDB.push(mainDB);
			                         pathToModifited.put(path, file.lastModified());}
			 			    	   	 
			  else{
				  mainDB.add(path);
				  pathToModifited.put(path, file.lastModified());
				  if(allFilter.size() > 0) updateStack();
				  else{ filtDB.pop(); filtDB.push(mainDB);}
			  }
		  }
		  filesCounter++;
	}
}
