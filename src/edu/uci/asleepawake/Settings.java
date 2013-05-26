package edu.uci.asleepawake;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
    Button submit;
//    public ArrayList<MyEntry<Date, Date>> dates = new ArrayList<MyEntry<Date,Date>>();

    
	@Override
    protected Dialog onCreateDialog(int id) {
				
        c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        
        int chour = c.get(Calendar.HOUR_OF_DAY);
        int cminute = c.get(Calendar.MINUTE);

        
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,  mDateSetListener,  cyear, cmonth, cday);
        case TIME_DIALOG_ID:
        	return new TimePickerDialog(this, mTimeSetListener, chour, cminute, false);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        @SuppressLint("SimpleDateFormat")
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //String date_selected = String.valueOf(monthOfYear+1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
            //Toast.makeText(getApplicationContext(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
            //startDate.setText(date_selected);
            //Save date to local var to be input into sharedprefs
        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//    		DateFormat dateFormat = DateFormat.getDateInstance(3);
        	c.set(Calendar.YEAR,year);
        	c.set(Calendar.MONTH,monthOfYear);
        	c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
    		startDate.setText(dateFormat.format(c.getTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        // onTimeSet method
        public void onTimeSet(TimePicker view, int hour, int minute) {
//        	String ampm = "AM";
//        	int adjustedHour = hour;
//        	if(hour >= 12){
//        		ampm = "PM";
//        		if(hour > 12)
//        			adjustedHour = hour-12;
//        	}
//        	String stringAdjustedHour = String.valueOf(adjustedHour);
//        	stringAdjustedHour = "0"+stringAdjustedHour;
//        	String adjustedMinute = String.valueOf(minute);
//        	if(minute < 10)
//        		adjustedMinute = "0"+adjustedMinute;
//        	
//            String time_selected = stringAdjustedHour+":"+adjustedMinute+" "+ampm;
//            System.out.println(time_selected);
            //Toast.makeText(getApplicationContext(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
            //time.setText(time_selected);
        	
        	DateFormat timeFormat = DateFormat.getTimeInstance(3);
        	c.set(Calendar.HOUR_OF_DAY,hour);
        	c.set(Calendar.MINUTE,minute);
        	time.setText(timeFormat.format(c.getTime()));
//        	tempTime = c.getTime();
        }
    };

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
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
		
		spinner = (Spinner) findViewById(R.id.Participant_Type);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.participant_type, android.R.layout.simple_dropdown_item_1line);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
        startDate=(EditText)findViewById(R.id.startDate);
     /* For startDate EditText*/
             startDate.setOnTouchListener(new OnTouchListener(){ 
                 public boolean onTouch(View v, MotionEvent event) { 
                     if(v == startDate)
                         showDialog(DATE_DIALOG_ID);
                     return false;              
                 }
             });
             
     timeFields = getViewsByTag((ViewGroup)findViewById(android.R.id.content),"time");
     
     for(final EditText timeField : timeFields) {
    	 System.out.println(timeField);
    	 
    	 timeField.setOnTouchListener(new OnTouchListener(){
    		 public boolean onTouch(View v, MotionEvent event) {
    			 if(v == timeField){
    				 time = timeField;    				 
    				 showDialog(TIME_DIALOG_ID);
    			 }
    			 return false;
    		 }
    	 });
     }
     
     submit = (Button)findViewById(R.id.savePrefs);
     submit.setOnClickListener(this);
     loadPrefs();
     
	}
	
	private void loadPrefs() {
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
        
        participantVar.setText(participant);
        if(type.equals("School")){
        	spinner.setSelection(0);
        } else {
        	spinner.setSelection(1);
        }
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
	
    private void savePrefs(String key, String value) {
	        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
	        Editor edit = sp.edit();
	        edit.putString(key, value);
	        edit.commit();
	    }
    
//    private void savePrefs(String key, Date date) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//        Editor edit = sp.edit();
//        edit.putLong(key, date.getTime());
//        edit.commit();    	
//    }
	
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
