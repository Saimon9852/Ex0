package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import mypack.Csv;
import mypack.DataException;
import mypack.Tokml;


public class TokmlTest {

	
	@Test
	public void testTokml() throws DataException {
		System.out.println("***test***"+"\n"+"Test of-Tokml Builder"+"\n"
				);
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");
		//first we will check that there are less then 10 points on each scan.
		for(int i=0;i<kml.getDB().size();i++){
			if(kml.getDB().get(i).getSpots().size()>10){
				fail("more then 10 points in the scan");
			}
		}
		//now we will check that the macim are currect.
		//we will do so by checking one of the macs in macim
		//and seeing if it is the strongest one in the DB
		Random rand = new Random();
		int  n = rand.nextInt(kml.getMacim().size());
		for(int i=0;i<kml.getDB().size();i++){
			for(int j=0;j<kml.getDB().get(i).getSpots().size();j++){
				if(kml.getMacim().get(n).get(j).getMac().equals(kml.getDB().get(i).getSpots().get(j).getMac())){
					if(Integer.parseInt(kml.getMacim().get(n).get(j).getRssi())
							<Integer.parseInt(kml.getDB().get(i).getSpots().get(j).getRssi())){
						fail("the mac in macim isnt the strogenst one");
					}
				}
			}
		}
	}

	@Test
	public void testGetDB() {
		Tokml kml=new Tokml();
		if((kml.getDB()!=null)){
		}else{
			fail("empty builder failed");
		}
	}

	@Test (expected = DataException.class)
	// here we test the create by filter
	//we will give filter data that will not result with any matching
	// we will then get an exception.
	public void testCreateKmlByFilter() throws DataException {
		System.out.println("***test***"+"\n"+"Test of-CreatekmlByFilter"+"\n"+
	"***user input required***"+"\n");
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");
		kml.CreateKmlByFilter("test");
	}

	@Test
	//because we have user input here,
	//the tester must press 1 inorder for this test to work.
	public void testUserInput1() {
		System.out.println("***test***"+"\n"+"Test of-UserInput"+"\n"+
				"***user input required***"+"\n");
		Tokml kml = new Tokml();
		String input=kml.userInput();
		  assertEquals(input,"Date");
	
	}
	@Test
	//because we have user input here,
	//the tester must press 2 inorder for this test to work.
	public void testUserInput2() {
		System.out.println("***test***"+"\n"+"Test of-UserInput"+"\n"+
				"***user input required***"+"\n");
		Tokml kml = new Tokml();
		String input=kml.userInput();
		  assertEquals(input,"Location");
	
	}
	@Test
	//because we have user input here,
	//the tester must press any number but "1" and "2" inorder for this test to work.
	public void testUserInput3() {
		System.out.println("***test***"+"\n"+"Test of-UserInput"+"\n"+
				"***user input required***"+"\n");
		Tokml kml = new Tokml();
		String input=kml.userInput();
		  assertEquals(input,"None");
	
	}
	

}
