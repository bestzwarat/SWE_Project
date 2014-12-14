package com.swe.wakeupnow;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class AlarmPreference extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_layout);
		addPreferencesFromResource(R.xml.alarmpreference);
		
		Preference button = (Preference)findPreference("button");
		button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				// code for what you want it to do
				return true;
			}
		});
	}

}
