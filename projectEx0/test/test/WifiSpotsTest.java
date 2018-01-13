    package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mypack.WifiSpot;
import mypack.WifiSpots;

public class WifiSpotsTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testWifiSpotsString() {
		WifiSpots s=new WifiSpots("C:\\Users\\Rachel Plaksin\\Desktop\\tests\\JUNIT.csv");
		System.out.println(s.toString());
	}

	@Test
	public void testWifisort() {
		WifiSpot a=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		WifiSpot b=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-84","32.0909486","34.8786141","80");
		WifiSpot c=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-85","32.0909486","34.8786141","80");
		WifiSpot d=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-86","32.0909486","34.8786141","80");
		WifiSpot e=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-87","32.0909486","34.8786141","80");
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-88","32.0909486","34.8786141","80");
		WifiSpots s=new WifiSpots();
		s.getSpots().add(a);
		s.getSpots().add(b);
		s.getSpots().add(c);
		s.getSpots().add(d);
		s.getSpots().add(e);
		s.getSpots().add(f);
		s.wifisort();
		for(int i=0;i<s.getSpots().size()-1;i++){
			if(Integer.parseInt(s.getSpots().get(i).getRssi())<Integer.parseInt(s.getSpots().get(i+1).getRssi())){
				fail("not sorted");
			}
		}
	}

	@Test
	public void testWifiSpots() {
		WifiSpots s=new WifiSpots("C:\\Users\\Rachel Plaksin\\Desktop\\tests\\JUNIT.csv");
	}

	@Test
	public void testGetFirstSeen() {
	WifiSpots s=new WifiSpots();
	s.setFirstSeen("28/10/2017 20:10");
	String str=s.getFirstSeen();
    assertEquals(str,"28/10/2017 20:10");

	}

	@Test
	public void testSetFirstSeen() {
		WifiSpots s=new WifiSpots();
		s.setFirstSeen("28/10/2017 20:10");
		String str=s.getFirstSeen();
	    assertEquals(str,"28/10/2017 20:10");
	}

	@Test
	public void testGetAltitude() {
		WifiSpots s=new WifiSpots();
		s.setAltitude("58");
		String str=s.getAltitude();
	    assertEquals(str,"58");
	}

	@Test
	public void testSetAltitude() {
		WifiSpots s=new WifiSpots();
		s.setAltitude("58");
		String str=s.getAltitude();
	    assertEquals(str,"58");
	}

	@Test
	public void testGetLongtitude() {
		WifiSpots s=new WifiSpots();
		s.setLongtitude("32.0001");
		String str=s.getLongtitude();
	    assertEquals(str,"32.0001");
	}

	@Test
	public void testSetLongtitude() {
		WifiSpots s=new WifiSpots();
		s.setLongtitude("32.0001");
		String str=s.getLongtitude();
	    assertEquals(str,"32.0001");
	}

	@Test
	public void testGetID() {
		WifiSpots s=new WifiSpots();
		s.setID("ehud");
		String str=s.getID();
	    assertEquals(str,"ehud");
	}

	@Test
	public void testSetID() {
		WifiSpots s=new WifiSpots();
		s.setID("ehud");
		String str=s.getID();
	    assertEquals(str,"ehud");
	}

	@Test
	public void testGetLatitude() {
		WifiSpots s=new WifiSpots();
		s.setLatitude("-32.0001");
		String str=s.getLatitude();
	    assertEquals(str,"-32.0001");
	}

	@Test
	public void testSetLatitude() {
		WifiSpots s=new WifiSpots();
		s.setLatitude("-32.0001");
		String str=s.getLatitude();
	    assertEquals(str,"-32.0001");
	}

	@Test
	public void testGetSpots() {
		WifiSpots s=new WifiSpots();
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		s.getSpots().add(f);
	    assertEquals(f,s.getSpots().get(0));

	}

	@Test
	public void testSetSpots() {
		WifiSpots s=new WifiSpots();
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		s.getSpots().add(f);
		WifiSpots q=new WifiSpots();
		q.setSpots(s.getSpots());
	    assertEquals(q.getSpots(),s.getSpots());


	}

	@Test
	public void testToPrint() {
		WifiSpots s=new WifiSpots("C:\\Users\\Rachel Plaksin\\Desktop\\tests\\JUNIT.csv");
		s.toPrint();
	}

	@Test
	public void testSetWifiSpots() {
		WifiSpots s=new WifiSpots();
		s.setWifiSpots("C:\\Users\\Rachel Plaksin\\Desktop\\tests\\JUNIT.csv");
	}

}
