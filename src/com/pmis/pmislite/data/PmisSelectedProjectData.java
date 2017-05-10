package com.pmis.pmislite.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;
import com.pmis.pmislite.sql.loader.SQLiteCursorLoader;

public class PmisSelectedProjectData {

	private String projectName;
	private Context context; 
	public PmisSelectedProjectData(Context context,String projectName) {
		// TODO Auto-generated constructor stub
		this.projectName=projectName;
		this.context=context;
	}
	
	public String getProjectName()
	{
		return projectName;
	}
	public List<TaskData> getTasksOfTheProject()
	{
		//PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
	  	
		return PmisUtils.getSelectedProjectTaskList(context, projectName);
		
	}
	
	public List<WbsData> getWbsDataOfTheProject()
	{
		
		return PmisUtils.getSelectedProjectWbsDataList(context, projectName);
		
	}
}
