package com.pmis.pmislite.teams;
import java.util.ArrayList;
import java.util.HashMap;

import com.pmis.pmislite.R;
import com.pmis.pmislite.messages.MessagesActivity;
import com.pmis.pmislite.projects.ProjectListActivity;
import com.pmis.pmislite.selected.PmisSelectedProjectAccordianActivity;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.pmis.pmislite.tasks.TaskListActivity;
 
public class TeamListActivity extends Activity {
     
    // List view
    private ListView lv;
     
    // Listview Adapter
    ArrayAdapter<String> adapter;
     
    // Search EditText
    EditText inputTeamSearch;
     
    // ArrayList for Listview
    ArrayList<HashMap<String, String>> teamList;
 
    final Context context=this;
    String selectedTeam=null; 
    
    Button newTeamButton;
    Button projectsButton;
    Button messagesButton;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_list_view);
         
        newTeamButton=(Button) findViewById(R.id.newTeamButton);
        newTeamButton.setOnClickListener(newTeamButtonHandler);
        
        projectsButton=(Button) findViewById(R.id.projectsButton);
        projectsButton.setOnClickListener(projectsButtonHandler);
        
        messagesButton=(Button) findViewById(R.id.messagesButton);
        messagesButton.setOnClickListener(messageButtonHandler);
        
        // Listview Data
        String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                                "iPhone 4S", "Samsung Galaxy Note 800",
                                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};
         
        lv = (ListView) findViewById(R.id.team_list);
        inputTeamSearch = (EditText) findViewById(R.id.inputSearch);
         
        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.project_item_view, R.id.project_name, products);
        lv.setAdapter(adapter);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.project_name);
                String text = textView.getText().toString(); 
                System.out.println("Choosen Project = : " + text);
                String selectedFromList = (lv.getItemAtPosition(position)).toString();
                System.out.println("Choosen Project1 = : " + selectedFromList);
                selectedTeam=selectedFromList;
                if(selectedTeam==null)
                	selectedFromList=text;
                
            	//Intent intent = new Intent(context, ProjectDetailActivity.class);
    		    Intent intent=new Intent(context,PmisSelectedTeamAccordianActivity.class);
                //intent.addCategory(selectedProject);
    		    intent.putExtra("selectedproject",selectedTeam);
            	startActivity(intent);

        }});
        
        /**
         * Enabling Search Filter
         * */
        inputTeamSearch.addTextChangedListener(new TextWatcher() {
             
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                TeamListActivity.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
    }  
    
    View.OnClickListener newTeamButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //projectButton.setBackgroundResource(R.drawable.ic_launcher);
        	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mtasks.jsf"));
            
        	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
            //startActivity(myWebLink);
        	Intent intent=new Intent(context,NewTeamActivity.class);
            //intent.addCategory(selectedProject);
		    //intent.putExtra("selectedproject",selectedProject);
        	startActivity(intent);

        }
    };


    View.OnClickListener projectsButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //projectButton.setBackgroundResource(R.drawable.ic_launcher);
        	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mteams.jsf"));
            
        	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
            //startActivity(myWebLink);
        
        	Intent intent=new Intent(context,ProjectListActivity.class);
        	startActivity(intent);

        }
    };


    View.OnClickListener messageButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //projectButton.setBackgroundResource(R.drawable.ic_launcher);
        	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mmessages.jsf"));
            
        	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
            //startActivity(myWebLink);
        
        	Intent intent=new Intent(context,MessagesActivity.class);
        	startActivity(intent);

        }
    };

}