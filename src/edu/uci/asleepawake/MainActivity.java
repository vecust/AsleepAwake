package edu.uci.asleepawake;

//This class controls the main screen to set the buttons for logging, taking surveys,
//accessing the settings, and the alert reminders for the app

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    String logIgnored;
    String timeStamp;
    String sleepTime;
    String wearingWatch;
    String type;
    String sleepIgnored;
    String wakeIgnored;
    String sleepLogged;
    String wakeLogged;
    String sleepinessSurveyIgnored;
    String relationshipSurveyIgnored;
    String ampm;
    SharedPreferences sp;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Assign ids of buttons in layout to local objects
//		final Button settingsButton = (Button) findViewById(R.id.MainSettings);
		final Button goingToBedButton = (Button) findViewById(R.id.MainGoingToBed);
		final Button wokeUpButton = (Button) findViewById(R.id.MainWokeUp);
		final Button manualEntryButton = (Button) findViewById(R.id.manualForm);
		final Button takeSurveysButton = (Button) findViewById(R.id.TakeSurveys);

		//Get shared preferences for use later
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        sleepLogged = sp.getString("sleepLogged", "");
        wakeLogged = sp.getString("wakeLogged", "");
        sleepinessSurveyIgnored = sp.getString("sleepinessSurveyIgnored","");
        relationshipSurveyIgnored = sp.getString("relationshipSurveyIgnored","");
        type = sp.getString("Type", "");
        String surveyTaken = sp.getString("SurveysTaken", "");
        
        //Set "clickability" of goingToBedButton and wokeUpButton
		if(sleepLogged.equals("YES")) {
			goingToBedButton.setEnabled(false);
			wokeUpButton.setEnabled(true);
//	        if(surveyTaken.equals("YES")){
//	        	goingToBedButton.performClick();
//	        }
		} else {
			wokeUpButton.setEnabled(false);
		}
		
        String participant = sp.getString("Participant", "");

        //If participant number is blank, it is assumed that
        //the rest of the settings are not set yet.
        //An alert is issued.
		if (participant.equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setMessage("Please enter settings data first")
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			        	   Intent loginIntent = new Intent(MainActivity.this,Login.class);
			        	   MainActivity.this.startActivity(loginIntent);
			        	   dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
        
        //Set all the listeners for the buttons on the screen
        manualEntryButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					savePrefs("ManualEntry", "Manual");
	        	   Intent formPage = new Intent(MainActivity.this,Form.class);
	        	   MainActivity.this.startActivity(formPage);
				
			}
        	
        });
		
		goingToBedButton.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logIgnored = "NO";
				//System.out.println("logIgnored: "+logIgnored);
				Calendar temp = Calendar.getInstance();
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				timeStamp = dateFormat.format(temp.getTime());
				DateFormat timeFormat = DateFormat.getTimeInstance(3);
				sleepTime = timeFormat.format(temp.getTime());
	        	//System.out.println("timeStamp: "+timeStamp);
	        	//System.out.println("sleepTime: "+sleepTime);
	        	//savePrefs("logIgnored",logIgnored);
	        	savePrefs("timeStamp",timeStamp);
	        	savePrefs("sleepTime",sleepTime);
	        	savePrefs("sleepLogged","YES");
	        	savePrefs("wakeLogged","NO");
				savePrefs("ManualEntry", "Manual");
				savePrefs("SurveysTaken", "");

		    	Calendar c = Calendar.getInstance();
		        int cday = c.get(Calendar.DAY_OF_MONTH);
		        DateFormat dateCancelFormat = DateFormat.getDateInstance(3);
		        String firstDay = sp.getString("Start", "");
		        Date formDate = null;

					try {
						formDate = dateCancelFormat.parse(firstDay);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Calendar settingDay = Calendar.getInstance();
					settingDay.setTime(formDate);
					int studyDay = -1;
					for(int i = 0;i<7;i++){
						if(cday == settingDay.get(Calendar.DAY_OF_MONTH)+i){
							studyDay = i+1;
						}
					}
					if(studyDay != -1){
				        Intent intent = new Intent(MainActivity.this, SurveyAlarmReceiverActivity.class);
				        PendingIntent pendingSleepIntent = PendingIntent.getActivity(MainActivity.this,
				            studyDay, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				        AlarmManager am = 
				            (AlarmManager)MainActivity.this.getSystemService(ALARM_SERVICE);
				        am.cancel(pendingSleepIntent);
				        System.out.println("Sleep Alarm Cancelled - Intent:"+studyDay);
				        
					}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Are You Wearing the Watch?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                //do things
				        	   wearingWatch = "YES";
				        	   savePrefs("wearingWatch",wearingWatch);
				        	   dialog.cancel();
				           }
				       })    
				           
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				    	   public void onClick(DialogInterface dialog, int id)	{
				    		   wearingWatch = "NO";
				        	   savePrefs("wearingWatch",wearingWatch);
				    		   dialog.cancel();
				       }
				       });
				AlertDialog alert = builder.create();
				alert.show();
				
	        	goingToBedButton.setEnabled(false);
	        	wokeUpButton.setEnabled(true);
			}
		});
		
		wokeUpButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar temp = Calendar.getInstance();
				DateFormat timeFormat = DateFormat.getTimeInstance(3);
				String wakeTime = timeFormat.format(temp.getTime());
	        	//System.out.println("wakeTime: "+wakeTime);
	        	savePrefs("wakeTime",wakeTime);
	        	savePrefs("wakeLogged","YES");
				savePrefs("ManualEntry", "Manual");
	        	
	        	postData();
			}
			
		});
        
		takeSurveysButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Flag as manual entry
				savePrefs("ManualSurveys", "Manual");
				savePrefs("ManualEntry", "Manual");
				Intent testRelationship = new Intent(MainActivity.this,Relationship.class);
				MainActivity.this.startActivity(testRelationship);

			}
		});
	

