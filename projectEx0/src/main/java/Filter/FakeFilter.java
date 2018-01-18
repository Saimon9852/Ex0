package main.java.Filter;

import java.io.Serializable;

import main.java.mypack.*;


public class FakeFilter implements Filter, Serializable {

	public FakeFilter(){
		
	}
	public boolean Filt(WifiSpots s) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean Filt(WifiSpot wifiSpot) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public String toString(){
		return "";
	}

}
