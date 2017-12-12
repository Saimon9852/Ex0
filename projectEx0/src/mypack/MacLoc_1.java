package mypack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
/**
 * this class is for asserting the location of a Router.
 * @author Ehud Plaksin
 *
 */
public class MacLoc_1 {
	//the data base we will use for the samples.
	ArrayList<WifiSpots> DB;
	//holds the "strongest macs".
	HashMap<String,ArrayList<WifiSpot>> macMap;
	//the number of samples we will use for the caculation.
	int samples;
	//the mac address of the router we will try to determine the location of.
	String macAddress;

	/**
	 * the constructor, we also call the functions Findmacim to set dbsamp.
	 * and the algorithm to get the desired location.
	 * @param sam
	 * @param db
	 * @param mac
	 */
	public MacLoc_1(int sam,Database db,String mac)
	{
		DB = db.getDB();
		macMap=db.getMacim();
		samples = sam;
		macAddress = mac;
		try {
			checkInput();
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * checking that we have enough samples.
	 * @throws DataException
	 */
	private void checkInput() throws DataException{
		if(samples>DB.size()){
			throw new DataException("more samples then data base size");
		}
	}
	/**
	 * finds the k strongest macs.
	 */
	
	public void algorithm(){
		double lat=0, lon=0, alt=0,weight=0,sWeight=0;
		Collections.sort(macMap.get(macAddress),new CompareMac());
		ArrayList<WifiSpot> cur=macMap.get(macAddress);
		for(int i=0;i<cur.size()&&i<samples;i++){
			weight=1/Math.pow(Double.parseDouble(cur.get(i).getRssi()), 2);
			sWeight=sWeight+weight;
			lat=lat+Double.parseDouble(cur.get(i).getLatitude())*(weight);
			lon=lon+Double.parseDouble(cur.get(i).getLongtitude())*(weight);
			alt=alt+Double.parseDouble(cur.get(i).getAltitude())*(weight);
		}
		lat=lat/sWeight;
		lon=lon/sWeight;
		alt=alt/sWeight;
		WifiSpot s=new WifiSpot(cur.get(cur.size()-1).getSsid(),macAddress,
				cur.get(cur.size()-1).getFirsseen(),
				cur.get(cur.size()-1).getChanel(),
				cur.get(cur.size()-1).getRssi(),String.valueOf(lat),
				String.valueOf(lon),String.valueOf(alt));
		System.out.println(s.toString());
	}
}
