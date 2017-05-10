package com.pmis.pmislite.login;
import java.util.ArrayList;
import java.util.HashMap;

import com.pmis.pmislite.R;
import com.pmis.pmislite.data.PmisNewProjectData;
import com.pmis.pmislite.data.PmisNewTeamData;
import com.pmis.pmislite.messages.MessagesActivity;
import com.pmis.pmislite.projects.NewProjectActivity;
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
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
  
public class LoginDialogManager {
    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     *               - pass null if you don't want icon
     * */
    public void showAlertDialog(Context context, String title, String message,
            Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
  
        // Setting Dialog Title
        alertDialog.setTitle(title);
  
        // Setting Dialog Message
        alertDialog.setMessage(message);
  
  //      if(status != null)
            // Setting alert dialog icon
  //          alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
  
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
  
        // Showing Alert Message
        alertDialog.show();
    }
}