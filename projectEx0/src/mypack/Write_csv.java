package mypack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Write_csv {

	private String name;

	public Write_csv(String name)
	{
		this.name= name;
	}

	public void write(Csv_noGPS csv){
		FileWriter fw;
		System.out.println("size again"+csv.getCsv().size());

		try {
			fw = new FileWriter(name +".csv");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Time,ID,Lat,Lon,Alt,#WiFi networks,SSID1,MAC1,Frequancy1,Signal1,SSID2,MAC2,Frequancy2,Signal2,SSID3"
					+ ",MAC3,Frequancy3,Signal3,SSID4,MAC4,Frequancy4,Signal4,SSID5,MAC5,Frequancy5,Signal5,"
					+ "SSID6,MAC6,Frequancy6,Signal6,SSID7,MAC7,Frequancy7,Signal7,"
					+ "SSID8,MAC8,Frequancy8,Signal8,SSID9,MAC9,Frequancy9,Signal9,SSID10,MAC10,Frequancy10,Signal10");
			bw.newLine();
			for(int k=0; k<csv.getCsv().size();k++){
				bw.write(csv.getCsv().get(k).getFirstSeen()
						+","+csv.getCsv().get(k).getID()
						+","+csv.getCsv().get(k).getLatitude()
						+","+csv.getCsv().get(k).getLongtitude()
						+","+csv.getCsv().get(k).getAltitude()
						+","+csv.getCsv().get(k).spots.size()+",");
				for(int j=0;j<csv.getCsv().get(k).spots.size();j++){
					bw.write(
							csv.getCsv().get(k).getSpots().get(j).getSsid()
							+","+csv.getCsv().get(k).getSpots().get(j).getMac()
							+","+csv.getCsv().get(k).getSpots().get(j).getChanel()
							+","+csv.getCsv().get(k).getSpots().get(j).getRssi()+","
							);						
				}bw.newLine();

			}


			System.out.println(name + ".csv was created successfully");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * gets array list of WifiSpot and create a csv file in the format for Algo1
	 * @param ArrayList<WifiSpot> ws
	 * @return void
	 */
	//
	public void wifispotToCSV(ArrayList<WifiSpot> ws){

		FileWriter fw;

		try {
			fw = new FileWriter(name +".csv");
			BufferedWriter bw1 = new BufferedWriter(fw);
			bw1.write("Time,Lon,Lat,Alt,MAC,Signal,ID,Channel");
			bw1.newLine();
			for(int k=0; k<ws.size();k++){

				bw1.write(ws.get(k).getFirsseen() +
						"," + ws.get(k).getLongtitude() +
						"," + ws.get(k).getLatitude() +
						"," + ws.get(k).getAltitude() +
						"," + ws.get(k).getMac() +
						"," + ws.get(k).getRssi() +
						"," + ws.get(k).getSsid() +
						"," + ws.get(k).getChanel());  						

				bw1.newLine();

			}
			System.out.println(name + ".csv was created successfully");
			bw1.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * The function create csv file as the format comparison: print all my coordinates and the file's coordinates ,for each mac address and calculate the difference 
	 * between each one of them and show the total average difference between lon,lat,alt
	 * @param Double totlon,totlat,totalt HashMap<String,ArrayList<WifiSpot>> diff HashMap<String,ArrayList<Double>> coordDiff
	 * @return void
	 */
	public void csvDifferent(double totlon,double totlat,double totalt,HashMap<String,ArrayList<WifiSpot>> diff,HashMap<String,ArrayList<Double>> coordDiff){

		FileWriter fw;

		try {
			fw = new FileWriter(name +".csv");
			BufferedWriter bw1 = new BufferedWriter(fw);
			bw1.write("MAC, ,myLon,myLat,myAlt, , ,DiffLon,DiffLat,DiffAlt, , ,Lon,Lat,Alt");
			bw1.newLine();

			for ( String key : diff.keySet() ) {
				WifiSpot ws = diff.get(key).get(0);
				WifiSpot myws = diff.get(key).get(1);
				ArrayList<Double> numdiff = coordDiff.get(key);
				bw1.write(key + "," + " " +
						"," + myws.getLongtitude() +
						"," + myws.getLatitude() +
						"," + myws.getAltitude() + "," + " " +"," + " " +
						"," + numdiff.get(0) + 
						"," + numdiff.get(1) + 
						"," + numdiff.get(2) + "," + " " +"," + " " +
						"," + ws.getLongtitude() +
						"," + ws.getLatitude() +
						"," + ws.getAltitude());
				bw1.newLine();

			}
			bw1.newLine();
			bw1.write("Total average Lon differnce: "+ totlon);
			bw1.newLine();
			bw1.write("Total average Lat differnce: "+ totlat);
			bw1.newLine();
			bw1.write("Total average Alt differnce: "+ totalt);

			System.out.println(name + ".csv was created successfully");
			bw1.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	
	
	/**
	 * The function create csv file as the format comparison: print all my coordinates and the file's coordinates ,for each time(line) and calculate the difference 
	 * between each one of them and show the total average difference between lon,lat,alt
	 * @param Double totlon,totlat,totalt HashMap<String,ArrayList<WifiSpot>> diff HashMap<String,ArrayList<Double>> coordDiff Integer timelong
	 * @return void
	 */
	public void csvDifferentX(double totlon,double totlat,double totalt,HashMap<String,ArrayList<WifiSpots>> diff,
			HashMap<String,ArrayList<Double>> coordDiff,int timelong){

		FileWriter fw;

		try {
			fw = new FileWriter(name +".csv");
			BufferedWriter bw1 = new BufferedWriter(fw);
			bw1.write("Time, ,myLon,myLat,myAlt, , ,DiffLon,DiffLat,DiffAlt, , ,Lon,Lat,Alt");
			bw1.newLine();

			for ( String key : diff.keySet() ) {
				
				WifiSpots ws = diff.get(key).get(1);
				WifiSpots myws = diff.get(key).get(0);
				ArrayList<Double> numdiff = coordDiff.get(key);
				int diffsize = key.length() - timelong;
            	String nw = key.substring(0, key.length() - diffsize);
				
				bw1.write(nw + "," + " " +
						"," + myws.getLongtitude() +
						"," + myws.getLatitude() +
						"," + myws.getAltitude() + "," + " " +"," + " " +
						"," + numdiff.get(0) + 
						"," + numdiff.get(1) + 
						"," + numdiff.get(2) + "," + " " +"," + " " +
						"," + ws.getLongtitude() +
						"," + ws.getLatitude() +
						"," + ws.getAltitude());
				bw1.newLine();

			}
			bw1.newLine();
			bw1.write("Total average Lon differnce: "+ totlon);
			bw1.newLine();
			bw1.write("Total average Lat differnce: "+ totlat);
			bw1.newLine();
			bw1.write("Total average Alt differnce: "+ totalt);

			System.out.println(name + ".csv was created successfully");
			bw1.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


}

