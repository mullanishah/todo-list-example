package com.app.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.app.todo.pojo.TaskDetail;

/**
 * @author Shahrukh
 * @since 24-Apr-2022
 */
public class TaskListDeleteDao {
	private Connection conn;
	private PreparedStatement pstDeleteTask;

	public TaskListDeleteDao() throws SQLException {
		this.conn = DatabaseUtils.getConnection();
		this.pstDeleteTask = conn.prepareStatement(QueryHelper.queryAddNewTask);
	}

	public void cleanUp() throws Exception {
		if(null != pstDeleteTask)
			pstDeleteTask.close();
		if(null != conn)
			conn.close();
	}

	//CRUD operations
	public Integer deleteTask(TaskDetail task) throws Exception {

		try {
			pstDeleteTask.setLong(1, task.getId());
			pstDeleteTask.setString(2, task.getTitle());

			int deleteStatus = pstDeleteTask.executeUpdate();
			if(deleteStatus == 1) {
				return deleteStatus;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			cleanUp();
		}
		return -1;
	}
}
