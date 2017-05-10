package com.pmis.pmislite.projects;
import java.util.ArrayList;
import java.util.HashMap;

import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisNewProjectData;
import com.pmis.pmislite.data.PmisNewTeamData;
import com.pmis.pmislite.login.SessionManager;
import com.pmis.pmislite.messages.MessagesActivity;
import com.pmis.pmislite.projects.NewProjectActivity;
import com.pmis.pmislite.projects.ProjectListActivity;
import com.pmis.pmislite.selected.PmisSelectedProjectAccordianActivity;
import com.pmis.pmislite.teams.TeamListActivity;
 
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
 
public class NewProjectActivity extends Activity {
     
    final Context context=this;
    
    Button projectsButton;
    Button teamsButton;
    Button messagesButton;
    
    Button saveProjectButton;
    Button cancelProjectButton;
    
    EditText projectID;
    EditText projectName;
    EditText projectDescription;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_project_view);
         
        projectsButton=(Button) findViewById(R.id.projectsButton);
        projectsButton.setOnClickListener(projectsButtonHandler);
        
        teamsButton=(Button) findViewById(R.id.teamsButton);
        teamsButton.setOnClickListener(teamsButtonHandler);
        
        messagesButton=(Button) findViewById(R.id.messagesButton);
        messagesButton.setOnClickListener(messagesButtonHandler);
        
        projectID=(EditText)findViewById(R.id.project_id);
        
        projectName=(EditText)findViewById(R.id.project_name);
        projectDescription=(EditText)findViewById(R.id.project_description);
        
        saveProjectButton=(Button) findViewById(R.id.saveProjectButton);
        saveProjectButton.setOnClickListener(saveProjectButtonHandler);
    }  


View.OnClickListener saveProjectButtonHandler = new View.OnClickListener() {

    public void onClick(View v) {
    	
    	Integer pid=Integer.valueOf(projectID.getText().toString());
    	PmisNewProjectData projectDt=new PmisNewProjectData(context,pid,projectName.getText().toString(),projectDescription.getText().toString());
    	
    	ContentValues rets=projectDt.saveProject();
    	if(rets.getAsInteger("error_code")!=0)//error
    	{
    		
    	}
    }
};

View.OnClickListener cancelProjectButtonHandler = new View.OnClickListener() {

    public void onClick(View v) {
    	
    	Intent intent=new Intent(context,ProjectListActivity.class);
    	startActivity(intent);
    }
};

View.OnClickListener projectsButtonHandler = new View.OnClickListener() {

    public void onClick(View v) {

    	Intent intent=new Intent(context,ProjectListActivity.class);
    	startActivity(intent);

    }
};

View.OnClickListener teamsButtonHandler = new View.OnClickListener() {

    public void onClick(View v) {
        //projectButton.setBackgroundResource(R.drawable.ic_launcher);
    	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mtasks.jsf"));
        
    	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
        //startActivity(myWebLink);
    	Intent intent=new Intent(context,TeamListActivity.class);
        //intent.addCategory(selectedProject);
	    //intent.putExtra("selectedproject",selectedProject);
    	startActivity(intent);

    }
};

View.OnClickListener messagesButtonHandler = new View.OnClickListener() {

    public void onClick(View v) {
        //projectButton.setBackgroundResource(R.drawable.ic_launcher);
    	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mtasks.jsf"));
        
    	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
        //startActivity(myWebLink);
    	Intent intent=new Intent(context,MessagesActivity.class);
        //intent.addCategory(selectedProject);
	    //intent.putExtra("selectedproject",selectedProject);
    	startActivity(intent);

    }
};

}
