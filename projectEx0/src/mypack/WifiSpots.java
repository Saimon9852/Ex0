package mypack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
/**
 * holds sufficient information to describe a collection of wifi spots.
 * a collection of wifi spots can have shared properties.
 * we use the ArrayList data Structure to implement the collection.
 * the function set wifi spots uses the commons CSV API inorder to pull the information from the table.
 * @author 
 *
 */
public class WifiSpots {
	private String FirstSeen;
	private String Altitude;
	private String Longtitude;
	private String ID;
	private String Latitude;
	public ArrayList<WifiSpot> spots=new ArrayList<WifiSpot>();

	WifiSpots(String path){
		setWifiSpots(path);
		Collections.sort(spots,new CustomComparator());

	}
	public void wifisort(){
		Collections.sort(spots,new CustomComparator());

	}

	WifiSpots(){
	}

	public String getFirstSeen() {
		return FirstSeen;
	}
	public void setFirstSeen(String string) {
		FirstSeen = string;
	}
	public String getAltitude() {
		return Altitude;
	}
	public void setAltitude(String altitude) {
		Altitude = altitude;
	}
	public String getLongtitude() {
		return Longtitude;
	}
	public void setLongtitude(String longtitude) {
		Longtitude = longtitude;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public ArrayList<WifiSpot> getSpots() {
		return spots;
	}

	public void setSpots(ArrayList<WifiSpot> spots) {
		this.spots = spots;
	}




	/**
	 * for debugging use mostly. prints the spots collection
	 */
	public void toPrint() {
		System.out.println(ID);
		for(int i=0;i<spots.size();i++){
			System.out.println(spots.get(i).toString());
		}
	}
	//reading the csv using commonns api. more info can be found on their website
	void setWifiSpots(String path){
		try{
			FileReader in = new FileReader(path);
			BufferedReader br = new BufferedReader(in);
			ID=br.readLine().split(",")[2].split("=")[1];
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(br);
			for (CSVRecord record : records) {
				String ssid = record.get("SSID");
				String mac = record.get("MAC");
				String firstseen = record.get("FirstSeen");
				String chanel=record.get("Channel");
				String rssi=record.get("RSSI");
				String latitude=record.get("CurrentLatitude");
				String longtitude=record.get("CurrentLongitude");
				String altitude=record.get("AltitudeMeters");
				String type=record.get("Type");
				if(type.equals("WIFI")){
					WifiSpot e=new WifiSpot(ssid,mac,firstseen,chanel,rssi,latitude,longtitude,
							altitude);
					spots.add(e);
					/*System.out.println(e);
					 */

				}
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}



