package edu.uci.asleepawake;

import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sleepiness);
		
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
	
	
	     submit = (Button)findViewById(R.id.sleepSchoolSubmit);
	     submit.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		
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
	       	
			AlertDialog.Builder builder = new AlertDialog.Builder(Sleepiness.this);
				builder.setMessage("Please answer all the questions before submitting")
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
			String fullUrl = "https://docs.google.com/a/uci.edu/forms/d/1x-YIb5tAnkImWDLaw0YtNIyqa0AXCroq26ogf_2yS9o/formResponse";
			HttpRequest mReq = new HttpRequest();

	        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
	        String participant = sp.getString("Participant", "");
			
			String data = "entry_1794600332=" + URLEncoder.encode(participant) + "&"
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
					+ "entry_860282205=" + URLEncoder.encode(realized.getSelectedItem().toString()) ;
			String response = mReq.sendPost(fullUrl, data);
			System.out.println("postData response: " + response);
     	   savePrefs("sleepinessSurveyIgnored","");
			
			finish();

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
