package mypack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


public class MacLoc_2 {
	String path;
	Database db;
	Csv_noGPS csv=new Csv_noGPS(path);
	int nSamples;
	HashMap<String,Double>scTopi=new HashMap<>();
	ArrayList<Wscan>scans=new ArrayList<Wscan>();
	public MacLoc_2(Database DB,int samples,String path){
		db=DB;
		csv.path=path;
		this.path=path;
		this.nSamples=samples;
		csv.flil();
		setWscans();
	}
	public void setWscans(){
		double lat=0;
		double lon=0;
		double alt=0;
		double sWeights=0;
		for(int k=0;k<csv.getCsv().size();k++){
			for(int i=0;i<db.getDB().size();i++){
				double pi=setPI(db.getDB().get(i).spots,csv.getCsv().get(k).getSpots());
				Wscan sc=new Wscan(pi,(db.getDB().get(i).spots));
				scans.add(sc);
			}	
			Collections.sort(scans,new CompareWscan());		
			for(int j=0;j<nSamples&&j<scans.size();j++){
				sWeights+=scans.get(j).weight;
				double weight=scans.get(j).weight;
				lat=lat+weight*Double.parseDouble(scans.get(j).getScan().get(0).getLatitude());
				lon=lon+weight*Double.parseDouble(scans.get(j).getScan().get(0).getLongtitude());
				alt=alt+weight*Double.parseDouble(scans.get(j).getScan().get(0).getAltitude());
			}
			lat=lat/sWeights;
			lon=lon/sWeights;
			alt=alt/sWeights;
			csv.getCsv().get(k).setLatitude(Double.toString(lat));
			csv.getCsv().get(k).setAltitude(Double.toString(alt));
			csv.getCsv().get(k).setLongtitude(Double.toString(lon));

		}scans=new ArrayList<Wscan>();
		FileWriter fw;

		try {
			fw = new FileWriter("fixed" +".csv");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Time,ID,Lat,Lon,Alt,#WiFi networks,SSID1,MAC1,Frequancy1,Signal1,SSID2,MAC2,Frequancy2,Signal2,SSID3"
					+ ",MAC3,Frequancy3,Signal3,SSID4,MAC4,Frequancy4,Signal4,SSID5,MAC5,Frequancy5,Signal5,"
					+ "SSID6,MAC6,Frequancy6,Signal6,SSID7,MAC7,Frequancy7,Signal7,"
					+ "SSID8,MAC8,Frequancy8,Signal8,SSID9,MAC9,Frequancy9,Signal9,SSID10,MAC10,Frequancy10,Signal10");
			bw.newLine();
			for(int k=0; k<csv.getCsv().size();k++){
				for(int i=0;i<csv.getCsv().get(k).getSpots().size();i++){
					bw.write(csv.getCsv().get(k).getSpots().get(i).getFirsseen()
							+","+csv.getCsv().get(i).getID()
							+","+csv.getCsv().get(i).getLatitude()
							+","+csv.getCsv().get(i).getLongtitude()
							+","+csv.getCsv().get(i).getAltitude()
							+","+csv.getCsv().get(i).spots.size()+",");
					for(int j=0;j<csv.getCsv().get(i).spots.size();j++){
						bw.write(
								csv.getCsv().get(i).getSpots().get(j).getSsid()
								+","+csv.getCsv().get(i).getSpots().get(j).getMac()
								+","+csv.getCsv().get(i).getSpots().get(j).getChanel()
								+","+csv.getCsv().get(i).getSpots().get(j).getRssi()+","
								);						
					}bw.newLine();

				}

			}
			System.out.println("fixed" + ".csv was created successfully");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public double setPI(ArrayList<WifiSpot> db,ArrayList<WifiSpot> csv){
		double sigdiffPower=0.4;
		double sig;
		double diff;
		double norm=100000;
		double nosignal=-120;
		double diffnosig=100;
		double PI=1;
		for(int i=0;i<csv.size();i++){
			sig=Double.parseDouble(csv.get(i).getRssi());
			int index=search(csv.get(i).getMac(),db);
			if(index!=-1){
				diff=(Math.abs(Math.abs(sig)-Math.abs(Double.parseDouble(db.get(index).getRssi()))))+3;
				PI=PI*(norm/Math.pow(diff, sigdiffPower)*(Math.pow(sig, 2)));
			}else{
				diff=100;
				PI=PI*(norm/Math.pow(diff, sigdiffPower)*(Math.pow(sig, 2)));
			}
		}return PI;
	}
	public int search(String mac,ArrayList<WifiSpot> db){
		for(int i=0;i<db.size();i++){
			if(db.get(i).getMac().equals(mac)){
				return i;
			}
		}return -1;
	}

}







