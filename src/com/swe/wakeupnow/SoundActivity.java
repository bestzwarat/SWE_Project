package com.swe.wakeupnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SoundActivity extends Activity{
	private Button mbtback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);
		mbtback = (Button) findViewById(R.id.btback);
		mbtback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlarmSetting.class);
				startActivity(i);
				finish();
				
			}
		});
		
	}
	
}
