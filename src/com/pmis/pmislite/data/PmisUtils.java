package com.pmis.pmislite.data;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;
import com.pmis.pmislite.sql.loader.SQLiteCursorLoader;

public class PmisUtils {


	public static boolean deleteWbsTask(Context context,String projectname,String taskname,String username)
	{
		long projectpk=getProjectPk(context,projectname);
		long taskpk=getTaskPk(context,taskname,projectpk);
		long userpk=getUserPk(context,username);
		
		long wbspk=getWbsPk(context,projectpk,taskpk,userpk);
		
		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		try
		{		
			
			if(database.isOpen())
			{
				database.beginTransaction();
				
				int rowsAffected = 0;
		        String[] Values = new String[1];
		        String Where = "_id=?";
			    Values[0]=String.valueOf(wbspk);    
			    database.delete("wbstbl",Where, Values);
			    
			    //delete tasktbl also
			   
			    //Values[0]=String.valueOf(taskpk);    
			    //database.delete("tasktbl",Where, Values);
			    
			    database.setTransactionSuccessful();
	            database.endTransaction();
	             
			}
		}
		catch(Exception e){
				
			database.setTransactionSuccessful();
            database.endTransaction();
            Toast.makeText(context, "Exception In Delete: Delete Wbs Record : task_name:" + taskname+"::user_name:"+username, Toast.LENGTH_LONG).show();
			return false;	
		      }
			finally{
	            database.close();            
	        }	
		
		Toast.makeText(context, "Successfully Deleted Wbs Record : task_name:" + taskname+"::user_name:"+username+"::projectname:"+projectname, Toast.LENGTH_LONG).show();
		return true;
	}

	public static boolean deleteTask(Context context,String projectname,String taskname)
	{
		long projectpk=getProjectPk(context,projectname);
		long taskpk=getTaskPk(context,taskname,projectpk);
		
		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		try
		{		
			
			if(database.isOpen())
			{
				database.beginTransaction();
				
				int rowsAffected = 0;
		        String[] Values = new String[1];
		        String Where = "_id=?";
			    Values[0]=String.valueOf(taskpk);    
			    database.delete("tasktbl",Where, Values);
			    
			    database.setTransactionSuccessful();
	            database.endTransaction();
	            
			}
		}
		catch(Exception e){
				
			database.setTransactionSuccessful();
            database.endTransaction();
            Toast.makeText(context, "Exception In Delete: Delete Task Record : task_name:" + taskname+"::project_name:"+projectname, Toast.LENGTH_LONG).show();
			return false;	
		      }
			finally{
	            database.close();            
	        }	
		
		Toast.makeText(context, "Successfully Deleted Wbs Record : task_name:" + taskname+"::projectname:"+projectname, Toast.LENGTH_LONG).show();
		return true;
	}


	public static boolean deleteTask(Context context,long taskpk)
	{
		if(taskpk<=0)
			return false;
		
		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		try
		{		
			
			if(database.isOpen())
			{
				database.beginTransaction();
				
				int rowsAffected = 0;
		        String[] Values = new String[1];
		        String Where = "_id=?";
			    Values[0]=String.valueOf(taskpk);    
			    database.delete("tasktbl",Where, Values);
			    
			    database.setTransactionSuccessful();
	            database.endTransaction();
	            
			}
		}
		catch(Exception e){
				
			database.setTransactionSuccessful();
            database.endTransaction();
            Toast.makeText(context, "Exception In Delete: Delete Task Record :", Toast.LENGTH_LONG).show();
			return false;	
		      }
			finally{
	            database.close();            
	        }	
		
		Toast.makeText(context, "Successfully Deleted Wbs Record : task_name:", Toast.LENGTH_LONG).show();
		return true;
	}


