package cn.com.ut.toolkit.builder.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.com.ut.toolkit.builder.pojo.DataBase;

public class JdbcUtil {

	/**
	 * getConnection
	 *
	 * @return Connection
	 */
	public static Connection getConnection(DataBase dataBase) {
	
		Connection conn = null;
		try {
	
			conn = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsername(),
					dataBase.getPassword());
		} catch (SQLException e) {
		}
		return conn;
	}

	/**
	 * closeResultSet
	 * 
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs) {
	
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
	
			} finally {
				rs = null;
			}
		}
	}

	/**
	 * closeConnection
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
	
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
	
			} finally {
				conn = null;
			}
		}
	}

	/**
	 * closeStatement
	 * 
	 * @param stmt
	 */
	public static void closeStatement(Statement stmt) {
	
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
	
			} finally {
				stmt = null;
			}
		}
	}

}
