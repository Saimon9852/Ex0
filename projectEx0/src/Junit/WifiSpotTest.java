package Junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mypack.WifiSpot;

public class WifiSpotTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testWifiSpotStringStringStringStringStringStringStringString() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","5","-83");
	
	}

	@Test
	public void testWifiSpot() {
		WifiSpot e=new WifiSpot();
	}

	@Test
	public void testWifiSpotStringStringStringString() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
	}

	@Test
	public void testGetSsid() {
	WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
	String str=f.getSsid();
    assertEquals("FHOME",str);
	}

	@Test
	public void testSetSsid() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		f.setSsid("test");
	    assertEquals(f.getSsid(),"test");
	}

	@Test
	public void testGetMac() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		String str=f.getMac();
		assertEquals("14:cc:20:c8:83:9c",str);
	}

	@Test
	public void testSetMac() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		f.setMac("12:cc:20:c1:83:9c");
	    assertEquals(f.getMac(),"12:cc:20:c1:83:9c");
	}

	@Test
	public void testGetFirsseen() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		String str=f.getFirsseen();
		assertEquals("28/10/2017 20:10",str);
	}

	@Test
	public void testSetFirsseen() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		f.setFirsseen("28/10/2017 21:10");
	    assertEquals(f.getFirsseen(),"28/10/2017 21:10");
	}

	@Test
	public void testGetChanel() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		String str=f.getChanel();
		assertEquals("5",str);
	}

	@Test
	public void testSetChanel() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		f.setChanel("8");
	    assertEquals(f.getChanel(),"8");
	}

	@Test
	public void testGetRssi() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		String str=f.getRssi();
		assertEquals("-83",str);
	}

	@Test
	public void testSetRssi() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		f.setRssi("-52");
	    assertEquals(f.getRssi(),"-52");
	}

	@Test
	public void testGetLatitude() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		String str=f.getLatitude();
		assertEquals("32.0909486",str);
	}

	@Test
	public void testSetLatitude() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		f.setLatitude("-32.022226");
	    assertEquals(f.getLatitude(),"-32.022226");
	}

	@Test
	public void testGetLongtitude() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		String str=f.getLongtitude();
		assertEquals("34.8786141",str);
	}

	@Test
	public void testSetLongtitude() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		f.setLongtitude("35.8786141");
		assertEquals("35.8786141",f.getLongtitude());
	}

	@Test
	public void testGetAltitude() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		String str=f.getAltitude();
		assertEquals(str,"80");
	}

	@Test
	public void testSetAltitude() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		f.setAltitude("234");
		assertEquals("234",f.getAltitude());
	}

	@Test
	public void testToString() {
		WifiSpot f=new WifiSpot("FHOME","14:cc:20:c8:83:9c","28/10/2017 20:10","5","-83","32.0909486","34.8786141","80");
		System.out.println(f.toString());
	}

}
