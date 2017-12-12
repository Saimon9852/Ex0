package mypack;

import java.util.ArrayList;

public class Csv_noGPS {
	ArrayList<WifiSpots>csv=new ArrayList<>();
	String path;
	public Csv_noGPS(String path) {
		this.path=path;
	}
	void flil(){
		Read_CSV_NoGPS reader=new Read_CSV_NoGPS(path);
		reader.read(csv);
	}
	public ArrayList<WifiSpots> getCsv(){
		return csv;
	}

}
