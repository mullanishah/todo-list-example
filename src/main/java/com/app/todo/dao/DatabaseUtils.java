package com.app.todo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * @author Shahrukh
 * @since 17-Apr-2022
 */
public class DatabaseUtils {
	
	private static final Logger log = Logger.getLogger(DatabaseUtils.class);
	private static Connection conn = null;
	
	public static Connection getConnection() {
		
		//log.info("Inside " + log.getClass().getName() + ".getConnection()");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/todo_list_db", "root", "root");
			//log.info("Driver loaded and connection loaded");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
