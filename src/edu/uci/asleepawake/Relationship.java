package edu.uci.asleepawake;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Relationship extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relationship);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_relationship, menu);
		return true;
	}

}
