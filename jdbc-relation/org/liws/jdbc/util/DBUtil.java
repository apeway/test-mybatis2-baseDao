package org.liws.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	
	public static Connection getConnection() {
		Properties prop = PropertiesUtil.getJdbcProp();
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String driver = prop.getProperty("driver");
		String url = prop.getProperty("url");
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password); // jar包放jdk的ext下
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void close(Connection con) {
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement ps) {
		try {
			if (ps != null) ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			if (rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
