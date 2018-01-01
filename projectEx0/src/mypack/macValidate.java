package mypack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class macValidate {
	
	public static boolean Validate(String mac1,String mac2,String mac3) {
		mac1.replaceAll("[^a-fA-F0-9]", "");
		mac2.replaceAll("[^a-fA-F0-9]", "");
		mac3.replaceAll("[^a-fA-F0-9]", "");
		Pattern p = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
		Matcher m1 = p.matcher(mac1);
		Matcher m2 = p.matcher(mac2);
		Matcher m3 = p.matcher(mac3);
		return m1.find()&&m2.find()&&m3.find();
	}
	public static boolean Validate(String mac1) {
		mac1.replaceAll("[^a-fA-F0-9]", "");
		
		Pattern p = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
		Matcher m1 = p.matcher(mac1);
		return m1.find();
	}
}
