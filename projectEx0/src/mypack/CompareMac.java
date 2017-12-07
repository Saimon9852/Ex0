package mypack;

import java.util.Comparator;

public class CompareMac implements Comparator<WifiSpot> {

	public int compare(WifiSpot o1, WifiSpot o2) {
		
		return (Integer.parseInt(o2.getRssi())-Integer.parseInt(o1.getRssi()));
	
}
}
