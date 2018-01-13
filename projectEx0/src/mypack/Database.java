package mypack;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
/*
 * holds all of our samples that we extracted from the csv files and processed.
 * macim holds the strongest macs.
 */
public class Database {

	private ArrayList<WifiSpots> DB=new ArrayList<WifiSpots>();
	private HashMap<String,ArrayList<WifiSpot>> macim=new HashMap<>();
	private HashMap<String,SQL_Server> server_watch=new HashMap<>();


	public Database(String path,String format){
		
		if(format.equals("WifiSpots")){
		    add(path);
		}
		else
			readcsvWifiSpot(path);
			
		macim=Macim(DB);
	}
	
	public HashMap<String, SQL_Server> getServer_watch() {
		return server_watch;
	}

	public void setServer_watch(HashMap<String, SQL_Server> server_watch) {
		this.server_watch = server_watch;
	}

	public void setDB(ArrayList<WifiSpots> dB) {
		DB = dB;
	}

	public void setMacim(HashMap<String, ArrayList<WifiSpot>> macim) {
		this.macim = macim;
	}

	public Database(ArrayList<WifiSpots> upDb){
		 DB = upDb;
		 macim = Macim(DB);
	}
	
	public void addToDB(ArrayList<WifiSpots> holdDb)
	{
		for (int i = 0; i < holdDb.size(); i++) {
			 DB.add(holdDb.get(i));
		}
		macim = Macim(DB);
	}

	public ArrayList<WifiSpots> getDB() {
		return DB;
	}
	public HashMap<String,ArrayList<WifiSpot>> getMacim(){
		return macim;
	}

	/**
	 * // we read the csv we wrote in the csv class.
	 * @param gets path of a csv file and read him to ArrayList<WifiSpots> DB
	 * @return void function
	 */
	public void add(String path){
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
				s.setFrom(path);
				s.setAltitude(altitude);
				s.setFirstSeen(time);
				s.setLongtitude(longtitude);
				s.setLatitude(latitude);
				for(int i=1;i<=Integer.parseInt(wifi);i++){
					String ssid = record.get("SSID"+i);
					String mac = record.get("MAC"+i);
					String chanel=record.get("Frequancy"+i);
					String rssi=record.get("Signal"+i);
					WifiSpot q=new WifiSpot(ssid,mac,time,chanel,rssi,latitude,longtitude,altitude);
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
	private HashMap<String,ArrayList<WifiSpot>> Macim(ArrayList<WifiSpots> c){

		HashMap<String,ArrayList<WifiSpot>> macim=new HashMap<>();
		for(int i=0;i<c.size();i++){
			for(int j=0;j<c.get(i).getSpots().size();j++){
				boolean check=sMacim(c.get(i).getSpots().get(j).getMac(),macim);
				if(check==true){
					macim.get(c.get(i).getSpots().get(j).getMac()).add(c.get(i).getSpots().get(j));
				}else{
					ArrayList<WifiSpot> macs=new ArrayList<>();
					macs.add(c.get(i).getSpots().get(j));
					macim.put(c.get(i).getSpots().get(j).getMac(), macs);
				}
			}
		}return macim;
	}	
	

	//searches for the mac address in the macim data structure
	//returns the index or -1 if not found.
	private boolean sMacim(String mac,HashMap<String,ArrayList<WifiSpot>> macim){
		if(macim.containsKey(mac)){
			return true;
		}return false;
	}
	
	
	/**
	 * // we read the csv we wrote in the csv class.
	 * @param gets path of a csv file and read him to ArrayList<WifiSpots> DB
	 * @return void function
	 */
	private void readcsvWifiSpot(String path){
		try{
			FileReader in = new FileReader(path);
			ArrayList<WifiSpot> readWifi = new ArrayList<WifiSpot>();
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				
				String latitude=record.get("Lat");
				String longtitude=record.get("Lon");
				String altitude=record.get("Alt");
				String mac = record.get("MAC");
				WifiSpot q=new WifiSpot("",mac,"","","",latitude,longtitude,altitude);
                readWifi.add(q);
				
			} 
			DB.add(new WifiSpots(readWifi));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	



}
