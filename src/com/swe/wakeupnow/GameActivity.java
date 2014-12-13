package com.swe.wakeupnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameActivity extends Activity{
	
	private Button mbtgameback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games);
		mbtgameback = (Button) findViewById(R.id.btgameback);
		mbtgameback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlarmSetting.class);
				startActivity(i);
				finish();
				
			}
		});
	}
	
}
