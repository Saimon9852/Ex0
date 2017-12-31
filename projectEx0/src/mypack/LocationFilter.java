package mypack;

import java.util.Scanner;
/*
 * we use this class as a tool,to filter our database.
 * we filter by a geographical point, and a radius.
 * we then only take samples that are in the radius,
 * and dont use the ones that outside of it.
 */
public class LocationFilter implements Filter{
	static double Latitude=35.23583;
	static double Longtitude=31.77617;
	static double Radius=5;

	/*
	 * input:latitude and longtitude of the point we are going to apply the filter to
	 * output:boolean value coressponding wheater the point is in the radius or not.
	 */
	public boolean  Filt(WifiSpots s){
		if(distance(Latitude,Longtitude,Double.parseDouble(s.getLatitude()),Double.parseDouble(s.getLongtitude()))<=Radius)return true;
		return false;
	}
	public boolean  Filt(WifiSpot s){
		if(distance(Latitude,Longtitude,Double.parseDouble(s.getLatitude()),Double.parseDouble(s.getLongtitude()))<=Radius)return true;
		return false;
	}

	/*
	 * getting the user input to set the local class variables.
	 * we also check the input.
	 */
	public  LocationFilter(){
		try {	
			Scanner sc=new Scanner(System.in);
			System.out.println("please enter the Latitude coordinate");
			double tLatitude=sc.nextDouble();
			System.out.println("please enter the Longtitude coordinate");
			double tLongtitude=sc.nextDouble();
			System.out.println("please enter the Radius");
			double tRadius=sc.nextDouble();
			if((tLatitude>=-90&&tLatitude<=90
					&&tLongtitude>=-180&&tLongtitude<=180
					&&tRadius>0)){
				Latitude=tLatitude;
				Longtitude=tLongtitude;
				Radius=tRadius;
			}
			else{
				throw new DataException ("User Input for Geographical Data is incorrect");
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
	}
	
	
	public  LocationFilter(double tLongtitude,double tLatitude,double tRadius){
		try {	
			if((tLatitude>=-90&&tLatitude<=90
					&&tLongtitude>=-180&&tLongtitude<=180
					&&tRadius>0)){
				Latitude=tLatitude;
				Longtitude=tLongtitude;
				Radius=tRadius;
			}
			else{
				throw new DataException ("User Input for Geographical Data is incorrect");
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * input: two geographical points (la1,lon1),(lat2,lon2).
	 * output: the distance in kilometers between them.
	 * credit to: http://www.geodatasource.com/developers/java
	 */
	static private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		return dist * 60 * 1.1515 * 1.609344;
	}

	static private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	static private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}


}
