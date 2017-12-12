package mypack;

import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Read_CSV_NoGPS {
	String path;
	public Read_CSV_NoGPS(String path) {
		this.path=path;
	}
	public void read(ArrayList<WifiSpots> csv){
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
					WifiSpot q=new WifiSpot(ssid,mac,time,chanel,rssi,latitude,longtitude,altitude);
					s.getSpots().add(q);
				}
				s.setAltitude(altitude);
				s.setLatitude(latitude);
				s.setLongtitude(longtitude);
				csv.add(s);
			} 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}


