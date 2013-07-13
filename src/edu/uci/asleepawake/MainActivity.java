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
import android.app.AlertDialog;
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
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public ArrayList<MyEntry<String, String>> dates = new ArrayList<MyEntry<String,String>>();
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
		final Button relationshipButton = (Button)findViewById(R.id.RelationshipSurveyButton);
		final Button howDoYouFeelButton = (Button)findViewById(R.id.HowDoYouFeelButton);
		final Button sleepinessButton = (Button)findViewById(R.id.SleepinessSurveyButton);

		//Get shared preferences for use later
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        sleepLogged = sp.getString("sleepLogged", "");
        wakeLogged = sp.getString("wakeLogged", "");
        sleepinessSurveyIgnored = sp.getString("sleepinessSurveyIgnored","");
        relationshipSurveyIgnored = sp.getString("relationshipSurveyIgnored","");
        type = sp.getString("Type", "");
        
        //Set "clickability" of goingToBedButton and wokeUpButton
		if(sleepLogged.equals("YES")) {
			goingToBedButton.setEnabled(false);
			wokeUpButton.setEnabled(true);
		} else {
			wokeUpButton.setEnabled(false);
		}
		
		//Instantiate dates arrayList
        dates = new ArrayList<MyEntry<String,String>>();
		
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
	        	   Intent formPage = new Intent(MainActivity.this,Form.class);
	        	   MainActivity.this.startActivity(formPage);
				
			}
        	
        });
        
