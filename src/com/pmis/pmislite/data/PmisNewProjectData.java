package com.pmis.pmislite.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.Toast;

import com.pmis.pmislite.login.SessionManager;
import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;
import com.pmis.pmislite.sql.loader.SQLiteCursorLoader;

public class PmisNewProjectData {

	private Integer projectID;
	private String projectName;
	private String projectDescription;
	
	Context context;
	
	public PmisNewProjectData(Context context,Integer projectID,String projectName,String description) {
		// TODO Auto-generated constructor stub
		this.projectID=projectID;
		this.projectName=projectName;
		this.projectDescription=description;
		this.context=context;
		
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
	
	public ContentValues saveProject()
	{
		//company_id,project_id,project_name,project_description,created_by
		
		ContentValues retValue=new ContentValues();
		retValue.put("error_code","0");
		retValue.put("error","");
		
		SessionManager session = new SessionManager(context.getApplicationContext());
    	HashMap<String,String> map=session.getUserDetails();
    	String user_mail=map.get(session.KEY_EMAIL);
		if(user_mail==null || user_mail.equalsIgnoreCase(""))
		{
			retValue.put("error_code","0");
			retValue.put("error","NO LOGIN DETAILS FOUND");
			return retValue;
		}
		
    	String table="usertbl";
			
		String rawQuery="select * from usertbl where email=?";
		String args[]=new String[]{user_mail};
		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader reader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);
		Cursor cursor=reader.buildCursor();
		
			//check if company name exists for user.
			int companypk=-1;
			if (cursor.moveToFirst()){
				   while(!cursor.isAfterLast()){
				      
				      //primary key of the companytbl
					  int colId=cursor.getColumnIndex("companyId");
				      if(colId!=-1)
				      {
				    	  //primary key of the company
				    	  companypk = cursor.getInt(colId);
				    	  Toast.makeText(context, "Companytbl Found: " + companypk, Toast.LENGTH_LONG).show();
				    	  break;
				      }
					   
				      // do what ever you want here
				      cursor.moveToNext();
				   }
				}
			
			cursor.close();
			
			if(companypk!=-1)
			{
				//create projecttbl
				String utable="projecttbl";
				ContentValues upairs=new ContentValues();
				//Long id=generateUniqueID();
				
				upairs.put("company_id",companypk);
				upairs.put("project_id",projectID);
				upairs.put("project_name",projectName);
				upairs.put("project_description",projectDescription);
				upairs.put("created_by",user_mail);
				
				//java.util.Date udate= new java.util.Date();
				//upairs.put("last_updated",udate.toLocaleString());
				
				reader.insert(utable, null,upairs);
				
				if(PmisUtils.getProjectPk(context, projectID,projectName)!=-1)//try with unique id
				{
					Toast.makeText(context, "Project Added Successfully: project_id:" + projectID+"::project_name:"+projectName, Toast.LENGTH_LONG).show();

					return retValue;
				}
					
			}
	
			Toast.makeText(context, "Internal Error Can Not Add project : project_id:" + projectID+"::project_name:"+projectName, Toast.LENGTH_LONG).show();
			retValue.put("error_code","-1");
			retValue.put("error","Could Not Locate Company");
		
		return retValue;
		
	}
	
}
