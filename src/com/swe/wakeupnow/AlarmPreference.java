package com.swe.wakeupnow;

import android.app.AlertDialog;
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
	private CheckBoxPreference vibrate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_layout);
		addPreferencesFromResource(R.xml.alarmpreference);
		
		mCancel = (Button) findViewById(R.id.btalarmbackP);
		mSave = (Button) findViewById(R.id.btalarmsaveP);
		
		// ringtone
//		final SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//		final Preference ringtonePref = (Preference) findPreference("ringtone");
//		
//		String ringtone_choose = prefs1.getString("ringtone", getResources().getString(R.string.ringtone_none));
//		ringtonePref.setSummary((CharSequence) ringtone_choose);
//		final CharSequence[] colors_radio={"Green","Black","White"};
//		
//		ringtonePref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
//			
//			@Override
//			public boolean onPreferenceChange(Preference preference, Object newValue) {
//				AlertDialog.Builder builder2 = new AlertDialog.Builder(AlarmPreference.this)
//				.setTitle("Choose a Color")
//				.setSingleChoiceItems(colors_radio, -1, new DialogInterface.OnClickListener() {
//					 
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(),
//				"Choose "+colors_radio[which], Toast.LENGTH_SHORT).show();
//				 
//			//dismissing the dialog when the user makes a selection.
//			dialog.dismiss();
//			}
//				});
//			AlertDialog alertdialog2 = builder2.create();
//			return alertdialog2;
//			}
//		});
		
		// vibrate
		vibrator = ((Vibrator)getSystemService(VIBRATOR_SERVICE));
		vibrate = (CheckBoxPreference) findPreference("vibrate");
		
		vibrate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				if(vibrate.isChecked()){
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
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				gamePref.setSummary((CharSequence) newValue);
				// Since we are handling the pref, we must save it
				SharedPreferences.Editor ed = prefs2.edit();
				ed.putString("game", newValue.toString());
				ed.commit();
				Toast.makeText(getBaseContext(), "Choose "+newValue.toString(), Toast.LENGTH_SHORT).show();
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
}
