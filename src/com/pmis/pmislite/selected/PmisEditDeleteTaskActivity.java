package com.pmis.pmislite.selected;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisEditWbsTaskData;
import com.pmis.pmislite.data.PmisNewWbsTaskData;
import com.pmis.pmislite.data.PmisUtils;
import com.pmis.pmislite.data.WbsData;
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
 
    public class PmisEditDeleteTaskActivity extends Activity {
	     
	    // Search EditText
    	EditText task_name;
    		 
    	EditText estimated_start;
	    EditText estimated_end;
	    Spinner priority_spinner;
	    Spinner status_spinner;
	    EditText assigned_to;
	    EditText efforts_per_day;
	 
	    Button saveTaskButton;
	    Button deleteTaskButton;
	    
	    final Context context=this;
	    
	    ////
	    Date eStart,eEnd;
	    
	    List<WbsData>wbslist=new ArrayList<WbsData>();
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.task_edit_delete_view);
	         
	        // Listview Data
	        String selectedTask=this.getIntent().getStringExtra("selectedtask");
	        
	        String pname=selectedTask;
	        pname+="::Edit Delete Task";
	        setTitle(pname);
	        
	        //We need to fill the data after getting from db initially
	        task_name=(EditText) findViewById(R.id.txt_task_name);
	        task_name.setText(selectedTask);
	        
	        
	        estimated_start = (EditText) findViewById(R.id.estimtaed_start);

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

	        estimated_end = (EditText) findViewById(R.id.estimated_end);
	        
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
	        addItemsOnStatusSpinner();
	        
	        efforts_per_day = (EditText) findViewById(R.id.efforts_per_day);
	        //efforts_per_day.setOnClickListener(new CustomOnItemSelectedListener());
	        
	        saveTaskButton=(Button) findViewById(R.id.saveTaskButton);
	        saveTaskButton.setOnClickListener(saveTaskButtonHandler);
	        
	        deleteTaskButton=(Button) findViewById(R.id.deleteTaskButton);
	        deleteTaskButton.setOnClickListener(deleteTaskButtonHandler);
	        
	        assigned_to= (EditText) findViewById(R.id.txt_assigned_to);
		      
	        setWbsParam();
	    }//end of create method
	    
	    private void setWbsParam()
	    {
	    	//EditText task_name;
   		 
	    	//EditText estimated_start;
		    //EditText estimated_end;
		    //Spinner priority_spinner;
		    //Spinner status_spinner;
		    //Spinner assigned_to_spinner;
		    //EditText efforts_per_day;
		 
	    	String userName=this.getIntent().getStringExtra("assignedto");
	        
	    	List<String>list=new ArrayList<String>();
	    	WbsData data=null;
	    	for(int i=0;i<wbslist.size();i++)
	    	{
	    		String name=PmisUtils.getUserName(context, wbslist.get(i).getAssignedPk());
	    		if(name!=null && !name.equalsIgnoreCase("") && name.equalsIgnoreCase(userName))
	    		{
	    			data=wbslist.get(i);
	    			break;
	    		}
	    	}
	    	
	    	if(data!=null)
	    	{
	    		task_name.setText(data.getTaskData().taskName);
	    		estimated_start.setText(data.getStartEstimate().toLocaleString());
	    		estimated_end.setText(data.getEndEstimate().toLocaleString());
	    		efforts_per_day.setText(String.valueOf(data.getEfforts()));
	    	}
	    	    
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
	    
	    public void addItemsOnStatusSpinner() {
	    	   
	        status_spinner = (Spinner) findViewById(R.id.status_spinner);
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	                R.array.task_status_list, android.R.layout.simple_spinner_item);
	        // Specify the layout to use when the list of choices appears
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        // Apply the adapter to the spinner
	        status_spinner.setAdapter(adapter);
	        
	        status_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	      }

        public void addItemsOnPrioritySpinner() {
     	   
    	    priority_spinner = (Spinner) findViewById(R.id.priority_spinner);
    	  	
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

	    private void setDefaultParams()
	    {
	    	
	    }
	  
	    View.OnClickListener saveTaskButtonHandler = new View.OnClickListener() {
	        public void onClick(View v) {	
	        //update the task in database
	        	
	        	String assignedTo = assigned_to.getText().toString();
	    	    long userpk=PmisUtils.getUserPk(context, assignedTo);
	        	//check which wbsdat has this assignedto
	    	    PmisEditWbsTaskData wbsdata=null;
	    	    for(int i=0;i<wbslist.size();i++)
	        	{
	        		long val=wbslist.get(i).getAssignedPk();
	        		
	        		if(val==userpk)
	        		{
	        			wbsdata=new PmisEditWbsTaskData(context,wbslist.get(i));
	        			break;
	        		}
	        	}
	        ContentValues rets=	wbsdata.updateWbsTask();
	        if(rets.getAsInteger("error_code")!=0)//failure
	        {
	        	
	        }
	     }
	        
	 };
	   
	   View.OnClickListener deleteTaskButtonHandler = new View.OnClickListener() {
	        public void onClick(View v) {
	          //1.confirm the delete by a dialog. if ok 
	        	//2. delete the wbs task. don't delete the task
	        	
	        	//deleteWbsTask
	        	
	       //delete the task min data base
	        }
	    };        

   }