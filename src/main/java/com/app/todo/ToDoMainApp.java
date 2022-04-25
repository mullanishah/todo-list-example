package com.app.todo;

import org.apache.log4j.Logger;

import com.app.todo.ui.TaskListUI;

/**
 * Hello world!
 *
 */
public class ToDoMainApp {

	private static final Logger log = Logger.getLogger(ToDoMainApp.class);

	public static void main( String[] args ) {

		try {
			log.info("Entered into ToDoMainApp class");
			TaskListUI uiApp = new TaskListUI();
			log.info("bye");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
