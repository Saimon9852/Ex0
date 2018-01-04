package mypack;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserialize {
	String path;
	Deserialize(String path){
		this.path=path;
	}
	
	public Filter Read(){
		Filter f = null;
	      try {
	         FileInputStream fileIn = new FileInputStream(path);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	          f =  (Filter) in.readObject();
	         in.close();
	         fileIn.close();
	         return f;
	      } catch (IOException i) {
	         i.printStackTrace();
	         return null;
	      } catch (ClassNotFoundException c) {
	         c.printStackTrace();
	         return null;
	      }
	      

	}
	

}
