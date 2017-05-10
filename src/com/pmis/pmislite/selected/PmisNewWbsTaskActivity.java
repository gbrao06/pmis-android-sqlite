package com.pmis.pmislite.selected;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisNewWbsTaskData;
import com.pmis.pmislite.data.PmisUtils;
import com.pmis.pmislite.data.TaskData;
import com.pmis.pmislite.projects.ProjectListActivity;
 
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
 
    public class PmisNewWbsTaskActivity extends Activity {
	     
	    String projectName;
    	// Search EditText
    	EditText wbsName;
	    EditText estimated_start;
	    Date eStart;
	    EditText estimated_end;
	    Date eEnd;
	    Spinner priority_spinner;
	    Spinner parent_task_spinner;
	    Spinner assigned_to_spinner;
	    Spinner status_spinner;
	    
	    EditText efforts_per_day;
	 
	    Button saveTaskButton;
	    Button deleteTaskButton;
	    
	    final Context context=this;
	    
	    EditText wbsID;
	    
	    List<String> userList=new ArrayList<String>();
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_new_wbs_task_view);
	        
	        // Listview Data
	        projectName=this.getIntent().getStringExtra("selectedproject");
	        String pname=projectName;
	        pname+="::Add New Task";
	        setTitle(pname);
	        wbsName=(EditText) findViewById(R.id.wbs_task_name);
	        
	        estimated_start = (EditText) findViewById(R.id.wbs_start_estimate);

	        estimated_start.setOnClickListener(new OnClickListener() {	
	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	            	 //Calendar mcurrentDate=Calendar.getInstance();
	                 //int mYear = mcurrentDate.get(Calendar.YEAR);
	                 //int mMonth=mcurrentDate.get(Calendar.MONTH);
	                 //int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
	                 
	                 //new DatePickerDialog(this,estimate,mYear, mMonth, mDay);
	                 //myCalendar = Calendar.getInstance();
	            	new DatePickerDialog(context,estimate,myCalendar
	                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	            	
	                updateLabelStartEstimate();
	                
	            }

	        });

	        estimated_end = (EditText) findViewById(R.id.wbs_end_estimate);
	        
	        estimated_end.setOnClickListener(new OnClickListener() {	
	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	            	 //Calendar mcurrentDate=Calendar.getInstance();
	                 //int mYear = mcurrentDate.get(Calendar.YEAR);
	                 //int mMonth=mcurrentDate.get(Calendar.MONTH);
	                 //int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
	                 
	                 //new DatePickerDialog(this,estimate,mYear, mMonth, mDay);
	            	//myCalendar = Calendar.getInstance();
	            	new DatePickerDialog(context,estimate,myCalendar
	                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	            	
	                updateLabelEndEstimate();
	                
	            }

	          
				
	        });

	        //priority_spinner = (Spinner) findViewById(R.id.priority_spinner);
	        addItemsOnPrioritySpinner();
	        //status_spinner = (Spinner) findViewById(R.id.status_spinner);
	        addItemsOnParentTaskSpinner();
	        
	        addItemsOnAssignedToSpinner();
	        
	        efforts_per_day = (EditText) findViewById(R.id.wbs_efforts_per_day);
	        //efforts_per_day.setOnClickListener(new CustomOnItemSelectedListener());
	        wbsID=(EditText) findViewById(R.id.wbs_id);
	        
	        saveTaskButton=(Button) findViewById(R.id.saveWbsButton);
	        deleteTaskButton=(Button) findViewById(R.id.cancelWbsButton);
	        
	        saveTaskButton.setOnClickListener(saveWbsButtonHandler);
	        deleteTaskButton.setOnClickListener(cancelWbsButtonHandler);
	    }//end of create method
	    

	    public void addItemsOnStatusSpinner()
	    {
	    	status_spinner = (Spinner) findViewById(R.id.status_new_spinner);
	        
	        List<String> list = new ArrayList<String>();
	        
	        list.add("Open");
	        list.add("Closed");
	   	    
	        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	          //      R.array.task_status_list, android.R.layout.simple_spinner_item);
	   	    ArrayAdapter<String> parentAdapter = new ArrayAdapter<String>(this,
	    	  		android.R.layout.simple_spinner_item, list);
	    	  	
	   	    // Specify the layout to use when the list of choices appears
	        parentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        // Apply the adapter to the spinner
	        status_spinner.setAdapter(parentAdapter);
	        
	        status_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	    }
	    

        private void updateLabelStartEstimate() {

	        String myFormat = "MM/dd/yy"; //In which you need put here
	        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

	        estimated_start.setText(sdf.format(myCalendar.getTime()));
	        eStart=myCalendar.getTime();
	     }

	    
	    private void updateLabelEndEstimate() {
	        String myFormat = "MM/dd/yy"; //In which you need put here
	        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

	        estimated_end.setText(sdf.format(myCalendar.getTime()));
	        eEnd=myCalendar.getTime();
	     }

	    public void addItemsOnAssignedToSpinner()
	    {
	    	assigned_to_spinner = (Spinner) findViewById(R.id.wbs_assigned_to_spinner);
	        
	        List<String> list = new ArrayList<String>();
	        
	        list=PmisUtils.getUserList(context);
	        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	          //      R.array.task_status_list, android.R.layout.simple_spinner_item);
    	    ArrayAdapter<String> parentAdapter = new ArrayAdapter<String>(this,
        	  		android.R.layout.simple_spinner_item, list);
        	  	
    	    // Specify the layout to use when the list of choices appears
	        parentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        // Apply the adapter to the spinner
	        assigned_to_spinner.setAdapter(parentAdapter);
	        
	        assigned_to_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	        
	    }
	    
	    public void addItemsOnParentTaskSpinner() {
	    	   
	        parent_task_spinner = (Spinner) findViewById(R.id.parent_task_spinner);
	        
	        List<String> list = new ArrayList<String>();
	        
	        List<TaskData> datalist = new ArrayList<TaskData>();
	        
	        datalist=PmisUtils.getSelectedProjectTaskList(context, projectName);
	        
	        for(int i=0;i<datalist.size();i++)
	        {
	        	list.add(datalist.get(i).taskName);
	        }
	        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	          //      R.array.task_status_list, android.R.layout.simple_spinner_item);
    	    ArrayAdapter<String> parentAdapter = new ArrayAdapter<String>(this,
        	  		android.R.layout.simple_spinner_item, list);
        	  	
    	    // Specify the layout to use when the list of choices appears
	        parentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        // Apply the adapter to the spinner
	        parent_task_spinner.setAdapter(parentAdapter);
	        
	        parent_task_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	      }

        public void addItemsOnPrioritySpinner() {
     	   
    	    priority_spinner = (Spinner) findViewById(R.id.wbs_priority_spinner);
    	  	
    	    List<Integer> list = new ArrayList<Integer>();
    	    
    	  	list.add(1);
    	  	list.add(2);
    	  	list.add(3);
    	  	list.add(4);
    	  	ArrayAdapter<Integer> priorityAdapter = new ArrayAdapter<Integer>(this,
    	  		android.R.layout.simple_spinner_item, list);
    	  	
    	  	priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	  	priority_spinner.setAdapter(priorityAdapter);
    	  	
    	  	//add listener
    	  	priority_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    	}
    	
	    Calendar myCalendar = Calendar.getInstance();

	    DatePickerDialog.OnDateSetListener estimate = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
	            myCalendar.set(Calendar.MONTH, monthOfYear);
	            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	    	}

	    };

	    View.OnClickListener saveWbsButtonHandler = new View.OnClickListener() {
	        public void onClick(View v) {
	          
	        	//new Wbs Task
	    	    String assignedTo = assigned_to_spinner.getSelectedItem().toString();
	    	    String priority = priority_spinner.getSelectedItem().toString();
	    	    String parentTask=parent_task_spinner.getSelectedItem().toString();
	    	    String status=status_spinner.getSelectedItem().toString();
	    	    
	    	    String taskName=wbsName.getText().toString();
	    	    
	    	    Integer iPriority=Integer.valueOf(priority);
	    	    float efforts=Float.valueOf(efforts_per_day.getText().toString());
	    	    String wbsid=wbsID.getText().toString();
	    	    
	    	    PmisNewWbsTaskData wbsData=new PmisNewWbsTaskData(context,taskName,parentTask,eStart,eEnd,efforts,assignedTo,iPriority,status,wbsid,projectName);
	        	ContentValues values=wbsData.saveWbsTask();
	        	int code=Integer.valueOf((String)values.get("error_code"));
	        	//0 means success
	        	if(code==0)
	        	{
	        		//return to projects
	        		Intent intent = new Intent(context, ProjectListActivity.class);
	    			startActivity(intent);
	    	        	
	        	}
	        //update the task in database
	        }
	   };
	   
	   View.OnClickListener cancelWbsButtonHandler = new View.OnClickListener() {
	        public void onClick(View v) {
	          // MY QUESTION STARTS HERE!!!
	          // IF b1 do this
	          // IF b2 do this
	          // MY QUESTION ENDS HERE!!!
	        Intent intent = new Intent(context, ProjectListActivity.class);
			startActivity(intent);
	        	
	       
	        }
	    };        

   }