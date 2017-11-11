package mypack;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
/**
 * holds sufficient information to describe a scan.
 * Two wifi spots are considered to be in the same scan if:
 * they share the same "FirstSeen" time, and they share the same device id.
 * the class variable scan is used to store the scan's wifi spots.
 * @author Rachel Plaksin
 *
 */
public class scan {
	///the builder gets An object of Type WifiSpots.
	// it sets the scan variable by the properties we stated at the class description
	// by using the scan function and cut access function

	ArrayList<WifiSpots> scan=new ArrayList<WifiSpots>();

	public scan(WifiSpots sp){
		setScan(sp);
		cutAcces();
	}
	//sets the scan by the properties we stated at the class descriptiion
	public ArrayList<WifiSpots> getScan() {
		return scan;
	}

	public void setScan(ArrayList<WifiSpots> scan) {
		this.scan = scan;
	}


	public void setScan(WifiSpots sp){
		int i=0;
		while(i<sp.getSpots().size()-1){
			//we set the shared values the spots will have. 
			WifiSpots cur=new WifiSpots();
			cur.setAltitude(sp.getSpots().get(i).getAltitude());
			cur.setFirstSeen(sp.getSpots().get(i).getFirsseen());
			cur.setID(sp.getID());
			cur.setLatitude(sp.getSpots().get(i).getLatitude());
			cur.setLongtitude(sp.getSpots().get(i).getLongtitude());
			// we test if there are 2 or more spots who share the same "firstseen" property
			if(!(sp.getSpots().get(i).firsseen.equals(sp.getSpots().get(i+1).firsseen))){
				//if this spot dosent share time stamp with other spots
				//we add it to cur and then add it as its own scan.
				cur.getSpots().add(sp.getSpots().get(i));
				//System.out.println(sp.getSpots().get(i).firsseen+" "+sp.getSpots().get(i+1).firsseen);
				i++;
			}
			//if we have 2 or more spots that share the same time stamp we add them all to cur 
			// and THEN set them up as their own scan.
			while(i<sp.getSpots().size()-1&&(sp.getSpots().get(i).firsseen.equals(sp.getSpots().get(i+1).firsseen))){
				//System.out.println(sp.getSpots().get(i).firsseen+" "+sp.getSpots().get(i+1).firsseen);
				cur.getSpots().add(sp.getSpots().get(i));
				i++;
			}
			cur.wifisort();
			scan.add(cur);

		}
	}
	//because we want up to 10 spots in the same scan, we cut the Acces spots
	//and becuase they are allready sorted by Singal Strength in the scan DB
	// we can just delete the last spot in each scan till each scan spots have at most 10 Wifi spot objects.
	public void cutAcces(){
		for(int i=0;i<scan.size();i++){
			if(scan.get(i).getSpots().size()>10&&Integer.parseInt(scan.get(i).getSpots().get(0).getRssi())
					<Integer.parseInt(scan.get(i).getSpots().get(1).getRssi())){
				scan.get(i).getSpots().remove(0);
			}
			if(scan.get(i).getSpots().size()>10){
				while(scan.get(i).getSpots().size()>10){
					//System.out.println(scan.get(i).getSpots().size());
					int rem=scan.get(i).getSpots().size();
					scan.get(i).getSpots().remove(rem-1);
				}
			}
		}
	}

}
