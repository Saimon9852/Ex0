package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Comparator.CompareMac;
import mypack.DataException;
import mypack.Database;
import mypack.WifiSpot;
import mypack.WifiSpots;
import mypack.Write_csv;
/**
 * this class is for asserting the location of a Router.
 * @author Ehud Plaksin, Saimon Lankry
 *
 */
public class MacLoc_1 {
	
	private double power;
	
	
	//the data base we will use for the samples.
	ArrayList<WifiSpots> DB;
	//holds the "strongest macs".
	HashMap<String,ArrayList<WifiSpot>> macMap;
	//the number of samples we will use for the caculation.
	int samples;
	//the mac address of the router we will try to determine the location of.
	String macAddress;
	//
	ArrayList<WifiSpot> macList; 

	/**
	 * the constructor, we also call the functions Findmacim to set dbsamp.
	 * and the algorithm to get the desired location.
	 * @param sam
	 * @param db
	 * @param mac
	 */
	public MacLoc_1(int sam,Database db)
	{
		DB = db.getDB();
		macMap=db.getMacim();
		samples = sam;
		power = 2.0;
		try
		{
			checkInput();
		} 
		catch (DataException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public void setPower(double pow){
		power = pow;
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

	public WifiSpot algorithm(String MacAddress){
		
		
		macAddress = MacAddress;
		double lat=0, lon=0, alt=0,weight=0,sWeight=0;
		//Sorting the arraylists of each mac, so that we can take the Strongest ones easily.
		Collections.sort(macMap.get(macAddress),new CompareMac());
		ArrayList<WifiSpot> cur=macMap.get(macAddress);
		//the algorithm itself
		for(int i=0;i<cur.size()&&i<samples;i++){
			weight=1/Math.pow(Double.parseDouble(cur.get(i).getRssi()), power);
			sWeight=sWeight+weight;
			lat=lat+Double.parseDouble(cur.get(i).getLatitude())*(weight);
			lon=lon+Double.parseDouble(cur.get(i).getLongtitude())*(weight);
			alt=alt+Double.parseDouble(cur.get(i).getAltitude())*(weight);
		}
		lat=lat/sWeight;
		lon=lon/sWeight;
		alt=alt/sWeight;
		WifiSpot s=new WifiSpot(cur.get(cur.size()-1).getSsid(),macAddress,
				cur.get(0).getFirsseen(),
				cur.get(0).getChanel(),
				cur.get(0).getRssi(),String.valueOf(lat),
				String.valueOf(lon),String.valueOf(alt));
		
		
		return s;
	}
	/**
	 * writes a csv file with the results of using the algorithm on every mac.
	 * @param fileName
	 */
	public void algorithmOnAllMac(String fileName){
		
		ArrayList<WifiSpot> a = new ArrayList<WifiSpot>();
		ArrayList<String> holdMacs = getAllMacs();
		for (int i = 0; i < holdMacs.size(); i++) {
			a.add(algorithm(holdMacs.get(i)));
		}
		
		Write_csv wc = new Write_csv(fileName);
		wc.wifispotToCSV(a);
		
	}
	/**
	 * returns an array list of all macs in hash map.
	 * @return
	 */
	private ArrayList<String> getAllMacs(){
		ArrayList<String> allmac = new ArrayList<String>();
		for ( String key : macMap.keySet() ) {
		      allmac.add(key);
		}
		return allmac;
	}
	
}
