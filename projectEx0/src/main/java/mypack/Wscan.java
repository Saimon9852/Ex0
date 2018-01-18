package main.java.mypack;

import java.util.ArrayList;
/**
 * this objects represents a scan of wifi's spots+weight that is generated by Algorithm2.
 * @author Ehud Plaksin, Saimon Lankry
 *
 */
public class Wscan {
	
	ArrayList<WifiSpot> scan;
	public double weight;
	public Wscan(double weight,ArrayList<WifiSpot> scan){
		this.weight=weight;
		this.scan=scan;
	}
	public ArrayList<WifiSpot> getScan() {
		return scan;
	}
	public void setScan(ArrayList<WifiSpot> scan) {
		this.scan = scan;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}