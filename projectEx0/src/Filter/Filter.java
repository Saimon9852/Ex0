package Filter;

import java.io.Serializable;

import mypack.WifiSpot;
import mypack.WifiSpots;

public interface Filter  extends Serializable {
	public  boolean  Filt(WifiSpots s);

	public boolean Filt(WifiSpot wifiSpot);
	
	public String toString();
}
