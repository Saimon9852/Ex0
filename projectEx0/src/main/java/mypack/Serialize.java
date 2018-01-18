package main.java.mypack;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import main.java.Filter.*;

public class Serialize {
	String path;
	public Serialize(String path){
		this.path=path;
	}
	
	public void Write(Filter f){
		
		if(f instanceof IdFilter){
			 f= (IdFilter)f;
		}
		else if(f instanceof TimeFilter){
			f = (TimeFilter)f;
		}
		else if(f instanceof LocationFilter)
			f = (LocationFilter)f;
		else if(f instanceof And_Filter){
			f=(And_Filter)f;
		}else if (f instanceof Or_Filter){
			f=(Or_Filter)f;
			
		}else if(f instanceof Not_Filter){
			f=(Not_Filter)f;
		}
		
		try {
			FileOutputStream fileOut =
					new FileOutputStream(path + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(f);
			out.close();
			fileOut.close();
			
		} catch (IOException i) {
			i.printStackTrace();
		}
	}


}
