package mypack;
import java.util.ArrayList;
import java.util.HashMap;


/*
 * able to load to different csv files and compare them by specific argument 
 * realse the calculations in csv file
 */
public class testMacLocDiff {
	
    private HashMap<String,ArrayList<WifiSpot>> diff = new HashMap<String,ArrayList<WifiSpot>>();
    private HashMap<String,ArrayList<WifiSpots>> diff2 = new HashMap<String,ArrayList<WifiSpots>>();
	private HashMap<String,ArrayList<Double>> coordDiff = new HashMap<String,ArrayList<Double>>();
	private HashMap<String,ArrayList<Double>> coordDiff2 = new HashMap<String,ArrayList<Double>>();
	private ArrayList<WifiSpots> myDB;
	private ArrayList<WifiSpots> nDB;
	
	public testMacLocDiff(String pathv,String Mypathv,boolean Algo){
		
		if(Algo){
			Database mydb = new Database(Mypathv,"");
			Database db = new Database(pathv,"");
			myDB = mydb.getDB();
			nDB = db.getDB();
		}
		else{
			Database mydb = new Database(Mypathv,"WifiSpots");
			Database db = new Database(pathv,"WifiSpots");
			myDB = mydb.getDB();
			nDB = db.getDB();
		}
	}
	
	
	
	/**
	 * Compare two csv file by the coordinates(lon,lat,alt) for algo1 and create csv file with the diff data
	 * @param String name for the new csv file for comparison
	 * @return void
	 */
	public void compateFiles(String name){
		
	    double difflon =0,difflat=0,diffalt=0;
	    double totalLon = 0,totalLat=0, totalAlt =0;
		Write_csv wc = new Write_csv(name);
		int num = nDB.get(0).getSpots().size();
		for (int i = 0; i < num; i++) {
			  String mac = nDB.get(0).getSpots().get(i).getMac();
			  ArrayList<WifiSpot> s = new ArrayList<WifiSpot>();
			  WifiSpot ws = findMac(mac);
			  s.add(nDB.get(0).getSpots().get(i));
			  s.add(ws);
			  diff.put(mac,s);
			
			  difflon = Math.abs(Double.parseDouble(nDB.get(0).getSpots().get(i).getLongtitude()) - Double.parseDouble(ws.getLongtitude()));
			  difflat = Math.abs(Double.parseDouble(nDB.get(0).getSpots().get(i).getLatitude()) - Double.parseDouble(ws.getLatitude()));
			  diffalt = Math.abs(Double.parseDouble(nDB.get(0).getSpots().get(i).getAltitude()) - Double.parseDouble(ws.getAltitude()));
			  totalLon += difflon;
			  totalLat += difflat;
			  totalAlt += diffalt;
			  ArrayList<Double> diffNum = new ArrayList<Double>();
			  diffNum.add(difflon);
			  diffNum.add(difflat);
			  diffNum.add(diffalt);
			  coordDiff.put(mac, diffNum);
		}
		totalLon = totalLon / num;
		totalLat = totalLat / num;
		totalAlt = totalAlt / num;
	    
		wc.csvDifferent(totalLon, totalLat, totalAlt, diff, coordDiff);
	
	}
	
	
	/**
	 * gets mac address from nDB and search it myDB
	 * @param String mac
	 * @return WifiSpot
	 */
	private WifiSpot findMac(String mac){
		
		for (int i = 0; i < myDB.get(0).getSpots().size(); i++) {
			  if(mac.equals(myDB.get(0).getSpots().get(i).getMac()))
				  return myDB.get(0).getSpots().get(i);
		}
		return null;
	}
	
	/**
	 * gets time as key from myDB and search it nDB
	 * @param String string
	 * @return WifiSpots
	 */
	private WifiSpots findSpotTime(String time){
		
		for (int i = 0; i < nDB.size(); i++) {
			if(nDB.get(i).getFirstSeen().equals(time))
				return nDB.get(i);
		}
		return null;
	}
	
	
	/**
	 * Compare two csv file by the coordinates(lon,lat,alt) for algo2 and create csv file with the diff data
	 * @param String name for the new csv file for comparison
	 * @return void
	 */
	public void compateFilesAlgo2(String name){
		
		double difflon =0,difflat=0,diffalt=0;
	    double totalLon = 0,totalLat=0, totalAlt =0;
		Write_csv wc = new Write_csv(name);
		int Timelong =  fixTime();
		
		int num = myDB.size();
		for (int i = 0; i < num; i++) {
			String time = myDB.get(i).getFirstSeen();
			WifiSpots ws = findSpotTime(time);
			ArrayList<WifiSpots> warr = new ArrayList<WifiSpots>();
			
			warr.add(myDB.get(i));
			warr.add(ws);
			diff2.put(time,warr);
			  
			  if(!ws.getLongtitude().equals("null") && !myDB.get(i).getLongtitude().equals("null")){
			  difflon = Math.abs(Double.parseDouble(myDB.get(i).getLongtitude()) - Double.parseDouble(ws.getLongtitude()));
			  difflat = Math.abs(Double.parseDouble(myDB.get(i).getLatitude()) - Double.parseDouble(ws.getLatitude()));
			  diffalt = Math.abs(Double.parseDouble(myDB.get(i).getAltitude()) - Double.parseDouble(ws.getAltitude()));
			  }
			  else{
				  difflon= 0;
				  difflat =0;
				  diffalt = 0;
			  }
			  totalLon += difflon;
			  totalLat += difflat;
			  totalAlt += diffalt;
			  ArrayList<Double> diffNum = new ArrayList<Double>();
			  diffNum.add(difflon);
			  diffNum.add(difflat);
			  diffNum.add(diffalt);
			  coordDiff2.put(time, diffNum);
		}
		totalLon = totalLon / num;
		totalLat = totalLat / num;
		totalAlt = totalAlt / num;
		
		wc.csvDifferentX(totalLon, totalLat, totalAlt, diff2, coordDiff2,Timelong);
	}

	
	/**
	 * fix the Time in myDB and nDB to use it as a key,return the original size of the string
	 * @param void
	 * @return Integer
	 */
	private int fixTime(){
		
		int timeLong = nDB.get(0).getFirstSeen().length();
		for (int i = 0; i < nDB.size(); i++) {
			  
			  nDB.get(i).setFirstSeen(nDB.get(i).getFirstSeen() +i);
		}
		
		for (int i = 0; i < myDB.size(); i++) {
			 myDB.get(i).setFirstSeen(myDB.get(i).getFirstSeen() +i);
		}
		return timeLong;
	}
}
