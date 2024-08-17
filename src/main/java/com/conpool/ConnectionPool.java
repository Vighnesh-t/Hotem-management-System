package com.conpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
	static String url = "jdbc:postgresql://localhost:5432/hotel_management?user=postgres&password=root";
	static String driver = "org.postgresql.Driver";
	static final int poolSize = 4;
	static List<Connection> conPool = new ArrayList<>();

	static {
		for (int i = 0; i < poolSize; i++) {
			conPool.add(createConnection());
		}
	}

	private static Connection createConnection() {
		Connection con=null;
		try {
			Class.forName(driver);
			
			con=DriverManager.getConnection(url);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	public static Connection giveConnection() {
		if(!conPool.isEmpty()) {
			return conPool.remove(0);
		}else {
			return createConnection();
		}
	}
	
	public static void submitConnection(Connection con) {
		if(conPool.size()<poolSize){
			conPool.add(con);
		}else {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}


