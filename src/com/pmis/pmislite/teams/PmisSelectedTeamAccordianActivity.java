package com.pmis.pmislite.teams;

import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisSelectedTeamData;

import android.app.ListActivity;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class PmisSelectedTeamAccordianActivity extends ListActivity implements OnClickListener
{
 
	public OnLongClickListener longClickListner;
 LinearLayout panel1,panel2,panel3,panel4,panel5;
 TextView text1,text2,text3,text4,text5;
 View openLayout;
 
//Listview Adapter
 ArrayAdapter<String> adapter;
  
 // Search EditText
 EditText inputBlogSearch;
  
 String selectedTeam;
 final Context context=this;

 Button newTeamButton;
 
 ///////Add new Blog
 EditText newBlogName;
 Button saveBlogButton;
 Button cancelBlogButton;
 
 ///Existing Blog List
 ListView blogListView;
 String selectedBlog;
 
///Team Members of The Team
ListView teamMembersListView;
ListAdapter membersAadapter;

PmisSelectedTeamData selectedTeamData;
 
 @Override
 public void onCreate(Bundle savedInstanceState)
 {
	super.onCreate(savedInstanceState);
  	setContentView(R.layout.selected_team_accordian_view);
  
  	String pname=this.getIntent().getStringExtra("selectedteam");
  	setTitle(pname);
  
  	selectedTeamData=new PmisSelectedTeamData(context,pname);
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
  
  ////Team Members List
  teamMembersListView=(ListView) findViewById(R.id.members_list);
  membersAadapter = new ArrayAdapter<String>(this, R.layout.project_item_view, R.id.project_name, selectedTeamData.getMembersOfTheTeam());
  teamMembersListView.setAdapter(membersAadapter);
  
  //////Blog List{{
  blogListView = (ListView) findViewById(R.id.blog_list);
  inputBlogSearch = (EditText) findViewById(R.id.inputBlogSearch);

  // Adding items to listview
  adapter = new ArrayAdapter<String>(this, R.layout.project_item_view, R.id.project_name, selectedTeamData.getBlogsOfTheTeam());
  blogListView.setAdapter(adapter);
  
  
  blogListView.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view,
          int position, long id) {
          TextView textView = (TextView) view.findViewById(R.id.project_name);
          String text = textView.getText().toString(); 
          System.out.println("Choosen Project = : " + text);
          String selectedFromList = (blogListView.getItemAtPosition(position)).toString();
          System.out.println("Choosen Project1 = : " + selectedFromList);
          selectedBlog=selectedFromList;
          if(selectedBlog==null)
        	  selectedBlog=text;
          
      	//Intent intent = new Intent(context, ProjectDetailActivity.class);
	//	    Intent intent=new Intent(context,PmisBlogActivity.class);
          //intent.addCategory(selectedProject);
	//	    intent.putExtra("selectedblog",selectedBlog);
	//	    intent.putExtra("selectedteam",selectedTeam);
		    
	//	    startActivity(intent);
  }});

  /**
   * Enabling Search Filter
   * */
  inputBlogSearch.addTextChangedListener(new TextWatcher() {
       
      @Override
      public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
          // When user changed the Text
    	  PmisSelectedTeamAccordianActivity.this.adapter.getFilter().filter(cs);   
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
  createAddNewBlogPanel();
 }
 
 private void createAddNewBlogPanel()
 {
	    // Listview Data
     String pname=this.getIntent().getStringExtra("selectedteam");
     pname+="::Create New Blog";
     setTitle(pname);
     newBlogName=(EditText) findViewById(R.id.blog_name);
     newBlogName.addTextChangedListener(new TextWatcher() {
         
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
     
     saveBlogButton=(Button) findViewById(R.id.saveBlogButton);
     cancelBlogButton=(Button) findViewById(R.id.cancelBlogButton);
     
     saveBlogButton.setOnClickListener(saveBlogButtonHandler);
     cancelBlogButton.setOnClickListener(cancelBlogButtonHandler);

 }
 
 View.OnClickListener saveBlogButtonHandler = new View.OnClickListener() {
     public void onClick(View v) {
       // MY QUESTION STARTS HERE!!!
       // IF b1 do this
       // IF b2 do this
       // MY QUESTION ENDS HERE!!!
     //Intent intent = new Intent(context, ProjectListActivity.class);
		//startActivity(intent);
     	
     //update the task in database
     }
};

View.OnClickListener cancelBlogButtonHandler = new View.OnClickListener() {
     public void onClick(View v) {
       // MY QUESTION STARTS HERE!!!
       // IF b1 do this
       // IF b2 do this
       // MY QUESTION ENDS HERE!!!
     //Intent intent = new Intent(context, ProjectListActivity.class);
		//startActivity(intent);
     	
    //delete the task min data base
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

