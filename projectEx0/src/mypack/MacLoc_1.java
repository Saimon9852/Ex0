package mypack;

import java.util.ArrayList;
import java.util.Collections;

public class MacLoc_1 {

	ArrayList<WifiSpots> DB;
	ArrayList<WifiSpot> dbsamp;
	int samples;
	String macAddress;
	
	
	public MacLoc_1(int sam,Database db,String mac)
	{
		DB = db.getDB();
		dbsamp = new ArrayList<WifiSpot>();
		samples = sam;
		macAddress = mac;
		Findmacim();
		try {
			checkInput();
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkInput() throws DataException{
		if(samples>DB.size()){
			throw new DataException("more samples then data base size");
		}
	}
	private void Findmacim(){
		
		ArrayList<WifiSpot> macDb = new ArrayList<WifiSpot>();
		
		for (int i = 0; i < DB.size(); i++) {
			   for (int j = 0; j < DB.get(i).getSpots().size(); j++) {
				    if(macAddress.equals(DB.get(i).getSpots().get(j).getMac()))
				    	macDb.add(DB.get(i).getSpots().get(j));
			}
		}
		
		Collections.sort(macDb,new CustomComparator());
		
		for (int i = 0; i < samples &&i<DB.size(); i++) {
			  dbsamp.add(macDb.get(i));
		}
	}
	private void algorithm(){
		
	}
}
