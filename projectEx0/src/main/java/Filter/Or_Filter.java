package main.java.Filter;

import main.java.mypack.*;

/**
 * 
 * @author Boaz ben moshe
 *
 */
public class Or_Filter implements Filter{
	private Filter _f1, _f2;
	public Or_Filter(Filter f1, Filter f2) {
		_f1 = f1;
		_f2 = f2;
	}

	public boolean Filt(WifiSpot rec) {
		return _f1.Filt(rec) || _f2.Filt(rec);
	}
	public boolean Filt(WifiSpots rec) {
		return _f1.Filt(rec) || _f2.Filt(rec);
	}

	public String toString() {
		return "("+_f1.toString()+" OR "+_f2.toString()+")";
	}
	
	
}