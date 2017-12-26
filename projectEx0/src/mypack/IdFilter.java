package mypack;

import java.util.Scanner;
/*
 * we use this class to filter our Database by id.
 * we get id from the user,and filter all the samples that dont have that id.
 */
public class IdFilter implements Filter {
	//holds the user id.
	static String identifier="";
	/*
	 * gets input from the user, and sets identifier with it.
	 */
	
	public IdFilter(){
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
		if(!identifier.equals(s.getID()))return false;
		return true;
				
	}
	public boolean Filt(WifiSpot s) {		
		return true;
				
	}
	
	

}
