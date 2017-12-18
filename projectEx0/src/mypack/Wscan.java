package mypack;

import java.util.ArrayList;

public class Wscan {
	
	ArrayList<WifiSpot> scan;
	double weight;
	Wscan(double weight,ArrayList<WifiSpot> scan){
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
