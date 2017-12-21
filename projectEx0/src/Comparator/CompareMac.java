package Comparator;
/**
 * a simple comparator for sorting ArrayList of Type WifiSpot by Signal Strength.
 */
import java.util.Comparator;

import mypack.WifiSpot;

public class CompareMac implements Comparator<WifiSpot> {

	public int compare(WifiSpot o1, WifiSpot o2) {
		/*the magic happens here:*/
		return (Integer.parseInt(o2.getRssi())-Integer.parseInt(o1.getRssi()));
	
}
}
