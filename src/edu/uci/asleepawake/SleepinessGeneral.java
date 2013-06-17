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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sleepiness_general);
		
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
	
	
	     submit = (Button)findViewById(R.id.Submit);
	     submit.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		
		if (morningGen.getSelectedItem().toString().equals("")
				|| wholeDayGen.getSelectedItem().toString().equals("")
				|| laterDayGen.getSelectedItem().toString().equals("")
				|| carGen.getSelectedItem().toString().equals("")
				|| awakeWholeDayGen.getSelectedItem().toString().equals("")
				|| alertAllDayGen.getSelectedItem().toString().equals("")
				|| busCarTrainGen.getSelectedItem().toString().equals("")
				|| realizedGen.getSelectedItem().toString().equals(""))		{
	       	
			AlertDialog.Builder builder = new AlertDialog.Builder(SleepinessGeneral.this);
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
					+ "entry_2039569836=" + URLEncoder.encode(morningGen.getSelectedItem().toString()) + "&"
					+ "entry_633281223=" + URLEncoder.encode(wholeDayGen.getSelectedItem().toString()) + "&"
					+ "entry_84773151=" + URLEncoder.encode(laterDayGen.getSelectedItem().toString()) + "&"
					+ "entry_1236987037=" + URLEncoder.encode(carGen.getSelectedItem().toString()) + "&"
					+ "entry_1480764914=" + URLEncoder.encode(awakeWholeDayGen.getSelectedItem().toString()) + "&"
					+ "entry_581428323=" + URLEncoder.encode(alertAllDayGen.getSelectedItem().toString()) + "&"
					+ "entry_502584888=" + URLEncoder.encode(busCarTrainGen.getSelectedItem().toString()) + "&"
					+ "entry_1297174710=" + URLEncoder.encode(realizedGen.getSelectedItem().toString()) ;
			String response = mReq.sendPost(fullUrl, data);
			System.out.println("postData response: " + response);
     	   savePrefs("sleepinessSurveyIgnored","NO");
			
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
