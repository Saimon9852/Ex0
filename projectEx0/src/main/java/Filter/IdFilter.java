package main.java.Filter;

import java.io.Serializable;
import java.util.Scanner;

import main.java.mypack.*;
;
/*
 * we use this class to filter our Database by id.
 * we get id from the user,and filter all the samples that dont have that id.
 */
public class IdFilter implements Filter ,Serializable {
	//holds the user id.
	 String identifier="";
	 String type="";
	/*
	 * gets input from the user, and sets identifier with it.
	 */
	public IdFilter(String type,String identifier){
		this.type=type;
		this.identifier=identifier;
	}
	
	public IdFilter(){
			type="ID";
		try {	
			Scanner sc=new Scanner(System.in);
			System.out.println("please enter the Id");
			String tid=sc.nextLine();
			if((!tid.isEmpty())){
				identifier=tid;
			}
			else{
				throw new DataException ("User Input for Geographical Data is incorrect");
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
	}
	/*
	 * input:String id (represents id that we are going to apply the filter on)
	 * output: boolean value true if the input id is the same as identifier,false otherwise.
	 */

	public boolean Filt(WifiSpots s) {
		switch(type){
		case "ID":
			if(!identifier.equals(s.getID()))return false;
			return true;
		case "Time":
			if(!identifier.equals(s.getFirstSeen()))return false;
			return true;
		case "Lat":
			if(!identifier.equals(s.getLatitude()))return false;
			return true;
		case "lon":
			if(!identifier.equals(s.getLongtitude()))return false;
			return true;
		case "Alt":
			if(!identifier.equals(s.getAltitude()))return false;
			return true;
		default: return false;

		}
		

	}
	
	public boolean Filt(WifiSpot s) {		
		return true;
	}

	public String toString(){
		return "Filter by key: Key =" + type + " by word =" + identifier;
	}
	

}
