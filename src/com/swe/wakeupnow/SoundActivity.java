package com.swe.wakeupnow;

import java.io.File;
import java.io.FilenameFilter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SoundActivity extends Activity{
	private Button mbtback;
	private static Uri[] mUris;
	private static String[] mFiles = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);
		mbtback = (Button) findViewById(R.id.btback);
		mbtback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println(getMusic());
				Intent i = new Intent(getBaseContext(), AlarmSetting.class);
				startActivity(i);
				finish();
			}
		});
	}

	private String state(){
		String state = Environment.getExternalStorageState();
		return state;
	}
	
	private String[] getMusic(){
		
		File music = Environment.getExternalStorageDirectory();

//		File asd = Environment.
//
//		String[] savedFiles = getApplicationContext().fileList();

		File[] musicList = music.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {  
				return ((name.endsWith(".mp3"))||(name.endsWith(".m4b"))||(name.endsWith(".flac")));
			}
		});

		mFiles = new String[musicList.length];
		for(int i=0; i<musicList.length; i++) {
		    mFiles[i] = musicList[i].getAbsolutePath();
		}
		return mFiles;
	}
	
}
