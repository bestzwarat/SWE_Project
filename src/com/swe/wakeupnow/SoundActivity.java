package com.swe.wakeupnow;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SoundActivity extends Activity{
	private Button mbtback;
//	private static Uri[] mUris;
	private static String[] mFiles = null;
//	private RadioButton mRdbt;
	private LinearLayout mLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);
		
		mLinearLayout = (LinearLayout) findViewById(R.id.linear1);
		
//		String music[] = {"Payphone.mp3", "Back to December.flac", "ไม่บอกเธอ.mp3", "a.ogg"};
		String music[] = getMusic();
		System.out.println(music.length);
		
		// create radio button
		final RadioButton[] rb = new RadioButton[music.length];
		RadioGroup rg = new RadioGroup(this);
		rg.setOrientation(RadioGroup.VERTICAL);
		for (int i = 0; i < music.length; i++) {
		    rb[i] = new RadioButton(this);
		    rg.addView(rb[i]);
		    rb[i].setText(music[i]);
		}
		mLinearLayout.addView(rg);
		
		mbtback = (Button) findViewById(R.id.btsoundback);
		mbtback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlarmSetting.class);
				startActivity(i);
				finish();
			}
		});
	}

//	private String state() {
//		String state = Environment.getExternalStorageState();
//		return state;
//	}
	
	private String[] getMusic(){
		mFiles = new String[];
		for(int i=0; i<musicList.length; i++) {
		    mFiles[i] = musicList[i].getName();
		    System.out.println(mFiles[i]);
		}
		return mFiles;
	}
	
	public void listRaw(){
		Field[] fields = R.raw.class.getFields();
	    for(int count=0; count < fields.length; count++){
	       
	    }
	}
	
//	private String[] getMusic(){
//		
//		String path = Environment.getExternalStorageDirectory().toString()+"/Ringtones";
//
//		File music = new File(path);
//		File musicList[] = music.listFiles(new FilenameFilter() {
//			@Override
//			public boolean accept(File dir, String name) {  
//				return ((name.endsWith(".mp3"))||(name.endsWith(".m4b"))||(name.endsWith(".flac")));
//			}
//		});
//
//		mFiles = new String[musicList.length];
//		for(int i=0; i<musicList.length; i++) {
//		    mFiles[i] = musicList[i].getName();
//		    System.out.println(mFiles[i]);
//		}
//		return mFiles;
//	}
	
}
