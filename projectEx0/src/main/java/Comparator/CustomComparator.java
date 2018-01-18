package Comparator;

import java.util.Comparator;

import mypack.WifiSpot;
/*
 * our custom comperator used to compare two objects of type WifiSpot
 * first we check if both objects share the same time stamp
 * if so we compare their strength. the higher the signal value the more superior it is.
 * if they dont share the time stamp we sort them by time stamp

 */
public class CustomComparator implements Comparator<WifiSpot> {
	@Override
	public int compare(WifiSpot o1, WifiSpot o2) {
		if(o1.getFirsseen().equals(o2.getFirsseen())){
			//if they have equal time we compare them by Signal Strength
			return (Integer.parseInt(o2.getRssi())-Integer.parseInt(o1.getRssi()));
		}else{
			//otherwise we compare by Date&time. 
			//we care only for the grouping of the results.
			return (o1.getFirsseen().compareTo(o2.getFirsseen()));
		}
	}
	
	
}


