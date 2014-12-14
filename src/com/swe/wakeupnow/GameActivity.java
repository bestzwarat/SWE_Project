package com.swe.wakeupnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GameActivity extends Activity{
	
	private Button mbtgameback;
	private Button mbtgamesave;
	private RadioGroup mRg;
	private RadioButton selectedRb;

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
		
		mbtgamesave = (Button) findViewById(R.id.btgamesave);
		mRg = (RadioGroup) findViewById(R.id.radioGroupGame);
		mbtgamesave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int selectedId = mRg.getCheckedRadioButtonId();
				selectedRb = (RadioButton) findViewById(selectedId);
				Toast.makeText(GameActivity.this, "You choose " + selectedRb.getText(), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(getBaseContext(), AlarmSetting.class);
				startActivity(i);
				finish();
			}
		});
	}
	
}
