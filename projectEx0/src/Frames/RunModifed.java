package Frames;

import java.io.File;
import java.util.ArrayList;

public class RunModifed implements Runnable {
	
	private myFrame frame;
	
	public RunModifed(myFrame f){
		   frame  = f;
	}
	@Override
	public void run() {
		while (true) {
			
			if(frame.getServer().filesCounter >0){
			frame.getServer().fileWasUpdated();
			frame.getServer().folderWasUpdated();
			frame.getServer().serverwasupdated();
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