//		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//		System.out.println("SDK Version: "+currentapiVersion);
	}
	
	//This method creates the connection with the Google Form and sends the 
	//manual entry log data to the spreadsheet
	
	//Instructions for getting Google Form URL and entry code can be found here:
	//http://www.youtube.com/watch?v=GyuJ2GtpZd0
	
	public void postData() {
		//Get buttons so that "clickability" can be set
		final Button goingToBedButton = (Button) findViewById(R.id.MainGoingToBed);
		final Button wokeUpButton = (Button) findViewById(R.id.MainWokeUp);
		
		//Make connection to Google Form
		String fullUrl = "https://docs.google.com/a/uci.edu/forms/d/1x-YIb5tAnkImWDLaw0YtNIyqa0AXCroq26ogf_2yS9o/formResponse";
		HttpRequest mReq = new HttpRequest();
		
		//Get preferences
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String participant = sp.getString("Participant", "");
		String date = sp.getString("timeStamp", "");
		String sleep = sp.getString("sleepTime", "");
		String wake = sp.getString("wakeTime", "");
		String watch = sp.getString("wearingWatch", "");
		String entryType = sp.getString("ManualEntry", "");
		
		//If participant number is blank, it is assumed that
        //the rest of the settings are not set yet.
        //An alert is issued.
		if (participant.equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setMessage("Please enter settings data first")
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			        	   dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		} else {

			String data = "entry_1794600332=" + URLEncoder.encode(participant) + "&"
					+"entry_2034720707=" + URLEncoder.encode(date) + "&"
					+ "entry_2032879505=" + URLEncoder.encode(sleep) + "&"
					+ "entry_1085709803=" + URLEncoder.encode(wake) + "&"
					+ "entry_2052787681=" + URLEncoder.encode(watch) + "&"
					+ "entry_12534346=" + URLEncoder.encode(entryType);
			String response = mReq.sendPost(fullUrl, data);
			System.out.println("postData response: " + response);

			timeStamp = null;
			
			goingToBedButton.setEnabled(true);
			wokeUpButton.setEnabled(false);
			logIgnored = "";
			savePrefs("logIgnored","");
			savePrefs("sleepLogged","NO");
			savePrefs("wakeLogged","NO");
			savePrefs("sleepIgnored","NO");
			savePrefs("wakeIgnored","NO");
		}
	}
  
	//This method saves the preferences
    private void savePrefs(String key, String value) {
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}	

	//This method is called when this activity view is brought back to the screen
	//It will force onCreate() to get called again
	@Override
	protected void onRestart() {
		Bundle tempBundle = new Bundle();
		onCreate(tempBundle);
	}
	
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        
        
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            System.out.println("Entered to change as Portrait ");
            setContentView(R.layout.activity_main);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//        	System.out.println("Entered to change as LandScape ");
            setContentView(R.layout.activity_main);
        }
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("SETTINGS pressed");
		Intent settingLogin = new Intent(MainActivity.this,Login.class);
		MainActivity.this.startActivity(settingLogin);
		return super.onOptionsItemSelected(item);
	}

}
