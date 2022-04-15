package com.app.todo.pojo;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author Shahrukh
 * @since 15-Apr-2022
 */
@Getter @Setter @NoArgsConstructor 
public class TaskDetail {
	
	private long id;
	private @NonNull String title;
	private @NonNull String description;
	private @NonNull String category;
	private @NonNull String status;
	private @NonNull Date dateCreated;
	private @NonNull Date dateCompleted;
	private @NonNull String comment;
	
	public TaskDetail(String title, String description, String category, String status, Date dateCreated, Date dateCompleted, String comment) {
		
		this.title = title;
		this.description = description;
		this.category = category;
		this.status = status;
		this.dateCreated = dateCreated;
		this.dateCompleted = dateCompleted;
		this.comment = comment;
	}
	
	
}
