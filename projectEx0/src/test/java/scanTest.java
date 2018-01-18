package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.java.mypack.*;

public class scanTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testScan() {
		WifiSpots s=new WifiSpots("wigle.csv");
		scan sc=new scan(s);
	}

	@Test
	public void testGetScan() {
		WifiSpots s=new WifiSpots("wigle.csv");
		scan sc=new scan(s);
		if(sc.getScan()==null){
			fail("scan failed");
		}
	}

	@Test
	public void testSetScanArrayListOfWifiSpots() {
		scan sc=new scan();
		WifiSpots s=new WifiSpots("wigle.csv");
		sc.setScan(s);
		if(sc.getScan()==null){
			fail("scan failed");
		}
	}

	

}
