package mypack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * we use this class to handle the Reading from Csv Files.
 * we hold the paths for a csv file in the local variable files
 * and we hold the Scans resulting from reading the file in Scans.
 * each Scan represents a file we read.
 *  
 *
 */
public class Csv {

	private String path;
	private ArrayList<scan> Scans=new ArrayList<scan>();
	private ArrayList<String> files=new ArrayList<String>();
	private ArrayList<WifiSpots> littleDB=new ArrayList<WifiSpots>();
	/**
	 * sets the csv object
	 */
	public Csv(){

	}
	public void ToString(){
		for(int i=0;i<littleDB.size();i++){
			littleDB.get(i).toPrint();
		}
	}
	public Csv(String path)  throws DataException
	{

		this.path=path;
		setCsv(); 

	}
	/*
	 * checks if the input file is in the right format.
	 * if so true false otherwise returns false
	 */
	private boolean hasRightFormat(String source){

		boolean b = false;
		try
		{
			FileReader fw = new FileReader(source);
			BufferedReader br = new BufferedReader(fw);
			String firstLine = br.readLine();
			String secondLine = br.readLine();
			br.close();
			b = firstLine.contains("WigleWifi");
			String[] str = {"Type","AccuracyMeters","AltitudeMeters","CurrentLongitude","CurrentLatitude","RSSI","Channel","FirstSeen","AuthMode","SSID","MAC"};
			for (int i = 0; i < str.length; i++) {
				if(secondLine.contains(str[i]) == false) b = false;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}

		return b;
	}
	/**
	 * sets the csv file object variables.
	 * @throws DataException
	 */
	public void setCsv()  throws DataException{
		files=getFiles();
		for(int i=0; i<files.size();i++){
			WifiSpots Ariel=new WifiSpots(files.get(i));
			scan s=new scan(Ariel);
			Scans.add(s);	
		}
		for(int k=0;k<Scans.size();k++){
			for(int i=0;i<Scans.get(k).getScan().size();i++){
				littleDB.add(Scans.get(k).getScan().get(i));
			}
		}
	}
	/**
	 * checks if the file in the directory is a file,
	 * and then we check if it is a csv file.
	 * if both criteria are met we insert the path in to the files list.
	 * it also checks if the file is legit.
	 * if the file isnt legit it throws and execption
	 */
	public ArrayList<String> getFiles()  throws DataException{
		File folder = new File(path);
		if(folder.isFile()){
			ArrayList<String> paths=new ArrayList<String>();
			paths.add(path);
			return paths;
		}
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> paths=new ArrayList<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()){
				if(isCsv(listOfFiles[i].getName()))
				{
					String str = listOfFiles[i].getPath();
					if(hasRightFormat(str)==false) throw new DataException("Wrong Format");
					paths.add(str);
				}
			}

		} if(paths.size()==0){
			throw new DataException("No currect input files");
		}
		return paths;
	}
	/**
	 * checks if the file is csv
	 * @param the path to the file
	 * @return boolean value, true if csv, false otherwise.
	 */
	private boolean isCsv(String s)
	{
		return (s.charAt(s.length() -1) == 'v')&& (s.charAt(s.length() - 2) == 's') && (s.charAt(s.length()-3) == 'c')&&(s.charAt(s.length()-4) == '.');
	}

	public void writescan(String name){
		FileWriter fw;

		try {
			fw = new FileWriter(name +".csv");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Time,ID,Lat,Lon,Alt,#WiFi networks,SSID1,MAC1,Frequancy1,Signal1,SSID2,MAC2,Frequancy2,Signal2,SSID3"
					+ ",MAC3,Frequancy3,Signal3,SSID4,MAC4,Frequancy4,Signal4,SSID5,MAC5,Frequancy5,Signal5,"
					+ "SSID6,MAC6,Frequancy6,Signal6,SSID7,MAC7,Frequancy7,Signal7,"
					+ "SSID8,MAC8,Frequancy8,Signal8,SSID9,MAC9,Frequancy9,Signal9,SSID10,MAC10,Frequancy10,Signal10");
			bw.newLine();
			for(int k=0; k<Scans.size();k++){
				for(int i=0;i<Scans.get(k).getScan().size();i++){
					bw.write(Scans.get(k).getScan().get(i).getFirstSeen()
							+","+Scans.get(k).getScan().get(i).getID()
							+","+Scans.get(k).getScan().get(i).getLatitude()
							+","+Scans.get(k).getScan().get(i).getLongtitude()
							+","+Scans.get(k).getScan().get(i).getAltitude()
							+","+Scans.get(k).getScan().get(i).spots.size()+",");
					for(int j=0;j<Scans.get(k).getScan().get(i).spots.size();j++){
						bw.write(
								Scans.get(k).getScan().get(i).getSpots().get(j).getSsid()
								+","+Scans.get(k).getScan().get(i).getSpots().get(j).getMac()
								+","+Scans.get(k).getScan().get(i).getSpots().get(j).getChanel()
								+","+Scans.get(k).getScan().get(i).getSpots().get(j).getRssi()+","

								);						
					}bw.newLine();

				}

			}
			System.out.println(name + ".csv was created successfully");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