	public static List<WbsData> getSelectedProjectWbsDataList(Context context, String projectName)
	{
		List<WbsData> list=new ArrayList<WbsData>();
		long projectpk=-1;
		
		projectpk=getProjectPk(context,projectName);
		
		if(projectpk==-1)
		{
			Toast.makeText(context.getApplicationContext(),"projectpk=-1"+"::projectName:"+projectName, Toast.LENGTH_LONG).show();
			return list;
		}
		Toast.makeText(context.getApplicationContext(),":2:projectpk:"+projectpk, Toast.LENGTH_LONG).show();
		
		String rawQuery="select * from wbstbl where project_pk=?";
		String args[]=new String[]{String.valueOf(projectpk)};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
   	
             //int priority,String status,Date start,end,assignedto
             if(cursor!=null)
            	 Toast.makeText(context.getApplicationContext(),"::cursor not null:"+projectpk, Toast.LENGTH_LONG).show();
     		
             if (cursor.moveToFirst()){
            	 Toast.makeText(context.getApplicationContext(),"::Inside:"+projectpk, Toast.LENGTH_LONG).show();
          		
            	 while(!cursor.isAfterLast()){
 				  
 				  long taskpk=-1;long wbspk=-1; long upk=-1;int priority = 4;String status = null;Timestamp start = null,end=null;String wbsid=null;float efforts=0;
 				
 				 Toast.makeText(context.getApplicationContext(),":2 Inside 2:", Toast.LENGTH_LONG).show();
  		     	
 				 int colId=cursor.getColumnIndex("_id");
 		    	
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	
			    	  wbspk = cursor.getLong(colId);
			    	  Toast.makeText(context.getApplicationContext(),":4:wbspk:"+wbspk, Toast.LENGTH_LONG).show();
				  		
			      }
			      
			      colId=cursor.getColumnIndex("wbs_id");
			      Toast.makeText(context.getApplicationContext(),":wbs_id:coldId:"+colId, Toast.LENGTH_LONG).show();
	 		     	
			      if(colId!=-1)
 			      {
 			    	  //primary key of the company
 			    	  wbsid = cursor.getString(colId);
 			    	  Toast.makeText(context.getApplicationContext(),":5:wbsid:"+wbsid, Toast.LENGTH_LONG).show();
 				  		
 			      }
 			      

 				  colId=cursor.getColumnIndex("task_pk");
 				 Toast.makeText(context.getApplicationContext(),":task_pk:coldId:"+colId, Toast.LENGTH_LONG).show();
  		     	
 			      if(colId!=-1)
 			      {
 			    	  //primary key of the company
 			    	  taskpk = cursor.getLong(colId);
 			    	  Toast.makeText(context.getApplicationContext(),":6:takpk:"+taskpk, Toast.LENGTH_LONG).show();
 				  		
 			      }
 			      
 				  colId=cursor.getColumnIndex("user_pk");
 				 Toast.makeText(context.getApplicationContext(),":user_pk:coldId:"+colId, Toast.LENGTH_LONG).show();
   		     	
 				  if(colId!=-1)
 			      {
 			    	  //primary key of the company
 			    	  upk = cursor.getLong(colId);
 			    	  Toast.makeText(context.getApplicationContext(),":7:upk:"+upk, Toast.LENGTH_LONG).show();
 				  		
 			      }
 			      
 			     colId=cursor.getColumnIndex("priority");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  priority = cursor.getInt(colId);
			    	  Toast.makeText(context.getApplicationContext(),":8:priority:"+priority, Toast.LENGTH_LONG).show();
				  		
			      }
			      colId=cursor.getColumnIndex("efforts_per_day");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  efforts = cursor.getFloat(colId);
			    	  Toast.makeText(context.getApplicationContext(),":9:efforts:"+efforts, Toast.LENGTH_LONG).show();
				  		
			      }
			       
