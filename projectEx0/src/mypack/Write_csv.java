package mypack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Write_csv {
static void write(Csv_noGPS csv){
	FileWriter fw;

	try {
		fw = new FileWriter("fixed" +".csv");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Time,ID,Lat,Lon,Alt,#WiFi networks,SSID1,MAC1,Frequancy1,Signal1,SSID2,MAC2,Frequancy2,Signal2,SSID3"
				+ ",MAC3,Frequancy3,Signal3,SSID4,MAC4,Frequancy4,Signal4,SSID5,MAC5,Frequancy5,Signal5,"
				+ "SSID6,MAC6,Frequancy6,Signal6,SSID7,MAC7,Frequancy7,Signal7,"
				+ "SSID8,MAC8,Frequancy8,Signal8,SSID9,MAC9,Frequancy9,Signal9,SSID10,MAC10,Frequancy10,Signal10");
		bw.newLine();
		for(int k=0; k<csv.getCsv().size();k++){
			for(int i=0;i<csv.getCsv().get(k).getSpots().size();i++){
				bw.write(csv.getCsv().get(k).getSpots().get(i).getFirsseen()
						+","+csv.getCsv().get(i).getID()
						+","+csv.getCsv().get(i).getLatitude()
						+","+csv.getCsv().get(i).getLongtitude()
						+","+csv.getCsv().get(i).getAltitude()
						+","+csv.getCsv().get(i).spots.size()+",");
				for(int j=0;j<csv.getCsv().get(i).spots.size();j++){
					bw.write(
							csv.getCsv().get(i).getSpots().get(j).getSsid()
							+","+csv.getCsv().get(i).getSpots().get(j).getMac()
							+","+csv.getCsv().get(i).getSpots().get(j).getChanel()
							+","+csv.getCsv().get(i).getSpots().get(j).getRssi()+","
							);						
				}bw.newLine();

			}

		}
		System.out.println("fixed" + ".csv was created successfully");
		bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
