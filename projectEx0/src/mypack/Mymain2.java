package mypack;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JLabel;


public class Mymain2 {


	public static void main(String[] args)  throws DataException{
		

		//Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel");
		//Ariel.writescan("take2");
		//Tokml kml=new Tokml("take2.csv");
        //kml.CreateKmlByFilter("nOfilter");


//		Tokml kml=new Tokml("take2.csv");
       // kml.CreateKmlByFilter("nOfilter");

//		 Database db=new Database("_comb_all_BM2_.csv","WifiSpots");
//         MacLoc_2 m2 = new MacLoc_2(db,4,"_comb_no_gps_ts2_.csv");
//         m2.setWscans("outputAlgo2fin");
//	     MacLoc_1 m1 = new MacLoc_1(4,db);
//		 m1.algorithmOnAllMac("outputAlgo1");
//		
//		testMacLocDiff test = new testMacLocDiff("boazOutputAlgo1.csv","outputAlgo1.csv",true);
//		testMacLocDiff test2 = new testMacLocDiff("boazOutputAlgo2.csv","outputAlgo2fin.csv",false);
//		test.compateFiles("testAlgo1");
//		test2.compateFilesAlgo2("testAlgo2");
//		
//		    Database mydb = new Database("asd\\_comb_all_BM2_.csv","WifiSpots");
//			MacLoc_2 mcheck = new MacLoc_2(mydb,4,"asd\\_comb_no_gps_ts1.csv");
//			mcheck.setWscans("CHECKoutputAlgo2fin");
//			testMacLocDiff test3 = new testMacLocDiff("asd\\Algo2_BM2_TS1_4.csv","CHECKoutputAlgo2fin.csv",false);
//			test3.compateFilesAlgo2("checkalgo2");
		
		 NavigableMap<String,Integer> pathToModifited = new TreeMap<String, Integer>();
		 NavigableMap<Integer,Integer> fileRange = new TreeMap<Integer, Integer>();
		 HashMap<Entry<String,Integer>,Entry<Integer, Integer>> fileInTable = new HashMap<>();
		 
		 pathToModifited.put("hello", 123433);
		 fileRange.put(1, 100);
		 fileInTable.put(pathToModifited.lastEntry(), fileRange.lastEntry());
		 
		 for(Entry key : fileInTable.keySet()){
			  String s = (String)key.getKey();
			  System.out.println(s);
		 }
		 
        
	}
}


