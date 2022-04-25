package com.app.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.app.todo.pojo.TaskDetail;

/**
 * @author Shahrukh
 * @since 16-Apr-2022
 */
public class TaskListGetDao {
	private static final Logger log = Logger.getLogger(TaskListGetDao.class);
	private Connection conn;
	private PreparedStatement pstGetAllTasks, pstGetTask;

	public TaskListGetDao() throws SQLException {
		this.conn = DatabaseUtils.getConnection();
		this.pstGetAllTasks = conn.prepareStatement(QueryHelper.queryGetAllTasks);
		this.pstGetTask = conn.prepareStatement(QueryHelper.queryGetTaskByDetail);
	}

	public void cleanUp() throws Exception {
		if(null != pstGetAllTasks)
			pstGetAllTasks.close();
		if(null != pstGetTask)
			pstGetTask.close();
		if(null != conn)
			conn.close();
	}

	//CRUD operations
	public Map<String, TaskDetail> getAllTaskDetails() throws Exception {

		log.info("Inside " + getClass() + ".getAllTaskDetails() method");
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
			log.info("Total rows retrived from database: " + rowCount);
		} catch (Exception e) {
			log.error("Error occurred while fetching/get all tasks");
			e.printStackTrace();
		} finally {
			//rst.close();
		}
		return taskDetailMap;
	}

	public TaskDetail getTaskDetails(String title) throws Exception {

		log.info("Inside " + getClass() + ".getTaskDetails() method");
		TaskDetail task = null;
		ResultSet rst = null;
		try {
			pstGetTask.setString(1, title);
			rst = pstGetTask.executeQuery();
			while(rst.next()) {
				task = new TaskDetail(
						rst.getLong("id"), 
						rst.getString("title"), 
						rst.getString("description"), 
						rst.getString("category"), 
						rst.getString("status"), 
						rst.getDate("date_created"), 
						rst.getDate("date_completed"),
						rst.getString("comment"));
			}
			log.info("Returned object: " + task);
		} catch (Exception e) {
			log.error("Error occurred while fetching/get the task");
			e.printStackTrace();
		} finally {
			//rst.close();
		}
		return task;
	}

}
