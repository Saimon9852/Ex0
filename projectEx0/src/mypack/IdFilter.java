package mypack;

import java.util.Scanner;
/*
 * we use this class to filter our Database by id.
 * we get id from the user,and filter all the samples that dont have that id.
 */
public class IdFilter {
	//holds the user id.
	static String identifier="";
	/*
	 * input:String id (represents id that we are going to apply the filter on)
	 * output: boolean value true if the input id is the same as identifier,false otherwise.
	 */
	static boolean  Filt(String id){
	if(!identifier.equals(id))return false;
	return true;
	}
	/*
	 * gets input from the user, and sets identifier with it.
	 */
	static void SetData(){
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
}
