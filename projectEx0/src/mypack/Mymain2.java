package mypack;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class Mymain2 {



	public static void main(String[] args)  throws DataException{
		
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\tests");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");
        kml.CreateKmlByFilter("circle");
	}
}


