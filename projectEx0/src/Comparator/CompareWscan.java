package Comparator;
import java.util.Comparator;

import mypack.Wscan;
/*
 * comparing Weighted Scans by their weight. (PI)
 */
public class CompareWscan implements Comparator<Wscan> {
	public int compare(Wscan o1, Wscan o2) {
		return (int) (o2.getWeight()-o1.getWeight());
	}
}
