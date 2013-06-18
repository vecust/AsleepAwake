package edu.uci.asleepawake;

//This class reads in the answers to the relationship survey
//and sends them to the Google Form via the HttpRequest class

import java.net.URLEncoder;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Relationship extends Activity implements OnSeekBarChangeListener, OnClickListener{

	String funTime, support, faults, everything, mad, criticize, secrets,
			pressure, argue, cheerUp;
	TextView funTimeTextView, supportTextView, faultsTextView,
			everythingTextView, madTextView, criticizeTextView,
			secretsTextView, pressureTextView, argueTextView, cheerUpTextView;
	SeekBar funTimeSeekBar, supportSeekBar, faultsSeekBar, everythingSeekBar,
			madSeekBar, criticizeSeekBar, secretsSeekBar, pressureSeekBar,
			argueSeekBar, cheerUpSeekBar;
	Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relationship);

		//Assign ids of views on layout to local TextView objects
		funTimeTextView = (TextView)findViewById(R.id.FunTime);
		supportTextView = (TextView)findViewById(R.id.Support);
		faultsTextView = (TextView)findViewById(R.id.Faults);
		everythingTextView = (TextView)findViewById(R.id.Everything);
		madTextView = (TextView)findViewById(R.id.Mad);
		criticizeTextView = (TextView)findViewById(R.id.Criticize);
		secretsTextView = (TextView)findViewById(R.id.Secrets);
		pressureTextView = (TextView)findViewById(R.id.Pressure);
		argueTextView = (TextView)findViewById(R.id.Argue);
		cheerUpTextView = (TextView)findViewById(R.id.CheerUp);

		//Assign ids of seekbars (sliders) on layout to local SeekBar objects		
		SeekBar funTimeSeekBar = (SeekBar)findViewById(R.id.FunTimeSeekBar);
		funTimeSeekBar.setMax(100); //This is how far the slider can go
		funTimeSeekBar.setProgress(50); //This is the default setting for the slider
		funTimeSeekBar.setOnSeekBarChangeListener(this); //Setting listener

		SeekBar supportSeekBar = (SeekBar)findViewById(R.id.SupportSeekBar);
		supportSeekBar.setMax(100);
		supportSeekBar.setProgress(50);
		supportSeekBar.setOnSeekBarChangeListener(this);
				
		SeekBar faultsSeekBar = (SeekBar)findViewById(R.id.FaultsSeekBar);
		faultsSeekBar.setMax(100);
		faultsSeekBar.setProgress(50);
		faultsSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar everythingSeekBar = (SeekBar)findViewById(R.id.EverythingSeekBar);
		everythingSeekBar.setMax(100);
		everythingSeekBar.setProgress(50);
		everythingSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar madSeekBar = (SeekBar)findViewById(R.id.MadSeekBar);
		madSeekBar.setMax(100);
		madSeekBar.setProgress(50);
		madSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar criticizeSeekBar = (SeekBar)findViewById(R.id.CriticizeSeekBar);
		criticizeSeekBar.setMax(100);
		criticizeSeekBar.setProgress(50);
		criticizeSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar secretsSeekBar = (SeekBar)findViewById(R.id.SecretsSeekBar);
		secretsSeekBar.setMax(100);
		secretsSeekBar.setProgress(50);
		secretsSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar pressureSeekBar = (SeekBar)findViewById(R.id.PressureSeekBar);
		pressureSeekBar.setMax(100);
		pressureSeekBar.setProgress(50);
		pressureSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar argueSeekBar = (SeekBar)findViewById(R.id.ArgueSeekBar);
		argueSeekBar.setMax(100);
		argueSeekBar.setProgress(50);
		argueSeekBar.setOnSeekBarChangeListener(this);

		SeekBar cheerUpSeekBar = (SeekBar)findViewById(R.id.CheerUpSeekBar);
		cheerUpSeekBar.setMax(100);
		cheerUpSeekBar.setProgress(50);
		cheerUpSeekBar.setOnSeekBarChangeListener(this);

		//Assign id to submit button and set listener
	     submit = (Button)findViewById(R.id.RelationshipSurveyButton);
	     submit.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_relationship, menu);
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar v, int progress, boolean isUser) {
		TextView tv = null;

		//This is the listener for when the slider has been changed
		//Associate the seekbar that was touched and its accompanying TextView
		//to a local TextView object
		if(v.getId() == R.id.FunTimeSeekBar){
			tv = (TextView)findViewById(R.id.FunTime);
		} else if(v.getId() == R.id.SupportSeekBar){
			tv = (TextView)findViewById(R.id.Support);
		} else if(v.getId() == R.id.FaultsSeekBar){
			tv = (TextView)findViewById(R.id.Faults);
		} else if(v.getId() == R.id.EverythingSeekBar){
			tv = (TextView)findViewById(R.id.Everything);
		} else if(v.getId() == R.id.MadSeekBar){
			tv = (TextView)findViewById(R.id.Mad);
		} else if(v.getId() == R.id.CriticizeSeekBar){
			tv = (TextView)findViewById(R.id.Criticize);
		} else if(v.getId() == R.id.SecretsSeekBar){
			tv = (TextView)findViewById(R.id.Secrets);
		} else if(v.getId() == R.id.PressureSeekBar){
			tv = (TextView)findViewById(R.id.Pressure);
		} else if(v.getId() == R.id.ArgueSeekBar){
			tv = (TextView)findViewById(R.id.Argue);
		} else if(v.getId() == R.id.CheerUpSeekBar){
			tv = (TextView)findViewById(R.id.CheerUp);
		}
		//So long as the local TextView object has been assigned,
		//Output the answer onto the layout
		if (tv != null) {
			if (progress >= 0 && progress <= 19)
				tv.setText("Never or hardly at all");
			if (progress >= 20 && progress <= 39)
				tv.setText("Seldom or not too much");
			if (progress >= 40 && progress <= 59)
				tv.setText("Sometimes or somewhat");
			if (progress >= 60 && progress <= 79)
				tv.setText("Often or very much");
			if (progress >= 80 && progress <= 100)
				tv.setText("ALWAYS or EXTREMELY much");
		}
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		//This is the listener for the submit button
		//If at least one of the questions haven't been answered,
		//display an alert telling the user to answer all the questions
		if(funTimeTextView.getText().equals("Slide left or right")
				|| supportTextView.getText().equals("Slide left or right")	
				|| faultsTextView.getText().equals("Slide left or right")	
				|| everythingTextView.getText().equals("Slide left or right")	
				|| madTextView.getText().equals("Slide left or right")	
				|| criticizeTextView.getText().equals("Slide left or right")	
				|| secretsTextView.getText().equals("Slide left or right")	
				|| pressureTextView.getText().equals("Slide left or right")	
				|| argueTextView.getText().equals("Slide left or right")	
				|| cheerUpTextView.getText().equals("Slide left or right")	
				) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(Relationship.this);
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
			
			//With all the questions answered, make a connection to the Google Form and 
			//send the answers to the spreadsheet
			
			//Instructions for getting Google Form URL and entry code can be found here:
			//http://www.youtube.com/watch?v=GyuJ2GtpZd0

		} else {
			String fullUrl = "https://docs.google.com/a/uci.edu/forms/d/1x-YIb5tAnkImWDLaw0YtNIyqa0AXCroq26ogf_2yS9o/formResponse";
			HttpRequest mReq = new HttpRequest();

	        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
	        String participant = sp.getString("Participant", "");
	        
			String data = "entry_1794600332=" + URLEncoder.encode(participant) + "&"
					+ "entry_1626012679=" + URLEncoder.encode(funTimeTextView.getText().toString()) + "&"
					+ "entry_898526966=" + URLEncoder.encode(supportTextView.getText().toString()) + "&"
					+ "entry_1455635904=" + URLEncoder.encode(faultsTextView.getText().toString()) + "&"
					+ "entry_163918764=" + URLEncoder.encode(everythingTextView.getText().toString()) + "&"
					+ "entry_2056872006=" + URLEncoder.encode(madTextView.getText().toString()) + "&"
					+ "entry_138751871=" + URLEncoder.encode(criticizeTextView.getText().toString()) + "&"
					+ "entry_848061379=" + URLEncoder.encode(secretsTextView.getText().toString()) + "&"
					+ "entry_1334397556=" + URLEncoder.encode(pressureTextView.getText().toString()) + "&"
					+ "entry_610276760=" + URLEncoder.encode(argueTextView.getText().toString()) + "&"
					+ "entry_1783259162=" + URLEncoder.encode(cheerUpTextView.getText().toString());
			String response = mReq.sendPost(fullUrl, data);
			System.out.println("postData response: " + response);
			
			savePrefs("relationshipSurveyIgnored","");
			
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
