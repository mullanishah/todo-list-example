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

	public TaskDetail(long id, String title, String description, String category, String status, Date dateCreated,
			Date dateCompleted, String comment) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.status = status;
		this.dateCreated = dateCreated;
		this.dateCompleted = dateCompleted;
		this.comment = comment;
	}
	
	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public String getStatus() {
		return status;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return "TaskDetail [id=" + id + ", title=" + title + ", description=" + description + ", category=" + category
				+ ", status=" + status + ", dateCreated=" + dateCreated + ", dateCompleted=" + dateCompleted
				+ ", comment=" + comment + "]";
	}	
}
