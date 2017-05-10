package com.pmis.pmislite.teams;
import java.util.ArrayList;
import java.util.HashMap;

import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisNewTeamData;
import com.pmis.pmislite.login.SessionManager;
import com.pmis.pmislite.messages.MessagesActivity;
import com.pmis.pmislite.projects.NewProjectActivity;
import com.pmis.pmislite.projects.ProjectListActivity;
import com.pmis.pmislite.selected.PmisSelectedProjectAccordianActivity;
 
import android.app.Activity;
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
 
public class NewTeamActivity extends Activity {
     
    final Context context=this;
    
    Button projectsButton;
    Button teamsButton;
    Button messagesButton;
    
    Button saveTeamButton;
    Button cancelTeamButton;
    
    EditText teamName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_team_view);
         
        projectsButton=(Button) findViewById(R.id.projectsButton);
        projectsButton.setOnClickListener(projectsButtonHandler);
        
        teamsButton=(Button) findViewById(R.id.teamsButton);
        teamsButton.setOnClickListener(teamsButtonHandler);
        
        messagesButton=(Button) findViewById(R.id.messagesButton);
        messagesButton.setOnClickListener(messagesButtonHandler);
        
        teamName=(EditText)findViewById(R.id.team_name);
        
        saveTeamButton=(Button) findViewById(R.id.saveTeamButton);
        saveTeamButton.setOnClickListener(saveTeamButtonHandler);
    }  


View.OnClickListener saveTeamButtonHandler = new View.OnClickListener() {

    public void onClick(View v) {
    	
    	SessionManager session=new SessionManager(context);
    	
    	HashMap<String,String> map=null;
    	map=session.getUserDetails();
    	String email=map.get(SessionManager.KEY_EMAIL);
    	
    	if(email!=null)
    	{
    		PmisNewTeamData teamDt=new PmisNewTeamData(context,teamName.getText().toString(),email);
        	teamDt.createTeam();
    	}
    }
};

View.OnClickListener cancelTeamButtonHandler = new View.OnClickListener() {

    public void onClick(View v) {
    	
    	Intent intent=new Intent(context,TeamListActivity.class);
    	startActivity(intent);
    }
};

View.OnClickListener projectsButtonHandler = new View.OnClickListener() {

    public void onClick(View v) {
        //projectButton.setBackgroundResource(R.drawable.ic_launcher);
    	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mtasks.jsf"));
        
    	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
        //startActivity(myWebLink);
    	Intent intent=new Intent(context,ProjectListActivity.class);
        //intent.addCategory(selectedProject);
	    //intent.putExtra("selectedproject",selectedProject);
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
