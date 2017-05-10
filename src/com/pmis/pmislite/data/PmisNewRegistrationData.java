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

public class PmisNewRegistrationData {

	private Integer companyID;
	private String companyName;
	
	private String userName;
	private String password;
	private Long mobile;
	
	private String firstName;
	private String lastName;
	
	Context context;
	
	public static final String ID_NAME = "_id";
	
	
	public PmisNewRegistrationData(Context context,Integer companyID,String companyName,Long mobile,String usermail,String password,String firstName,String lastName) {
		// TODO Auto-generated constructor stub
		this.companyID=companyID;
		this.companyName=companyName;
		this.mobile=mobile;
		
		this.userName=usermail;
		this.password=password;
		this.firstName=firstName;
		this.lastName=lastName;
		
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

	private SQLiteCursorLoader getReader(String rawQuery,String[] args)
	{
		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);
		SQLiteCursorLoader loader=null;
	  	try {
	  		helper.createDataBase();
	  		loader=new SQLiteCursorLoader(context.getApplicationContext(), helper,rawQuery,args);	

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
	
		
	public ContentValues registerUser()
	{
		
		ContentValues retValue=new ContentValues();
		retValue.put("error_code","0");
		retValue.put("error","");
		Toast.makeText(context, "Entered Values: user_id:" + userName+"::passwd:"+password+"::mobile:"+mobile+"first:"+firstName+"::last:"+lastName, Toast.LENGTH_LONG).show();
		
		SQLiteCursorLoader writer=getLoader();
		
			String table="companytbl";
			ContentValues pairs=new ContentValues();
			//Long id=generateUniqueID();
			pairs.put("companyId", companyID);
			pairs.put("companyname",companyName);
			java.util.Date date= new java.util.Date();
			writer.insert(table, null,pairs);
			
			String rawQuery="select * from companytbl where companyname=?";
			String args[]=new String[]{companyName};
			
			SQLiteCursorLoader reader=getReader(rawQuery,args);
			Cursor cursor=reader.buildCursor();
			//("_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "user_id" TEXT NOT NULL UNIQUE, "passwd" TEXT, "email" TEXT NOT NULL UNIQUE, "first_name" TEXT NOT NULL, "last_name" TEXT NOT NULL, "role" TEXT, "mobile_no" INT NOT NULL, "landline_no" INT, "companyId" INTEGER NOT NULL REFERENCES "companytbl" ("_id"), "house_no" TEXT, "street_name" TEXT, "addressline1" TEXT, "addressline2" TEXT, "city" TEXT, "state" TEXT, "zip" TEXT, "county" TEXT, "country" TEXT, "last_updated" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY("companyId") REFERENCES companytbl("_id"));
			
			//company name exists.
			int companypk=-1;
			if (cursor.moveToFirst()){
				   while(!cursor.isAfterLast()){
				      //String data = cursor.getString(cursor.getColumnIndex("data"));
				      //primary key of the companytbl
					   int colId=cursor.getColumnIndex(ID_NAME);
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
				//create usertbl
				String utable="usertbl";
				ContentValues upairs=new ContentValues();
				//Long id=generateUniqueID();
				
				upairs.put("user_id", userName);
				upairs.put("passwd",PmisUtils.sha256(password));
				upairs.put("email",userName);
				upairs.put("first_name",firstName);
				upairs.put("last_name",lastName);
				upairs.put("role","developer");
				
				upairs.put("mobile_no",mobile);
				
				upairs.put("companyId",companypk);
				
				//java.util.Date udate= new java.util.Date();
				//upairs.put("last_updated",date.toString());
				
				writer.insert(utable, null,upairs);
				if(PmisUtils.getUserPk(context, userName)!=-1)
				{
					Toast.makeText(context, "Registration Successful: user_id:" + userName+"::passwd:"+password+"::mobile:"+mobile+"first:"+firstName+"::last:"+lastName, Toast.LENGTH_LONG).show();

					return retValue;
				}
					
			}
	
			Toast.makeText(context, "Error In Registration: user_id:" + userName+"::passwd:"+password+"::mobile:"+mobile+"first:"+firstName+"::last:"+lastName, Toast.LENGTH_LONG).show();
			retValue.put("error_code","-1");
			retValue.put("error","Could Not Locate Company");
		
		return retValue;
	}
	
}
