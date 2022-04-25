/**
 * 
 */
package com.app.todo.dao;

/**
 * @author Shahrukh
 * @since 17-Apr-2022
 */
public interface QueryHelper {
	
	String queryGetAllTasks = "SELECT * FROM todo_list_db.task";
	
	String queryGetTaskByDetail = "SELECT * FROM todo_list_db.task WHERE title = ?";
	
	String queryAddNewTask = "INSERT INTO task (title, description, category, status, date_created, date_completed, comment) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	String queryUpdateDescription = "UPDATE todo_list_db.task SET description = ? WHERE (id = ? and title = ?)";
	
	String queryUpdateTask = "UPDATE task "
			+ "SET title = ?, description = ?, category = ?, status = ?, date_completed = ?, comment = ? WHERE (id = ? and title = ?)"; 
	
	String queryDeleteTask = "DELETE FROM todo_list_db.task WHERE (id = ? and title = ?)";
	
	String insertSuccess = "New task detail created successfully!";
	String insertFail = "New task detail was not created, please try again later.";
	
	String updateSuccess = "Task detail modified successfully!";
	String updateFail = "Error while task detail updation, please try again later.";
	
	String deleteSuccess = "Task detail removed successfully!";
	String deleteFail = "Error while task detail deletion, please try again later.";
	
	String searchSuccess = "Task details found!";
	String searchFail = "Task details with given title does not exist!";
	
	String statusComplete = "COMPLETED";
	
	//validations
	String errorSearchEmpty = "Error, Please enter value to be searched!";
	String errorTitleEmpty = "Error, Task title is a mandatory field inorder to create a task. Please try again!";
}

