package Junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mypack.WifiSpots;
import mypack.scan;

public class scanTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testScan() {
		WifiSpots s=new WifiSpots("C:\\Users\\Rachel Plaksin\\Desktop\\tests\\JUNIT.csv");
		scan sc=new scan(s);
	}

	@Test
	public void testGetScan() {
		WifiSpots s=new WifiSpots("C:\\Users\\Rachel Plaksin\\Desktop\\tests\\JUNIT.csv");
		scan sc=new scan(s);
		if(sc.getScan()==null){
			fail("scan failed");
		}
	}

	@Test
	public void testSetScanArrayListOfWifiSpots() {
		scan sc=new scan();
		WifiSpots s=new WifiSpots("C:\\Users\\Rachel Plaksin\\Desktop\\tests\\JUNIT.csv");
		sc.setScan(s);
		if(sc.getScan()==null){
			fail("scan failed");
		}
	}

	@Test
	public void testSetScanWifiSpots() {
		scan sc=new scan();
		WifiSpots s=new WifiSpots("C:\\Users\\Rachel Plaksin\\Desktop\\tests\\JUNIT.csv");
		sc.setScan(s);
		for(int i=0;i<sc.getScan().size()-1;i++){
			if(sc.getScan().get(i).getSpots().size()>10){
				fail("more then 10 spots per scan sample");
			}
		}
	}


	@Test
	public void testCutAcces() {
		scan sc=new scan();
		WifiSpots s=new WifiSpots("C:\\Users\\Rachel Plaksin\\Desktop\\tests\\JUNIT.csv");
		sc.setScan(s);
		for(int i=0;i<sc.getScan().size()-1;i++){
			if(sc.getScan().get(i).getSpots().size()>10){
				fail("more then 10 spots per scan sample");
			}
		}
	}

}
