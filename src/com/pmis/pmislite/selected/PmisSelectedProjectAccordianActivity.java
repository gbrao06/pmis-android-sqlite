package com.pmis.pmislite.selected;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.pmis.pmislite.R;
import com.pmis.pmislite.R.id;
import com.pmis.pmislite.R.layout;
import com.pmis.pmislite.data.PmisNewWbsTaskData;
import com.pmis.pmislite.data.PmisSelectedProjectData;
import com.pmis.pmislite.data.PmisUtils;
import com.pmis.pmislite.data.TaskData;
import com.pmis.pmislite.data.WbsData;
import com.pmis.pmislite.projects.ProjectListActivity;
import com.pmis.pmislite.sql.loader.PmisDatabaseHelper;
import com.pmis.pmislite.sql.loader.SQLiteCursorLoader;
import com.pmis.pmislite.tasks.ExpandableTaskListAdapter;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout.LayoutParams;

public class PmisSelectedProjectAccordianActivity extends ListActivity implements OnClickListener
{
 
public OnLongClickListener longClickListner;
 LinearLayout panel1,panel2,panel3,panel4,panel5;
 TextView text1,text2,text3,text4,text5;
 View openLayout;

 //ListView taskListView;
 
 ExpandableListView taskListView;
 
 PmisSelectedProjectData currentProject;
 
//Listview Adapter
 ArrayAdapter<String> adapter;
  
 // Search EditText
 EditText inputTaskSearch;
  
 String selectedTask;
 String selectedUser;
 final Context context=this;
///////////////Add New Wbs Task Code Starts Here
 EditText wbsName;
 EditText estimated_start;
 EditText estimated_end;
 Spinner priority_spinner;
 Spinner parent_task_spinner;
 Spinner assigned_to_spinner;
 EditText efforts_per_day;
 Spinner status_spinner;
 
 Date eStart,eEnd;
 Button saveTaskButton;
 Button deleteTaskButton;
 
 EditText wbsID;
 
 List<TaskData>taskdatalist=new ArrayList<TaskData>();
 
 
 List<String>tasklist=new ArrayList<String>();
 List<String> childList=new ArrayList<String>();
 Map<String, List<String>> childCollection;
 
 List<String>userlist=new ArrayList<String>();
 
