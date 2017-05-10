package com.pmis.pmislite.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;

import com.pmis.pmislite.login.SessionManager;
import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;
import com.pmis.pmislite.sql.loader.SQLiteCursorLoader;

public class PmisNewWbsTaskData {

	private String taskName;
	private Date eStart;
	private Date eEnd;
	private int priority;
	private String assignedTo;
	private String wbsID;
	private float efforts;
	private Context context;
	private String parentTask;
	private String status;
	private String projectName;
				
	public PmisNewWbsTaskData(Context context,String taskName,String parentTask,Date start,Date end,float efforts,String assignedTo,Integer priority,String wbsId,String status,String projectName) {
		// TODO Auto-generated constructor stub
		this.taskName=taskName;
		this.context=context;
		this.eStart=start;
		this.eEnd=end;
		this.priority=priority;
		this.assignedTo=assignedTo;
		this.wbsID=wbsId;
		this.efforts=efforts;
		this.parentTask=parentTask;
		this.status=status;
		this.projectName=projectName;
	}
	
	private SQLiteCursorLoader getLoader()
	{
		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader loader=null;
	  	try {
	  		helper.createDataBase();
	  		loader=new SQLiteCursorLoader(context.getApplicationContext(), helper,null,null);	

	  	} catch (IOException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}
	  	
	  	return loader;
	}
	
	private long getSelectedProjectPk()
	{
		return PmisUtils.getProjectPk(context,projectName);
		
	}

	public ContentValues saveWbsTask()
	{
		//use db directly
		
		//check if task already present. if so update it.
		//if new task insert it.
		ContentValues retValue=new ContentValues();
		retValue.put("error_code","0");
		retValue.put("error","");
		
		long projectpk=getSelectedProjectPk();
		
		
		if(projectpk==-1)
		{
			return retValue;
		}
		long taskpk=PmisUtils.getTaskPk(context,taskName,projectpk);
		
		long userpk=-1;
		userpk=PmisUtils.getUserPk(context, assignedTo);
		//we got taskpk,projectpk,get user pk of assignedTo
		
				
		if(userpk==-1 || eStart==null || eEnd==null) //still record not exist
		{
			
			retValue.put("error_code","-1");
			retValue.put("error","NO USER RECORD FOUND");
			Toast.makeText(context, "taskpk2:"+taskpk+"::eStart,eEnd=null", Toast.LENGTH_LONG).show();
				
			return retValue;
		}
			
		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);

		SQLiteDatabase database = helper.getWritableDatabase();
        try
        {
        if (database.isOpen()){ 
        	database.beginTransaction();
	       
			//parent task
			long parentpk=-1;
			
			if(parentTask!=null && !parentTask.equalsIgnoreCase(""))
				parentpk=PmisUtils.getTaskPk(context,parentTask,projectpk);
			
			if(taskpk==-1)//new task
			{
				ContentValues upairs=new ContentValues();
				upairs.put("task_name",taskName);
				upairs.put("project_pk",projectpk);
				if(parentpk!=-1)
				{
					upairs.put("parent_task_id",parentpk);
				}
				//Date dt=new Date();
				//upairs.put("last_updated",dt.toLocaleString());
		
				//loader.insert("tasktbl", null,upairs);
				taskpk=database.insert("tasktbl",null,upairs);
					
			}//taskpk==-1
			
			Toast.makeText(context, "taskpk2:"+taskpk+"::eStart:"+eStart.toLocaleString()+"::eEnd:"+eEnd.toLocaleString(), Toast.LENGTH_LONG).show();
				
			if(taskpk==-1) //still record not exist
			{
				retValue.put("error_code","-1");
				retValue.put("error","NO LOGIN DETAILS FOUND");
				
				return retValue;
			}
				
				
			//now insert wbs 
			//"wbs_id" "project_pk" "task_pk" "user_pk"  , int "priority" ,float "efforts_per_day" , "start_estimate" DATE NOT NULL, "end_estimate" DATE NOT NULL,  "status" TEXT DEFAULT "unknown", "last_updated" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
			//db.insertWithOnConflict(tableName, BaseColumns._ID, v, 
		      //      SQLiteDatabase.CONFLICT_REPLACE);
			
			ContentValues wpairs=new ContentValues();
			
			wpairs.put("wbs_id",wbsID);
			wpairs.put("project_pk", projectpk);
			wpairs.put("task_pk", taskpk);
			wpairs.put("user_pk", userpk);
			wpairs.put("priority",String.valueOf(priority));
			wpairs.put("efforts_per_day",String.valueOf(efforts));
			wpairs.put("start_estimate",eStart.toLocaleString());
			wpairs.put("end_estimate",eEnd.toLocaleString());
			wpairs.put("status","open");
			Date dt=new Date();
			wpairs.put("last_updated",dt.toLocaleString());
			
			long wbs_pk=-1;
			wbs_pk=database.insertOrThrow("wbstbl",null,wpairs);
			
			Toast.makeText(context, "wbspk3:"+wbs_pk+"::eStart:"+eStart.toLocaleString()+"::eEnd:"+eEnd.toLocaleString(), Toast.LENGTH_LONG).show();
			
			//read the db now again check whether record exists?
			/*String rawQuery="select * from wbstbl where task_pk=? AND project_pk=? AND user_pk=?";
			String[] args=new String[]{String.valueOf(taskpk),String.valueOf(projectpk),String.valueOf(userpk)};
			
			Cursor cursor=database.rawQuery(rawQuery, args);
		
			//check if wbs record present  exists and get project_pk.
			
			if (cursor.moveToFirst()){
				   while(!cursor.isAfterLast()){
				      
				      //primary key of the companytbl
					  int colId=cursor.getColumnIndex("_id");
				      if(colId!=-1)
				      {
				    	  //primary key of the company
				    	  wbs_pk = cursor.getLong(colId);  
				    	  break;
				      }
					   
				      // do what ever you want here
				      cursor.moveToNext();
				   }
				}
			
			cursor.close();
			*/
            database.setTransactionSuccessful();
            database.endTransaction();
            
			if(wbs_pk!=-1)//record exists
			{
				Toast.makeText(context, "Wbs Record Created Successfully: task_pk:" + taskpk+"::task_name:"+taskName, Toast.LENGTH_LONG).show();
				return retValue;					
			}
			
        }
        else //db  not open
        {
        	
        }
       }
        catch(Exception ee)
		{
        	database.setTransactionSuccessful();
            database.endTransaction();
            
        	retValue.put("error_code","-1");
			retValue.put("error","INTERNAL DATABASE ERROR");
			Toast.makeText(context,ee.fillInStackTrace()+ ":Exception In Insert: Create Wbs Record : task_pk:" + taskpk+"::task_name:"+taskName, Toast.LENGTH_LONG).show();
			return retValue;

		}
finally
{
	database.close();
}
			Toast.makeText(context, "Internal Error Can Not Create Wbs Record : task_pk:" + taskpk+"::task_name:"+taskName, Toast.LENGTH_LONG).show();
			retValue.put("error_code","-1");
			retValue.put("error","Could Not Locate Company");
		
		return retValue;

	}

}
