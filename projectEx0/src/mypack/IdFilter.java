package mypack;

import java.util.Scanner;

public class IdFilter {
	static String identifier="";
	static boolean  Filt(String id){
	if(!identifier.equals(id))return false;
	return true;
	}
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
