package mypack;
import java.util.Comparator;

public class CompareWscan implements Comparator<Wscan> {
	public int compare(Wscan o1, Wscan o2) {
		return (int) (o2.weight-o1.weight);
	}
}
