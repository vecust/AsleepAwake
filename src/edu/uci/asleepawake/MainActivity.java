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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button settingsButton = (Button) findViewById(R.id.MainSettings);
		final Button goingToBedButton = (Button) findViewById(R.id.MainGoingToBed);
		final Button wokeUpButton = (Button) findViewById(R.id.MainWokeUp);

		wokeUpButton.setEnabled(false);

//		Calendar test = Calendar.getInstance();
//		System.out.println("Test time:"+test.getTime());
//
//		DateFormat testDateFormat = DateFormat.getDateInstance(3);
//		System.out.println("Test Date format:"+testDateFormat.format(test.getTime()));		
//		
//		DateFormat testFormat = DateFormat.getTimeInstance(3);
//		System.out.println("Test Time format:"+testFormat.format(test.getTime()));
		
		loadPrefs();
		
        dates = new ArrayList<MyEntry<String,String>>();
		
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
				System.out.println("logIgnored: "+logIgnored);
				Calendar temp = Calendar.getInstance();
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				timeStamp = dateFormat.format(temp.getTime());
				DateFormat timeFormat = DateFormat.getTimeInstance(3);
				sleepTime = timeFormat.format(temp.getTime());
	        	System.out.println("timeStamp: "+timeStamp);
	        	System.out.println("sleepTime: "+sleepTime);
	        	savePrefs("logIgnored",logIgnored);
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
	        	//wokeUpButton.setEnabled(true);
			}
		});
		
		wokeUpButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar temp = Calendar.getInstance();
				DateFormat timeFormat = DateFormat.getTimeInstance(3);
				String wakeTime = timeFormat.format(temp.getTime());
	        	System.out.println("wakeTime: "+wakeTime);
	        	savePrefs("wakeTime",wakeTime);
	        	savePrefs("wakeLogged","YES");
	        	
	        	postData();
			}
			
		});
        
		final Button testSettingsButton = (Button)findViewById(R.id.formSubmit);
		testSettingsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//loadPrefs();
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
					postData();

					}
					});
					t.start();
			}
		});
		
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
			savePrefs("sleepLogged","");
			savePrefs("wakeLogged","");
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
				        	   dialog.cancel();
				           }
				       });    
				     
				AlertDialog alert = builder.create();
				alert.show();
				return;
		}
		for(int i=0;i<7;i++){
	        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
	        String sleepVar = "Day"+(i+1)+"Sleep";
	        System.out.println("dateVar: "+sleepVar);
	        String tempSleep = sp.getString(sleepVar, "");
	        System.out.println("Getting Sleep from Prefs: "+tempSleep);
			
			System.out.println("Getting DAY from array: "+dates.get(i).getKey());
			System.out.println("Getting DATE from array: "+dates.get(i).getValue().toString());
	        Calendar today = Calendar.getInstance();
	        DateFormat timeFormat = DateFormat.getTimeInstance(3);
	        nowTime = timeFormat.format(today.getTime());
	        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	        String todaysDate = dateFormat.format(today.getTime());
	        
	        System.out.println("todaysDate: "+todaysDate);
	        System.out.println("dates array["+i+"]: "+dates.get(i).getValue().toString());
	        
			if(tempSleep != null && dates.get(i).getValue().toString().contains(todaysDate)){
				System.out.println("Found Date: "+todaysDate);
//				String prefHour = tempSleep.substring(0,1);

				SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
				String thisHour = String.valueOf(today.get(Calendar.HOUR));
				if(thisHour.equals("0"))
					thisHour = "12";
				Date tempHour = null;
				try {
					tempHour = hourFormat.parse(tempSleep);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String prefHour = String.valueOf(tempHour.getHours());
				System.out.println("prefHour: "+prefHour);
				System.out.println("thisHour: "+thisHour);
				
				System.out.println("nowTime: "+nowTime);
				System.out.println("tempSleep: "+tempSleep);
				if(prefHour.equals(thisHour) && (nowTime.endsWith("AM") && tempSleep.endsWith("AM")) || (nowTime.endsWith("PM") && tempSleep.endsWith("PM"))){
		        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("Don't Forget to Log Your Sleep")
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                //do things
					        	   logIgnored = "YES";
					        	   System.out.println("logIgnored: "+logIgnored);
									Calendar temp = Calendar.getInstance();
						        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									timeStamp = dateFormat.format(temp.getTime());
					        	   System.out.println("timeStamp: "+timeStamp);
					        	   savePrefs("logIgnored",logIgnored);
					        	   savePrefs("timeStamp",timeStamp);
					        	   dialog.cancel();
					           }
					       });    
					     
					AlertDialog alert = builder.create();
					alert.show();

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