			      colId=cursor.getColumnIndex("start_estimate");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  start = Timestamp.valueOf(cursor.getString(colId));
			    	  Toast.makeText(context.getApplicationContext(),":10:start:"+start.toLocaleString(), Toast.LENGTH_LONG).show();
				  		
			      }
			      colId=cursor.getColumnIndex("end_estimate");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  end = Timestamp.valueOf(cursor.getString(colId));
			    	  Toast.makeText(context.getApplicationContext(),":11:end:"+end.toLocaleString(), Toast.LENGTH_LONG).show();
				  		
			      }
			      
			      colId=cursor.getColumnIndex("status");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  status = cursor.getString(colId);
			    	  Toast.makeText(context.getApplicationContext(),":12:status:"+status, Toast.LENGTH_LONG).show();
				  		
			      }
			      
			      TaskData tdata=null;
			      tdata=getTaskDataFromTaskPk(context,taskpk);
			      if(tdata==null)
			      {
			    	  Toast.makeText(context.getApplicationContext(),"tdata=null", Toast.LENGTH_LONG).show();
			    	  return list;
			      }
			      Toast.makeText(context.getApplicationContext(),"Data:wbspk:"+wbspk+"::userpk:"+upk+"::start:"+start.toLocaleString()+"::end"+end.toLocaleString(), Toast.LENGTH_LONG).show();
			      
			      if(wbspk!=-1 &&upk!=-1 && start!=null && end!=null && tdata!=null)
			      {
			    	  list.add(new WbsData(context,wbspk,tdata,upk,priority,status,start,end,wbsid,efforts));
			      }
			      cursor.moveToNext();
 			   }
 			}
 		
             cursor.close();

		
         return list;
	}

	public static List<TaskData> getSelectedProjectTaskList(Context context, String projectname)
	{
		List<TaskData> list=new ArrayList<TaskData>();
		long projectpk=-1;
		projectpk=getProjectPk(context,projectname);
		if(projectpk==-1)
			return list;
		
		String rawQuery="select * from tasktbl where project_pk=?";
		String args[]=new String[]{String.valueOf(projectpk)};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteDatabase database = helper.getWritableDatabase();
         
         if (database.isOpen()){ 
             database.beginTransaction();
             Cursor cursor=database.rawQuery(rawQuery, args);
     		
             //int priority,String status, Date start, end, assignedto
             
             if (cursor.moveToFirst()){
 			   while(!cursor.isAfterLast()){
 				  
 				  long taskpk=-1;String taskname=null; long parenttask = -1;
 				  
 				 int colId=cursor.getColumnIndex("_id");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  taskpk = cursor.getLong(colId);
			      }
			      
			      colId=cursor.getColumnIndex("task_name");
 			      if(colId!=-1)
 			      {
 			    	  //primary key of the company
 			    	  taskname = cursor.getString(colId);
 			      }
 			      
 			      
 			     colId=cursor.getColumnIndex("parent_task_id");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  parenttask = cursor.getLong(colId);
			      }
			        if(taskpk!=-1 &&projectpk!=-1 )
			      {
			    	  list.add(new TaskData(taskname,taskpk,parenttask,projectpk));
			      }
 				   cursor.moveToNext();
 			   }
 			}
 		
             cursor.close();

             database.setTransactionSuccessful();
             database.endTransaction();
         }
		
         return list;
	}

	public static List<Long> getAssignedListOfTask(Context context, long taskpk)
	{
		List<Long> upks=new ArrayList<Long>();
		
		if(taskpk==-1)
			return upks;
		
		String rawQuery="select * from wbstbl where task_pk=?";
		String args[]=new String[]{String.valueOf(taskpk)};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteDatabase database = helper.getReadableDatabase();
         
         if (database.isOpen()){ 
             database.beginTransaction();
             Cursor cursor=database.rawQuery(rawQuery, args);
     		
             //int priority,String status, Date start, end, assignedto
             
             if (cursor.moveToFirst()){
 			   while(!cursor.isAfterLast()){
 				  long upk=-1;	  
 				   				  
 				 int colId=cursor.getColumnIndex("user_pk");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  upk = cursor.getLong(colId);
			      }
			      
			      if(upk!=-1 )
			      {
			    	  upks.add(upk);
			      }
			      
 				   cursor.moveToNext();
 			   }
 			}
 		
             cursor.close();

             database.setTransactionSuccessful();
             database.endTransaction();
         }
         	

         return upks;
	}

	public static List<WbsData> getWbsDataOfTask(Context context, String taskname)
	{
		List<WbsData> list=new ArrayList<WbsData>();
		TaskData tdata=null;
		tdata=getTaskData(context,taskname);
		if(tdata==null)
			return list;
		
		String table="wbstbl";
		
		String rawQuery="select * from wbstbl where task_pk=? AND project_pk=?";
		String args[]=new String[]{String.valueOf(tdata.taskpk),String.valueOf(tdata.projectpk)};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteDatabase database = helper.getWritableDatabase();
         
         if (database.isOpen()){ 
             database.beginTransaction();
             Cursor cursor=database.rawQuery(rawQuery, args);
     		
             //int priority,String status,Date start,end,assignedto
             
             if (cursor.moveToFirst()){
 			   while(!cursor.isAfterLast()){
 				  
 				  long wbspk=-1; long upk=-1;int priority = 4;String status = null;Timestamp start = null,end=null;String wbsid=null;float efforts=0;
 				  
 				 int colId=cursor.getColumnIndex("_id");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  wbspk = cursor.getLong(colId);
			      }
			      
			      colId=cursor.getColumnIndex("wbs_id");
 			      if(colId!=-1)
 			      {
 			    	  //primary key of the company
 			    	  wbsid = cursor.getString(colId);
 			      }
 			      
 				  colId=cursor.getColumnIndex("user_pk");
 			      if(colId!=-1)
 			      {
 			    	  //primary key of the company
 			    	  upk = cursor.getLong(colId);
 			      }
 			      
 			     colId=cursor.getColumnIndex("priority");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  priority = cursor.getInt(colId);
			      }
			      colId=cursor.getColumnIndex("efforts_per_day");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  efforts = cursor.getFloat(colId);
			      }
			       
			      colId=cursor.getColumnIndex("start_estimate");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  start = Timestamp.valueOf(cursor.getString(colId));
			      }
			      colId=cursor.getColumnIndex("end_estimate");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  end = Timestamp.valueOf(cursor.getString(colId));
			      }
			      
			      colId=cursor.getColumnIndex("status");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  status = cursor.getString(colId);
			      }
			      
			      if(wbspk!=-1 &&upk!=-1 && start!=null && end!=null)
			      {
			    	  list.add(new WbsData(context,wbspk,tdata,upk,priority,status,start,end,wbsid,efforts));
			      }
 				   cursor.moveToNext();
 			   }
 			}
 		
             cursor.close();

             database.setTransactionSuccessful();
             database.endTransaction();
         }
		
         return list;
	}

	
	public static long getUserPk(Context context,String email)
	{
		
		String rawQuery="select * from usertbl where email=?";
		String args[]=new String[]{email};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		long userpk=-1;
		if (cursor.moveToFirst()){
			   while(!cursor.isAfterLast()){
			       int colId=cursor.getColumnIndex("_id");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  userpk = cursor.getLong(colId);
			    	  
			    	  break;
			      }
				   cursor.moveToNext();
			   }
			}
		
		cursor.close();

		return userpk;
	}

	public static long getTeamPk(Context context,String teamName)
	{
		
		String rawQuery="select * from teamtbl where team_name=?";
		String args[]=new String[]{teamName};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		long teampk=-1;
		if (cursor.moveToFirst()){
			   while(!cursor.isAfterLast()){
			       int colId=cursor.getColumnIndex("_id");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  teampk = cursor.getLong(colId);
			    	  
			    	  break;
			      }
				   cursor.moveToNext();
			   }
			}
		
		cursor.close();

		return teampk;
	}

	public static long getWbsPk(Context context,String projectname,String taskname,String username)
	{
		long projectpk=getProjectPk(context,projectname);
		long taskpk=getTaskPk(context,taskname,projectpk);
		long userpk=getUserPk(context,username);
		
		String rawQuery="select * from wbstbl where project_pk=? AND task_pk=? AND user_pk=?";
		String args[]=new String[]{String.valueOf(projectpk),String.valueOf(taskpk),String.valueOf(userpk)};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		long wbspk=-1;
		if (cursor.moveToFirst()){
			   while(!cursor.isAfterLast()){
			       int colId=cursor.getColumnIndex("_id");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  wbspk = cursor.getLong(colId);
			    	  
			    	  break;
			      }
				   cursor.moveToNext();
			   }
			}
		
		cursor.close();

		return wbspk;
	}

	public static long getWbsPk(Context context,long projectpk,long taskpk,long userpk)
	{
		if(projectpk<=0 || taskpk<=0 || userpk<=0)
			return -1;
		
		
		String rawQuery="select * from wbstbl where project_pk=? AND task_pk=? AND user_pk=?";
		String args[]=new String[]{String.valueOf(projectpk),String.valueOf(taskpk),String.valueOf(userpk)};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		long wbspk=-1;
		if (cursor.moveToFirst()){
			   while(!cursor.isAfterLast()){
			       int colId=cursor.getColumnIndex("_id");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  wbspk = cursor.getLong(colId);
			    	  
			    	  break;
			      }
				   cursor.moveToNext();
			   }
			}
		
		cursor.close();

		return wbspk;
	}

	public static String getUserName(Context context,long pk)
	{
		
		String rawQuery="select * from usertbl where _id=?";
		String args[]=new String[]{String.valueOf(pk)};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		String username=null;
		if (cursor.moveToFirst()){
			   while(!cursor.isAfterLast()){
			       int colId=cursor.getColumnIndex("email");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  username = cursor.getString(colId);
			    	  
			    	  break;
			      }
				   cursor.moveToNext();
			   }
			}
		
		cursor.close();

		return username;
	}

	public static List<String> getUserList(Context context)
	{
		
		String rawQuery="select * from usertbl";
		String args[]=null;

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		List<String>list=new ArrayList<String>();
		
		if (cursor.moveToFirst()){
			   while(!cursor.isAfterLast()){
				   String username=null;
				   int colId=cursor.getColumnIndex("email");
			      if(colId!=-1)
			      {
			    	  //username 
			    	  username = cursor.getString(colId);
			      }
			      
			      if(username!=null)
			    	  list.add(username);
			      
				   cursor.moveToNext();
			   }
			}
		
		cursor.close();

		return list;
	}

	public static TaskData getTaskData(Context context,String name)
	{
		String rawQuery="select * from tasktbl where task_name=?";
		String args[]=new String[]{name};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		//let the caller handle the exception	
		//check if task  exists and get project_pk.
			long task_pk=-1;
			long project_pk=-1;
			long parent_pk=-1;
			
			TaskData data=null;
			if (cursor.moveToFirst()){
				   while(!cursor.isAfterLast()){
				      
				      //primary key of the companytbl
					  int colId=cursor.getColumnIndex("_id");
				      if(colId!=-1)
				      {
				    	  //primary key of the company
				    	  task_pk = cursor.getLong(colId);
				    	  Toast.makeText(context, "Tasktbl Found: " + task_pk, Toast.LENGTH_LONG).show();
				      }
				      
				      colId=cursor.getColumnIndex("project_pk");
				      if(colId!=-1)
				        project_pk = cursor.getLong(colId);
				    	
				      colId=cursor.getColumnIndex("parent_task_id");
				      if(colId!=-1)
				        parent_pk = cursor.getLong(colId);
				    					    	
				      // do what ever you want here
				      cursor.moveToNext();
				   }
				}
			
			cursor.close();
		
			if(task_pk!=-1 && project_pk!=-1)
			{
				data=new TaskData(name,task_pk,parent_pk,project_pk);
			}
		return data;
	}

	public static TaskData getTaskDataFromTaskPk(Context context,long pk)
	{
		String rawQuery="select * from tasktbl where _id=?";
		String args[]=new String[]{String.valueOf(pk)};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		//let the caller handle the exception	
		//check if task  exists and get project_pk.
			String task_name=null;
			long project_pk=-1;
			long parent_pk=-1;
			
			TaskData data=null;
			if (cursor.moveToFirst()){
				   while(!cursor.isAfterLast()){
				      
				      //primary key of the companytbl
					  int colId=cursor.getColumnIndex("task_name");
				      if(colId!=-1)
				      {
				    	  //primary key of the company
				    	  task_name = cursor.getString(colId);
				    	  //Toast.makeText(context, "Tasktbl Found: " + task_pk, Toast.LENGTH_LONG).show();
				      }
				      
				      colId=cursor.getColumnIndex("project_pk");
				      if(colId!=-1)
				        project_pk = cursor.getLong(colId);
				    	
				      colId=cursor.getColumnIndex("parent_task_id");
				      if(colId!=-1)
				        parent_pk = cursor.getLong(colId);
				    					    	
				      // do what ever you want here
				      cursor.moveToNext();
				   }
				}
			
			cursor.close();
		
			if(task_name!=null && !task_name.equalsIgnoreCase("") && project_pk!=-1)
			{
				data=new TaskData(task_name,pk,parent_pk,project_pk);
			}
		return data;
	}

	public static long getTaskPk(Context context,String name,long projectpk)
	{
		String rawQuery="select * from tasktbl where task_name=? AND project_pk=?";
		String args[]=new String[]{name,String.valueOf(projectpk)};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		//let the caller handle the exception	
		//check if task  exists and get project_pk.
			long task_pk=-1;
						
			if (cursor.moveToFirst()){
				   while(!cursor.isAfterLast()){
				      
				      //primary key of the companytbl
					  int colId=cursor.getColumnIndex("_id");
				      if(colId!=-1)
				      {
				    	  //primary key of the company
				    	  task_pk = cursor.getLong(colId);
				    	  Toast.makeText(context, "Tasktbl Found: " + task_pk, Toast.LENGTH_LONG).show();
				    	  break;
				      }
				      
				      // do what ever you want here
				      cursor.moveToNext();
				   }
				}
			
			cursor.close();
		
		return task_pk;
	}
	
	public static long getProjectPk(Context context,Integer projectId,String projectName)
	{
		String table="usertbl";
		
		String rawQuery="select * from projecttbl where project_id=? AND project_name=?";
		String args[]=new String[]{projectId.toString(),projectName};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		long projectpk=-1;
		if (cursor.moveToFirst()){
			   while(!cursor.isAfterLast()){
			       int colId=cursor.getColumnIndex("_id");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  projectpk = cursor.getLong(colId);	    	  
			    	  break;
			      }
				   cursor.moveToNext();
			   }
			}
		
		cursor.close();

		return projectpk;
	}

	public static long getProjectPk(Context context,String projectName)
	{
		
		String rawQuery="select * from projecttbl where project_name=?";
		String args[]=new String[]{projectName};

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		Cursor cursor=helper.getReadableDatabase().rawQuery(rawQuery, args);
		
		long projectpk=-1;
		if (cursor.moveToFirst()){
			   while(!cursor.isAfterLast()){
			       int colId=cursor.getColumnIndex("_id");
			      if(colId!=-1)
			      {
			    	  //primary key of the company
			    	  projectpk = cursor.getLong(colId);
			    	  
			    	  break;
			      }
				   cursor.moveToNext();
			   }
			}
		
		cursor.close();

		return projectpk;
	}


	public static List<String> getProjectList(Context context)
	{
		String table="projecttbl";
		
		String rawQuery="select * from projecttbl";
		String args[]=null;

		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
		List<String>list=new ArrayList<String>();
		if (cursor.moveToFirst()){
			   while(!cursor.isAfterLast()){
			       int colId=cursor.getColumnIndex("project_name");
			      if(colId!=-1)
			      {
			    	  //name of the project
			    	  list.add( cursor.getString(colId));
			      }
				   cursor.moveToNext();
			   }
			}
		
		cursor.close();

		return list;
	}


	public static Date Convert_To_Date(String str) {
		System.out.println("Entered:WbsBeanManaged::ConvertoTDate:str="+str);
        Date date1 = null;

        //String str = "Thu, 1 Mar 2012 13:57:06 -0600";
        //DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        //dt=Dateformat.getDateInstance(DateFormat.MEDIUM).parse(str);
        //SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssZ");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            //dt = formatter.parse(str);
        	Date dateStr = formatter.parse(str);
	        String formattedDate = formatter.format(dateStr);
	        System.out.println("yyyy-MM-dd date is ==>"+formattedDate);
	        date1 = formatter.parse(formattedDate);
	        
        }
        catch ( ParseException pe) {
            System.out.println(pe.getMessage());
        }
        
        //String strDt = dt.toString(); 
        System.out.println(date1);
        System.out.println("Exited:WbsBeanManaged::ConvertoTDate");
        return date1;
    }

	public static int calculateDifference(Date a, Date b)
	{
		System.out.println("entered:calculateDifference");
		
		if(a==null || b==null || !a.getClass().equals(Date.class) || !b.getClass().equals(Date.class))
			return 0;
	    int tempDifference = 0;
	    int difference = 0;
	    Calendar earlier = Calendar.getInstance();
	    Calendar later = Calendar.getInstance();

	    if (a.compareTo(b) < 0)
	    {
	        earlier.setTime(a);
	        later.setTime(b);
	    }
	    else
	    {
	        earlier.setTime(b);
	        later.setTime(a);
	    }
	    
	    while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR))
	    {
	        tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
	        difference += tempDifference;

	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }

	    if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR))
	    {
	        tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
	        difference += tempDifference;
	        earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
	    }
	    System.out.println("exited:calculateDifference");
	    return difference;
	}


	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	public static boolean equalOrBetween(Date date, Date dateStart, Date dateEnd) {
	    if (date != null && dateStart != null && dateEnd != null) {
	    	
	    	if(date.equals(dateStart) || date.equals(dateEnd))
	    		return true;
	    	
	        if (date.after(dateStart) && date.before(dateEnd)) {
	            return true;
	        }
	        else {
	            return false;
	        }
	    }
	    return false;
	}
	
	
}
