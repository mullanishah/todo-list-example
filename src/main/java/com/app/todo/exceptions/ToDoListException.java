package com.app.todo.exceptions;

/**
 * @author Shahrukh
 * @since 15-Apr-2022
 */
public class ToDoListException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public ToDoListException(String msg) {
		this.message = msg;
	}

	
}
