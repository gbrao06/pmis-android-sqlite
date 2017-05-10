package com.pmis.pmislite.data;

import java.util.Date;

import android.content.Context;

public class WbsData {

	Context context;
	long wbspk;
	TaskData taskdata;
	long assignedpk;//can't be edited
	
	//editable
	int priority;
	String status;
	Date eStart,eEnd;
	String wbsID;
	float efforts;
	
	public Date getStartEstimate()
	{
		return eStart;
	}
	public Date getEndEstimate()
	{
		return eEnd;
	}
	public String getWbsID()
	{
		return wbsID;
	}
	public float getEfforts()
	{
		return efforts;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public long getAssignedPk()
	{
		return assignedpk;
	}
	
	public String getAssignedName()
	{
		return PmisUtils.getUserName(context, assignedpk);
	}
	
	public TaskData getTaskData()
	{
		return taskdata;
	}
	
	public WbsData(Context context,long wbspk,TaskData tdata,long assignedtopk,int priority,String status,Date start,Date end,String wbsID,float efforts) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.wbspk=wbspk;
		this.taskdata=tdata;
		this.assignedpk=assignedtopk;
		this.priority=priority;
		this.status=status;
		this.eStart=start;
		this.eEnd=end;
		this.wbsID=wbsID;
		this.efforts=efforts;
	}

}
