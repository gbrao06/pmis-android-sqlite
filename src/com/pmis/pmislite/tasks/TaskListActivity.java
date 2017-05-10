package com.pmis.pmislite.tasks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pmis.pmislite.R;
import com.pmis.pmislite.messages.MessagesActivity;
import com.pmis.pmislite.projects.ProjectListActivity;
import com.pmis.pmislite.selected.PmisEditDeleteTaskActivity;
import com.pmis.pmislite.teams.NewTeamActivity;
import com.pmis.pmislite.teams.TeamListActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Filterable;
import android.widget.Toast;
public class TaskListActivity extends Activity {
     
    // List view
     
    // Listview Adapter
    ArrayAdapter<String> adapter;
     
    // Search EditText
    EditText inputSearch;
     
    // ArrayList for Listview
    
    final Context context=this;
    String selectedProject=null; 
    
    Button projectsButton;
    Button teamsButton;
    Button messagesButton;
    
    List<String> groupList=new ArrayList<String>();
    List<String> childList=new ArrayList<String>();
    Map<String, List<String>> laptopCollection;
    ExpandableListView imageTaskListView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_view);
         
        projectsButton=(Button) findViewById(R.id.projectsButton);
        projectsButton.setOnClickListener(projectsButtonHandler);
        
        teamsButton=(Button) findViewById(R.id.teamsButton);
        teamsButton.setOnClickListener(teamsButtonHandler);
        
        messagesButton=(Button) findViewById(R.id.messagesButton);
        messagesButton.setOnClickListener(messagesButtonHandler);
        
        createGroupList();
        
        createCollection();
  
        imageTaskListView = (ExpandableListView) findViewById(R.id.image_task_list);
        
        final ExpandableTaskListAdapter expListAdapter = new ExpandableTaskListAdapter(
                this,selectedProject, groupList, laptopCollection);
        
        imageTaskListView.setAdapter(expListAdapter);
        
        //setGroupIndicatorToRight();
 
        imageTaskListView.setOnChildClickListener(new OnChildClickListener() {
 
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();
                //go for edit / delete task
                
                Intent intent=new Intent(context,PmisEditDeleteTaskActivity.class);
                intent.putExtra("selectedtask",selected);
      		    startActivity(intent);

                return true;
            }
        });
        
        /**
         * Enabling Search Filter
         * */
        inputSearch=(EditText) findViewById(R.id.inputImageTaskSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
             
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //TaskListActivity.this.adapter.getFilter().filter(cs);
                ((Filterable) imageTaskListView.getAdapter()).getFilter().filter(cs);
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
    
    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("HP");
        groupList.add("Dell");
        groupList.add("Lenovo");
        groupList.add("Sony");
        groupList.add("HCL");
        groupList.add("Samsung");
    }
 
    private void createCollection() {
        // preparing laptops collection(child)
        String[] hpModels = { "HP Pavilion G6-2014TX", "ProBook HP 4540",
                "HP Envy 4-1025TX" };
        String[] hclModels = { "HCL S2101", "HCL L2102", "HCL V2002" };
        String[] lenovoModels = { "IdeaPad Z Series", "Essential G Series",
                "ThinkPad X Series", "Ideapad Z Series" };
        String[] sonyModels = { "VAIO E Series", "VAIO Z Series",
                "VAIO S Series", "VAIO YB Series" };
        String[] dellModels = { "Inspiron", "Vostro", "XPS" };
        String[] samsungModels = { "NP Series", "Series 5", "SF Series" };
 
        laptopCollection = new LinkedHashMap<String, List<String>>();
 
        for (String laptop : groupList) {
            if (laptop.equals("HP")) {
                loadChild(hpModels);
            } else if (laptop.equals("Dell"))
                loadChild(dellModels);
            else if (laptop.equals("Sony"))
                loadChild(sonyModels);
            else if (laptop.equals("HCL"))
                loadChild(hclModels);
            else if (laptop.equals("Samsung"))
                loadChild(samsungModels);
            else
                loadChild(lenovoModels);
 
            laptopCollection.put(laptop, childList);
        }
    }
 
    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }
 
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
    
    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
 
        imageTaskListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }
 
    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
 /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_list_view, menu);
        return true;
    }
  */
}