package edu.uci.asleepawake;

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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
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
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button settingsButton = (Button) findViewById(R.id.MainSettings);
		final Button goingToBedButton = (Button) findViewById(R.id.MainGoingToBed);
		final Button wokeUpButton = (Button) findViewById(R.id.MainWokeUp);
		final Button manualEntryButton = (Button) findViewById(R.id.manualForm);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sleepLogged = sp.getString("sleepLogged", "");
        wakeLogged = sp.getString("wakeLogged", "");
        sleepinessSurveyIgnored = sp.getString("sleepinessSurveyIgnored","");
        relationshipSurveyIgnored = sp.getString("relationshipSurveyIgnored","");
        type = sp.getString("Type", "");
        
		if(sleepLogged.equals("YES")) {
			goingToBedButton.setEnabled(false);
			wokeUpButton.setEnabled(true);
		} else {
			wokeUpButton.setEnabled(false);
		}

//		Calendar test = Calendar.getInstance();
//		System.out.println("Test time:"+test.getTime());
//
//		DateFormat testDateFormat = DateFormat.getDateInstance(3);
//		System.out.println("Test Date format:"+testDateFormat.format(test.getTime()));		
//		
//		DateFormat testFormat = DateFormat.getTimeInstance(3);
//		System.out.println("Test Time format:"+testFormat.format(test.getTime()));
		
		//loadPrefs();
		
        dates = new ArrayList<MyEntry<String,String>>();
		
        manualEntryButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	        	   Intent formPage = new Intent(MainActivity.this,Form.class);
	        	   MainActivity.this.startActivity(formPage);
				
			}
        	
        });
        
		settingsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent settingLogin = new Intent(MainActivity.this,Login.class);
				MainActivity.this.startActivity(settingLogin);
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
        
		final Button testSettingsButton = (Button)findViewById(R.id.Submit);
		testSettingsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//loadPrefs();
	
					//postData();
					
				Intent testRelationship = new Intent(MainActivity.this,Relationship.class);
				MainActivity.this.startActivity(testRelationship);

			}
		});
		
        Calendar today = Calendar.getInstance();
		//SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		String thisHour = String.valueOf(today.get(Calendar.HOUR));
		ampm = String.valueOf(today.get(Calendar.AM_PM));
		System.out.println("ampm: "+ampm);
		System.out.println("sleepinessSurveyIgnored: "+sleepinessSurveyIgnored);
		
		//bring up survey alerts
		//check if sleepiness survey is ignored
		System.out.println("thisHour: "+thisHour);
		if(!thisHour.equals("4")){
			//System.out.println("It's not time for the sleepiness survey");
			savePrefs("sleepinessSurveyIgnored","");
		}
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
		
			sleepLogReminder();

	}
	

	
	public void postData() {
		final Button goingToBedButton = (Button) findViewById(R.id.MainGoingToBed);
		final Button wokeUpButton = (Button) findViewById(R.id.MainWokeUp);
		String fullUrl = "https://docs.google.com/a/uci.edu/forms/d/1x-YIb5tAnkImWDLaw0YtNIyqa0AXCroq26ogf_2yS9o/formResponse";
		HttpRequest mReq = new HttpRequest();
		
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String participant = sp.getString("Participant", "");
		String date = sp.getString("timeStamp", "");
		String sleep = sp.getString("sleepTime", "");
		String wake = sp.getString("wakeTime", "");
		String watch = sp.getString("wearingWatch", "");
		
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
	
	@SuppressLint("SimpleDateFormat")
	private void loadPrefs() {
		final Button goingToBedButton = (Button) findViewById(R.id.MainGoingToBed);
		final Button wokeUpButton = (Button) findViewById(R.id.MainWokeUp);
		
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String isLogIgnored = sp.getString("logIgnored", "");
        String lastTimeStamp = sp.getString("timeStamp", "");
        Date timeStampDate = null;

        Date today = Calendar.getInstance().getTime();
//		String thisDay = String.valueOf(today.get(Calendar.DAY_OF_YEAR));
		
        SimpleDateFormat dayFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
			timeStampDate = dayFormat.parse(lastTimeStamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(lastTimeStamp != "" && today.after(timeStampDate)){
        	if(isLogIgnored.equals("YES")){
        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setMessage("Log your sleep now")
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
							Intent formPage = new Intent(MainActivity.this,Form.class);
							MainActivity.this.startActivity(formPage);
			        	   dialog.cancel();
			           }
			       });    
			     
			AlertDialog alert = builder.create();
			alert.show();
        	}
        	else if(isLogIgnored.equals("NO")){
        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setMessage("Log your wake time now")
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			        	   goingToBedButton.setEnabled(false);
			        	   wokeUpButton.setEnabled(true);
			        	   dialog.cancel();
			           }
			       });    
			     
			AlertDialog alert = builder.create();
			alert.show();
        }
        }
	}
	
	@SuppressLint("SimpleDateFormat")
	private void getDates() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Date date = null;
        String start = sp.getString("Start", "");
        
        if(start.equals("")){
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
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
			date = dateFormat.parse(start);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(int i=0;i<7;i++){
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(date);
        	calendar.add(Calendar.DAY_OF_YEAR, i);
        	System.out.println("Date in array: "+calendar.getTime());
//        	Date newDate = calendar.getTime();
        	String newDate = dateFormat.format(calendar.getTime());
        	MyEntry<String, String> temp = new MyEntry<String, String>("Day"+(i+1),newDate);
        	System.out.println("Checking Date: "+newDate);
        	dates.add(temp);
        }
        
	}
	
	@SuppressLint("SimpleDateFormat")
	private void sleepLogReminder() {
		final Button goingToBedButton = (Button) findViewById(R.id.MainGoingToBed);
		final Button wokeUpButton = (Button) findViewById(R.id.MainWokeUp);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		getDates();
//		int count = dates.size();
		String nowTime = "";
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
	        
			//if today is in dates[i]	        
			if(tempSleep != null && tempWake != null && dates.get(i).getValue().toString().contains(todaysDate)){
		        System.out.println("todaysDate: "+todaysDate);
				System.out.println("Found Date: "+todaysDate);
//				String prefHour = tempSleep.substring(0,1);

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
				if(!thisHour.equals(prefSleepHour) && !ampm.equals("1"))
					savePrefs("relationshipSurveyIgnored","NO");
				//if this hour is sleepTime of dates[i] || this hour is after sleepTime of dates[i] && !sleeplogged show sleep reminder
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
					                //do things

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

					
					//if wakeignored || sleepignored show form
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
					} else {
			        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
						builder.setMessage("Don't Forget to Log Your Wake Time")
						       .setCancelable(false)
						       .setPositiveButton("Log Wake Time", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                //do things

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
	}
    
    private void savePrefs(String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }
    
//    private void savePrefs(String key, Date value) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//        Editor edit = sp.edit();
//        edit.putString(key, String.valueOf(value));
//        edit.commit();
//    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void goingToSleep(Menu menu) {
		//Do something
		//Intent intent = new Intent(this,DisplayMessageActivity.class);
	}
	
//	public void sendMessage(View view) {
//		//Do something
//		Intent intent = new Intent(this,DisplayMessageActivity.class);
//		EditText editText = (EditText) findViewById(R.id.editText1);
//		String wentToSleep = editText.getText().toString();
//		intent.putExtra(EXTRA_MESSAGE, wentToSleep);
//		startActivity(intent);
//	}
	
	@Override
	protected void onRestart() {
		Bundle tempBundle = new Bundle();
		onCreate(tempBundle);
	}

}
