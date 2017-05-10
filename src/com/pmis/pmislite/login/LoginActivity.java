package com.pmis.pmislite.login;

import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisLoginData;
import com.pmis.pmislite.projects.ProjectListActivity;
 
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
import android.widget.Toast;
 
public class LoginActivity extends Activity {
     
    // Email, password edittext
    EditText txtUsername, txtPassword;
     
    // login button
    Button btnLogin;
    
    Button btnRegister;
    
    // Alert Dialog Manager
    LoginDialogManager alert = new LoginDialogManager();
     
    // Session Manager Class
    SessionManager session;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); 
         
        // Session Manager
        session = new SessionManager(getApplicationContext());                
        btnRegister= (Button) findViewById(R.id.register_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                Intent i = new Intent(getApplicationContext(), NewRegistrationActivity.class);
                startActivity(i);
                finish();
            }     
        });
        
        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.login_user_name);
        txtPassword = (EditText) findViewById(R.id.login_pass_word); 
         
        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
         
        // Login button
        btnLogin = (Button) findViewById(R.id.login_button);
         
         
        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                 
                // Check if username, password is filled                
                if(username.trim().length() > 0 && password.trim().length() > 0){
                    // For testing puspose username, password is checked with sample data
                    // username = test
                    // password = test
                	PmisLoginData data=new PmisLoginData(getApplicationContext(),username,password);
                	ContentValues retValues=data.loginUser();
                	if(retValues.getAsInteger("error_code")!=0)//error
                	{
                		Toast.makeText(getApplicationContext(), "User Login Failed: " + retValues.getAsString("error"), Toast.LENGTH_LONG).show();
                		alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
                		return;	
                	}
                	else
                	{
                    	session.createLoginSession("PMISPRO",username);
                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), ProjectListActivity.class);
                        startActivity(i);
                        finish();
                	}    
                                 
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
                }
                 
            }
        });
    }        
}