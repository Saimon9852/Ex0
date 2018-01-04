package mypack;

import java.io.Serializable;

public interface Filter  extends Serializable {
	public  boolean  Filt(WifiSpots s);

	public boolean Filt(WifiSpot wifiSpot);
	
	public String toString();
}
