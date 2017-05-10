package com.pmis.pmislite.messages;
import java.util.ArrayList;
import java.util.HashMap;

import com.pmis.pmislite.R;
import com.pmis.pmislite.projects.ProjectListActivity;
import com.pmis.pmislite.selected.PmisSelectedProjectAccordianActivity;
import com.pmis.pmislite.teams.TeamListActivity;
 
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
 
public class MessagesActivity extends Activity {
     
 
    final Context context=this;
    
    Button projectsButton;
    Button teamsButton;
    Button blogsButton;
    
    Button inboxButton;
    Button sentMessagesButton;
    Button sendPeerMessageButton;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_view);
         
        projectsButton=(Button) findViewById(R.id.newProjectButton);
        teamsButton=(Button) findViewById(R.id.teamsButton);
        blogsButton=(Button) findViewById(R.id.blogsButton);
  
        inboxButton=(Button) findViewById(R.id.messages_inbox_Button);
        sentMessagesButton=(Button) findViewById(R.id.sent_messages_Button);      
        sendPeerMessageButton=(Button) findViewById(R.id.send_peer_message_Button); 
    }  

    View.OnClickListener sendPeerMessageButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //projectButton.setBackgroundResource(R.drawable.ic_launcher);
        	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mtasks.jsf"));
            
        	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
            //startActivity(myWebLink);
   //     	Intent intent=new Intent(context,SendPeerMessageActivity.class);
            //intent.addCategory(selectedProject);
		    //intent.putExtra("selectedproject",selectedProject);
    //    	startActivity(intent);

        }
    };
    
    View.OnClickListener sentMessagesButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //projectButton.setBackgroundResource(R.drawable.ic_launcher);
        	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mtasks.jsf"));
            
        	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
            //startActivity(myWebLink);
     //   	Intent intent=new Intent(context,SentMessagesActivity.class);
            //intent.addCategory(selectedProject);
		    //intent.putExtra("selectedproject",selectedProject);
      //  	startActivity(intent);

        }
    };


    View.OnClickListener inboxButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //projectButton.setBackgroundResource(R.drawable.ic_launcher);
        	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mtasks.jsf"));
            
        	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
            //startActivity(myWebLink);
       // 	Intent intent=new Intent(context,MessageInboxActivity.class);
            //intent.addCategory(selectedProject);
		    //intent.putExtra("selectedproject",selectedProject);
       // 	startActivity(intent);

        }
    };


    View.OnClickListener blogsButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //projectButton.setBackgroundResource(R.drawable.ic_launcher);
        	//Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            //myWebLink.setData(Uri.parse("http://192.168.1.2:17070/pmisapp/mobile/mtasks.jsf"));
            
        	//myWebLink.setData(Uri.parse("http://www.pmisoffice.com"));
            //startActivity(myWebLink);
        //	Intent intent=new Intent(context,BlogListActivity.class);
            //intent.addCategory(selectedProject);
		    //intent.putExtra("selectedproject",selectedProject);
        //	startActivity(intent);

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

}