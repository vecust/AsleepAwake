package edu.uci.asleepawake;

//This class reads in the answers to the sleepiness survey (school version)
//and sends them to the Google Form via the HttpRequest class

import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;
import android.os.AsyncTask;

public class Sleepiness extends Activity implements OnClickListener{

    Button submit;
    private Spinner morningClasses;
    private Spinner schoolDay;
    private Spinner lastClass;
    private Spinner car;
    private Spinner awakeWholeDay;
    private Spinner afternoonClasses;
    private Spinner alertAllClasses;
    private Spinner asleepMorning;
    private Spinner awakeInClass;
    private Spinner awakeLastClass;
    private Spinner busCarTrain;
    private Spinner realized;
    String data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sleepiness);

		//Assign ids of spinners (dropdown menus) on layout to local Spinner objects
		morningClasses = (Spinner) findViewById(R.id.MorningClasses);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.survey_spinner, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		// Apply the adapter to the spinner
		morningClasses.setAdapter(adapter);
		morningClasses.setPrompt("I fell asleep during my morning classes");
		
		schoolDay = (Spinner) findViewById(R.id.SchoolDay);
		schoolDay.setAdapter(adapter);
		schoolDay.setPrompt("I got through the whole school day without feeling tired");
		
		 lastClass = (Spinner) findViewById(R.id.LastClass);
		lastClass.setAdapter(adapter);
		lastClass.setPrompt("I fell asleep during the last class of the day");
		
		 car = (Spinner) findViewById(R.id.Car);
		car.setAdapter(adapter);
		car.setPrompt("I felt drowsy when I rode in a car for longer than 5 minutes");
		
		 awakeWholeDay = (Spinner) findViewById(R.id.AwakeWholeDay);
		awakeWholeDay.setAdapter(adapter);
		awakeWholeDay.setPrompt("I felt wide-awake the whole day");
		
		 afternoonClasses = (Spinner) findViewById(R.id.AfternoonClasses);
		afternoonClasses.setAdapter(adapter);
		afternoonClasses.setPrompt("I fell asleep at school in my afternoon classes");
		
		 alertAllClasses = (Spinner) findViewById(R.id.AlertAllClasses);
		alertAllClasses.setAdapter(adapter);
		alertAllClasses.setPrompt("I felt alert during my classes");
		
		 asleepMorning = (Spinner) findViewById(R.id.AsleepMorning);
		asleepMorning.setAdapter(adapter);
		asleepMorning.setPrompt("In the morning when I was in school, I fell asleep");
		
		 awakeInClass = (Spinner) findViewById(R.id.AwakeInClass);
		awakeInClass.setAdapter(adapter);
		awakeInClass.setPrompt("When I was in class, I felt wide-awake");
		
		 awakeLastClass = (Spinner) findViewById(R.id.AwakeLastClass);
		awakeLastClass.setAdapter(adapter);
		awakeLastClass.setPrompt("I felt wide-awake the last class of the day");
		
		 busCarTrain = (Spinner) findViewById(R.id.BusCarTrain);
		busCarTrain.setAdapter(adapter);
		busCarTrain.setPrompt("I fell alseep when I rode in a bus, car, or train");
		
		 realized = (Spinner) findViewById(R.id.Realized);
		realized.setAdapter(adapter);
		realized.setPrompt("During the school day, there were times when I realized that I had just fallen asleep");
		
		//Assign id to submit button and set listener
	     submit = (Button)findViewById(R.id.SleepinessSchoolButton);
	     submit.setOnClickListener(this);
	}

	public void onClick(View arg0) {

		//This is the listener for the submit button
		//If at least one of the questions haven't been answered,
		//display an alert telling the user to answer all the questions

		if (morningClasses.getSelectedItem().toString().equals("")
				|| schoolDay.getSelectedItem().toString().equals("")
				|| lastClass.getSelectedItem().toString().equals("")
				|| car.getSelectedItem().toString().equals("")
				|| awakeWholeDay.getSelectedItem().toString().equals("")
				|| afternoonClasses.getSelectedItem().toString().equals("")
				|| alertAllClasses.getSelectedItem().toString().equals("")
				|| asleepMorning.getSelectedItem().toString().equals("")
				|| awakeInClass.getSelectedItem().toString().equals("")
				|| awakeLastClass.getSelectedItem().toString().equals("")
				|| busCarTrain.getSelectedItem().toString().equals("")
				|| realized.getSelectedItem().toString().equals(""))		{

			TableRow morningClassesRow = (TableRow) findViewById(R.id.MorningClassesRow);
			TableRow schoolDayRow = (TableRow) findViewById(R.id.SchoolDayRow);
			TableRow lastClassRow = (TableRow) findViewById(R.id.LastClassRow);
			TableRow carRow = (TableRow) findViewById(R.id.CarRow);
			TableRow awakeWholeDayRow = (TableRow) findViewById(R.id.AwakeWholeDayRow);
			TableRow afternoonClassesRow = (TableRow) findViewById(R.id.AfternoonClassesRow);
			TableRow alertAllClassesRow = (TableRow) findViewById(R.id.AlertAllClassesRow);
			TableRow asleepMorningRow = (TableRow) findViewById(R.id.AsleepMorningRow);			
			TableRow awakeInClassRow = (TableRow) findViewById(R.id.AwakeInClassRow);			
			TableRow awakeLastClassRow = (TableRow) findViewById(R.id.AwakeLastClassRow);			
			TableRow busCarTrainRow = (TableRow) findViewById(R.id.BusCarTrainRow);			
			TableRow realizedRow = (TableRow) findViewById(R.id.RealizedRow);			

			if(morningClasses.getSelectedItem().toString().equals("")){
				
				morningClassesRow.setBackgroundColor(Color.YELLOW);
			} else {
				morningClassesRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(schoolDay.getSelectedItem().toString().equals("")){
				
				schoolDayRow.setBackgroundColor(Color.YELLOW);
			} else {
				schoolDayRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(lastClass.getSelectedItem().toString().equals("")){
				
				lastClassRow.setBackgroundColor(Color.YELLOW);
			} else {
				lastClassRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(car.getSelectedItem().toString().equals("")){
				
				carRow.setBackgroundColor(Color.YELLOW);
			} else {
				carRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(awakeWholeDay.getSelectedItem().toString().equals("")){
				
				awakeWholeDayRow.setBackgroundColor(Color.YELLOW);
			} else {
				awakeWholeDayRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(afternoonClasses.getSelectedItem().toString().equals("")){
				
				afternoonClassesRow.setBackgroundColor(Color.YELLOW);
			} else {
				afternoonClassesRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(alertAllClasses.getSelectedItem().toString().equals("")){
				
				alertAllClassesRow.setBackgroundColor(Color.YELLOW);
			} else {
				alertAllClassesRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(asleepMorning.getSelectedItem().toString().equals("")){
				
				asleepMorningRow.setBackgroundColor(Color.YELLOW);
			} else {
				asleepMorningRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(awakeInClass.getSelectedItem().toString().equals("")){
				
				awakeInClassRow.setBackgroundColor(Color.YELLOW);
			} else {
				awakeInClassRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(awakeLastClass.getSelectedItem().toString().equals("")){
				
				awakeLastClassRow.setBackgroundColor(Color.YELLOW);
			} else {
				awakeLastClassRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(busCarTrain.getSelectedItem().toString().equals("")){
				
				busCarTrainRow.setBackgroundColor(Color.YELLOW);
			} else {
				busCarTrainRow.setBackgroundColor(Color.WHITE);			
			}
			
			if(realized.getSelectedItem().toString().equals("")){
				
				realizedRow.setBackgroundColor(Color.YELLOW);
			} else {
				realizedRow.setBackgroundColor(Color.WHITE);			
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(Sleepiness.this);
				builder.setTitle("Please answer all questions before submitting")
				   	   .setMessage("Unanswered questions are highlighted")
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                //do things
				        	   dialog.cancel();
				           }
				       });    
				     
				AlertDialog alert = builder.create();
				alert.show();
				
				//With all the questions answered, make a connection to the Google Form and 
				//send the answers to the spreadsheet
				
				//Instructions for getting Google Form URL and entry code can be found here:
				//http://www.youtube.com/watch?v=GyuJ2GtpZd0
				
		} else {
			String fullUrl = "https://docs.google.com/a/uci.edu/forms/d/1x-YIb5tAnkImWDLaw0YtNIyqa0AXCroq26ogf_2yS9o/formResponse";
			HttpRequest mReq = new HttpRequest();

	        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
	        String participant = sp.getString("Participant", "");
	        String entryType = sp.getString("ManualSurveys", "");
			
			data = "entry_1794600332=" + URLEncoder.encode(participant) + "&"
					+ "entry_877114785=" + URLEncoder.encode(morningClasses.getSelectedItem().toString()) + "&"
					+ "entry_2107663340=" + URLEncoder.encode(schoolDay.getSelectedItem().toString()) + "&"
					+ "entry_193970558=" + URLEncoder.encode(lastClass.getSelectedItem().toString()) + "&"
					+ "entry_1236987037=" + URLEncoder.encode(car.getSelectedItem().toString()) + "&"
					+ "entry_1480764914=" + URLEncoder.encode(awakeWholeDay.getSelectedItem().toString()) + "&"
					+ "entry_1633852414=" + URLEncoder.encode(afternoonClasses.getSelectedItem().toString()) + "&"
					+ "entry_1377396200=" + URLEncoder.encode(alertAllClasses.getSelectedItem().toString()) + "&"
					+ "entry_1322731368=" + URLEncoder.encode(asleepMorning.getSelectedItem().toString()) + "&"
					+ "entry_2144314179=" + URLEncoder.encode(awakeInClass.getSelectedItem().toString()) + "&"
					+ "entry_1193985820=" + URLEncoder.encode(awakeLastClass.getSelectedItem().toString()) + "&"
					+ "entry_502584888=" + URLEncoder.encode(busCarTrain.getSelectedItem().toString()) + "&"
					+ "entry_860282205=" + URLEncoder.encode(realized.getSelectedItem().toString()) + "&"
					+ "entry_12534346=" + URLEncoder.encode(entryType);
//			String response = mReq.sendPost(fullUrl, data);
//			System.out.println("postData response: " + response);
			
			UploadFormData doItNow = new UploadFormData();
			doItNow.execute(fullUrl);

     	   savePrefs("sleepinessSurveyIgnored","NO");
			
//			finish();

    	   Intent surveyPage = new Intent(Sleepiness.this,FeelRightNow.class);
    	   surveyPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	   Sleepiness.this.startActivity(surveyPage);	
		}
	}

	private class UploadFormData extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			String response = "";
			for (String url : urls){
			try{
				HttpRequest mReq = new HttpRequest();
				String mdata = data;
				System.out.print(url);
				response = mReq.sendPost(url, mdata);
			}catch (Exception e){
				e.printStackTrace();
			}
			}
			
			return response;
		}
		
		protected void onPostExecute(String result){
			Context context = getApplicationContext();
			CharSequence text = "Sleepiness Form data submitted.";
			int duration = Toast.LENGTH_SHORT;
			
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		
	}

	
    private void savePrefs(String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sleepiness, menu);
		return true;
	}

}
