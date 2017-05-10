package com.pmis.pmislite.util;

import java.io.IOException;

import android.database.SQLException;
import android.content.Context;

import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;

public class PmisDatabaseClient {

	public PmisDatabaseClient() {
		// TODO Auto-generated constructor stub
	}

	public void getDataBase(Context context) throws IOException
	{
		PmisDatabaseHelper myDbHelper = null;
        myDbHelper = PmisDatabaseHelper.getInstance(context);
 
        try {
        	
        	myDbHelper.createDataBase();
 
 	} catch (IOException ioe) {
 
 		throw new Error("Unable to create database");
 
 	}
 
 	try {
 
 		myDbHelper.openDataBase();
 
 	}catch(SQLException sqle){
 
 		throw sqle;
 
 	}
	}
}
