package mypack;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Filter.Filter;
import Frames.myFrame;

public class Server {

	//private myFrame myframe;
    private JPanel p,panel_1;
	//private JFrame frame;
	public Database mainDB;
	 public Stack<Database> filtDB;
    public ArrayList<Filter> allFilter;
	public int filesCounter;
    NavigableMap<String,Long> pathToModifited = new TreeMap<String, Long>();
	private JLabel lblNumberOfMacs,lblNumberOfLines;
	 public ArrayList<String> allFolders = new ArrayList<String>();
	
	public Server(JLabel macs,JLabel line,JPanel p,JPanel p1){
			this.lblNumberOfLines =line;
			this.lblNumberOfMacs = macs;
			this.p=p;
			this.panel_1 = p1;
		    filtDB = new Stack<Database>();
			allFilter = new ArrayList<Filter>();
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
				  JButton importFilter = new JButton("Export");
				  
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
	
	public void display(){
		
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
	
	public ArrayList<String> getAllPaths(String path)  throws DataException{
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
	
	public void updateStack(){
		
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
	
	public int cutLastinPath(String s){
		int index = -1;
		for (int i = 0; i < s.length(); i++) {
			  if(s.charAt(i) == '\\')
					  index =i;
		}
		return index;
	}
	
	public void fileWasUpdated(){
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
	
	public void folderWasUpdated(){
		
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
	
	public void addFile(String path){
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
