package main.java.mypack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.TimePrimitive;
import main.java.Filter.*;
/**
 * this class is used to write Kml files
 * the class varialbes DB and macim are used to hold the data we want to write.
 * macim holds the location and singal streght of the mac adresses in the scans.
 * it holds 1 instance of each mac adress it holds the location where it is the Strongest
 * @author Ehud Plaksin, Saimon Lankry
 *
 */
public class Tokml {

	private ArrayList<WifiSpots> DB=new ArrayList<WifiSpots>();
	private HashMap<String,ArrayList<WifiSpot>> macim=new HashMap<>();
	Database db;
	public Tokml(){
		
	}
	public Tokml(String path){
	    db = new Database(path,"WifiSpots");
		DB = db.getDB();
		macim= db.getMacim();
	}

	public Tokml(Database mydb){
	    db = mydb;
		DB = db.getDB();
		macim= db.getMacim();
	}
	
	public ArrayList<WifiSpots> getDB() {
		return DB;
	}
	public HashMap<String,ArrayList<WifiSpot>> getMacim(){
		return macim;
	}
	public Database getdb(){
		return db;
	}
	
	/**
	 * @param get String byFilt,startRange and endRange: choose the correct filter by wordKey(byFilt),and take the ranges for specific users filt
	 * @return ArrayList<WifiSpots>
	 * @throws DataException 
	 */
	public void CreateKmlByFilter(String name) throws DataException{
		boolean isEmpty=false;
		try {
			 
			boolean check=false;
			String tFilter=userInput();
			final Kml kml = new Kml();
			Document doc=kml.createAndSetDocument();
			Filter f=new FakeFilter();
			switch(tFilter){
			case "Location": f=new LocationFilter();
			break;
			case "Date":f=new TimeFilter();
			
			break;
			case "Id":f=new IdFilter();
			}
			
			for (int j = 0; j < DB.size(); j++) {
				switch(tFilter){
				case "Location": check=f.Filt(DB.get(j));
				break;
				case "Date": check=f.Filt(DB.get(j));
				break;
				case "Id":check=f.Filt(DB.get(j));
				break;
				default: check=true;
				}

				if(check){
					isEmpty=true;
					Placemark p = doc.createAndAddPlacemark();
					p.createAndSetTimeStamp().withWhen(DB.get(j).getFirstSeen().replace(' ', 'T'));
					p.withName(DB.get(j).getID()).withDescription(DB.get(j).getSpots().get(0).getSsid())
					.createAndSetPoint().addToCoordinates(Double.parseDouble(DB.get(j).getLongtitude()), 
							Double.parseDouble(DB.get(j).getLatitude()),Double.parseDouble(DB.get(j).getAltitude()));
				}

			}

			for (String key : macim.keySet()) {
				switch(tFilter){
				case "Location": check=f.Filt(macim.get(key).get(0));
				break;
				case "Date": check=f.Filt(macim.get(key).get(0));
				break;
				default: check=true;
				}
				if(check){
					isEmpty=true;
					Placemark p = doc.createAndAddPlacemark();
					p.createAndSetTimeStamp().withWhen(macim.get(key).get(0).getFirsseen().replace(' ','T'));
					p.withName(macim.get(key).get(0).getMac()).withDescription(macim.get(key).get(0).getRssi())
					.createAndSetPoint().addToCoordinates(Double.parseDouble(macim.get(key).get(0).getLongtitude()), 
							Double.parseDouble(macim.get(key).get(0).getLatitude()),Double.parseDouble(macim.get(key).get(0).getAltitude()));	
				}  
			}

			kml.marshal(new File(name+".kml"));
			System.out.println(name + ".kml was created successfully, By filter " + tFilter);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if(isEmpty==false){
			throw new DataException("the filter requested didnot match any result");
		}

	}

	
	public void CreateKmlByFilter(String name,JFrame frame,String path) throws DataException{
		boolean isEmpty=false;
		try {
			 
			boolean check=true;
			final Kml kml = new Kml();
			Document doc=kml.createAndSetDocument();
			for (int j = 0; j < DB.size(); j++) {
				
				if(check){
					isEmpty=true;
					Placemark p = doc.createAndAddPlacemark();
					p.createAndSetTimeStamp().withWhen(DB.get(j).getFirstSeen().replace(' ', 'T'));
					p.withName(DB.get(j).getID()).withDescription(DB.get(j).getSpots().get(0).getSsid())
					.createAndSetPoint().addToCoordinates(Double.parseDouble(DB.get(j).getLongtitude()), 
							Double.parseDouble(DB.get(j).getLatitude()),Double.parseDouble(DB.get(j).getAltitude()));
				}

			}

			for (String key : macim.keySet()) {
				
				if(check){
					isEmpty=true;
					Placemark p = doc.createAndAddPlacemark();
					p.createAndSetTimeStamp().withWhen(macim.get(key).get(0).getFirsseen().replace(' ','T'));
					p.withName(macim.get(key).get(0).getMac()).withDescription(macim.get(key).get(0).getRssi())
					.createAndSetPoint().addToCoordinates(Double.parseDouble(macim.get(key).get(0).getLongtitude()), 
							Double.parseDouble(macim.get(key).get(0).getLatitude()),Double.parseDouble(macim.get(key).get(0).getAltitude()));	
				}  
			}

			kml.marshal(new File(path,name+".kml"));
			JOptionPane.showMessageDialog(frame, name + ".kml was created successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if(isEmpty==false){
			throw new DataException("the filter requested didnot match any result");
		}

	}

	
	
	public String userInput(){
		Scanner sc=new Scanner(System.in);
		System.out.println("Press 1 for Date Filter,Press 2 for Location Filter,Press 3 for Id filter"
				+ "Press anykey for unFilterd.");
		String input=sc.nextLine();
		switch(Integer.parseInt(input)){
		case 1: return "Date";
		case 2:return "Location";
		case 3:return "Id";
		default:return "None";
		}
	}





}