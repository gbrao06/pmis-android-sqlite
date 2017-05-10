package com.pmis.pmislite.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;
import com.pmis.pmislite.sql.loader.SQLiteCursorLoader;

public class PmisSelectedTeamData {

	private String teamName;
	private Context context;
	
	public PmisSelectedTeamData(Context context,String teamName) {
		// TODO Auto-generated constructor stub
		this.teamName=teamName;
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
	
	public List<String> getMembersOfTheTeam()
	{

	  	
		List<String> members=new ArrayList<String>();
		members.add("Member1");
		members.add("Member2");
		members.add("Member3");
		members.add("member4");
		return members;
		
	}

	public List<String> getBlogsOfTheTeam()
	{

		SQLiteCursorLoader loader=getLoader();
	  	
		List<String> members=new ArrayList<String>();
		members.add("Blog1");
		members.add("Blog2");
		members.add("Blog33");
		members.add("Blog4");
		return members;
		
	}
	
	
	
}
