package mypack;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public  class TokmlTest {
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test(expected = DataException.class)
	public  void testTokml() throws DataException  {
		 Tokml k=new Tokml();
		k.readcsv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel\\");
	}

	@Test(expected = DataException.class)
	public void testReadcsv() throws DataException {
		Tokml k=new Tokml();
		k.readcsv("C:\\Users\\Rachel Plaksin\\Desktop\\x.csv");
	}

	@Test
	public void testMacim() throws DataException {
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");
		ArrayList<WifiSpot>tmacim=kml.getMaicm();
		for(int i=0;i<tmacim.size()-1;i++){
			if(tmacim.get(i).getMac().equals(tmacim.get(i+1).getMac())){
				fail("two of the same macs in macim");
			}
		}
	}

	@Test
	public void testSMacim() throws DataException {
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");
		if(kml.sMacim("mac", kml.getMaicm())!=-1){
			fail("search dosent work");
		}
	}

	//here we enter an unvalid filter. for example a date from the future.
	//or coordinates for cyprus when our scan is in israel.
	@Test(expected = DataException.class)
	public void testCreateKmlByFilter() throws DataException {
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");
        kml.CreateKmlByFilter("nOfilter");
}

	@Test
	public void testSelectMacFilt() throws DataException {
		Csv Ariel=new Csv("C:\\Users\\Rachel Plaksin\\Desktop\\Ariel");
		Ariel.writescan("take2");
		Tokml kml=new Tokml("take2.csv");
		String[] s=kml.selectMacFilt((kml.getMaicm().size()-1),	1);
		if(!s[0].equals(kml.getMaicm().get(kml.getMaicm().size()-1).getFirsseen())){
			fail("filter id dosent correspond with the returned value");
		}
	}

	@Test//insert None for 0,Time for 1,lat for 2....
	//we will assume you put None here and check if output is 0
	
	public void testTypeOfSort() {
		System.out.println(" type of sort test");
		Tokml kml=new Tokml();
		String[] filtersKind = {"None","Time","Lat","Lon","Alt","inRadius"};
		if(kml.typeOfSort(filtersKind)!=0){
			fail("index dosent match the filter type");
		}
	}

	@Test
	//lets put wrong input, has to be wrong time format
	public void testValidValues() {
		Tokml k=new Tokml();
		String[] s=k.validValues(2);
		if(!(s[0].equals(32)&&s[1].equals(34))){
			System.out.println(s[0]+" "+s[1]);
			fail("failed for input of 32 34");
		}
	}

	@Test
	public void testIsArrInt() {
		Tokml k=new Tokml();
		String[] s={"12","a","b","13"};
		if(k.isArrInt(s)==true){
			fail("input had non integers and output was for all integer");
		}
	}
*/
	@Test
	public void testInCircle() {
		Tokml k=new Tokml();
		if(k.inCircle("31.9055504","34.8893547","31.8084452","34.8323631", 13)==false){
			fail("radius function dosent work");
		}
	}

	@Test
	public void testDistance() {
		Tokml k=new Tokml();
		if(k.distance(31.9055504,34.8893547,31.8084452,34.8323631)>=13){
			fail("distance function failer");
		}
	}

	@Test
	public void testDeg2rad() {
	}

	@Test
	public void testRad2deg() {
	}

	@Test
	public void testSelectByFilter() {
		Tokml k=new Tokml();
		WifiSpots e=new WifiSpots();
		e.setAltitude("55");
		k.getDB().add(0,e);
		String[] s=k.selectByFilter(4, 0);
		if(!(s[0]==e.getAltitude())){
			fail("test Select by filter malfunction");
		}
		
	}

	@Test
	public void testInRange() {
		Tokml k=new Tokml();
	}

	@Test
	public void testHowManyexist() {
		fail("Not yet implemented");
	}

}
