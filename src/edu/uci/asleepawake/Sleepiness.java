package edu.uci.asleepawake;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Sleepiness extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sleepiness);
		
		Spinner spinner = (Spinner) findViewById(R.id.MorningClasses);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.survey_spinner, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		spinner = (Spinner) findViewById(R.id.SchoolDay);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.LastClass);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.Car);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.AwakeWholeDay);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.AfternoonClasses);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.AlertAllClasses);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.AsleepMorning);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.AwakeInClass);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.AwakeLastClass);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.BusCarTrain);
		spinner.setAdapter(adapter);
		spinner = (Spinner) findViewById(R.id.Realized);
		spinner.setAdapter(adapter);}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sleepiness, menu);
		return true;
	}

}
