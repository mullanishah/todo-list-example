package com.app.todo.operations;

import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;
import com.app.todo.dao.QueryHelper;
import com.app.todo.dao.TaskListAddDao;
import com.app.todo.dao.TaskListDeleteDao;
import com.app.todo.dao.TaskListGetDao;
import com.app.todo.dao.TaskListUpdateDao;
import com.app.todo.pojo.TaskDetail;
import com.app.todo.utils.CommonUtils;

/**
 * @author Shahrukh
 * @since 24-Apr-2022
 */
public class TaskListOperations {

	private static final Logger log = Logger.getLogger(TaskListOperations.class);
	private TaskListAddDao addTaskDao = null;
	private TaskListUpdateDao updateTaskDao = null;
	private TaskListDeleteDao deleteTaskDao = null;
	private TaskListGetDao getTaskDao = null;

	public TaskListOperations() throws Exception {
		addTaskDao = new TaskListAddDao();
		updateTaskDao = new TaskListUpdateDao();
		deleteTaskDao = new TaskListDeleteDao();
		getTaskDao = new TaskListGetDao();
	}

	public Map<String, TaskDetail> getAllTasks() throws Exception{
		
		log.info("Inside " + getClass() + ".getAllTasks() method");
		return getTaskDao.getAllTaskDetails();
	}

	public String addTask(TaskDetail task) throws Exception {

		log.info("Inside " + getClass() + ".addTask() method");
		int insertStatus = addTaskDao.addNewTask(task);
		return (insertStatus == 1) ? QueryHelper.insertSuccess : QueryHelper.insertFail;
	}

	public String updateTask(TaskDetail oldTaskDetails, TaskDetail task) throws Exception {

		log.info("Inside " + getClass() + ".updateTask() method");
		int updateStatus = updateTaskDao.updateTask(oldTaskDetails, task);
		return (updateStatus == 1) ? QueryHelper.updateSuccess : QueryHelper.updateFail;
	}

	public String deleteTask(TaskDetail task) throws Exception{

		log.info("Inside " + getClass() + ".deleteTask() method");
		int deleteStatus = deleteTaskDao.deleteTask(task);
		return (deleteStatus == 1) ? QueryHelper.deleteSuccess : QueryHelper.deleteFail;
	}
	
	public String searchTask(String title) throws Exception {
		
		log.info("Inside " + getClass() + ".searchTask() method");
		TaskDetail task = getTaskDao.getTaskDetails(title);
		if(task != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(QueryHelper.searchSuccess + "\n");
			sb.append("Title: " + task.getTitle() + "\n");
			sb.append("Description: " + task.getDescription() + " | ");
			sb.append("Status: " + task.getStatus() + "\n");
			sb.append("Created on: " + task.getDateCreated() + " | Completed on: " + getEnrichedTaskCompletedDate(task.getDateCompleted()));
			return sb.toString(); 
		}
		return QueryHelper.searchFail;
	}
	
	private String getEnrichedTaskCompletedDate(Date completionDate) {
		
		log.info("Inside " + getClass() + ".getEnrichedTaskCompletedDate() method");
		if(completionDate == null) {
			return "Not yet";
		} else if(CommonUtils.getSdf().format(completionDate) != null) {
			return completionDate.toString();
		}
		return "Not yet";
	}

}
