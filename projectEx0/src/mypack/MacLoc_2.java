package mypack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * this class is used for figuring user location by mac's surrounding him.
 * 
 *
 */
public class MacLoc_2 {
	
	final static double power=2;
	final static double norm=100000;
	final static double sig_diff=0.4;
	final static double no_signal=-120;
	final static double diff_no_signal=100;
	String path;
	Database db;
	Csv_noGPS csv=new Csv_noGPS(path);
	int nSamples;
	//HashMap<String,Double> scTopi=new HashMap<>();
	ArrayList<Wscan> scans=new ArrayList<Wscan>();
	
	public MacLoc_2(Database DB,int samples,String path){
		db=DB;
		csv.path=path;
		this.path=path;
		this.nSamples=samples;
		csv.flil();
		System.out.println("size:: "+csv.getCsv().size());
		setWscans();
	}
	
	public void setWscans(){

		for(int k=0;k<csv.getCsv().size();k++){
			for(int i=0;i<db.getDB().size();i++){
				double pi=setPI(db.getDB().get(i).spots,csv.getCsv().get(k).getSpots());
				Wscan sc=new Wscan(pi,(db.getDB().get(i).spots));
				scans.add(sc);
			}	
			Collections.sort(scans,new CompareWscan());
			setLocation(scans,csv,k);


		}scans=new ArrayList<Wscan>();
		Write_csv ws = new Write_csv("outputAlgo2fin");
		ws.write(csv);
	}



	public double setPI(ArrayList<WifiSpot> db,ArrayList<WifiSpot> csv){
		double sig;
		double diff;
		double PI=1;
		for(int i=0;i<csv.size();i++){
			sig=Double.parseDouble(csv.get(i).getRssi());
			int index=search(csv.get(i).getMac(),db);
			if(index!=-1){
				diff=(Math.abs(Math.abs(sig)-Math.abs(Double.parseDouble(db.get(index).getRssi()))))+3;
				PI=PI*(norm/Math.pow(diff, sig_diff)*(Math.pow(sig, power)));
			}else{
				PI=PI*(norm/Math.pow(diff_no_signal, sig_diff)*(Math.pow(sig, power)));
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
	
	public void setLocation(ArrayList<Wscan> scans,Csv_noGPS csv,int index){
		
		
		double lat=0;
		double lon=0;
		double alt=0;
		double sWeights=0;
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
		csv.getCsv().get(index).setLatitude(Double.toString(lat));
		csv.getCsv().get(index).setAltitude(Double.toString(alt));
		csv.getCsv().get(index).setLongtitude(Double.toString(lon));
	}

	

}







