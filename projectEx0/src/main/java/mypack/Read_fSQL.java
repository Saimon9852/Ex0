package main.java.mypack;

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
	private String _ip;
	private String _url;
	private String _user;
	private String _password;
	private static Connection _con = null;
	private String table;
	private String table_shcema;
	private Database db;

	
	public Read_fSQL(Database db,String pass,String user,String url,String ip,String table,String tableSch){
		
		this._password = pass;
		this._user = user;
		this._url=url;
		this._ip=ip;
		this.table = table;
		this.table_shcema = tableSch;
		this.db = db;
		
	}
	
	/**
	 * reads from the remote sql server,
	 * this function is only used for the initial connection.
	 * we also create a SQL_Server object to hold the connection details here.
	 * the Data is then fed into the DataBase.
	 */
	public void READ() {
		Statement st = null;
		ResultSet rs = null;
		int max_id = -1;
		String scan;
		ArrayList<WifiSpots> scans=new ArrayList();
		WifiSpots s=new WifiSpots();
		WifiSpot w=new WifiSpot();
		String lstModified="";
		SQL_Server server=new SQL_Server(lstModified, _ip, _user, _password,table,table_shcema,_url);
		/*put the Server into the Hashmap*/
		db.getServer_watch().put(_url, server);
		
		try {     
			_con = DriverManager.getConnection(_url, _user, _password);
			st = _con.createStatement();
			rs = st.executeQuery("SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = "+"'"+table_shcema+"'"+ "AND TABLE_NAME = "+"'"+table+"'");
			if (rs.next()) {
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
				//s.toPrint();
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
		}
		db.addToDB(scans);
		

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
				//s.toPrint();
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
	
}
