package mypack;

import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
/**
 * holds a sufficient information to describe a wifi spot.
 * function:
 * typical getters and setters.
 * toString method
 * @author Ehud Plaksin, Saimon Lankry
 *
 */
public class WifiSpot implements Serializable {
	private String ssid;
	private String mac;
	private String firsseen;
	private String chanel;
	private String rssi;
	private String latitude;
	private String longtitude;
	private String altitude;
	public WifiSpot(String ssid,String mac,String firstseen,String chanel,String rssi,String latitude
			,String longtitude,String altitude){
		this.ssid=ssid;
		this.mac=mac;
		this.firsseen=firstseen;
		this.chanel=chanel;
		this.rssi=rssi;
		this.latitude=latitude;
		this.longtitude=longtitude;
		this.altitude=altitude;
	}
	public WifiSpot(){
		
	}
	public WifiSpot(String ssid,String mac,String chanel,String rssi){
		this.rssi=rssi;
		this.ssid=ssid;
		this.mac=mac;
		this.chanel=chanel;
	}
	public WifiSpot(String mac,String rssi){
		this.rssi=rssi;
		this.mac=mac;
	}


	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getFirsseen() {
		return firsseen;
	}

	public void setFirsseen(String firsseen) {
		this.firsseen = firsseen;
	}

	public String getChanel() {
		return chanel;
	}

	public void setChanel(String chanel) {
		this.chanel = chanel;
	}

	public String getRssi() {
		return rssi;
	}

	public void setRssi(String rssi) {
		this.rssi = rssi;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}


	@Override
	public String toString() {
		return "WifiSpot [ssid=" + ssid + ", mac=" + mac + ", firsseen=" + firsseen + ", chanel=" + chanel + ", rssi="
				+ rssi + ", latitude=" + latitude + ", longtitude=" + longtitude + ", altitude=" + altitude + ", type="
				+ "]";
	}

	
}