//		settingsButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent settingLogin = new Intent(MainActivity.this,Login.class);
//				MainActivity.this.startActivity(settingLogin);
//			}
//		});
		
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
	        	
	        	postData();
			}
			
		});
        
		relationshipButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub					
				Intent testRelationship = new Intent(MainActivity.this,Relationship.class);
				MainActivity.this.startActivity(testRelationship);

			}
		});


		howDoYouFeelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				savePrefs("howDoYouFeelButtonPressed","YES");
				Intent feelRightNowIntent = new Intent(MainActivity.this,FeelRightNow.class);
				MainActivity.this.startActivity(feelRightNowIntent);

			}
		});		

		sleepinessButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
	        	   if(type.equals("School")){
	        	   Intent surveyPage = new Intent(MainActivity.this,Sleepiness.class);
	        	   MainActivity.this.startActivity(surveyPage);	
	        	   } else if(type.equals("General")){
		        	   Intent surveyPage = new Intent(MainActivity.this,SleepinessGeneral.class);
		        	   MainActivity.this.startActivity(surveyPage);				        		   
	        	   }

			}
		});		
		
		//Get today's hour info
		Calendar today = Calendar.getInstance();
		//SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		String thisHour = String.valueOf(today.get(Calendar.HOUR));
		ampm = String.valueOf(today.get(Calendar.AM_PM));
		System.out.println("ampm: "+ampm);
		System.out.println("sleepinessSurveyIgnored: "+sleepinessSurveyIgnored);
		
		
		//bring up sleepiness survey alert
		//check if sleepiness survey is ignored
		System.out.println("thisHour: "+thisHour);
		//If it is not 4:00, save blank info for "sleepinessSurveyIgnored" in sharedPrefs
		if(!thisHour.equals("4")){
			//System.out.println("It's not time for the sleepiness survey");
			savePrefs("sleepinessSurveyIgnored","");
		}
		//If it is 4:00 PM and there is blank info for "sleepinessSurveyIgnored" in sharedPrefs
		//Issue alert to prompt user to take the sleepiness survey
		//Choosing the "Take Survey" button will open the survey
		//Dismissing the alert with set "YES" for "sleepinessSurveyIgnored" in sharedPrefs
		if(thisHour.equals("4") && ampm.equals("1") && sleepinessSurveyIgnored.equals("")) {
			System.out.println("Met Condition - thisHour: "+thisHour);					

        	AlertDialog.Builder sleepSurveySchoolAlert = new AlertDialog.Builder(MainActivity.this);
        	sleepSurveySchoolAlert.setMessage("Take Sleepiness Survey")
			       .setCancelable(false)
			       .setPositiveButton("Take Survey", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   //check if the participant type is general or school
			        	   if(type.equals("School")){
			        	   Intent surveyPage = new Intent(MainActivity.this,Sleepiness.class);
			        	   MainActivity.this.startActivity(surveyPage);	
			        	   } else if(type.equals("General")){
				        	   Intent surveyPage = new Intent(MainActivity.this,SleepinessGeneral.class);
				        	   MainActivity.this.startActivity(surveyPage);				        		   
			        	   }
			        	   dialog.cancel();
			           }
			       })
			       .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
			        	   savePrefs("sleepinessSurveyIgnored","YES");
			        	   dialog.cancel();
					}
			    	   
			       });
				  
			     
			AlertDialog alert = sleepSurveySchoolAlert.create();
			alert.show();					
		}
		
		//Call this method to compare current date and time to info in s
		//then issue alerts to prompt the user to log sleep/waking or take surveys
		//sleepLogReminder();

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		System.out.println("SDK Version: "+currentapiVersion);
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
					+ "entry_2052787681=" + URLEncoder.encode(watch);
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
	
	//This method gets the dates that will be logged based on the start date
	//set in the settings and add them to the "dates" arrayList
	@SuppressLint("SimpleDateFormat")
	private void getDates() {
		//Get start date
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Date date = null;
        String start = sp.getString("Start", "");
        
        //If the date preference is blank, it is assumed that
        //the rest of the settings are not set yet.
        //An alert is issued.        
//        if(start.equals("")){
//        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//			builder.setMessage("Missing Start Date in settings")
//			       .setCancelable(false)
//			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int id) {
//			                //Go to login screen for settings
//							Intent settingLogin = new Intent(MainActivity.this,Login.class);
//							MainActivity.this.startActivity(settingLogin);
//			        	   dialog.cancel();
//			           }
//			       });    
//			     
//			AlertDialog alert = builder.create();
//			alert.show();
//			return;
//        }
		if (!start.equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			try {
				date = dateFormat.parse(start);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Add dates to arraylist
			for (int i = 0; i < 7; i++) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_YEAR, i);
				System.out.println("Date in array: " + calendar.getTime());
				// Date newDate = calendar.getTime();
				String newDate = dateFormat.format(calendar.getTime());
				MyEntry<String, String> temp = new MyEntry<String, String>(
						"Day" + (i + 1), newDate);
				System.out.println("Checking Date: " + newDate);
				dates.add(temp);
			}
		}
        
	}
	
	//This method handles all the logic to control the logging and survey alerts
	//so that the user is prompted based on the dates and times set in the settings
	@SuppressLint("SimpleDateFormat")
	private void sleepLogReminder() {
		//Get buttons so that "clickability" can be set
		final Button goingToBedButton = (Button) findViewById(R.id.MainGoingToBed);
		final Button wokeUpButton = (Button) findViewById(R.id.MainWokeUp);
		
		//Create object with sharedPreferences data
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        //Call this method to put study dates into the "dates" arraylist
        getDates();

		String nowTime = "";
        //If the dates arraylist is blank, it is assumed that
        //the rest of the settings are not set yet.
        //An alert is issued.
		if(dates.size() == 0){
	       	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Please add settings")
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                //do things
								Intent settingLogin = new Intent(MainActivity.this,Login.class);
								MainActivity.this.startActivity(settingLogin);
				        	   dialog.cancel();
				           }
				       });    
				     
				AlertDialog alert = builder.create();
				alert.show();
				return;
		}
		for(int i=0;i<7;i++){
			//for each day, get the sleep and wake time preference
	        String sleepVar = "Day"+(i+1)+"Sleep";
	        String wakeVar = "Day"+(i+1)+"Wake";
	        //System.out.println("dateVar: "+sleepVar);
	        String tempSleep = sp.getString(sleepVar, "");
	        String tempWake = sp.getString(wakeVar, "");
	        sleepLogged = sp.getString("sleepLogged", "");
	        wakeLogged = sp.getString("wakeLogged", "");
	        sleepIgnored = sp.getString("sleepIgnored", "");
	        wakeIgnored = sp.getString("wakeIgnored", "");
	        Date nowTimeDate = null;
	        Date prefSleepDate = null;
	        Date prefWakeDate = null;
	        
	        System.out.println("Getting Sleep from Prefs: "+tempSleep);
	        System.out.println("Getting Wake from Prefs: "+tempWake);
			
			//System.out.println("Getting DAY from array: "+dates.get(i).getKey());
			//System.out.println("Getting DATE from array: "+dates.get(i).getValue().toString());
	        
	        //Get today's date information
	        Calendar today = Calendar.getInstance();
	        DateFormat timeFormat = DateFormat.getTimeInstance(3);
	        nowTime = timeFormat.format(today.getTime());
	        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	        String todaysDate = dateFormat.format(today.getTime());
	        
	        try {
				 nowTimeDate = timeFormat.parse(nowTime);
				 prefSleepDate = timeFormat.parse(tempSleep);
				 prefWakeDate = timeFormat.parse(tempWake);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        //System.out.println("todaysDate: "+todaysDate);
	        //System.out.println("dates array["+i+"]: "+dates.get(i).getValue().toString());
	        
			//Check if today is in dates arraylist	        
			if(tempSleep != null && tempWake != null && dates.get(i).getValue().toString().contains(todaysDate)){
		        System.out.println("todaysDate: "+todaysDate);
				System.out.println("Found Date: "+todaysDate);
//				String prefHour = tempSleep.substring(0,1);

				//Get current hour
				SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
				String thisHour = String.valueOf(today.get(Calendar.HOUR));
				
				if(thisHour.equals("0"))
					thisHour = "12";
				Date tempSleepHour = null;
				Date tempWakeHour = null;
				
				try {
					tempSleepHour = hourFormat.parse(tempSleep);
					tempWakeHour = hourFormat.parse(tempWake);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String prefSleepHour = String.valueOf(tempSleepHour.getHours());
				String prefWakeHour = String.valueOf(tempWakeHour.getHours());

				ampm = String.valueOf(today.get(Calendar.AM_PM));
				
				//If thisHour is not the preferences sleep hour for today,
				//save blank info for "relationshipSurveyIgnored"
				//and "sleepLogged" in sharedPrefs
				if(!thisHour.equals(prefSleepHour) && !ampm.equals("1")) {
					savePrefs("relationshipSurveyIgnored","");
					savePrefs("sleepLogged","");
				}

				//If thisHour is not the preferences wake hour for today,
				//save blank info for "wakeIgnored"
				//and "wakeLogged" in sharedPrefs
				if(!thisHour.equals(prefWakeHour) && !ampm.equals("0")) {
					savePrefs("wakeIgnored","");
					savePrefs("wakeLogged","");
				}

				//If this hour is sleepTime of dates[i]
				//|| this hour is after sleepTime of dates[i]
				//&& !sleeplogged show sleep reminder
				if (((prefSleepHour.equals(thisHour)
						&& (nowTime.endsWith("AM") && tempSleep.endsWith("AM")) || (nowTime
						.endsWith("PM") && tempSleep.endsWith("PM"))) || nowTimeDate
						.after(prefSleepDate))
						&& (sleepLogged.equals("NO") || sleepLogged.equals(""))) {
					
					System.out.println("Matching sleep data:");
					System.out.println("prefSleepHour: "+prefSleepHour);
					System.out.println("thisHour: "+thisHour);					
					System.out.println("nowTime: "+nowTime);
					System.out.println("tempSleep: "+tempSleep);
					System.out.println("nowTimeDate: "+nowTimeDate);
					System.out.println("prefSleepDate: "+prefSleepDate);
					System.out.println("relationshipSurveyIgnored: "+relationshipSurveyIgnored);
					
					if(relationshipSurveyIgnored.equals("")){
					AlertDialog.Builder relationshipAlert = new AlertDialog.Builder(MainActivity.this);
					relationshipAlert.setMessage("Please take the relationship survey")
					       .setCancelable(false)
					       .setPositiveButton("Take Survey", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                //do things
					        	   Intent reltationshipIntent = new Intent(MainActivity.this,Relationship.class);
					        	   MainActivity.this.startActivity(reltationshipIntent);
					        	   dialog.cancel();
					           }
					       })
					       .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
					        	   savePrefs("relationshipSurveyIgnored","YES");
					        	   dialog.cancel();
							}
					    	   
					       });
						  
					     
					AlertDialog alert = relationshipAlert.create();
					alert.show();
					}
					
		        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("Don't Forget to Log Your Sleep")
					       .setCancelable(false)
					       .setPositiveButton("Log Sleep", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                //Call method to click the goingToBedButton
					        	   //This will log the sleep
					        	   goingToBedButton.performClick();
					        	   dialog.cancel();
					           }
					       })
					       .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
					        	   sleepIgnored = "YES";
					        	   System.out.println("sleepIgnored: "+sleepIgnored);
					        	   savePrefs("sleepIgnored",sleepIgnored);
					        	   dialog.cancel();
							}
					    	   
					       });
						  
					     
					AlertDialog alert = builder.create();
					alert.show();

					
				//if this hour is wakeTime of dates[i] || this hour is after wakeTime of dates[i]  && !wakelogged show wake reminder
				} else if (((prefWakeHour.equals(thisHour)
						&& (nowTime.endsWith("AM") && tempSleep.endsWith("AM")) || (nowTime
						.endsWith("PM") && tempSleep.endsWith("PM"))) || nowTimeDate
						.after(prefWakeDate))
						&& (wakeLogged.equals("NO") || wakeLogged.equals(""))) {

					System.out.println("Matching wake data:");
					System.out.println("prefWakeHour: "+prefWakeHour);
					System.out.println("thisHour: "+thisHour);					
					System.out.println("nowTime: "+nowTime);
					System.out.println("tempWake: "+tempWake);
					System.out.println("nowTimeDate: "+nowTimeDate);
					System.out.println("prefWakeDate: "+prefWakeDate);

					
					//if wakeignored || sleepignored take user directly to manual entry log form
					if(wakeIgnored.equals("YES") || sleepIgnored.equals("YES")) {
			        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
						builder.setMessage("Log sleep and wake time now")
						       .setCancelable(false)
						       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                //do things

						        	   Intent formPage = new Intent(MainActivity.this,Form.class);
						        	   MainActivity.this.startActivity(formPage);
						        	   dialog.cancel();
						           }
						       });
					//Otherwise issue alert prompting the user to log wake time	
					}
					
			        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
						builder.setMessage("Don't Forget to Log Your Wake Time")
						       .setCancelable(false)
						       .setPositiveButton("Log Wake Time", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                //Call method to click the wokeUpButton
						        	   //This will log the wake time
						        	   wokeUpButton.performClick();
						        	   dialog.cancel();
						           }
						       })
						       .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
						        	   wakeIgnored = "YES";
						        	   System.out.println("wakeIgnored: "+wakeIgnored);
						        	   savePrefs("wakeIgnored",wakeIgnored);
						        	   dialog.cancel();
								}
						    	   
						       });
							  
						     
						AlertDialog alert = builder.create();
						alert.show();						
				}
			}

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
            System.out.println("Entered to change as Portrait ");
            setContentView(R.layout.activity_main);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	System.out.println("Entered to change as LandScape ");
            setContentView(R.layout.activity_main);
        }
	}
	
	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//	    if ( keyCode == KeyEvent.KEYCODE_MENU ) {
//	    	System.out.println("MENU pressed");
//	        return true;
//	    }
//	    return super.onKeyDown(keyCode, event);
//	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("SETTINGS pressed");
		Intent settingLogin = new Intent(MainActivity.this,Login.class);
		MainActivity.this.startActivity(settingLogin);
		return super.onOptionsItemSelected(item);
	}

}
