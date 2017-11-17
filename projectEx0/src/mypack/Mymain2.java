package mypack;

import java.util.Collections;
import java.util.Comparator;


public class Mymain2 {



	public static void main(String[] args)  throws DataException{
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel\\");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");
		kml.CreatBasicKml("test1");
		//kml.cKml(kml.DB);

	}
}


