package mypack;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
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
	private int filesCounter;
	private NavigableMap<String,Long> pathToModifited = new TreeMap<String, Long>();
	private JLabel lblNumberOfMacs,lblNumberOfLines;
	private NavigableMap<Integer,Integer> fileRange = new TreeMap<Integer, Integer>();
	private HashMap<Entry<String,Long>,Entry<Integer, Integer>> fileInTable = new HashMap<>();
	private ArrayList<String> allFolders = new ArrayList<String>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					myFrame window = new myFrame();
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
		frame.setBounds(100, 100, 1800, 1600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNumberOfLines = new JLabel("Number of lines: 0");
		lblNumberOfLines.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblNumberOfLines.setBounds(37, 1374, 390, 33);
		panel.add(lblNumberOfLines);
		
		lblNumberOfMacs = new JLabel("Number of Macs: 0");
		lblNumberOfMacs.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblNumberOfMacs.setBounds(453, 1374, 397, 33);
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
		btnCreateCsv.setBounds(1380, 1362, 372, 81);
		btnCreateCsv.setFont(new Font("Tahoma", Font.PLAIN, 36));
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
		btnCreateKml.setBounds(982, 1361, 372, 81);
		btnCreateKml.setFont(new Font("Tahoma", Font.PLAIN, 36));
		panel.add(btnCreateKml);
		
		
		JButton btnAddFilter = new JButton("Add filter");
		btnAddFilter.setBounds(37, 1246, 342, 64);
		btnAddFilter.setFont(new Font("Tahoma", Font.PLAIN, 36));
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
		panel_1.setBounds(37, 69, 342, 1130);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		p = new JPanel();
		p.setBounds(426, 0, 1342, 1310);
		p.setLayout(null);
		display();
		panel.add(p);
		
		JLabel lblFilters = new JLabel("Filters:");
		lblFilters.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblFilters.setBounds(37, 28, 179, 33);
		panel.add(lblFilters);
		
		JButton btnUpdated = new JButton("updated");
		btnUpdated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(filesCounter>0){
				fileWasUpdated();
				folderWasUpdated();}
			}
		});
		btnUpdated.setBounds(790, 1373, 171, 41);
		panel.add(btnUpdated);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 40));
		//menuBar.add(Box.createHorizontalGlue());
		frame.setJMenuBar(menuBar);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 40));
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
		mntmAddFile.setFont(new Font("Segoe UI", Font.PLAIN, 35));
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
		
		mntmAddFolder.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		mnEdit.add(mntmAddFolder);
		
		JMenu mnAlgo = new JMenu("Algo");
		mnAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 40));
		menuBar.add(mnAlgo);
		
		JMenuItem mntmAlgo_1 = new JMenuItem("Algo 2");
		mntmAlgo_1.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		mntmAlgo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Algo2_frame algo2=new Algo2_frame (mainDB);
				algo2.setVisible(true);
			}
		});
		mnAlgo.add(mntmAlgo_1);
		
		JMenuItem mntmAlgo = new JMenuItem("Algo 1");
		mntmAlgo.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		mntmAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Algo1_frame Algo1=new Algo1_frame(mainDB);
				Algo1.setVisible(true);
			}
		});
		mnAlgo.add(mntmAlgo);
		
	}
	
	
	private void displayFilters(){
		
		JLabel lbl =null;
		panel_1.removeAll();
		for (int i = 0; i < filtDB.size(); i++) {
			  if(i==0) lbl = new JLabel("Datebase");
			  else lbl = new JLabel("Filter" + i);
			  lbl.setBounds(100, 10+ i*70, 180, 40);
			  //panel_1.removeAll();
			  panel_1.add(lbl);
			  lbl.setFont(new Font("Tahoma", Font.PLAIN, 36));
			  if(i == allFilter.size()){
				  JButton undo = new JButton("X");
				  undo.setBounds(20, 10 + i*70, 60, 40);
				  undo.setForeground(Color.RED);
				  undo.setBorder(BorderFactory.createEmptyBorder());
				  undo.setOpaque(false);
				  undo.setContentAreaFilled(false);
				  undo.setFont(new Font("Tahoma", Font.PLAIN, 36));
				  undo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(allFilter.size() > 0)allFilter.remove(allFilter.size() -1);
							else {
								filesCounter =0;
								allFolders = new ArrayList<String>();
								fileInTable = new HashMap<>();
								pathToModifited = new TreeMap<String, Long>();
								fileRange = new TreeMap<Integer, Integer>();
							}
							filtDB.pop();
							display();
							//displayFilters();
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
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFillsViewportHeight(true);
		scrollPane.setBounds(0, 0, 1342, 1310);
		p.removeAll();
		p.add(scrollPane);
		
		displayFilters();
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
	
	private void fileWasUpdated(){
		
		for(Entry<String,Long> key : fileInTable.keySet()){
			
			String path = (String) key.getKey();
			File file = new File(path);
			if(file.lastModified() != (long)key.getValue()){
				 ArrayList<WifiSpots> newFile = readAgain(path);
				 int start = fileInTable.get(key).getKey();
				 int end = fileInTable.get(key).getValue();
				 if(end-start == newFile.size()-1){
					 mainDB.UpdateDB(start, end, newFile);
				 }
				 updateStack();
				 display();
			}
		}
	}

	private void folderWasUpdated(){
		
		for (int i = 0; i < allFolders.size(); i++) {
			File folder = new File(allFolders.get(i));
			File[] listOfFiles = folder.listFiles();
			for (int j = 0; j < listOfFiles.length; j++) {
				  String pathfile = listOfFiles[j].getPath();
				  boolean found = false;
				  for(Entry<String,Long> key : fileInTable.keySet()){
					   String s = (String)key.getKey();
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
			    	 	fileRange.put(0, mainDB.getDB().size()-1);
			    	    pathToModifited.put(path, file.lastModified());
			    	 	fileInTable.put(pathToModifited.lastEntry(),fileRange.lastEntry());
			         }
				 else {
					
					 mainDB.addToDB(myfile.getLittleDB());
					 fileRange.put(fileRange.lastEntry().getValue()+1,mainDB.getDB().size()-1);
					 pathToModifited.put(path, file.lastModified());
			    	 fileInTable.put(pathToModifited.lastEntry(),fileRange.lastEntry());
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
			                         fileRange.put(0, mainDB.getDB().size() -1);pathToModifited.put(path, file.lastModified());
			 			    	   	 fileInTable.put(pathToModifited.lastEntry(),fileRange.lastEntry());}
			  else{
				  mainDB.add(path);
				  fileRange.put(fileRange.lastEntry().getValue()+1,mainDB.getDB().size()-1);
				  pathToModifited.put(path, file.lastModified());
		    	 	fileInTable.put(pathToModifited.lastEntry(),fileRange.lastEntry());
				  if(allFilter.size() > 0) updateStack();
				  else{ filtDB.pop(); filtDB.push(mainDB);}
			  }
		  }
		  filesCounter++;
	}
}
