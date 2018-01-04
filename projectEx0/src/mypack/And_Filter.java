package mypack;
/**
 * 
 *Credit for Boaz Ben Moshe
 */
public class And_Filter implements Filter {
	private Filter _f1, _f2;
	public And_Filter(Filter f1, Filter f2) {
		_f1 = f1;
		_f2 = f2;
	}

	public boolean Filt(WifiSpot s) {
		return _f1.Filt(s) && _f2.Filt(s);
	}
	public boolean Filt(WifiSpots s) {
		return _f1.Filt(s) && _f2.Filt(s);
	}
	public String toString() {
		return "("+_f1.toString()+" AND "+_f2.toString()+")";
	}
}
