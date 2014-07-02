package edu.uci.asleepawake;

//This class reads in the answers to the sleepiness survey (general version)
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

public class SleepinessGeneral extends Activity implements OnClickListener{

    Button submit;
    private Spinner morningGen;
    private Spinner wholeDayGen;
    private Spinner laterDayGen;
    private Spinner carGen;
    private Spinner awakeWholeDayGen;
    private Spinner alertAllDayGen;
    private Spinner busCarTrainGen;
    private Spinner realizedGen;
    String data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sleepiness_general);
		
		//Assign ids of spinners (dropdown menus) on layout to local Spinner objects
		morningGen = (Spinner) findViewById(R.id.MorningGen);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.survey_spinner, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		// Apply the adapter to the spinner
		morningGen.setAdapter(adapter);
		morningGen.setPrompt("I fell asleep during the morning");
		
		wholeDayGen = (Spinner) findViewById(R.id.WholeDayGen);
		wholeDayGen.setAdapter(adapter);
		wholeDayGen.setPrompt("I got through the whole day without feeling tired");
		
		laterDayGen = (Spinner) findViewById(R.id.LaterDayGen);
		laterDayGen.setAdapter(adapter);
		laterDayGen.setPrompt("I fell asleep during the later in the day");
		
		carGen = (Spinner) findViewById(R.id.CarGen);
		carGen.setAdapter(adapter);
		carGen.setPrompt("I felt drowsy when I rode in a car for longer than 5 minutes");
		
		awakeWholeDayGen = (Spinner) findViewById(R.id.AwakeWholeDayGen);
		awakeWholeDayGen.setAdapter(adapter);
		awakeWholeDayGen.setPrompt("I felt wide-awake the whole day");
				
		alertAllDayGen = (Spinner) findViewById(R.id.AlertAllDayGen);
		alertAllDayGen.setAdapter(adapter);
		alertAllDayGen.setPrompt("I felt alert all day");
		
		busCarTrainGen = (Spinner) findViewById(R.id.BusCarTrainGen);
		busCarTrainGen.setAdapter(adapter);
		busCarTrainGen.setPrompt("I fell alseep when I rode in a bus, car, or train");
		
		realizedGen = (Spinner) findViewById(R.id.RealizedGen);
		realizedGen.setAdapter(adapter);
		realizedGen.setPrompt("During the day, there were times when I realized that I had just fallen asleep");
	
		//Assign id to submit button and set listener	
	     submit = (Button)findViewById(R.id.SleepinessGenSubmitButton);
	     submit.setOnClickListener(this);
	}

	public void onClick(View arg0) {

		//This is the listener for the submit button
		//If at least one of the questions haven't been answered,
		//display an alert telling the user to answer all the questions

		if (morningGen.getSelectedItem().toString().equals("")
				|| wholeDayGen.getSelectedItem().toString().equals("")
				|| laterDayGen.getSelectedItem().toString().equals("")
				|| carGen.getSelectedItem().toString().equals("")
				|| awakeWholeDayGen.getSelectedItem().toString().equals("")
				|| alertAllDayGen.getSelectedItem().toString().equals("")
				|| busCarTrainGen.getSelectedItem().toString().equals("")
				|| realizedGen.getSelectedItem().toString().equals(""))		{

			TableRow morningGenRow = (TableRow) findViewById(R.id.MorningGenRow);
			TableRow laterDayGenRow = (TableRow) findViewById(R.id.LaterDayGenRow);
			TableRow carGenRow = (TableRow) findViewById(R.id.CarGenRow);
			TableRow awakeWholeDayGenRow = (TableRow) findViewById(R.id.AwakeWholeDayGenRow);
			TableRow alertAllDayGenRow = (TableRow) findViewById(R.id.AlertAllDayGenRow);
			TableRow busCarTrainGenRow = (TableRow) findViewById(R.id.BusCarTrainGenRow);
			TableRow realizedGenRow = (TableRow) findViewById(R.id.RealizedGenRow);
			TableRow wholeDayGenRow = (TableRow) findViewById(R.id.WholeDayGenRow);
			
			if(morningGen.getSelectedItem().toString().equals("")){
				
				morningGenRow.setBackgroundColor(Color.YELLOW);
			} else {
				morningGenRow.setBackgroundColor(Color.WHITE);
				
			}
			if(laterDayGen.getSelectedItem().toString().equals("")){
				
				laterDayGenRow.setBackgroundColor(Color.YELLOW);
			} else {
				laterDayGenRow.setBackgroundColor(Color.WHITE);
				
			}
			if(carGen.getSelectedItem().toString().equals("")){
				
				carGenRow.setBackgroundColor(Color.YELLOW);
			} else {
				carGenRow.setBackgroundColor(Color.WHITE);
				
			}
			if(awakeWholeDayGen.getSelectedItem().toString().equals("")){
				
				awakeWholeDayGenRow.setBackgroundColor(Color.YELLOW);
			} else {
				awakeWholeDayGenRow.setBackgroundColor(Color.WHITE);
				
			}
			if(alertAllDayGen.getSelectedItem().toString().equals("")){
				
				alertAllDayGenRow.setBackgroundColor(Color.YELLOW);
			} else {
				alertAllDayGenRow.setBackgroundColor(Color.WHITE);
				
			}
			if(busCarTrainGen.getSelectedItem().toString().equals("")){
				
				busCarTrainGenRow.setBackgroundColor(Color.YELLOW);
			} else {
				busCarTrainGenRow.setBackgroundColor(Color.WHITE);
				
			}
			if(realizedGen.getSelectedItem().toString().equals("")){
				
				realizedGenRow.setBackgroundColor(Color.YELLOW);
			} else {
				realizedGenRow.setBackgroundColor(Color.WHITE);
				
			}
			if(wholeDayGen.getSelectedItem().toString().equals("")){
				
				wholeDayGenRow.setBackgroundColor(Color.YELLOW);
			} else {
				wholeDayGenRow.setBackgroundColor(Color.WHITE);
				
			}
			
			AlertDialog.Builder builder = new AlertDialog.Builder(SleepinessGeneral.this);
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
					+ "entry_2039569836=" + URLEncoder.encode(morningGen.getSelectedItem().toString()) + "&"
					+ "entry_633281223=" + URLEncoder.encode(wholeDayGen.getSelectedItem().toString()) + "&"
					+ "entry_84773151=" + URLEncoder.encode(laterDayGen.getSelectedItem().toString()) + "&"
					+ "entry_1236987037=" + URLEncoder.encode(carGen.getSelectedItem().toString()) + "&"
					+ "entry_1480764914=" + URLEncoder.encode(awakeWholeDayGen.getSelectedItem().toString()) + "&"
					+ "entry_581428323=" + URLEncoder.encode(alertAllDayGen.getSelectedItem().toString()) + "&"
					+ "entry_502584888=" + URLEncoder.encode(busCarTrainGen.getSelectedItem().toString()) + "&"
					+ "entry_1297174710=" + URLEncoder.encode(realizedGen.getSelectedItem().toString()) + "&"
					+ "entry_12534346=" + URLEncoder.encode(entryType);
//			String response = mReq.sendPost(fullUrl, data);
//			System.out.println("postData response: " + response);
			
			UploadFormData doItNow = new UploadFormData();
			doItNow.execute(fullUrl);

     	   savePrefs("sleepinessSurveyIgnored","NO");
			
//			finish();

    	   Intent surveyPage = new Intent(SleepinessGeneral.this,FeelRightNow.class);
    	   surveyPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	   SleepinessGeneral.this.startActivity(surveyPage);	

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
