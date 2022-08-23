package com.crm.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConection {

	public static Connection getConnection() {
		String datebase = "jdbc:mysql://localhost:3306/crm?useSSL=false";
		String username = "root";
		String password = "1234";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(datebase, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
