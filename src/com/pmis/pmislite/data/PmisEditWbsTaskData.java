package com.pmis.pmislite.data;

import java.util.Date;

import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

public class PmisEditWbsTaskData {

	Context context;
	WbsData data;
	
	public PmisEditWbsTaskData(Context context,WbsData data) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.data=data;
	}

	public ContentValues deleteWbsTask()
	{
		ContentValues retValue=new ContentValues();
		retValue.put("error_code","0");
		retValue.put("error","");
		
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
			    Values[0]=String.valueOf(data.wbspk);    
			    database.delete("wbstbl",Where, Values);
			    
			    database.setTransactionSuccessful();
	            database.endTransaction();
	            
			}
		}
		catch(Exception e){
				
			database.setTransactionSuccessful();
            database.endTransaction();
            
				retValue.put("error_code","-1");
				retValue.put("error","Unsuccessful Delete");
				Toast.makeText(context, "Exception In Delete: Delete Wbs Record : task_pk:" + data.taskdata.taskpk+"::user_pk:"+data.assignedpk+"::task_name:"+data.taskdata.taskName, Toast.LENGTH_LONG).show();
				
		      }
			finally{
	            database.close();            
	        }	
		
		return retValue;
	}
	

	public ContentValues updateWbsTask()
	{
		//use db directly
		
		//check if task already present. if so update it.
		//if new task insert it.
		ContentValues retValue=new ContentValues();
		retValue.put("error_code","0");
		retValue.put("error","");
		
		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteDatabase database=helper.getWritableDatabase();
		try
		{		
			
			if(database.isOpen())
			{
				database.beginTransaction();
				
				int rowsAffected = 0;
		        String[] Values = new String[1];
		        String Where = "";
			        
				ContentValues wpairs=new ContentValues();
				if(data.wbsID!=null && !data.wbsID.equalsIgnoreCase(""))
					wpairs.put("wbs_id",data.wbsID);
				
			//wpairs.put("project_pk", projectpk); can not be edited
			//wpairs.put("task_pk", taskpk); can not be edited
			//wpairs.put("user_pk", userpk); can not be edited
			
			if(data.priority!=0)
				wpairs.put("priority",String.valueOf(data.priority));
			
			wpairs.put("efforts_per_day",String.valueOf(data.efforts));
			
			if(data.eStart!=null)
				wpairs.put("start_estimate",data.eStart.toLocaleString());
			if(data.eEnd!=null)
				wpairs.put("end_estimate",data.eEnd.toLocaleString());
			
			if(data.status!=null && !data.status.equalsIgnoreCase(""))	
				wpairs.put("status",data.status);
			
			//Date dt=new Date();
			//wpairs.put("last_updated",dt.toLocaleString());
		
			Values[0] = String.valueOf(data.wbspk);
             
            Where = "_id= ?";
             
			rowsAffected = database.update("wbstbl", wpairs, Where , Values);
			
			 if (rowsAffected == 0){
                 //Insert row since it doesn't truly exist.
					ContentValues npairs=new ContentValues();
					
					npairs.put("wbs_id",data.wbsID);
					npairs.put("project_pk", data.taskdata.projectpk);
					npairs.put("task_pk", data.taskdata.taskpk);
					npairs.put("user_pk", data.assignedpk);
					npairs.put("priority",String.valueOf(data.priority));
					npairs.put("efforts_per_day",String.valueOf(data.efforts));
					npairs.put("start_estimate",data.eStart.toLocaleString());
					npairs.put("end_estimate",data.eEnd.toLocaleString());
					npairs.put("status","open");
					Date dt1=new Date();
					npairs.put("last_updated",dt1.toLocaleString());
					
	               database.insert("wbstbl", null, npairs);
	                 
             }//rowaffeted
			 		
	
		}//database.open
		
		//read the db now 
		String rawQuery="select * from wbstbl where _id=?";
		String[] args=new String[]{String.valueOf(data.wbspk)};
		
		//Cursor cursor=helper.getReadableDatabase().rawQuery(rawQuery, args);
		Cursor cursor=database.rawQuery(rawQuery, args);
			//check if wbs record present  exists and get project_pk.
			long wbs_pk=-1;
			if (cursor.moveToFirst()){
				   while(!cursor.isAfterLast()){
				      
				      //primary key of the wbstbl
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
			
			database.setTransactionSuccessful();
	             
	        database.endTransaction();
		
			if(wbs_pk!=-1)//record exists
			{
				Toast.makeText(context, "Wbs Record Update Successful:: task_pk:" + data.taskdata.taskpk+"::user_pk:"+data.assignedpk+"::task_name:"+data.taskdata.taskName, Toast.LENGTH_LONG).show();
				return retValue;					
			}
	
		 
		}
		catch(Exception e){
			database.setTransactionSuccessful();
            database.endTransaction();
            
			retValue.put("error_code","-1");
				retValue.put("error","NO USER RECORD FOUND");
				Toast.makeText(context, "Exception In Insert: Create Wbs Record : task_pk:" + data.taskdata.taskpk+"::user_pk:"+data.assignedpk+"::task_name:"+data.taskdata.taskName, Toast.LENGTH_LONG).show();
			
		      }
			finally{
	            database.close();            
	        }	
		
			
		return retValue;

	}

}
