package edu.uci.asleepawake;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Calendar;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.AsyncTask;

public class WakeAlarmReceiverActivity extends Activity {
	private MediaPlayer mMediaPlayer;
	SharedPreferences sp;
	String data;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_wake_alarm_receiver);

	    sp = PreferenceManager.getDefaultSharedPreferences(this);
//	    final int sleepID = sp.getInt("sleepID", -1);
	    final String sleepLogged = sp.getString("sleepLogged", "");
		
		if(SurveyAlarmReceiverActivity.instance != null){
			try {
//				if(sleepID > -1){
//		        Intent intent = new Intent(SurveyAlarmReceiverActivity.instance, SurveyAlarmReceiverActivity.class);
////		        intent.putExtra("intentID", 12345);
//		        PendingIntent pendingIntent = PendingIntent.getActivity(SurveyAlarmReceiverActivity.instance,
//		        		sleepID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//		        AlarmManager am = 
//		            (AlarmManager)SurveyAlarmReceiverActivity.instance.getSystemService(ALARM_SERVICE);
//		        am.cancel(pendingIntent);
//		        savePrefs("sleepID", -1);
//				}
				savePrefs("sleepLogged", "NO");
				SurveyAlarmReceiverActivity.instance.finish();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		final int intentID = getIntent().getIntExtra("intentID", 0);
	    System.out.println("Wake Intent ID: "+intentID);
		
	    
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				WakeAlarmReceiverActivity.this);
		if(sleepLogged.equals("YES")){
			alertDialogBuilder.setMessage("Time to take off your sleep watch and put it in a safe place");
		} else {
			alertDialogBuilder.setMessage("Please log your sleep and wake time");			
		}
		alertDialogBuilder
				.setTitle("Good morning!")
//				.setMessage("Time to take off your sleep watch and put it in a safe place")
				.setCancelable(false)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								mMediaPlayer.stop();
								
						        Intent intent = new Intent(WakeAlarmReceiverActivity.this, WakeAlarmReceiverActivity.class);
//						        intent.putExtra("intentID", 12345);
						        PendingIntent pendingIntent = PendingIntent.getActivity(WakeAlarmReceiverActivity.this,
						            intentID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
						        AlarmManager am = 
						            (AlarmManager)WakeAlarmReceiverActivity.this.getSystemService(ALARM_SERVICE);
						        am.cancel(pendingIntent);
								pendingIntent.cancel();
								System.out.println("Wake Alarm Cancelled - Intent: "+intentID);
								savePrefs("ManualEntry", "Scheduled");
						        if(sleepLogged.equals("YES")){
						        postData();
								Intent backToMainIntent = new Intent(WakeAlarmReceiverActivity.this, MainActivity.class);
//							    backToMainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								WakeAlarmReceiverActivity.this.startActivity(backToMainIntent);
						        } else {
						        	savePrefs("ManualEntry","Manual");
									Intent formIntent = new Intent(WakeAlarmReceiverActivity.this, Form.class);
									WakeAlarmReceiverActivity.this.startActivity(formIntent);						        	
						        }
							}
						})
				.setNegativeButton("Snooze",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								mMediaPlayer.stop();
//						        Intent intent = new Intent(AlarmReceiverActivity.this, FirstAlarmDismissed.class);
//						        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						        AlarmReceiverActivity.this.startActivity(intent);
								
						        //Create an offset from the current time in which the alarm will go off.
						        Calendar cal = Calendar.getInstance();
						        cal.add(Calendar.MINUTE, 10);
						        System.out.println("Wake Alarm Snooze: "+cal.getTime().toString());
						        //Create a new PendingIntent and add it to the AlarmManager
						        Intent intent = new Intent(WakeAlarmReceiverActivity.this, WakeAlarmReceiverActivity.class);
						        System.out.println("Wake Intent ID: "+intentID);
						        intent.putExtra("intentID", intentID);
						        PendingIntent pendingIntent = PendingIntent.getActivity(WakeAlarmReceiverActivity.this,
						        		intentID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
						        AlarmManager am = 
						            (AlarmManager)WakeAlarmReceiverActivity.this.getSystemService(ALARM_SERVICE);
						        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
						                pendingIntent);
								
								finish();
							}
						});

		AlertDialog alert = alertDialogBuilder.create();
		alert.show();

		playSound(this, getAlarmUri());
	}

	private void playSound(Context context, Uri alert) {
		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(context, alert);
			final AudioManager audioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			}
		} catch (IOException e) {
			System.out.println("OOPS");
		}
	}

	// Get an alarm sound. Try for an alarm. If none set, try notification,
	// Otherwise, ringtone.
	private Uri getAlarmUri() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alert == null) {
			alert = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if (alert == null) {
				alert = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}
		}
		return alert;
	}
	
	//This method saves the preferences
    private void savePrefs(String key, String value) {
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    private void savePrefs(String key, int value) {
        Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }
    
    public void postData(){
		Calendar temp = Calendar.getInstance();
		DateFormat timeFormat = DateFormat.getTimeInstance(3);
		String wakeTime = timeFormat.format(temp.getTime());
    	savePrefs("wakeTime",wakeTime);
    	savePrefs("wakeLogged","YES");
    	
		//Make connection to Google Form
		String fullUrl = "https://docs.google.com/a/uci.edu/forms/d/1x-YIb5tAnkImWDLaw0YtNIyqa0AXCroq26ogf_2yS9o/formResponse";
		HttpRequest mReq = new HttpRequest();
		
		//Get preferences
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String participant = sp.getString("Participant", "");
		String date = sp.getString("timeStamp", "");
		String sleep = sp.getString("sleepTime", "");
		String wake = sp.getString("wakeTime", "");
		String watch = sp.getString("wearingWatch", "");
		String entryType = sp.getString("ManualEntry", "");

		data = "entry_1794600332=" + URLEncoder.encode(participant) + "&"
				+"entry_2034720707=" + URLEncoder.encode(date) + "&"
				+ "entry_2032879505=" + URLEncoder.encode(sleep) + "&"
				+ "entry_1085709803=" + URLEncoder.encode(wake) + "&"
				+ "entry_2052787681=" + URLEncoder.encode(watch) + "&"
				+ "entry_12534346=" + URLEncoder.encode(entryType);
//		String response = mReq.sendPost(fullUrl, data);
//		System.out.println("postData response: " + response);

		UploadFormData doItNow = new UploadFormData();
		doItNow.execute(fullUrl);

		savePrefs("timeStamp","");
		savePrefs("logIgnored","");
		savePrefs("sleepLogged","NO");
		savePrefs("wakeLogged","NO");
		savePrefs("sleepIgnored","NO");
		savePrefs("wakeIgnored","NO");

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
			CharSequence text = "Form data submitted.";
			int duration = Toast.LENGTH_SHORT;
			
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		
	}
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wake_alarm_receiver, menu);
		return true;
	}

}
