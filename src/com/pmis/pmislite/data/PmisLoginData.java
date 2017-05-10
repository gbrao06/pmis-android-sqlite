package com.pmis.pmislite.data;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;
import com.pmis.pmislite.sql.loader.SQLiteCursorLoader;

public class PmisLoginData {

	
	private String userName;
	private String password;
	
	//keep additional details
	Context context;

	
	public PmisLoginData(Context context,String usermail,String password) {
		// TODO Auto-generated constructor stub
		
		this.userName=usermail;
		this.password=password;
		
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

	private Long generateUniqueID()
	{
		Calendar cal=Calendar.getInstance();
		return cal.getTimeInMillis();
	}
	
	public ContentValues loginUser()
	{
		
		ContentValues retValue=new ContentValues();
		retValue.put("error_code","0");
		retValue.put("error","");
		
			String rawQuery="select * from usertbl where user_id=? AND  passwd= ?";
			String args[]=new String[]{userName,PmisUtils.sha256(password)};
			
			Toast.makeText(context, ":: user_id:" + userName+"::passwd:"+PmisUtils.sha256(password), Toast.LENGTH_LONG).show();
			
			PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
			SQLiteCursorLoader reader=new SQLiteCursorLoader(context, helper, rawQuery,args);
			Cursor cursor=reader.buildCursor();
			//("_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "user_id" TEXT NOT NULL UNIQUE, "passwd" TEXT, "email" TEXT NOT NULL UNIQUE, "first_name" TEXT NOT NULL, "last_name" TEXT NOT NULL, "role" TEXT, "mobile_no" INT NOT NULL, "landline_no" INT, "companyId" INTEGER NOT NULL REFERENCES "companytbl" ("_id"), "house_no" TEXT, "street_name" TEXT, "addressline1" TEXT, "addressline2" TEXT, "city" TEXT, "state" TEXT, "zip" TEXT, "county" TEXT, "country" TEXT, "last_updated" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY("companyId") REFERENCES companytbl("_id"));
			
			//usermail  exists.
			String usermail="";
			
			if (cursor.moveToFirst()){
				   while(!cursor.isAfterLast()){
				      int colId=cursor.getColumnIndex("email");
				      if(colId!=-1)
				      {
				    	  //usermail present
				    	  usermail = cursor.getString(colId);
				    	  break;
				      }
					   	      
				      // do what ever you want here
				      cursor.moveToNext();
				   }
				}
			
			cursor.close();
			
			//check for 
			if(usermail==null || usermail.equalsIgnoreCase(""))
			{
				retValue.put("error_code","-1");
				retValue.put("error","Could Not Locate UserCredentials");
			}
			
			
		return retValue;
	}
	
}
