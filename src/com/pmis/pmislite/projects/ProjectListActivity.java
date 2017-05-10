package com.pmis.pmislite.projects;
import java.util.ArrayList;
import java.util.HashMap;

import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisUtils;
import com.pmis.pmislite.messages.MessagesActivity;
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
import com.pmis.pmislite.tasks.TaskListActivity;
 
public class ProjectListActivity extends Activity {
     
    // List view
    private ListView lv;
     
    // Listview Adapter
    ArrayAdapter<String> adapter;
     
    // Search EditText
    EditText inputSearch;
     
    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
 
    final Context context=this;
    String selectedProject=null; 
    
    Button newProjectButton;
    Button teamsButton;
    Button messagesButton;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_list_view);
         
        newProjectButton=(Button) findViewById(R.id.newProjectButton);
        newProjectButton.setOnClickListener(newProjectButtonHandler);
        
        teamsButton=(Button) findViewById(R.id.teamsButton);
        teamsButton.setOnClickListener(teamsButtonHandler);
        
        messagesButton=(Button) findViewById(R.id.messagesButton);
        messagesButton.setOnClickListener(messageButtonHandler);
           
        // Listview Data
        String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                                "iPhone 4S", "Samsung Galaxy Note 800",
                                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};
         
        lv = (ListView) findViewById(R.id.project_list);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
         
        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.project_item_view, R.id.project_name,PmisUtils.getProjectList(context.getApplicationContext()));
        lv.setAdapter(adapter);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.project_name);
                String text = textView.getText().toString(); 
                System.out.println("Choosen Project = : " + text);
                String selectedFromList = (lv.getItemAtPosition(position)).toString();
                System.out.println("Choosen Project1 = : " + selectedFromList);
                selectedProject=selectedFromList;
                if(selectedProject==null)
                	selectedFromList=text;
                
            	//Intent intent = new Intent(context, ProjectDetailActivity.class);
    		    Intent intent=new Intent(context,PmisSelectedProjectAccordianActivity.class);
                //intent.addCategory(selectedProject);
    		    intent.putExtra("selectedproject",selectedProject);
            	startActivity(intent);

        }});
        
        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {
             
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ProjectListActivity.this.adapter.getFilter().filter(cs);   
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
    
    View.OnClickListener newProjectButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //projectButton.setBackgroundResource(R.drawable.ic_launcher);
        	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mtasks.jsf"));
            
        	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
            //startActivity(myWebLink);
        	Intent intent=new Intent(context,NewProjectActivity.class);
            //intent.addCategory(selectedProject);
		    //intent.putExtra("selectedproject",selectedProject);
        	startActivity(intent);

        }
    };


    View.OnClickListener teamsButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //projectButton.setBackgroundResource(R.drawable.ic_launcher);
        	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mteams.jsf"));
            
        	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
            //startActivity(myWebLink);
        
        	Intent intent=new Intent(context,TaskListActivity.class);
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