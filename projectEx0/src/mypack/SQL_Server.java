package mypack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * hold all the data needed to communecate with a remote mySQL server.
 * @author ehud
 *
 */
public class SQL_Server {
	private String lstModified;
	private  String _ip;
	private  String _url;
	private  String _user;
	private  String _password;
	private String table;
	private String table_Schema;
	SQL_Server(String lstModified,String _ip,String _user,String _password,String table,String table_Schema){
		this._ip=_ip;
		this._password=_password;
		this.lstModified=lstModified;
		this._user=_user;
		this.table=table;
		this.table_Schema=table_Schema;
	}
	public String gettable_Schema() {
		return table_Schema;
	}
	public void settable_Schema(String table_Schema) {
		this.table_Schema = table_Schema;
	}
	public String getLstModified() {
		return lstModified;
	}
	public void setLstModified(String lstModified) {
		this.lstModified = lstModified;
	}
	public String get_ip() {
		return _ip;
	}
	public String get_table() {
		return this.table;
	}
	public void set_ip(String _ip) {
		this._ip = _ip;
	}
	public String get_url() {
		return _url;
	}
	public void set_url(String _url) {
		this._url = _url;
	}
	public String get_user() {
		return _user;
	}
	public void set_user(String _user) {
		this._user = _user;
	}
	public String get_password() {
		return _password;
	}
	public void set_password(String _password) {
		this._password = _password;
	}
	/**
	 * This function checks if  the table was updated.
	 * @return true if table was modified false if it wasnt
	 */
	public boolean isModified() {
		Statement st = null;
		ResultSet rs = null;
	    Connection _con = null;

		try {     
			_con = DriverManager.getConnection(_url, _user, _password);
			st = _con.createStatement();
			rs = st.executeQuery("SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = 'oop_course_ariel' AND TABLE_NAME = +"+table);
			if (rs.next()) {
				/*if the last modified time isn't current, then table was modified*/
				if(rs.getString(1)!=lstModified) {
					lstModified=rs.getString(1);
					return true;
				}else {
					return false;
				}
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
		} return false;

	}
}
