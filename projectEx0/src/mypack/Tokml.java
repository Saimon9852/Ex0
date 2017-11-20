package mypack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.TimePrimitive;
/**
 * this class is used to write Kml files
 * the class varialbes DB and macim are used to hold the data we want to write.
 * macim holds the location and singal streght of the mac adresses in the scans.
 * it holds 1 instance of each mac adress it holds the location where it is the Strongest
 * @author Rachel Plaksin
 *
 */
public class Tokml {
	ArrayList<WifiSpots> DB=new ArrayList<WifiSpots>();
	ArrayList<WifiSpot> macim=new ArrayList<WifiSpot>();
	String path;

	public Tokml(String path){
		this.path=path;
		readcsv(path);
		macim=Macim(DB);
	}

	public ArrayList<WifiSpots> getDB() {
		return DB;
	}


	public void setDB(ArrayList<WifiSpots> dB) {
		DB = dB;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
	 * 
	 * @param get the ArrayList<WifiSpots> c and set it by the strongest macs with the best signal
	 * @return ArrayList<WifiSpots>
	 */
	public ArrayList<WifiSpot> Macim(ArrayList<WifiSpots> c){

		ArrayList<WifiSpot> macim=new ArrayList<WifiSpot>();
		for(int i=0;i<c.size();i++){
			for(int j=0;j<c.get(i).getSpots().size();j++){
				int check=sMacim(c.get(i).getSpots().get(j).mac,macim);
				if(check==-1){
					c.get(i).getSpots().get(j).setAltitude(c.get(i).getAltitude());
					c.get(i).getSpots().get(j).setLongtitude(c.get(i).getLongtitude());
					c.get(i).getSpots().get(j).setLatitude(c.get(i).getLatitude());
					macim.add(c.get(i).getSpots().get(j));
				}else{
					if(Integer.parseInt(macim.get(check).getRssi())
							>Integer.parseInt(c.get(i).getSpots().get(j).getRssi())){
						macim.get(check).setAltitude(c.get(i).getLongtitude());
						macim.get(check).setLatitude(c.get(i).getLatitude());
						macim.get(check).getLongtitude();
						macim.get(check).setRssi(c.get(i).getSpots().get(j).getRssi());
					}
				}
			}
		}
		return macim;

	}
	
	//searches for the mac address in the macim data structure
	//returns the index or -1 if not found.
	public int sMacim(String mac,ArrayList<WifiSpot> macim){
		for(int i=0;i<macim.size();i++){
			if(mac.equals(macim.get(i).getMac())){
				return i;
			}
		}return -1;
	}
	
		
	
	/**
	 * 
	 * @param get String byFilt,startRange and endRange: choose the correct filter by wordKey(byFilt),and take the ranges for specific users filt
	 * @return ArrayList<WifiSpots>
	 */
	public void CreateKmlByFilter(String name){

		
		String[] filtersKind = {"None","Time","Lat","Lon","Alt","inRadius"};
		final Kml kml = new Kml();
		Document doc=kml.createAndSetDocument();
		int k = typeOfSort(filtersKind);
		boolean istime = k==1;
		
		String[] values = new String [3];
		if(k!=0) values = validValues(k);
		double rad = -1;
		if(k==5) rad = Double.parseDouble(values[2]);
		
		for (int j = 0; j < DB.size(); j++) {
			boolean check;
			if(k!=0){
				String[] hold = selectByFilter(k,j);
				check=inRange(values[0],values[1],hold,istime,rad);
			}
			else check = true;
			
			if(check){
				
				Placemark p = doc.createAndAddPlacemark();
				p.createAndSetTimeStamp().withWhen(DB.get(j).getFirstSeen().replace(' ', 'T'));
				p.withName(DB.get(j).getSpots().get(0).getSsid()).withDescription("sup").withOpen(Boolean.TRUE)
				   .createAndSetPoint().addToCoordinates(Double.parseDouble(DB.get(j).getLongtitude()), 
						   Double.parseDouble(DB.get(j).getLatitude()),Double.parseDouble(DB.get(j).getAltitude()));
				
			}
		}
			
		try {
			
			kml.marshal(new File(name+".kml"));
			System.out.println(name + ".kml was created successfully, By filter " + filtersKind[k]);
			
		}
		catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}

	}
 
	private int typeOfSort(String[] filtersKind){
		
		String allfilt = "";
		for (int i = 0; i < filtersKind.length; i++) {
			   if(i+1<filtersKind.length)
				   allfilt += filtersKind[i] + ",";
			   else allfilt += filtersKind[i];
		}
		boolean b = false;
		String filter = "";
		int index = 0;
		while(!b){
			  System.out.println("Choose type of filter:" + allfilt);
				Scanner scanner = new Scanner(System.in);
				filter = scanner.nextLine();
				for (int i = 0; i < filtersKind.length; i++) {
					   if(filter.equals(filtersKind[i])){
						   b= true;
						   index = i;
					   }
				}
				if(b==false)System.out.println("The filter is not exist in the list");
		  
		}
		
		return index;
	}

