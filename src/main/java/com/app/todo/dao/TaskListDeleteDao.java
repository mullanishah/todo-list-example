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
public class TaskListDeleteDao {
	private static final Logger log = Logger.getLogger(TaskListDeleteDao.class);
	private Connection conn;
	private PreparedStatement pstDeleteTask;

	public TaskListDeleteDao() throws SQLException {
		this.conn = DatabaseUtils.getConnection();
		this.pstDeleteTask = conn.prepareStatement(QueryHelper.queryDeleteTask);
	}

	public void cleanUp() throws Exception {
		if(null != pstDeleteTask)
			pstDeleteTask.close();
		if(null != conn)
			conn.close();
	}

	//CRUD operations
	public Integer deleteTask(TaskDetail task) throws Exception {
		
		log.info("Inside " + getClass() + ".deleteTask() method");
		try {
			pstDeleteTask.setLong(1, task.getId());
			pstDeleteTask.setString(2, task.getTitle());

			int deleteStatus = pstDeleteTask.executeUpdate();
			if(deleteStatus == 1) {
				log.info("Task deleted with title: " + task.getTitle());
				return deleteStatus;
			}
		}catch (Exception e) {
			log.error("Error occurred while deleting the task");
			e.printStackTrace();
		}
		return -1;
	}
}
