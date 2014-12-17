package com.swe.wakeupnow;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmScreen extends Activity {
	
	public final String TAG = this.getClass().getSimpleName();

	private WakeLock mWakeLock;
	private MediaPlayer mPlayer;
	private Vibrator mVibrate;
	private String name;
	private int timeHour;
	private int timeMinute;
	private String tone;
	private Boolean vibrate;
	private String game;


	private static final int WAKELOCK_TIMEOUT = 60 * 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Setup layout
		this.setContentView(R.layout.activity_alarm_screen);

		name = getIntent().getStringExtra(AlarmManagerHelper.NAME);
		timeHour = getIntent().getIntExtra(AlarmManagerHelper.TIME_HOUR, 0);
		timeMinute = getIntent().getIntExtra(AlarmManagerHelper.TIME_MINUTE, 0);
		tone = getIntent().getStringExtra(AlarmManagerHelper.TONE);
		vibrate = getIntent().getBooleanExtra(AlarmManagerHelper.VIBRATE, false);
		game = getIntent().getStringExtra(AlarmManagerHelper.GAME);
		
//		Toast.makeText(this, game, Toast.LENGTH_LONG).show();
		
		TextView tvName = (TextView) findViewById(R.id.alarm_screen_name);
		tvName.setText(name);
		
		TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
		tvTime.setText(String.format("%02d:%02d", timeHour, timeMinute));
		
		Button dismissButton = (Button) findViewById(R.id.alarm_screen_button);
		dismissButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (game.equals("None")) {
					System.out.println("None");
					stopMediaOrVibrate();
					createDismissDialog();
				}
				else {
					System.out.println(game);
					stopMediaOrVibrate();
					createDismissDialog2();
				}
				
			}
		});

		//Play alarm tone
		mPlayer = new MediaPlayer();
		try {
			if (tone != null && !tone.equals("")) {
				Uri toneUri = Uri.parse(tone);
				if (toneUri != null) {
					mPlayer.setDataSource(this, toneUri);
					mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
					mPlayer.setLooping(true);
					mPlayer.prepare();
					mPlayer.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Play vibrate
		mVibrate = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		long[] pattern = { 0, 1500, 800};
		try {
			if (vibrate == true) {
				mVibrate.vibrate(pattern, 0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Ensure wakelock release
		Runnable releaseWakelock = new Runnable() {

			@Override
			public void run() {
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

				if (mWakeLock != null && mWakeLock.isHeld()) {
					mWakeLock.release();
				}
			}
		};

		new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();

		// Set the window to keep screen on
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		// Acquire wakelock
		PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
		if (mWakeLock == null) {
			mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
		}

		if (!mWakeLock.isHeld()) {
			mWakeLock.acquire();
			Log.i(TAG, "Wakelock aquired!!");
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mWakeLock != null && mWakeLock.isHeld()) {
			mWakeLock.release();
		}
	}
	
	private void createDismissDialog() {
		System.out.println("Dialog create");
		AlertDialog.Builder builder  = new AlertDialog.Builder(this);
		builder.setTitle("Alarm!!!");
		builder.setMessage(setMessage());
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		builder.create().show();
	}
	
	private void createDismissDialog2() {
		AlertDialog.Builder builder  = new AlertDialog.Builder(this);
		builder.setTitle("Alarm!!!");
		builder.setMessage(setMessage());
		builder.setPositiveButton("Start game now", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				System.out.println(game);
				if (game.equals("Matching Game")){
					Intent i = new Intent(AlarmScreen.this, MatchGameActivity.class);
					startActivity(i);
					finish();
				}
				else if (game.equals("Mathematics Game")){
					Intent i = new Intent(AlarmScreen.this, MathDashActivity.class);
					startActivity(i);
					finish();
				}
				else {
					Intent i = new Intent(AlarmScreen.this, TicTacToeActivity.class);
					startActivity(i);
					finish();
				}
			}
		});
		builder.create().show();
	}
	
	private String setMessage() {
		Calendar c = Calendar.getInstance();
		int form = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		int alarmH = timeHour;
		int alarmM = timeMinute;
		int alarmS = 0;
		int avgH = form - alarmH;
		int avgM = minute - alarmM;
		int avgS = second - alarmS;
		String quote = "You stop alarm late for ";
		String sec;
		String min;
		String ho;
		String message;
		
		if (avgS == 1 || avgS == 0) {
			sec = String.valueOf(avgS) + " second";
		}
		else {
			sec = String.valueOf(avgS) + " seconds";
		}
		if (avgM == 1 || avgM == 0) {
			min = String.valueOf(avgM) + " minute ";
		}
		else {
			min = String.valueOf(avgM) + " minutes ";
		}
		if (avgH == 1) {
			ho = String.valueOf(avgM) + " hour ";
		}
		else {
			ho = String.valueOf(avgM) + " hours ";
		}
		
		if (avgH == 0) {
			if (avgM == 0) {
				message = quote + sec;
			}
			else {
				message = quote + min + sec;
			}
		}
		else {
			message = quote + ho + min +sec;
		}
		return message;
	}
	
	public void stopMediaOrVibrate() {
		mPlayer.stop();
		mVibrate.cancel();
	}
	
}
