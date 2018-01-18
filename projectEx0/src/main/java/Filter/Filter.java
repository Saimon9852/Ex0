package main.java.Filter;

import java.io.Serializable;

import main.java.mypack.*;


public interface Filter  extends Serializable {
	public  boolean  Filt(WifiSpots s);

	public boolean Filt(WifiSpot wifiSpot);
	
	public String toString();
	
}
