package mypack;
/**
 * 
 * @author Boaz ben moshe
 *
 */
public class Not_Filter implements Filter{
	private Filter _Filter;
	public Not_Filter(Filter f) {
		_Filter = f;
	}
	public boolean Filt(WifiSpots s) {
		return !(_Filter.Filt(s));
	}
	public boolean Filt(WifiSpot s) {
		return !(_Filter.Filt(s));
	}

	public String toString() {
		return "not("+_Filter+")";
	}
}