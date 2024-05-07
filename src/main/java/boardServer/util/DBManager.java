package boardServer.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBManager {
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Context init = new InitialContext();
			DataSource source = (DataSource) init.lookup("java:comp/env/jdbc/BoardServerDB");
			
			conn = source.getConnection();
			
			System.out.println("[DB 연동 성공]");
		} catch (Exception e) {
			System.out.println("[DB 연동 실패]");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			rs.close();
			pstmt.close();
			conn.close();
			
			System.out.println("[DB 연결 해제]");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[DB 연결 해제 실패]");
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt) {
		try {
			pstmt.close();
			conn.close();
			
			System.out.println("[DB 연결 해제]");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[DB 연결 해제 실패]");
		}
	}
}
