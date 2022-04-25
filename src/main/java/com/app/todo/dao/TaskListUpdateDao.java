package com.app.todo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.app.todo.pojo.TaskDetail;

/**
 * @author Shahrukh
 * @since 24-Apr-2022
 */
public class TaskListUpdateDao {
	
	//private static Logger log = Logger.getLogger(TaskListUpdateDao.class);
	private Connection conn;
	private PreparedStatement pstUpdateTask;

	public TaskListUpdateDao() throws SQLException {
		this.conn = DatabaseUtils.getConnection();
		this.pstUpdateTask = conn.prepareStatement(QueryHelper.queryUpdateTask);
	}

	public void cleanUp() throws Exception {
		if(null != pstUpdateTask)
			pstUpdateTask.close();
		if(null != conn)
			conn.close();
	}

	//CRUD operations
	public Integer updateTask(TaskDetail oldTaskDetails, TaskDetail task) throws Exception {

		try {
			//log.debug("updateTask(), oldTitle: " + oldTitle +", task:" + task);
			//System.out.println("updateTask(), oldTitle: " + oldTaskDetails +" | task:" + task);
			pstUpdateTask.setString(1, task.getTitle());
			pstUpdateTask.setString(2, task.getDescription());
			pstUpdateTask.setString(3, task.getCategory());
			pstUpdateTask.setString(4, task.getStatus());
			if(task.getDateCompleted() != null && !(task.getDateCompleted().toString().equals("")))
				pstUpdateTask.setDate(5, new java.sql.Date(task.getDateCompleted().getTime()));
			if(task.getDateCompleted() == null || task.getDateCompleted().toString().equals(""))
				pstUpdateTask.setDate(5, null);
			pstUpdateTask.setString(6, task.getComment());
			pstUpdateTask.setLong(7, oldTaskDetails.getId());
			pstUpdateTask.setString(8, oldTaskDetails.getTitle());
			
			int updateCount = pstUpdateTask.executeUpdate();
			//.debug("updateTask(), updateCount: " + updateCount);
			//System.out.println("updateTask(), updateCount: " + updateCount);
			if(updateCount == 1) {
				return updateCount;
			}
		}catch (Exception e) {
			e.printStackTrace();
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
		return -1;
	}
}
