package Algorithms;
import mypack.Wscan;
import mypack.Csv;
import mypack.Read_CSV_NoGPS;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;

import Comparator.CompareWscan;
import mypack.Csv_noGPS;
import mypack.Database;
import mypack.WifiSpot;
import mypack.WifiSpots;
import mypack.Write_csv;
import mypack.Wscan;

/**
 * this class is used for figuring user location by mac's surrounding him.
 * 
 *
 */
public class MacLoc_2 {
	//the constants
	final static double power=2;
	final static double norm=100000;
	final static double sig_diff=0.4;
	final static double no_signal=-120;
	final static double diff_no_signal=100;
	private String path;
	private Database db;
	private Csv_noGPS csv=new Csv_noGPS(path);
	private int nSamples;
	private WifiSpots s;
	//HashMap<String,Double> scTopi=new HashMap<>();
	ArrayList<Wscan> scans=new ArrayList<Wscan>();
	/**
	 * class builder
	 * @param DB- Data base
	 * @param samples- number of samples to use.
	 * @param path-the path for the CSV without location.
	 */
	public MacLoc_2(Database DB,int samples,String path){
		db=DB;
		csv.path=path;
		this.path=path;
		this.nSamples=samples;
		//initializes the virtual csv data base from the csv file.
		csv.fill();

		// executes the algorithm and write the results to csv

	}
	public MacLoc_2(Database DB,int samples,WifiSpots s){
		db=DB;
		this.nSamples=samples;
		this.s=s;
	}
	/**
	 * executes the algorithm and write the results to csv.
	 */
	public void setWscans(){
		for(int i=0;i<db.getDB().size();i++){
			//setting the pi for each scan
			double pi=setPI(db.getDB().get(i).spots,s.getSpots());
			//creating a new weighted scan
			if(pi != -1){
				Wscan sc=new Wscan(pi,(db.getDB().get(i).spots));
				//adding it to the arraylist of scans
				scans.add(sc);
			}


		}	
		//sorting the weighted scans.
		if(scans.size()==0){
			s.setAltitude("null");
			s.setLongtitude("null");
			s.setLatitude("null");
		}
		else{
			Collections.sort(scans,new CompareWscan());
			//setting the location for the NOGPS csv.
			setLocation(scans,s);
		}
	}


	public void setWscans(String name){
		//goes over the Noloc DB and for every mac there goes over our entire DB.
		for(int k=0;k<csv.getCsv().size();k++){
			for(int i=0;i<db.getDB().size();i++){
				//setting the pi for each scan
				double pi=setPI(db.getDB().get(i).spots,csv.getCsv().get(k).getSpots());
				//creating a new weighted scan
				if(pi != -1){
					Wscan sc=new Wscan(pi,(db.getDB().get(i).spots));
					//adding it to the arraylist of scans
					scans.add(sc);
				}


			}	
			//sorting the weighted scans.
			if(scans.size()==0){
				csv.getCsv().get(k).setAltitude("null");
				csv.getCsv().get(k).setLongtitude("null");
				csv.getCsv().get(k).setLatitude("null");
			}
			else{
				Collections.sort(scans,new CompareWscan());
				//setting the location for the NOGPS csv.
				setLocation(scans,csv,k);
				scans=new ArrayList<Wscan>();
				//new mac new arraylist.
			}
		}

		//writing the macs with the set location to csv.
		Write_csv ws = new Write_csv(name);

		ws.write(csv);
	}


	/**
	 * setting the PI weight by Boaz's algorithm
	 * @param db -the full DataBase
	 * @param csv -Data base of location-less macs.
	 * @return - the weight of scan in DB.
	 */
	public double setPI(ArrayList<WifiSpot> db,ArrayList<WifiSpot> csv){
		double sig;
		double diff;
		double PI=1;
		int countF_search = 0;
		for(int i=0;i<csv.size();i++){
			sig=Double.parseDouble(csv.get(i).getRssi());
			int index=search(csv.get(i).getMac(),db);
			if(index!=-1){
				diff=(Math.abs(Math.abs(sig)-Math.abs(Double.parseDouble(db.get(index).getRssi()))))+3;
				PI=PI*(norm/Math.pow(diff, sig_diff)*(Math.pow(sig, power)));
			}
			else{
				countF_search++;
				PI=PI*(norm/Math.pow(diff_no_signal, sig_diff)*(Math.pow(sig, power)));
			}
		}
		if(countF_search == csv.size())
			return -1;
		return PI;
	}
	/**
	 * searches a certain mac in arrays list. returns its index
	 * @param mac
	 * @param db
	 * @return- index of the mac in arraylist
	 */
	public int search(String mac,ArrayList<WifiSpot> db){
		for(int i=0;i<db.size();i++){
			if(db.get(i).getMac().equals(mac)){
				return i;
			}
		}return -1;
	}
	/**
	 * 
	 * @param scans-holds our weighted scans
	 * @param csv-no GPS csv
	 * @param index of the mac in the location less CSV.
	 */
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
	public void setLocation(ArrayList<Wscan> scans,WifiSpots s){


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


		s.setLatitude(Double.toString(lat));
		s.setAltitude(Double.toString(alt));
		s.setLongtitude(Double.toString(lon));
	}



}







