package com.swe.wakeupnow;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SoundActivity extends Activity{
	private Button mbtback;
	private Button mbtsave;
//	private static Uri[] mUris;
//	private static String[] mFiles = null;
//	private RadioButton mRdbt;
	private LinearLayout mLinearLayout;
	final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
	private ArrayList<HashMap<String, String>> musicList = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);
		
		mLinearLayout = (LinearLayout) findViewById(R.id.linear1);
		showRdButton();
		
		mbtback = (Button) findViewById(R.id.btsoundback);
		mbtback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlarmSetting.class);
				startActivity(i);
				finish();
			}
		});
		
		mbtsave = (Button) findViewById(R.id.btsoundsave);
		mbtsave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlarmSetting.class);
				startActivity(i);
				finish();
			}
		});
	}

	private void showRdButton() {
//		String music[] = {"Payphone.mp3", "Back to December.flac", "ไม่บอกเธอ.mp3", "a.ogg"};
//		String[] music = getMusic();
		String[] music = getMusicFrRaw();
	
		// create radio button
		RadioButton[] rb = new RadioButton[music.length];
		RadioGroup rg = new RadioGroup(this);
		rg.setOrientation(RadioGroup.VERTICAL);
		for (int i = 0; i < music.length; i++) {
			rb[i] = new RadioButton(this);
			rg.addView(rb[i]);
			String nameMusic = music[i].substring(0,1).toUpperCase().concat(music[i].substring(1,music[i].length()));
			rb[i].setText(nameMusic);
			rb[i].setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		mLinearLayout.addView(rg);
		
	}

//	private String state() {
//		String state = Environment.getExternalStorageState();
//		return state;
//	}
	
	
//	private String[] getMusic(){
//		
//		String path = Environment.getExternalStorageDirectory().toString()+"/Ringtones";
//
//		File music = new File(path);
//		String musicList[] = music.list(
////				new FilenameFilter() {
////			@Override
////			public boolean accept(File dir, String name) {  
////				return ((name.endsWith(".mp3"))||(name.endsWith(".m4b"))||(name.endsWith(".flac")));
////			}
////		}
//			);
//
//		mFiles = new String[musicList.length];
//		for(int i=0; i<musicList.length; i++) {
//		    mFiles[i] = musicList[i];
//		    System.out.println(mFiles[i]);
//		}
//		return mFiles;
//	}
	
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
