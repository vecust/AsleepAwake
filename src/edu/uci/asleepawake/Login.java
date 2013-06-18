package edu.uci.asleepawake;

//This class handles the login for access to the settings
//Current password is "password"

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//Assign the login button and set listener
		final Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Assign text field for password
				EditText editText = (EditText) findViewById(R.id.loginText);
				final String password = editText.getText().toString();
				//System.out.print(password);
				
				//Check if password entered equals "password"
				//If so, open the settings view
				//Otherwise issue and alert asking for the 
				//correct password
				if(password.equals("password")) {
					Intent settings = new Intent(Login.this,Settings.class);
					Login.this.startActivity(settings);
					finish();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
					builder.setMessage("Please enter correct password")
					       .setCancelable(false)
					       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                //do things
					        	   dialog.cancel();
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
