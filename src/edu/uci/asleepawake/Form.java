package edu.uci.asleepawake;

//This class reads in the manual entry of the sleep log
//and sends the entry to the Google Form via the HttpRequest class

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class Form extends Activity implements OnClickListener {

	private Calendar c;
	private EditText date;
//	private Date tempTime;
	private EditText sleepTime;
	private EditText time;
	ArrayList<EditText> timeFields;
	private EditText wakeTime;
	private CheckBox wearingWatch;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    Button submit;
	
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
        case TIME_DIALOG_ID:
        	return new TimePickerDialog(this, mTimeSetListener, chour, cminute, false);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        @SuppressLint("SimpleDateFormat")
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //Save date to local var
        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//    		DateFormat dateFormat = DateFormat.getDateInstance(3);
        	c.set(Calendar.YEAR,year);
        	c.set(Calendar.MONTH,monthOfYear);
        	c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
    		date.setText(dateFormat.format(c.getTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        // onTimeSet method
        public void onTimeSet(TimePicker view, int hour, int minute) {
        	//Save time to local var
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
		setContentView(R.layout.activity_form);
		
		//Assign ids of views in layout to local objects
		date = (EditText)findViewById(R.id.formDate);
    	sleepTime = (EditText)findViewById(R.id.formSleep);
    	wakeTime = (EditText)findViewById(R.id.formWake);
    	wearingWatch = (CheckBox)findViewById(R.id.formWatch);
    	wearingWatch.setChecked(false);

        date.setOnTouchListener(new OnTouchListener(){ 
            public boolean onTouch(View v, MotionEvent event) { 
                if(v == date)
                    showDialog(DATE_DIALOG_ID);
                return false;              
            }
        });
        
   	 sleepTime.setOnTouchListener(new OnTouchListener(){
		 public boolean onTouch(View v, MotionEvent event) {
			 if(v == sleepTime){
				 time = sleepTime;
				 showDialog(TIME_DIALOG_ID);
			 }
			 return false;
		 }
	 });
   	 
   	 wakeTime.setOnTouchListener(new OnTouchListener(){
		 public boolean onTouch(View v, MotionEvent event) {
			 if(v == wakeTime){
				 time = wakeTime;
				 showDialog(TIME_DIALOG_ID);
			 }
			 return false;
		 }
	 });
   	 
     //Assign id of submit button and set listener
   	 submit = (Button)findViewById(R.id.FormSubmitButton);
     submit.setOnClickListener(this);
   	 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_form, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		//Instructions for getting Google Form URL and entry code can be found here:
		//http://www.youtube.com/watch?v=GyuJ2GtpZd0
		
		//Create connection to Google Form
		String fullUrl = "https://docs.google.com/a/uci.edu/forms/d/1x-YIb5tAnkImWDLaw0YtNIyqa0AXCroq26ogf_2yS9o/formResponse";
		HttpRequest mReq = new HttpRequest();
		
		//Get participant number from sharedPrefs
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String participant = sp.getString("Participant", "");
        
        //If the participant number is blank, it is assumed that
        //the rest of the settings are not set yet.
        //An alert is issued.
		if (participant.equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(Form.this);
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
		
		//If the fields on the form are empty, an alert is issued
		//to tell the user to fill out the form before submitting
		} else if (date.getText().toString().equals("") || sleepTime.getText().toString().equals("") || wakeTime.getText().toString().equals("")) {
			AlertDialog.Builder formBuilder = new AlertDialog.Builder(Form.this);
			formBuilder.setMessage("Please fill out form before submitting")
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			        	   dialog.cancel();
			           }
			       });
			AlertDialog alert = formBuilder.create();
			alert.show();
			
		//Once all the fields have been filled, grab the watch checkbox status
		//and send all the manual entry data to the Google form
		} else {
			String watch = "";
			if(wearingWatch.isChecked()) {
				watch = "YES";
			} else {
				watch = "NO";
			}
			
			String data = "entry_1794600332=" + URLEncoder.encode(participant) + "&"
					+"entry_2034720707=" + URLEncoder.encode(date.getText().toString()) + "&"
					+ "entry_2032879505=" + URLEncoder.encode(sleepTime.getText().toString()) + "&"
					+ "entry_1085709803=" + URLEncoder.encode(wakeTime.getText().toString()) + "&"
					+ "entry_2052787681=" + URLEncoder.encode(watch);
			String response = mReq.sendPost(fullUrl, data);
			System.out.println("postData response: " + response);
			
			savePrefs("logIgnored","");
			savePrefs("sleepLogged","");
			savePrefs("wakeLogged","");
			
			finish();
	}
	}
	
    private void savePrefs(String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

}
