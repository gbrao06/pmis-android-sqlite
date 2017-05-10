package com.pmis.pmislite.data;

import java.util.Date;

public class TaskData {

	public String taskName;
	long taskpk;//can't be edited
	long parenttaskpk;//can't be edited
	long projectpk;//can't be edited
	
	public String getTaskName() {
		return taskName;
	}

	public long getTaskpk() {
		return taskpk;
	}

	public long getParenttaskpk() {
		return parenttaskpk;
	}

	public long getProjectpk() {
		return projectpk;
	}

	public TaskData(String taskName,long taskpk,long parentpk,long projectpk) {
		// TODO Auto-generated constructor stub
		this.taskName=taskName;
		this.taskpk=taskpk;
		this.parenttaskpk=parentpk;
		this.projectpk=projectpk;
	}

}