 @Override
 public void onCreate(Bundle savedInstanceState)
 {
	 super.onCreate(savedInstanceState);
  	 setContentView(R.layout.selected_project_accordian_view);
  
  	 String pname=this.getIntent().getStringExtra("selectedproject");
  	 setTitle(pname);
  
  	 currentProject=new PmisSelectedProjectData(context.getApplicationContext(),pname);
  	
  	 panel1 = (LinearLayout) findViewById(R.id.panel1);
  	 panel2 = (LinearLayout) findViewById(R.id.panel2);
  	 panel3 = (LinearLayout) findViewById(R.id.panel3);
  	 panel4 = (LinearLayout) findViewById(R.id.panel4);
  	 panel5 = (LinearLayout) findViewById(R.id.panel5);
  
  

  //panel1.setVisibility(View.VISIBLE);
  
  //panel1.setVisibility(View.VISIBLE);
  
  //Log.v("CZ","height at first ..." + panel1.getMeasuredHeight());
  
  text1 = (TextView) findViewById(R.id.text1);
  text2 = (TextView) findViewById(R.id.text2);
  text3 = (TextView) findViewById(R.id.text3);
  text4 = (TextView) findViewById(R.id.text4);
  text5 = (TextView) findViewById(R.id.text5);
  
  text1.setOnClickListener(this);
  text2.setOnClickListener(this);
  text3.setOnClickListener(this);
  text4.setOnClickListener(this);
  text5.setOnClickListener(this);
  
  taskListView = (ExpandableListView) findViewById(R.id.expandable_task_list);
 
  inputTaskSearch = (EditText) findViewById(R.id.inputTaskSearch);
   
  // Adding items to listview
  
  taskdatalist=PmisUtils.getSelectedProjectTaskList(context, pname);
  tasklist.clear();
  for(int i=0;i<taskdatalist.size();i++)
  {
	  String taskname=taskdatalist.get(i).taskName;
	  tasklist.add(taskname);
  }
  
  createChildCollection();
 
  adapter = new ArrayAdapter<String>(this, R.layout.project_item_view, R.id.project_name,tasklist);
  
  final ExpandableTaskListAdapter expListAdapter = new ExpandableTaskListAdapter(
          this,pname, tasklist, childCollection);
  
  taskListView.setAdapter(expListAdapter);
  
  taskListView.setOnChildClickListener(new OnChildClickListener() {

      public boolean onChildClick(ExpandableListView parent, View v,
              int groupPosition, int childPosition, long id) {
          final String selected = (String) expListAdapter.getChild(
                  groupPosition, childPosition);
          Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                  .show();
          //go for edit / delete task
          
          Intent intent=new Intent(context,PmisEditDeleteTaskActivity.class);
          intent.putExtra("selectedproject",currentProject.getProjectName());
          intent.putExtra("selectedtask",selectedTask);
          intent.putExtra("assignedto",selected);
          
		  startActivity(intent);

          return true;
      }
  });

  /*
  taskListView.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view,
          int position, long id) {
          TextView textView = (TextView) view.findViewById(R.id.project_name);
          String text = textView.getText().toString(); 
          System.out.println("Choosen Project = : " + text);
          String selectedFromList = (taskListView.getItemAtPosition(position)).toString();
          System.out.println("Choosen Project1 = : " + selectedFromList);
          selectedTask=selectedFromList;
          if(selectedTask==null)
        	  selectedTask=text;
          
      	//Intent intent = new Intent(context, ProjectDetailActivity.class);
	    Intent intent=new Intent(context,PmisEditDeleteTaskActivity.class);
          //intent.addCategory(selectedProject);
		    intent.putExtra("selectedproject",currentProject.getProjectName());
		    intent.putExtra("selectedtask",selectedTask);
		    
		    startActivity(intent);
      	
  }
      
      
  });

  
  /**
   * Enabling Search Filter
   * */
  inputTaskSearch.addTextChangedListener(new TextWatcher() {
       
      @Override
      public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
          // When user changed the Text
    	 // PmisSelectedProjectAccordianActivity.this.adapter.getFilter().filter(cs);   
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
  
  //add code for Add New Wbs Task
  createAddNewWbsPanel();
 }
 

 private List<String> getAssignedToList(Long taskpk)
 {
	 List<Long> upks=new ArrayList<Long>();
	 upks=PmisUtils.getAssignedListOfTask(context, taskpk);
	 List<String> ulist=new ArrayList<String>();
	 
	 for(int i=0;i<upks.size();i++)
     {
    	 String uname=null;
    	 uname=PmisUtils.getUserName(context,upks.get(i));
    	 if(uname!=null)
    	 {

             Toast.makeText(context,":username:"+uname, Toast.LENGTH_LONG).show();
    		 ulist.add(uname);
    	 }
    	 else
    		 Toast.makeText(context,":nullusername:"+uname, Toast.LENGTH_LONG).show();
     }
	 
	 return ulist;
 }
 
 private void createChildCollection() {
     // preparing laptops collection(child)

     childCollection = new LinkedHashMap<String, List<String>>();

     Toast.makeText(getBaseContext(),":taskdatalistsize:"+taskdatalist.size(), Toast.LENGTH_LONG).show();
     
     for(int i=0;i<taskdatalist.size();i++)
     {
    	 childList=childList = new ArrayList<String>();
    	 childList=getAssignedToList(taskdatalist.get(i).getTaskpk());
   
    	 childCollection.put(taskdatalist.get(i).taskName, childList);
     }
  
 }

 private void loadChild(String[] laptopModels) {
     childList = new ArrayList<String>();
     for (String model : laptopModels)
         childList.add(model);
 }

 private void createAddNewWbsPanel()
 {
	    // Listview Data
     String pname=this.getIntent().getStringExtra("selectedproject");
     pname+="::Add New Task";
     setTitle(pname);
     wbsName=(EditText) findViewById(R.id.wbs_task_name);
     wbsName.addTextChangedListener(new TextWatcher() {
         
         @Override
         public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
             // When user changed the Text
       	     
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
     
     addItemsOnStatusSpinner();
     
     
     efforts_per_day = (EditText) findViewById(R.id.wbs_efforts_per_day);
     //efforts_per_day.setOnClickListener(new CustomOnItemSelectedListener());
     wbsID=(EditText) findViewById(R.id.wbs_id);
     wbsID.addTextChangedListener(new TextWatcher() {
         
         @Override
         public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
             // When user changed the Text
       	     
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
     

     saveTaskButton=(Button) findViewById(R.id.saveWbsButton);
     deleteTaskButton=(Button) findViewById(R.id.cancelWbsButton);
     
     saveTaskButton.setOnClickListener(saveWbsButtonHandler);
     deleteTaskButton.setOnClickListener(cancelWbsButtonHandler);

 }
 
 
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
     
     list=PmisUtils.getUserList(context.getApplicationContext());
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
     
     datalist=PmisUtils.getSelectedProjectTaskList(context, currentProject.getProjectName());
     String empty="";
     list.add(empty);
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
       //create new wbs task or edit the wbs
    	//new Wbs Task
 	    String assignedTo = assigned_to_spinner.getSelectedItem().toString();
 	    String priority = priority_spinner.getSelectedItem().toString();
 	    String parentTask=parent_task_spinner.getSelectedItem().toString();
 	    String status=status_spinner.getSelectedItem().toString();
 	    String taskName=wbsName.getText().toString();
 	    
 	    Integer iPriority=Integer.valueOf(priority);
 	    float efforts=Float.valueOf(efforts_per_day.getText().toString());
 	    String wbsid=wbsID.getText().toString();
 	    
 	    PmisNewWbsTaskData wbsData=new PmisNewWbsTaskData(context,taskName,parentTask,eStart,eEnd,efforts,assignedTo,iPriority,wbsid,status,currentProject.getProjectName());
     	ContentValues rets=wbsData.saveWbsTask();
     	
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

 
 @Override
 public void onClick(View v)
 {
  hideOthers(v);
 }
 
 private void hideThemAll()
 {
  if(openLayout == null) return;
  if(openLayout == panel1)
	  panel1.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
  if(openLayout == panel2)
  {
	  panel2.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
	
	 // Intent intent=new Intent(context,PmisNewWbsTaskActivity.class);
      //intent.addCategory(selectedProject);
	  //intent.putExtra("selectedproject",selectedProject);
	  //startActivity(intent);

  }
  if(openLayout == panel3)
	  panel3.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
  if(openLayout == panel4)
	  panel4.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel4, true));
  if(openLayout == panel5)
	  panel5.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel5, true));
 }
 
 private void hideOthers(View layoutView)
 {
  {
   int v;
   if(layoutView.getId() == R.id.text1)
   {
    v = panel1.getVisibility();
    if(v != View.VISIBLE)
    {
     panel1.setVisibility(View.VISIBLE);
     Log.v("CZ","height..." + panel1.getHeight());
    }
    
    //panel1.setVisibility(View.GONE);
    //Log.v("CZ","again height..." + panel1.getHeight());
    hideThemAll();
    if(v != View.VISIBLE)
    {
    	panel1.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
    }
   }
   else if(layoutView.getId() == R.id.text2)
   {
    v = panel2.getVisibility();
    hideThemAll();
    if(v != View.VISIBLE)
    {
     panel2.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
    }
   }
   else if(layoutView.getId() == R.id.text3)
   {
    v = panel3.getVisibility();
    hideThemAll();
    if(v != View.VISIBLE)
    {
     panel3.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
    }
   }
   else if(layoutView.getId() == R.id.text4)
   {
    v = panel4.getVisibility();
    hideThemAll();
    if(v != View.VISIBLE)
    {
     panel4.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel4, true));
    }
   }
   else if(layoutView.getId() == R.id.text5)
   {
    v = panel5.getVisibility();
    hideThemAll();
    if(v != View.VISIBLE)
    {
     panel5.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel5, true));
    }
   }
  }
 }
 
 
 public class ScaleAnimToHide extends ScaleAnimation
 {

         private View mView;

         private LayoutParams mLayoutParams;

         private int mMarginBottomFromY, mMarginBottomToY;

         private boolean mVanishAfter = false;

         public ScaleAnimToHide(float fromX, float toX, float fromY, float toY, int duration, View view,boolean vanishAfter)
         {
             super(fromX, toX, fromY, toY);
             setDuration(duration);
             openLayout = null;
             mView = view;
             mVanishAfter = vanishAfter;
             mLayoutParams = (LayoutParams) view.getLayoutParams();
             int height = mView.getHeight();
             mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin - height;
             mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) - height;
            
             Log.v("CZ","height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY  + " , mMarginBottomToY.." +mMarginBottomToY);
         }

         @Override
         protected void applyTransformation(float interpolatedTime, Transformation t)
         {
             super.applyTransformation(interpolatedTime, t);
             if (interpolatedTime < 1.0f)
             {
                 int newMarginBottom = mMarginBottomFromY + (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime);
                 mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin,mLayoutParams.rightMargin, newMarginBottom);
                 mView.getParent().requestLayout();
                 //Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
             }
             else if (mVanishAfter)
             {
                 mView.setVisibility(View.GONE);
             }
         }
 }
 public class ScaleAnimToShow extends ScaleAnimation
 {

         private View mView;

         private LayoutParams mLayoutParams;

         private int mMarginBottomFromY, mMarginBottomToY;

         private boolean mVanishAfter = false;

         public ScaleAnimToShow(float toX, float fromX, float toY, float fromY, int duration, View view,boolean vanishAfter)
         {
             super(fromX, toX, fromY, toY);
             openLayout = view;
             setDuration(duration);
             mView = view;
             mVanishAfter = vanishAfter;
             mLayoutParams = (LayoutParams) view.getLayoutParams();
             mView.setVisibility(View.VISIBLE);
             int height = mView.getHeight();
             //mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin + height;
             //mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) + height;
            
             mMarginBottomFromY = 0;
             mMarginBottomToY = height;
            
             Log.v("CZ",".................height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY  + " , mMarginBottomToY.." +mMarginBottomToY);
         }

         @Override
         protected void applyTransformation(float interpolatedTime, Transformation t)
         {
             super.applyTransformation(interpolatedTime, t);
             if (interpolatedTime < 1.0f)
             {
                 int newMarginBottom = (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime) - mMarginBottomToY;
                 mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin,mLayoutParams.rightMargin, newMarginBottom);
                 mView.getParent().requestLayout();
                 //Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
             }
         }

 }
 
 
 
}

