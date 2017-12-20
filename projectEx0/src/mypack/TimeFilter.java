package mypack;

	import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
	import java.util.Calendar;
	import java.util.Date;
import java.util.Scanner;

import sun.util.resources.cldr.aa.CalendarData_aa_ER;
/**
 * this class is used to filter by time.
 * @author Ehud Plaksin, Saimon Lankry
 *
 */
public class TimeFilter {
		static DateFormat format=new SimpleDateFormat("MM/dd/yyyy HH:mm");
		static Date from=new Date(0);
		static Date to=Calendar.getInstance().getTime();

		static boolean Filt (Date d){
		if(from.after(d)&&to.before(d))return true;
		return false;
		}
		/**
		 * sets the from date.
		 */
		static void setfrom(){
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter Start Date (MM/dd/yyyy HH:mm)");
			String date=sc.nextLine();
			try {
				from=format.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * sets the To date.
		 */
		static void setTo(){
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter End Date (MM/dd/yyyy HH:mm)");
			String date=sc.nextLine();
			try {
				to=format.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}


