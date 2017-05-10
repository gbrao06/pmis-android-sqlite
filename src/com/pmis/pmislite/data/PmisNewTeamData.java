package com.pmis.pmislite.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;

import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;
import com.pmis.pmislite.sql.loader.SQLiteCursorLoader;

public class PmisNewTeamData {

	private String teamName;
	private String owner;
	
	Context context;
	public PmisNewTeamData(Context context,String teamName,String owner) {
		// TODO Auto-generated constructor stub
		this.teamName=teamName;
		this.owner=owner;
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
	

	public ContentValues createTeam()
	{
		
		ContentValues retValue=new ContentValues();
		retValue.put("error_code","0");
		retValue.put("error","");


		long teampk=-1;
		teampk=PmisUtils.getTeamPk(context, teamName);
		if(teampk>0)
		{
			Toast.makeText(context, "Record Already exists:TeamName:" + teamName, Toast.LENGTH_LONG).show();
			return retValue;
		}
		long userpk=-1;
		userpk=PmisUtils.getUserPk(context, owner);
			
		if(teamName==null || teamName.equalsIgnoreCase("") || userpk<0)
		{
			retValue.put("error_code","-1");
			retValue.put("error","Invalid TeamName");
			
			Toast.makeText(context, "Invalid params ::Team Name:" + teamName+"::User:"+owner, Toast.LENGTH_LONG).show();
			
			return retValue;
		}
		
		PmisDatabaseHelper helper= PmisDatabaseHelper.getInstance(context);

		SQLiteDatabase database = helper.getWritableDatabase();

		try
        {
			
        	if (database.isOpen()){ 
	        	database.beginTransaction();

				String table="teamtbl";
				ContentValues pairs=new ContentValues();
				//Long id=generateUniqueID();
				pairs.put("team_name", teamName);
				pairs.put("owner",userpk);
				
				teampk=database.insertOrThrow(table, null,pairs);
				Toast.makeText(context, "Record Created Successfully:TeamName:" + teamName, Toast.LENGTH_LONG).show();

				database.setTransactionSuccessful();
	            database.endTransaction();

	            
	        }
				
		}
		catch(Exception ee)
		{
			
			database.setTransactionSuccessful();
            database.endTransaction();
            
            retValue.put("error_code","-1");
			retValue.put("error","Invalid Data");
			
		}
        finally
        {
        	database.close();
        }
			
		return retValue;
	}
	
}

