package com.pmis.pmislite.login;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisNewProjectData;
import com.pmis.pmislite.data.PmisNewRegistrationData;
import com.pmis.pmislite.data.PmisNewTeamData;
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
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class NewRegistrationActivity extends Activity {
     
    // Email, password edittext
    EditText username, password;
    EditText companyid, companyname;
    EditText firstname, lastname;
    EditText mobile_no;
     
    // Registration button
    Button btnRegister;
     
    // Alert Dialog Manager
    LoginDialogManager alert = new LoginDialogManager();
     
    // Session Manager Class
    SessionManager session;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration); 
         
        // Session Manager
        session = new SessionManager(getApplicationContext());                
         
        // Email, Password input text
        companyid = (EditText) findViewById(R.id.company_id); 
        companyname = (EditText) findViewById(R.id.company_name); 
        mobile_no = (EditText) findViewById(R.id.mobile_no); 
        
        username = (EditText) findViewById(R.id.user_email);
        password = (EditText) findViewById(R.id.pass_word); 
        firstname = (EditText) findViewById(R.id.first_name); 
        lastname = (EditText) findViewById(R.id.last_name); 
         
         
        // Regster button
        btnRegister = (Button) findViewById(R.id.register_button);
         
         
        // registration button click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
            	String scompanyid =companyid.getText().toString();
                String scompanyname =companyname.getText().toString();
                String smobile =mobile_no.getText().toString();
                
            	String uname = username.getText().toString();
                String pword = password.getText().toString();
                
             // Email, Password input text
                String sfirstname =firstname.getText().toString();
                String slastname =lastname.getText().toString();
                
                List<String> list=new ArrayList<String>();
                list.add(uname);
                list.add(pword);
                list.add(scompanyid);
                list.add(scompanyname);
                list.add(smobile);
                list.add(sfirstname);
                list.add(slastname);
                      
                // Check if username, password is filled                
                if(checkForEssentials(list)){
                    	 
                   	PmisNewRegistrationData pdata=new PmisNewRegistrationData(getApplicationContext(),Integer.valueOf(scompanyid),scompanyname,Long.valueOf(smobile),uname,pword,sfirstname,slastname); 
                   	ContentValues codes=pdata.registerUser();
                   	
                   	 if(codes.getAsInteger("error_code")!=0)
                   	 {
                   		 Toast.makeText(getApplicationContext(), "Failed: " + codes.getAsString("error"), Toast.LENGTH_LONG).show();
                         return;
                   	 }
                    	//session.createRegistrationSession("Pmis",uname);
                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                         
                    }else{
                        // username / password doesn't match
                        alert.showAlertDialog(NewRegistrationActivity.this, "Registration failed..", "Kindly Fill All Fields", false);
                    }               
                 
            }
        });
    }        
    
    private boolean checkForEssentials(List<String> list)
    {
    	if(list==null)
    		return false;
    	
    	for(int i=0;i<list.size();i++)
    	{
    		if(list.get(i)==null || list.get(i).equalsIgnoreCase(""))
    			return false;
    	}
    	
    	return true;
    }
}