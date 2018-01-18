package main.java.mypack;

import java.util.ArrayList;
/**
 * for reading and storing Csv of Wifispots without location.
 *
 */
public class Csv_noGPS {
	ArrayList<WifiSpots>csv=new ArrayList<>();
	public String path;
	public Csv_noGPS(String path) {
		this.path=path;
	}
	public void fill(){
		Read_CSV_NoGPS reader=new Read_CSV_NoGPS(path);
		reader.read(csv);
	}
	public ArrayList<WifiSpots> getCsv(){
		return csv;
	}

}
