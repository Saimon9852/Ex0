package mypack;

import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Database {
	
	private ArrayList<WifiSpots> DB=new ArrayList<WifiSpots>();
	private ArrayList<WifiSpot> macim=new ArrayList<WifiSpot>();
	
	
	public Database(String path){
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


}
