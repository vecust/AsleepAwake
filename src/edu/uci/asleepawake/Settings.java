package edu.uci.asleepawake;

//This class reads in the settings and saves them to the system sharedprefs

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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
//import java.util.Date;

public class Settings extends Activity implements OnClickListener{
	private Calendar c;
	private EditText startDate;
//	private Date tempTime;
	private EditText participantVar;
	private Spinner spinner;
	private EditText time;
	ArrayList<EditText> timeFields;
	private EditText day1WakeTime;
	private EditText day2WakeTime;
	private EditText day3WakeTime;
	private EditText day4WakeTime;
	private EditText day5WakeTime;
	private EditText day6WakeTime;
	private EditText day7WakeTime;
	private EditText day1SleepTime;
	private EditText day2SleepTime;
	private EditText day3SleepTime;
	private EditText day4SleepTime;
	private EditText day5SleepTime;
	private EditText day6SleepTime;
	private EditText day7SleepTime;
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    static final int SLEEP_TIME_DIALOG_ID = 2;
    static final int WAKE_TIME_DIALOG_ID = 3;
    Button submit;
	Date alarmDate = null;

//    TimePickerDialog.OnTimeSetListener mSleepTimeSetListener;
//    TimePickerDialog.OnTimeSetListener mWakeTimeSetListener;
    
//    public ArrayList<MyEntry<Date, Date>> dates = new ArrayList<MyEntry<Date,Date>>();

    
	@Override
    protected Dialog onCreateDialog(int id) {
				
    	//Get today's date info
        c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        int chour = c.get(Calendar.HOUR_OF_DAY);
        int cminute = c.get(Calendar.MINUTE);
        
        //create dialogs (pickers) for time and date
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,  mDateSetListener,  cyear, cmonth, cday);
//        case TIME_DIALOG_ID:
//        	return new TimePickerDialog(this, mTimeSetListener, chour, cminute, false);
        case SLEEP_TIME_DIALOG_ID:
        	return new TimePickerDialog(this, mSleepTimeSetListener, 22, 0, false);
        case WAKE_TIME_DIALOG_ID:
        	return new TimePickerDialog(this, mWakeTimeSetListener, 7, 0, false);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        @SuppressLint("SimpleDateFormat")
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //Save date to local var to be input into sharedprefs
        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//    		DateFormat dateFormat = DateFormat.getDateInstance(3);
        	c.set(Calendar.YEAR,year);
        	c.set(Calendar.MONTH,monthOfYear);
        	c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
    		startDate.setText(dateFormat.format(c.getTime()));
        }
    };

