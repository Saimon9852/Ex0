package mypack;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serialize {
	String path;
	Serialize(String path){
		this.path=path;
	}
	
	public void Write(Filter f){
		
		if(f instanceof IdFilter){
			 f= (IdFilter)f;
		}
		else if(f instanceof TimeFilter){
			f = (TimeFilter)f;
		}
		else
			f = (LocationFilter)f;
		
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
