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
import android.text.format.Time;
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
		
		Toast.makeText(this, game, Toast.LENGTH_LONG).show();
		
		TextView tvName = (TextView) findViewById(R.id.alarm_screen_name);
		tvName.setText(name);
		
		TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
		tvTime.setText(String.format("%02d:%02d", timeHour, timeMinute));
		
		Button dismissButton = (Button) findViewById(R.id.alarm_screen_button);
		dismissButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
//				if (game == "None") {
					System.out.println(game);
					getmPlayer().stop();
					getmVibrate().cancel();
					createDismissDialog();
//				}
//				
//				else {
//					getmPlayer().stop();
//					getmVibrate().cancel();
//					System.out.println(game);
//					Intent i = new Intent(AlarmScreen.this, TestGameActivity.class);
//					startActivity(i);
//					finish();
//				}
				
			}
		});

		//Play alarm tone
		setmPlayer(new MediaPlayer());
		try {
			if (tone != null && !tone.equals("")) {
				Uri toneUri = Uri.parse(tone);
				if (toneUri != null) {
					getmPlayer().setDataSource(this, toneUri);
					getmPlayer().setAudioStreamType(AudioManager.STREAM_ALARM);
					getmPlayer().setLooping(true);
					getmPlayer().prepare();
					getmPlayer().start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Play vibrate
		setmVibrate((Vibrator) this.getSystemService(VIBRATOR_SERVICE));
		long[] pattern = { 0, 1500, 800};
		try {
			if (vibrate == true) {
				getmVibrate().vibrate(pattern, 0);
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
		Calendar c = Calendar.getInstance();
		int form = c.get(Calendar.HOUR_OF_DAY);
		int hour = c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String format;
		if (form > 12) {
			format = "PM";
		}
		else {
			format = "AM";
		}
		builder.setMessage("Current time is  " + String.valueOf(hour) + " : " + String.valueOf(minute) + " : " + String.valueOf(second) + " " + format);
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
		builder.setMessage("Alarm will stop when you win a game");
		builder.setCancelable(false);
		builder.setPositiveButton("Start game", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public MediaPlayer getmPlayer() {
		return mPlayer;
	}

	public void setmPlayer(MediaPlayer mPlayer) {
		this.mPlayer = mPlayer;
	}

	public Vibrator getmVibrate() {
		return mVibrate;
	}

	public void setmVibrate(Vibrator mVibrate) {
		this.mVibrate = mVibrate;
	}
	
}
