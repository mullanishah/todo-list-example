package com.app.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import com.app.todo.pojo.TaskDetail;

/**
 * @author Shahrukh
 * @since 16-Apr-2022
 */
public class TaskListGetDao {
	
	private Connection conn;
	private PreparedStatement pstGetAllTasks;
	
	public TaskListGetDao() throws SQLException {
		this.conn = DatabaseUtils.getConnection();
		this.pstGetAllTasks = conn.prepareStatement(QueryHelper.queryGetAllTasks);
	}
	
	public void cleanUp() throws Exception {
		if(null != pstGetAllTasks)
			pstGetAllTasks.close();
		if(null != conn)
			conn.close();
	}
	
	//CRUD operations
	public Map<String, TaskDetail> getAllTaskDetails() throws Exception {
		
		int rowCount = 0;
		ResultSet rst = null;
		Map<String, TaskDetail> taskDetailMap = new LinkedHashMap<String, TaskDetail>();
		
		try {
			rst = pstGetAllTasks.executeQuery();
			while(rst.next() != false) {
				String title = rst.getString("title");
				taskDetailMap.put(title, new TaskDetail(
						rst.getLong("id"), 
						title, 
						rst.getString("description"), 
						rst.getString("category"), 
						rst.getString("status"), 
						rst.getDate("date_created"), 
						rst.getDate("date_completed"),
						rst.getString("comment")));
				rowCount++;
			}
			System.out.println("Total rows retrived from database: " + rowCount);
			//change it to logger
		} catch (Exception e) {
			System.out.println("Error in get all tasks");
			//change it to logger
		} finally {
			rst.close();
			cleanUp();
		}
		return taskDetailMap;
	}
	
}
