package com.app.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.app.todo.pojo.TaskDetail;

/**
 * @author Shahrukh
 * @since 24-Apr-2022
 */
public class TaskListUpdateDao {
	private Connection conn;
	private PreparedStatement pstUpdateTask;

	public TaskListUpdateDao() throws SQLException {
		this.conn = DatabaseUtils.getConnection();
		this.pstUpdateTask = conn.prepareStatement(QueryHelper.queryAddNewTask);
	}

	public void cleanUp() throws Exception {
		if(null != pstUpdateTask)
			pstUpdateTask.close();
		if(null != conn)
			conn.close();
	}

	//CRUD operations
	public Integer updateTask(TaskDetail task) throws Exception {

		try {
			pstUpdateTask.setString(1, task.getTitle());
			pstUpdateTask.setString(2, task.getDescription());
			pstUpdateTask.setString(3, task.getCategory());
			pstUpdateTask.setString(4, task.getStatus());
			pstUpdateTask.setDate(5, new java.sql.Date(task.getDateCompleted().getTime()));
			pstUpdateTask.setString(6, task.getComment());
			pstUpdateTask.setLong(7, task.getId());
			pstUpdateTask.setString(8, task.getTitle());

			int updateCount = pstUpdateTask.executeUpdate();
			if(updateCount == 1) {
				return updateCount;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.cleanUp();
		}
		return -1;	
	}
	
	/*
	 * TODO: update status and completion date if status completed
	 *  status: NEW, In progress, blockage, completed
	 */
	public Integer updateTaskStatus() throws Exception {
		try {
			int a = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			cleanUp();
		}
		return -1;
	}
}
