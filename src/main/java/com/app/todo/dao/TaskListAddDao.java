package com.app.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.app.todo.pojo.TaskDetail;

/**
 * @author Shahrukh
 * @since 24-Apr-2022
 */
public class TaskListAddDao {
	
	private static final Logger log = Logger.getLogger(TaskListAddDao.class);
	private Connection conn;
	private PreparedStatement pstAddNewTask;
	
	public TaskListAddDao() throws SQLException {
		this.conn = DatabaseUtils.getConnection();
		this.pstAddNewTask = conn.prepareStatement(QueryHelper.queryAddNewTask);
	}
	
	public void cleanUp() throws Exception {
		if(null != pstAddNewTask)
			pstAddNewTask.close();
		if(null != conn)
			conn.close();
	}
	
	//CRUD operations
	public Integer addNewTask(TaskDetail task) throws Exception {
		log.info("Inside " + getClass() + ".addNewTask() method");
		
		try {	
			pstAddNewTask.setString(1, task.getTitle());
			pstAddNewTask.setString(2, task.getDescription());
			pstAddNewTask.setString(3, task.getCategory());
			pstAddNewTask.setString(4, task.getStatus());
			pstAddNewTask.setDate(5, new java.sql.Date(task.getDateCreated().getTime()));
			pstAddNewTask.setDate(6, null);
			pstAddNewTask.setString(7, task.getComment());
			
			int insertCount = pstAddNewTask.executeUpdate();
			if(insertCount == 1) {
				log.info("New task created with title: " + task.getTitle());
				return insertCount;
			}
		}catch (Exception e) {
			log.error("Error occurred while creating a new task");
			e.printStackTrace();
		}
		return -1;
	}
	
}
