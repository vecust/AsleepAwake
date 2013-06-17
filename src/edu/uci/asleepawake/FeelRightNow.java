package edu.uci.asleepawake;

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

public class FeelRightNow extends Activity implements OnSeekBarChangeListener, OnClickListener{

	String sittingReading, watchingTV, publicPlace, passenger,
			afternoonRest, sittingTalking, afterLunch, carTraffic;
	TextView sittingReadingTextView, watchingTVTextView, publicPlaceTextView,
			passengerTextView, afternoonRestTextView, sittingTalkingTextView,
			afterLunchTextView, carTrafficTextView;
	SeekBar sittingReadingSeekBar, watchingTVSeekBar, publicPlaceSeekBar,
			passengerSeekBar, afternoonRestSeekBar, sittingTalkingSeekBar,
			afterLunchSeekBar, carTrafficSeekBar;
	Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feel_right_now);

		sittingReadingTextView = (TextView)findViewById(R.id.SittingReading);
		watchingTVTextView = (TextView)findViewById(R.id.WatchingTV);
		publicPlaceTextView = (TextView)findViewById(R.id.PublicPlace);
		passengerTextView = (TextView)findViewById(R.id.Passenger);
		afternoonRestTextView = (TextView)findViewById(R.id.AfternoonRest);
		sittingTalkingTextView = (TextView)findViewById(R.id.SittingTalking);
		afterLunchTextView = (TextView)findViewById(R.id.AfterLunch);
		carTrafficTextView = (TextView)findViewById(R.id.CarTraffic);
		
		SeekBar sittingReadingSeekBar = (SeekBar)findViewById(R.id.SittingReadingSeekBar);
		sittingReadingSeekBar.setMax(100);
		sittingReadingSeekBar.setProgress(50);
		sittingReadingSeekBar.setOnSeekBarChangeListener(this);

		SeekBar watchingTVSeekBar = (SeekBar)findViewById(R.id.WatchingTVSeekBar);
		watchingTVSeekBar.setMax(100);
		watchingTVSeekBar.setProgress(50);
		watchingTVSeekBar.setOnSeekBarChangeListener(this);
				
		SeekBar publicPlaceSeekBar = (SeekBar)findViewById(R.id.PublicPlaceSeekBar);
		publicPlaceSeekBar.setMax(100);
		publicPlaceSeekBar.setProgress(50);
		publicPlaceSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar passengerSeekBar = (SeekBar)findViewById(R.id.PassengerSeekBar);
		passengerSeekBar.setMax(100);
		passengerSeekBar.setProgress(50);
		passengerSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar afternoonRestSeekBar = (SeekBar)findViewById(R.id.AfternoonRestSeekBar);
		afternoonRestSeekBar.setMax(100);
		afternoonRestSeekBar.setProgress(50);
		afternoonRestSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar sittingTalkingSeekBar = (SeekBar)findViewById(R.id.SittingTalkingSeekBar);
		sittingTalkingSeekBar.setMax(100);
		sittingTalkingSeekBar.setProgress(50);
		sittingTalkingSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar afterLunchSeekBar = (SeekBar)findViewById(R.id.AfterLunchSeekBar);
		afterLunchSeekBar.setMax(100);
		afterLunchSeekBar.setProgress(50);
		afterLunchSeekBar.setOnSeekBarChangeListener(this);
		
		SeekBar carTrafficSeekBar = (SeekBar)findViewById(R.id.CarTrafficSeekBar);
		carTrafficSeekBar.setMax(100);
		carTrafficSeekBar.setProgress(50);
		carTrafficSeekBar.setOnSeekBarChangeListener(this);

	     submit = (Button)findViewById(R.id.RelationshipSurveyButton);
	     submit.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_display_message, menu);
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar v, int progress, boolean isUser) {
		TextView tv = null;

		if(v.getId() == R.id.SittingReadingSeekBar){
			tv = (TextView)findViewById(R.id.SittingReading);
		} else if(v.getId() == R.id.WatchingTVSeekBar){
			tv = (TextView)findViewById(R.id.WatchingTV);
		} else if(v.getId() == R.id.PublicPlaceSeekBar){
			tv = (TextView)findViewById(R.id.PublicPlace);
		} else if(v.getId() == R.id.PassengerSeekBar){
			tv = (TextView)findViewById(R.id.Passenger);
		} else if(v.getId() == R.id.AfternoonRestSeekBar){
			tv = (TextView)findViewById(R.id.AfternoonRest);
		} else if(v.getId() == R.id.SittingTalkingSeekBar){
			tv = (TextView)findViewById(R.id.SittingTalking);
		} else if(v.getId() == R.id.AfterLunchSeekBar){
			tv = (TextView)findViewById(R.id.AfterLunch);
		} else if(v.getId() == R.id.CarTrafficSeekBar){
			tv = (TextView)findViewById(R.id.CarTraffic);
		}
		if (tv != null) {
			if (progress >= 0 && progress <= 24)
				tv.setText("Would Never Doze");
			if (progress >= 25 && progress <= 49)
				tv.setText("Slight Chance of Dozing");
			if (progress >= 50 && progress <= 74)
				tv.setText("Moderate Chance of Dozing");
			if (progress >= 75 && progress <= 100)
				tv.setText("High Chance of Dozing");

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
		if(sittingReadingTextView.getText().equals("Slide left or right")
				|| watchingTVTextView.getText().equals("Slide left or right")	
				|| publicPlaceTextView.getText().equals("Slide left or right")	
				|| passengerTextView.getText().equals("Slide left or right")	
				|| afternoonRestTextView.getText().equals("Slide left or right")	
				|| sittingTalkingTextView.getText().equals("Slide left or right")	
				|| afterLunchTextView.getText().equals("Slide left or right")	
				|| carTrafficTextView.getText().equals("Slide left or right")	
	
				) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(FeelRightNow.this);
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
					+ "entry_2035552502=" + URLEncoder.encode(sittingReadingTextView.getText().toString()) + "&"
					+ "entry_430809170=" + URLEncoder.encode(watchingTVTextView.getText().toString()) + "&"
					+ "entry_1462571302=" + URLEncoder.encode(publicPlaceTextView.getText().toString()) + "&"
					+ "entry_729054248=" + URLEncoder.encode(passengerTextView.getText().toString()) + "&"
					+ "entry_302061174=" + URLEncoder.encode(afternoonRestTextView.getText().toString()) + "&"
					+ "entry_487982611=" + URLEncoder.encode(sittingTalkingTextView.getText().toString()) + "&"
					+ "entry_1030822716=" + URLEncoder.encode(afterLunchTextView.getText().toString()) + "&"
					+ "entry_500458483=" + URLEncoder.encode(carTrafficTextView.getText().toString());
			String response = mReq.sendPost(fullUrl, data);
			System.out.println("postData response: " + response);
			
			savePrefs("feelRightNowSurveyIgnored","NO");
			
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
