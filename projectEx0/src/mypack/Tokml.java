package mypack;

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
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.TimePrimitive;
/**
 * this class is used to write Kml files
 * the class varialbes DB and macim are used to hold the data we want to write.
 * macim holds the location and singal streght of the mac adresses in the scans.
 * it holds 1 instance of each mac adress it holds the location where it is the Strongest
 * @author Rachel Plaksin
 *
 */
public class Tokml {

	private ArrayList<WifiSpots> DB=new ArrayList<WifiSpots>();
	private ArrayList<WifiSpot> macim=new ArrayList<WifiSpot>();
	public Tokml(){
		
	}
	public Tokml(String path){
		readcsv(path);
		macim=Macim(DB);
	}

	public ArrayList<WifiSpots> getDB() {
		return DB;
	}
	public ArrayList<WifiSpot>getMacim(){
		return macim;
	}
	

	/**
	 * // we read the csv we wrote in the csv class.
	 * @param gets path of a csv file and read him to ArrayList<WifiSpots> DB
	 * @return void function
	 */
	private void readcsv(String path){
		try{
			FileReader in = new FileReader(path);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				WifiSpots s=new WifiSpots();
				String wifi=record.get("#WiFi networks");
				String latitude=record.get("Lat");
				String longtitude=record.get("Lon");
				String altitude=record.get("Alt");
				String time=record.get("Time");
				String id=record.get("ID");
				s.setID(id);
				s.setAltitude(altitude);
				s.setFirstSeen(time);
				s.setLongtitude(longtitude);
				s.setLatitude(latitude);
				for(int i=1;i<=Integer.parseInt(wifi);i++){
					String ssid = record.get("SSID"+i);
					String mac = record.get("MAC"+i);
					String chanel=record.get("Frequancy"+i);
					String rssi=record.get("Signal"+i);
					WifiSpot q=new WifiSpot(ssid,mac,chanel,rssi);
					s.getSpots().add(q);
				}
				s.setAltitude(altitude);
				s.setLatitude(latitude);
				s.setLongtitude(longtitude);
				DB.add(s);
			} 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param get the ArrayList<WifiSpots> c and set it by the strongest macs with the best signal
	 * @return ArrayList<WifiSpots>
	 */
	private ArrayList<WifiSpot> Macim(ArrayList<WifiSpots> c){

		ArrayList<WifiSpot> macim=new ArrayList<WifiSpot>();
		for(int i=0;i<c.size();i++){
			for(int j=0;j<c.get(i).getSpots().size();j++){
				int check=sMacim(c.get(i).getSpots().get(j).getMac(),macim);
				if(check==-1){
					c.get(i).getSpots().get(j).setAltitude(c.get(i).getAltitude());
					c.get(i).getSpots().get(j).setLongtitude(c.get(i).getLongtitude());
					c.get(i).getSpots().get(j).setLatitude(c.get(i).getLatitude());
					c.get(i).getSpots().get(j).setFirsseen(c.get(i).getFirstSeen());
					macim.add(c.get(i).getSpots().get(j));
				}else{
					if(Integer.parseInt(macim.get(check).getRssi())
							>Integer.parseInt(c.get(i).getSpots().get(j).getRssi())){
						macim.get(check).setAltitude(c.get(i).getLongtitude());
						macim.get(check).setLatitude(c.get(i).getLatitude());
						macim.get(check).setFirsseen(c.get(i).getFirstSeen());
						macim.get(check).getLongtitude();
						macim.get(check).setRssi(c.get(i).getSpots().get(j).getRssi());
					}
				}
			}
		}	return macim;
	}
	//searches for the mac address in the macim data structure
	//returns the index or -1 if not found.
	private int sMacim(String mac,ArrayList<WifiSpot> macim){
		for(int i=0;i<macim.size();i++){
			if(mac.equals(macim.get(i).getMac())){
				return i;
			}
		}return -1;
	}

	/**
	 * @param get String byFilt,startRange and endRange: choose the correct filter by wordKey(byFilt),and take the ranges for specific users filt
	 * @return ArrayList<WifiSpots>
	 * @throws DataException 
	 */
	public void CreateKmlByFilter(String name) throws DataException{
		boolean isEmpty=false;
		try {
			DateFormat format=new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date d;
			boolean check=false;
			String tFilter=userInput();
			final Kml kml = new Kml();
			Document doc=kml.createAndSetDocument();
		
			switch(tFilter){
			case "Location":LocationFilter.SetData();
			break;
			case "Date":TimeFilter.setfrom();
			TimeFilter.setTo();
			break;
			case "Id":IdFilter.SetData();
			}
			
			for (int j = 0; j < DB.size(); j++) {
				switch(tFilter){
				case "Location": check=LocationFilter.Filt(Double.parseDouble(DB.get(j).getLatitude())
						,Double.parseDouble(DB.get(j).getLongtitude()));
				break;
				case "Date": check=TimeFilter.Filt(d=format.parse(DB.get(j).getFirstSeen()));
				break;
				case "Id":check=IdFilter.Filt(DB.get(j).getID());
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

			for (int i = 0; i < macim.size(); i++) {
				switch(tFilter){
				case "Location": check=LocationFilter.Filt(Double.parseDouble(macim.get(i).getLatitude()),
						Double.parseDouble(macim.get(i).getLongtitude()));
				break;
				case "Date": check=TimeFilter.Filt(d=format.parse(macim.get(i).getFirsseen()));
				break;
				case "Id":check=IdFilter.Filt(macim.get(i).getSsid());
				break;
				default: check=true;
				}
				if(check){
					isEmpty=true;
					Placemark p = doc.createAndAddPlacemark();
					p.createAndSetTimeStamp().withWhen(macim.get(i).getFirsseen().replace(' ','T'));
					p.withName(macim.get(i).getMac()).withDescription(macim.get(i).getRssi())
					.createAndSetPoint().addToCoordinates(Double.parseDouble(macim.get(i).getLongtitude()), 
							Double.parseDouble(macim.get(i).getLatitude()),Double.parseDouble(macim.get(i).getAltitude()));	
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