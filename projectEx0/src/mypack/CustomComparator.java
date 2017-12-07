package mypack;

import java.util.Comparator;
//our custom comperator used to compare two objects of type WifiSpot
//first we check if both objects share the same time stamp
//if so we compare their strength. the higher the signal value the more superior it is.
//if they dont share the time stamp we sort them by time stamp
public class CustomComparator implements Comparator<WifiSpot> {
	@Override
	public int compare(WifiSpot o1, WifiSpot o2) {
		if(o1.getFirsseen().equals(o2.getFirsseen())){
			return (Integer.parseInt(o2.getRssi())-Integer.parseInt(o1.getRssi()));
		}else{
			return (o1.getFirsseen().compareTo(o2.getFirsseen()));
		}
	}
	
	
}


