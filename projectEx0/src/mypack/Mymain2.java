package mypack;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class Mymain2 {



	public static void main(String[] args)  throws DataException{
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel\\");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");
		kml.CreatBasicKml("test1");
		//kml.cKml(kml.DB);
		/*String start="28/10/2017 20:10";
		String bet="29/10/2017 20:10";
		String end="30/10/2017 20:10";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		Date betDate;
		Date startDate;
		Date endDate;
		try {
		    betDate = df.parse(bet);
		    startDate=df.parse(start);
		    endDate=df.parse(end);
		    if(startDate.before(betDate)&&endDate.after(betDate)){
				System.out.println("yep");
		    }
		    String newDateString = df.format(betDate);
		    System.out.println(newDateString);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
	}*/
	}
}