	private String[] validValues(int typeFilt){
		
		String c = "";
		int val1 =0,val2=0,val3=0,val4 =0;
		String s = "";
		if(typeFilt==1){val2 = 59;val4=24; c = ":"; s= "Please enter the hour the scan was taken in the following format <hr>:<mins>"; }
		else if(typeFilt == 2){val1 = -180;val2=180;val3=-180;val4=180; c="-";s= "Please insert an Latitude range with the format NUMBER-NUMBER(in meters)";}
		else if(typeFilt ==3){val1=-90;val2=90;val3=-90;val4=90; c="-";s = "Please insert an Longtitude range with the format NUMBER-NUMBER(in meters between -90 to 90)";}
		else if(typeFilt == 4){val1=-300;val2=15000;val3=-300;val3=15000; c="-";s="Please insert an Altitude range with the format NUMBER-NUMBER(in meters)";}
		else {c=","; s = "Please insert coordinates and radius in the format: Longtitude,Latitude,Radius";}
		
		
		System.out.println(s);
		String[] test = new String[2];
		boolean btest=false;
		String filter="";
		try{
			while(btest==false){
				Scanner scanner = new Scanner(System.in);
				filter = scanner.nextLine();
				int a = howManyexist(filter,c.charAt(0));
				test=filter.split(c);
				boolean isNum = isArrInt(test);
				if(a==1 && isNum)
				{
					if((Double.parseDouble(test[1])>=val1&&Double.parseDouble(test[1])<=val2)
							&&Double.parseDouble(test[1])>=val3&&Double.parseDouble(test[1])<=val4){
						btest=true;
					}
					else{
						System.out.println("the input format is incorrect");
					}
				}
				else if(a==2 && isNum)
				{
					if((Double.parseDouble(test[0]) >= -90 && Double.parseDouble(test[0]) <= 90)
							&& (Double.parseDouble(test[1]) >= -180 && Double.parseDouble(test[1]) <= 180)
							&& Double.parseDouble(test[2]) > 0)
					btest = true;
					
				}
				else
					System.out.println("the input format is incorrect");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		
		return test;
		
		
	}
		
	private boolean isArrInt(String[] st){
		
		for (int i = 0; i < st.length; i++) {
			 try 
			 {  
		         Double.parseDouble(st[i]);  
		         
		      } catch (NumberFormatException e) {  
		         return false;  
		      }
		}
		return true;
	}
	
	private boolean inCircle(String x1,String y1,String x2,String y2,double radius){
	
		 return distance(Double.parseDouble(x1),Double.parseDouble(y1),Double.parseDouble(x2),
				 Double.parseDouble(y2)) <= radius;
	}

	private double distance(double lat1, double lon1, double lat2, double lon2) {
		
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		return dist * 60 * 1.1515 * 1.609344;
	}
	
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	
	/**
	 * 
	 * @param gets Integer k, and Integer index , k present which filter is been done and index send for drawing the right data from the DB
	 * @return String 
	 */
	private String[] selectByFilter(int k ,int index){

		String[] s = new String[2];
		s[1] = "TEST";
		if(k==4) s[0] = DB.get(index).getAltitude();
		if(k==3) s[0] = DB.get(index).getLongtitude();
		if(k==2) s[0] = DB.get(index).getLatitude();
		if(k==1) s[0] = DB.get(index).getFirstSeen();
		if(k==5) {s[0] = DB.get(index).getLongtitude();s[1]= DB.get(index).getLatitude();}
		
		return s;
	}

	/**
	 * 
	 * @param gets String start,end,bet,boolean b: if b=false using Coordinates filter else time filter,start and end present the ranges from the user,
	 * bet shows the data from the DB and checked for his stand in conditions
	 * @return boolean 
	 */
	private boolean inRange(String start,String end,String[] bet,boolean b,double rad){

		if(!b && bet[1].equals("TEST"))
		{
			double low = (Double.parseDouble(start));

			double big = (Double.parseDouble(end));
			double mid = (Double.parseDouble(bet[0]));
			return big>=mid && mid>=low;
		}
		
		if(bet[1].equals("TEST") == false)
		{
			return inCircle(start,end,bet[0],bet[1],rad);
		}
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm"); 
		Date betDate;
		Date startDate;
		Date endDate;
		try {
		    betDate = df.parse(bet[0]);
		    startDate=df.parse(start);
		    endDate=df.parse(end);
		    if(startDate.after(betDate)&&endDate.before(endDate)){
				return true;
		    }
		    String newDateString = df.format(betDate);
		    System.out.println(newDateString);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return false;


	}


	/**
	 * 
	 * @param get String s and char symbol, check how many times the char symbol exists in the string s
	 * @ Integer
	 */
	private int howManyexist(String s , char symbol){

		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == symbol){
				count++;
			}
		}
		return count;
	}
}



