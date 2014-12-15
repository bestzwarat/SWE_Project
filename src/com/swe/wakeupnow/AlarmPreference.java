package com.swe.wakeupnow;

import java.lang.reflect.Field;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlarmPreference extends PreferenceActivity {

	private Button mCancel;
	private Button mSave;
	private Vibrator vibrator;
	private Preference ringtone;
	private CheckBoxPreference vibrate;
	private AlertDialog ringtoneDialog;
	private SharedPreferences mPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_layout);
		addPreferencesFromResource(R.xml.alarmpreference);
		mPrefs = getSharedPreferences("alarm_prefs", MODE_PRIVATE); 

		mCancel = (Button) findViewById(R.id.btalarmbackP);
		mSave = (Button) findViewById(R.id.btalarmsaveP);

		 // ringtone
		 ringtone = (Preference) findPreference("ringtone");
		
		 ringtone.setOnPreferenceClickListener(new
		 Preference.OnPreferenceClickListener() {
		
			 @Override
			 	public boolean onPreferenceClick(Preference arg0) {
				 	Intent i = new Intent(getBaseContext(), SoundActivity.class);
				 	startActivity(i);
				 	finish();
				 	return true;
			 	}
		 });
		
//		//ringtone
//		ringtone = (Preference) findPreference("ringtone");
//		final CharSequence[] music = getMusicFrRaw();
//		
//		ringtone.setOnPreferenceClickListener(new
//				 Preference.OnPreferenceClickListener() {
//			@Override
//		 	public boolean onPreferenceClick(Preference arg0) {
//				// Creating and Building the Dialog 
//		        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//		        builder.setTitle("Ringtone");
//		        builder.setSingleChoiceItems(music, -1, new DialogInterface.OnClickListener() {
//		        	public void onClick(DialogInterface dialog, int item) {
//		        		ringtoneDialog.dismiss();    
//		            }
//		        });
//		        ringtoneDialog = builder.create();
//		        ringtoneDialog.show();
//			 	return true;
//		 	}
//		});
		
		
		
		 
//		 //ringtone
//		final SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//		final ListPreference ringtonePref = (ListPreference) findPreference("ringtone");
//		String ringtone_choose = prefs1.getString("ringtone", getResources().getString(R.string.ringtone_none));
//		ringtonePref.setSummary((CharSequence) ringtone_choose);
//
//		ringtonePref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
//
//			@Override
//			public boolean onPreferenceChange(Preference preference,
//					Object newValue) {
//				ringtonePref.setSummary((CharSequence) newValue);
//				// Since we are handling the pref, we must save it
//				SharedPreferences.Editor ed = prefs1.edit();
//				ed.putString("ringtone", newValue.toString());
//				ed.commit();
//				Toast.makeText(getBaseContext(),
//						"Choose " + newValue.toString(), Toast.LENGTH_SHORT)
//						.show();
//				return true;
//			}
//		});

		// vibrate
		vibrator = ((Vibrator) getSystemService(VIBRATOR_SERVICE));
		vibrate = (CheckBoxPreference) findPreference("vibrate");

		vibrate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				if (vibrate.isChecked()) {
					vibrator.vibrate(800);
				}
				return true;
			}
		});

		// game
		final SharedPreferences prefs2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		final ListPreference gamePref = (ListPreference) findPreference("game");

		String game_choose = prefs2.getString("game", getResources().getString(R.string.game_none));
		gamePref.setSummary((CharSequence) game_choose);

		gamePref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				gamePref.setSummary((CharSequence) newValue);
				// Since we are handling the pref, we must save it
				SharedPreferences.Editor ed = prefs2.edit();
				ed.putString("game", newValue.toString());
				ed.commit();
				Toast.makeText(getBaseContext(),
						"Choose " + newValue.toString(), Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});

		mCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), MainActivity.class);
				startActivity(i);
				finish();
			}
		});

		mSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), MainActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	
	private String[] getMusicFrRaw() {
		Field[] fields = R.raw.class.getFields();
		String[] music = new String[fields.length];
		System.out.println(music.length);
		for (int i=0; i< fields.length; i++) {
			music[i] = fields[i].getName();
			System.out.println(music[i]);
		}
		return music;
	}
}
