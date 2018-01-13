package mypack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 * @author ehud and Boaz
 *
 */
public class Read_fSQL {
	private static String _ip = "5.29.193.52";
	private static String _url = "jdbc:mysql://"+_ip+":3306/oop_course_ariel";
	private static String _user = "oop1";
	private static String _password = "Lambda1();";
	private static Connection _con = null;
	private static String table="ex4_db";
	private static String table_shcema="oop_course_ariel";
	Database db;
	Read_fSQL(Database db){
		this.db=db;
	}
	/**
	 * reads from the remote sql server,
	 * this function is only used for the initial connection.
	 * we also create a SQL_Server object to hold the connection details here.
	 * the Data is then fed into the DataBase.
	 */
	void READ() {
		Statement st = null;
		ResultSet rs = null;
		int max_id = -1;
		String scan;
		ArrayList<WifiSpots> scans=new ArrayList();
		WifiSpots s=new WifiSpots();
		WifiSpot w=new WifiSpot();
		String lstModified=null;
		SQL_Server server=new SQL_Server(lstModified, _ip, _user, _password,table,table_shcema);
		/*put the Server into the Hashmap*/
		db.getServer_watch().put(_url, server);
		try {     
			_con = DriverManager.getConnection(_url, _user, _password);
			st = _con.createStatement();
			rs = st.executeQuery("SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = "+"'"+table_shcema+"'"+ "AND TABLE_NAME = "+"'"+table+"'");
			if (rs.next()) {
				System.out.println("**** Update: "+rs.getString(1));
				server.setLstModified(rs.getString(1));
			}
			
			PreparedStatement pst = _con.prepareStatement("SELECT * FROM "+table);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				int size = rs.getInt(7);
				int len = 7+2*size;
				if(size>0) {
					/*first five fields are date,id,location*/
					 s=new WifiSpots(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
							rs.getString(6),_url);
				}else {
					throw new SQLException("null table");
				}
				for(int i=8;i<len;i+=2){
					  w=new WifiSpot(rs.getString(i),rs.getString(i+1));
					  s.getSpots().add(w);
				}scans.add(s);
				s.toPrint();
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(MySQL_101.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (st != null) { st.close(); }
				if (_con != null) { _con.close();  }
			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(MySQL_101.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}db.addToDB(scans);

	}
	/**
	 * this function is used to Update Data if server's table was modified
	 * 
	 * @param srv
	 * @return
	 */
	static ArrayList<WifiSpots> Read_Again(SQL_Server srv) {
		Statement st = null;
		ResultSet rs = null;
		int max_id = -1;
		String scan;
		ArrayList<WifiSpots> scans=new ArrayList();
		WifiSpots s=new WifiSpots();
		WifiSpot w=new WifiSpot();
		try {     
			_con = DriverManager.getConnection(srv.get_url(), srv.get_user(), srv.get_password());
			st = _con.createStatement();
		
			PreparedStatement pst = _con.prepareStatement("SELECT * FROM "+srv.get_table());
			rs = pst.executeQuery();
		
			while (rs.next()) {
				int size = rs.getInt(7);
				int len = 7+2*size;
				if(size>0) {
					/*first five fields are date,id,location*/
					 s=new WifiSpots(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
							rs.getString(6),srv.get_url());
				}
				for(int i=8;i<len;i+=2){
					  w=new WifiSpot(rs.getString(i),rs.getString(i+1));
					  s.getSpots().add(w);
				}scans.add(s);
				s.toPrint();
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(MySQL_101.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (st != null) { st.close(); }
				if (_con != null) { _con.close();  }
			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(MySQL_101.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}return scans;

		
	}
	public static void main(String[] args) {
		Database db=new Database("//home//ehud//Desktop///asd//Algo2_BM2_TS1_4.csv","WifiSpots");
		Read_fSQL f=new Read_fSQL(db);
		System.out.println(		db.getDB().size());
		f.READ();
		System.out.println(		db.getDB().size());
	}
}