//    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        // onTimeSet method
//        public void onTimeSet(TimePicker view, int hour, int minute) {
//        	
//        	DateFormat timeFormat = DateFormat.getTimeInstance(3);
//        	c.set(Calendar.HOUR_OF_DAY,hour);
//        	c.set(Calendar.MINUTE,minute);
//        	time.setText(timeFormat.format(c.getTime()));
////        	tempTime = c.getTime();
//        }
//    },

    private TimePickerDialog.OnTimeSetListener mSleepTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//    mSleepTimeSetListener = new OnTimeSetListener() {
        // onTimeSet method
        public void onTimeSet(TimePicker view, int hour, int minute) {
        	        	
        	final DateFormat timeFormat = DateFormat.getTimeInstance(3);
        	c.set(Calendar.HOUR_OF_DAY,hour);
        	c.set(Calendar.MINUTE,minute);
        	time.setText(timeFormat.format(c.getTime()));
//        	tempTime = c.getTime();
        	
			AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
			builder.setMessage("Set same time for all days?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           	day1SleepTime.setText(timeFormat.format(c.getTime()));
			        	day2SleepTime.setText(timeFormat.format(c.getTime()));
			        	day3SleepTime.setText(timeFormat.format(c.getTime()));
			        	day4SleepTime.setText(timeFormat.format(c.getTime()));
			        	day5SleepTime.setText(timeFormat.format(c.getTime()));
			        	day6SleepTime.setText(timeFormat.format(c.getTime()));
			        	day7SleepTime.setText(timeFormat.format(c.getTime()));
			        	   
			        	   dialog.cancel();
			           }   
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
			    	   
			       });    
			     
			AlertDialog alert = builder.create();
			alert.show();
        	
        }
    };
    private TimePickerDialog.OnTimeSetListener mWakeTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//    mWakeTimeSetListener = new OnTimeSetListener() {
        // onTimeSet method
        public void onTimeSet(TimePicker view, int hour, int minute) {
        	
        	final DateFormat timeFormat = DateFormat.getTimeInstance(3);
        	c.set(Calendar.HOUR_OF_DAY,hour);
        	c.set(Calendar.MINUTE,minute);
        	time.setText(timeFormat.format(c.getTime()));
//        	tempTime = c.getTime();
        	
			AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
			builder.setMessage("Set same time for all days?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           	day1WakeTime.setText(timeFormat.format(c.getTime()));
			        	day2WakeTime.setText(timeFormat.format(c.getTime()));
			        	day3WakeTime.setText(timeFormat.format(c.getTime()));
			        	day4WakeTime.setText(timeFormat.format(c.getTime()));
			        	day5WakeTime.setText(timeFormat.format(c.getTime()));
			        	day6WakeTime.setText(timeFormat.format(c.getTime()));
			        	day7WakeTime.setText(timeFormat.format(c.getTime()));
			        	   
			        	   dialog.cancel();
			           }   
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
			    	   
			       });    
			     
			AlertDialog alert = builder.create();
			alert.show();

        }
    };
    
    public void cancelAlarm(int intentID, String receiver){
    	Intent intent;
    	if(receiver.equals("Sleep")){
    		intent = new Intent(Settings.this, SleepAlarmReceiverActivity.class);
    	} else if (receiver.equals("Wake")){
    		intent = new Intent(Settings.this, WakeAlarmReceiverActivity.class);
    	} else {
    		intent = new Intent(Settings.this, SurveyAlarmReceiverActivity.class);
    	}
        PendingIntent pendingIntent = PendingIntent.getActivity(Settings.this,
            intentID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = 
            (AlarmManager)Settings.this.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		for(int i = 1;i<=7;i++){
			cancelAlarm(i,"Survey");
			cancelAlarm(i+10,"Wake");
		}
		cancelAlarm(12345,"Sleep");
		
		//Assign ids of views in layout to local objects
    	day1WakeTime = (EditText)findViewById(R.id.Day1Wake);
    	day2WakeTime = (EditText)findViewById(R.id.Day2Wake);
    	day3WakeTime = (EditText)findViewById(R.id.Day3Wake);
    	day4WakeTime = (EditText)findViewById(R.id.Day4Wake);
    	day5WakeTime = (EditText)findViewById(R.id.Day5Wake);
    	day6WakeTime = (EditText)findViewById(R.id.Day6Wake);
    	day7WakeTime = (EditText)findViewById(R.id.Day7Wake);
    	day1SleepTime = (EditText)findViewById(R.id.Day1Sleep);
    	day2SleepTime = (EditText)findViewById(R.id.Day2Sleep);
    	day3SleepTime = (EditText)findViewById(R.id.Day3Sleep);
    	day4SleepTime = (EditText)findViewById(R.id.Day4Sleep);
    	day5SleepTime = (EditText)findViewById(R.id.Day5Sleep);
    	day6SleepTime = (EditText)findViewById(R.id.Day6Sleep);
    	day7SleepTime = (EditText)findViewById(R.id.Day7Sleep);


		
		participantVar = (EditText)findViewById(R.id.Participant);
		
		//This spinner (dropdown) sets the participant type (school or general)
		spinner = (Spinner) findViewById(R.id.Participant_Type);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.participant_type, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setPrompt("Participant Type:");
		
        startDate=(EditText)findViewById(R.id.startDate);
     /* For startDate EditText*/
             startDate.setOnTouchListener(new OnTouchListener(){ 
                 public boolean onTouch(View v, MotionEvent event) { 
                     if(v == startDate)
                         showDialog(DATE_DIALOG_ID);
                     return false;              
                 }
             });

     //Get all views for time and add them to the timeFields arrayList        
     timeFields = getViewsByTag((ViewGroup)findViewById(android.R.id.content),"time");
     
     for(final EditText timeField : timeFields) {
    	 System.out.println(timeField);
    	 
    	 timeField.setOnTouchListener(new OnTouchListener(){
    		 public boolean onTouch(View v, MotionEvent event) {
    			 if(timeField == day1WakeTime 
    				|| timeField == day2WakeTime	 
    				|| timeField == day3WakeTime	 
    				|| timeField == day4WakeTime	 
    				|| timeField == day5WakeTime	 
    				|| timeField == day6WakeTime	 
    				|| timeField == day7WakeTime){
    				 time = timeField;    				 
    				 showDialog(WAKE_TIME_DIALOG_ID);

    			 } else if(timeField == day1SleepTime 
    				|| timeField == day2SleepTime	 
    				|| timeField == day3SleepTime	 
    				|| timeField == day4SleepTime	 
    				|| timeField == day5SleepTime	 
    				|| timeField == day6SleepTime	 
    				|| timeField == day7SleepTime){
    				 time = timeField;    				 
    				 showDialog(SLEEP_TIME_DIALOG_ID);
    				 
    			 } else
    			 if(v == timeField){
//    				 time = timeField;    				 
//    				 showDialog(TIME_DIALOG_ID);
    			 }
    			 return false;
    		 }
    	 });
     }
     
     //Assign id of submit button and set listener
     submit = (Button)findViewById(R.id.savePrefs);
     submit.setOnClickListener(this);
     
     //Call this function to get all the saved sharedPreferences
     loadPrefs();
     
	}
	
	private void loadPrefs() {
		//Create sharedpreferences object and get all the preferences then
		//assign them to local variables
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String participant = sp.getString("Participant", "");
        String type = sp.getString("Type", "");
        String start = sp.getString("Start", "");
        String gotDay1WakeTime = sp.getString("Day1Wake", "");
        String gotDay2WakeTime = sp.getString("Day2Wake", "");
        String gotDay3WakeTime = sp.getString("Day3Wake", "");
        String gotDay4WakeTime = sp.getString("Day4Wake", "");
        String gotDay5WakeTime = sp.getString("Day5Wake", "");
        String gotDay6WakeTime = sp.getString("Day6Wake", "");
        String gotDay7WakeTime = sp.getString("Day7Wake", "");
        String gotDay1SleepTime = sp.getString("Day1Sleep", "");
        String gotDay2SleepTime = sp.getString("Day2Sleep", "");
        String gotDay3SleepTime = sp.getString("Day3Sleep", "");
        String gotDay4SleepTime = sp.getString("Day4Sleep", "");
        String gotDay5SleepTime = sp.getString("Day5Sleep", "");
        String gotDay6SleepTime = sp.getString("Day6Sleep", "");
        String gotDay7SleepTime = sp.getString("Day7Sleep", "");
        
        //If the type is set to school or general,
        //show it on the view
        participantVar.setText(participant);
        if(type.equals("School")){
        	spinner.setSelection(0);
        } else {
        	spinner.setSelection(1);
        }
        
        //Show the rest of the settings onto the view layout
        startDate.setText(start);
        
    	day1WakeTime.setText(gotDay1WakeTime);
    	day2WakeTime.setText(gotDay2WakeTime);
    	day3WakeTime.setText(gotDay3WakeTime);
    	day4WakeTime.setText(gotDay4WakeTime);
    	day5WakeTime.setText(gotDay5WakeTime);
    	day6WakeTime.setText(gotDay6WakeTime);
    	day7WakeTime.setText(gotDay7WakeTime);
    	day1SleepTime.setText(gotDay1SleepTime);
    	day2SleepTime.setText(gotDay2SleepTime);
    	day3SleepTime.setText(gotDay3SleepTime);
    	day4SleepTime.setText(gotDay4SleepTime);
    	day5SleepTime.setText(gotDay5SleepTime);
    	day6SleepTime.setText(gotDay6SleepTime);
    	day7SleepTime.setText(gotDay7SleepTime);


	}

	//This method saves the preferences
    private void savePrefs(String key, String value) {
	        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
	        Editor edit = sp.edit();
	        edit.putString(key, value);
	        edit.commit();
	    }
	
    //This method helps get all the views by tag
	 private static ArrayList<EditText> getViewsByTag(ViewGroup root, String tag){
		    ArrayList<EditText> views = new ArrayList<EditText>();
		    final int childCount = root.getChildCount();
		    for (int i = 0; i < childCount; i++) {
		        final View child = root.getChildAt(i);
		        if (child instanceof ViewGroup) {
		            views.addAll(getViewsByTag((ViewGroup) child, tag));
		        } 

		        final Object tagObj = child.getTag();
		        if (tagObj != null && tagObj.equals(tag)) {
		            views.add((EditText)child);
		        }

		    }
		    return views;
		}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(participantVar.getText().toString().equals("")
				|| spinner.getSelectedItem().toString().equals("")	
				|| startDate.getText().toString().equals("")	
				|| day1WakeTime.getText().toString().equals("")	
				|| day2WakeTime.getText().toString().equals("")	
				|| day3WakeTime.getText().toString().equals("")	
				|| day4WakeTime.getText().toString().equals("")	
				|| day5WakeTime.getText().toString().equals("")	
				|| day6WakeTime.getText().toString().equals("")	
				|| day7WakeTime.getText().toString().equals("")	
				|| day1SleepTime.getText().toString().equals("")	
				|| day2SleepTime.getText().toString().equals("")	
				|| day3SleepTime.getText().toString().equals("")	
				|| day4SleepTime.getText().toString().equals("")	
				|| day5SleepTime.getText().toString().equals("")	
				|| day6SleepTime.getText().toString().equals("")	
				|| day7SleepTime.getText().toString().equals("")	
				) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
			builder.setMessage("Please fill all settings before submitting")
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			        	   dialog.cancel();
			           }
			       });    
			     
			AlertDialog alert = builder.create();
			alert.show();
			
		//This is the listener for the submit button
		//The savePrefs() method is used to save all the preferences
		//that were input by the user
		} else {
			Calendar sleepTime1 = getAlarmTime(startDate.getText().toString(), 1, day1SleepTime.getText().toString(), 0);
//			System.out.println("Sleep Alarm time: "+sleepTime1.getTime().toString());
			createAlarm(sleepTime1, 01,"sleep");

			Calendar sleepTime2 = getAlarmTime(startDate.getText().toString(), 1, day2SleepTime.getText().toString(), 1);
			createAlarm(sleepTime2, 02,"sleep");
			Calendar sleepTime3 = getAlarmTime(startDate.getText().toString(), 1, day3SleepTime.getText().toString(), 2);
			createAlarm(sleepTime3, 03,"sleep");
			Calendar sleepTime4 = getAlarmTime(startDate.getText().toString(), 1, day4SleepTime.getText().toString(), 3);
			createAlarm(sleepTime4, 04,"sleep");
			Calendar sleepTime5 = getAlarmTime(startDate.getText().toString(), 1, day5SleepTime.getText().toString(), 4);
			createAlarm(sleepTime5, 05,"sleep");
			Calendar sleepTime6 = getAlarmTime(startDate.getText().toString(), 1, day6SleepTime.getText().toString(), 5);
			createAlarm(sleepTime6, 06,"sleep");
			Calendar sleepTime7 = getAlarmTime(startDate.getText().toString(), 1, day7SleepTime.getText().toString(), 6);
			createAlarm(sleepTime7, 07,"sleep");
			
			Calendar wakeTime1 = getAlarmTime(startDate.getText().toString(), 0, day1WakeTime.getText().toString(), 0);
//			System.out.println("Wake Alarm time: "+wakeTime1.getTime().toString());
			createAlarm(wakeTime1, 11,"wake");

			Calendar wakeTime2 = getAlarmTime(startDate.getText().toString(), 0, day2WakeTime.getText().toString(), 1);
			createAlarm(wakeTime2, 12,"wake");
			Calendar wakeTime3 = getAlarmTime(startDate.getText().toString(), 0, day3WakeTime.getText().toString(), 2);
			createAlarm(wakeTime3, 13,"wake");
			Calendar wakeTime4 = getAlarmTime(startDate.getText().toString(), 0, day4WakeTime.getText().toString(), 3);
			createAlarm(wakeTime4, 14,"wake");
			Calendar wakeTime5 = getAlarmTime(startDate.getText().toString(), 0, day5WakeTime.getText().toString(), 4);
			createAlarm(wakeTime5, 15,"wake");
			Calendar wakeTime6 = getAlarmTime(startDate.getText().toString(), 0, day6WakeTime.getText().toString(), 5);
			createAlarm(wakeTime6, 16,"wake");
			Calendar wakeTime7 = getAlarmTime(startDate.getText().toString(), 0, day7WakeTime.getText().toString(), 6);
			createAlarm(wakeTime7, 17,"wake");
			
		savePrefs("Participant",participantVar.getText().toString());
		savePrefs("Type",spinner.getSelectedItem().toString());
		savePrefs("Start",startDate.getText().toString());
		savePrefs("Day1Wake",day1WakeTime.getText().toString());
		savePrefs("Day2Wake",day2WakeTime.getText().toString());
		savePrefs("Day3Wake",day3WakeTime.getText().toString());
		savePrefs("Day4Wake",day4WakeTime.getText().toString());
		savePrefs("Day5Wake",day5WakeTime.getText().toString());
		savePrefs("Day6Wake",day6WakeTime.getText().toString());
		savePrefs("Day7Wake",day7WakeTime.getText().toString());
		savePrefs("Day1Sleep",day1SleepTime.getText().toString());
		savePrefs("Day2Sleep",day2SleepTime.getText().toString());
		savePrefs("Day3Sleep",day3SleepTime.getText().toString());
		savePrefs("Day4Sleep",day4SleepTime.getText().toString());
		savePrefs("Day5Sleep",day5SleepTime.getText().toString());
		savePrefs("Day6Sleep",day6SleepTime.getText().toString());
		savePrefs("Day7Sleep",day7SleepTime.getText().toString());
		
		finish();
		}
	}
	
	public Calendar getAlarmTime(String mFirstDay, int sleepWake, String mSettingTime, int day){
		Date alarmTime = null;
		Calendar cal = Calendar.getInstance();
        DateFormat timeFormat = DateFormat.getTimeInstance(3);
        DateFormat dateFormat = DateFormat.getDateInstance(3);
        String settingTime = mSettingTime;
        String firstDay = mFirstDay;
//        System.out.println("firstDay: "+firstDay);
        System.out.println("settingTime: "+settingTime);

			try {
				alarmTime = timeFormat.parse(settingTime);
				alarmDate = dateFormat.parse(firstDay);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			alarmTime.setDate(alarmDate.getDate());
			alarmTime.setMonth(alarmDate.getMonth());
			alarmTime.setYear(alarmDate.getYear());
			System.out.println("Alarm time (DATE): "+alarmTime.toString());
			
			cal.setTime(alarmTime);
			
			long milliseconds = cal.getTimeInMillis();
			System.out.println("milliseconds: "+milliseconds);
			
			if(sleepWake == 1){
				milliseconds -= 900000;
				if(cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) < 15){
					milliseconds += 86400000;
				}
				if(((cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) > 15) || cal.get(Calendar.HOUR_OF_DAY) > 0) && cal.get(Calendar.HOUR_OF_DAY) <= 12){
					milliseconds += 86400000;					
				} /*!!!Put this back after testing!!!*/
				
			}
			else if(sleepWake == 0){
				milliseconds += 86400000; /*!!!Put this back after testing!!!*/
			}
			if(day > 0){
				milliseconds += (86400000*day);
			}
			
			cal.setTimeInMillis(milliseconds);
			System.out.println("Alarm time: "+cal.getTime().toString());

			return cal;

	}
	
	public void createAlarm(Calendar cal, int id, String sleepOrWake){
        //Create a new PendingIntent and add it to the AlarmManager
		Intent intent;
		Calendar today = Calendar.getInstance();
		
		if((sleepOrWake.equals("sleep") && cal.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR))
				|| (sleepOrWake.equals("wake") && (cal.before(today) || cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)))){
			System.out.println("Can't set alarm for "+cal.getTime().toString()+" because it is before today");
		}else{/*!!!Put this back after testing!!!*/
		if(sleepOrWake.equals("sleep")){
			intent = new Intent(Settings.this, SurveyAlarmReceiverActivity.class);
		} else {
	        intent = new Intent(Settings.this, WakeAlarmReceiverActivity.class);
		}
        intent.putExtra("intentID", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
            id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager)this.getSystemService(ALARM_SERVICE);
	        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
	                pendingIntent);	
		}/*!!!Put this back after testing!!!*/
	}
	
//	public boolean isValidAlarm(Calendar cal, Date start, String sleepOrWake){
//		Calendar secondDay;
//		Calendar startCal = Calendar.getInstance();
//		startCal.setTime(start);
//
//		if((sleepOrWake.equals("sleep") && (cal.after(startCal) || cal.get(Calendar.DAY_OF_MONTH) == startCal.get(Calendar.DAY_OF_MONTH))) ||
//				(sleepOrWake.equals("wake") && )	){
//		return true;
//		} else {
//			return false;
//		}
//	}


}
