package com.swe.wakeupnow;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
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
import android.widget.Toast;

public class SoundActivity extends Activity{
	private Button mbtsoundback;
	private Button mbtsoundsave;
//	private static Uri[] mUris;
//	private static String[] mFiles = null;
//	private RadioButton mRdbt;
	private LinearLayout mLinearLayout;
	private RadioGroup mRg;
	private RadioButton[] mRb;
	private RadioButton selectedRb;
//	final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);
		
		mLinearLayout = (LinearLayout) findViewById(R.id.linear1);
		showRdButton();
//		if(mRg.getCheckedRadioButtonId()!=-1){
//		    int id= mRg.getCheckedRadioButtonId();
//		    View radioButton = mRg.findViewById(id);
//		    int radioId = mRg.indexOfChild(radioButton);
//		    RadioButton btn = (RadioButton) mRg.getChildAt(radioId);
//		    String selection = (String) btn.getText();
//			Toast.makeText(SoundActivity.this, selection, Toast.LENGTH_SHORT).show();
//
//		}

		
		mbtsoundback = (Button) findViewById(R.id.btsoundback);
		mbtsoundback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlarmPreference.class);
				startActivity(i);
				finish();
			}
		});
		
		mbtsoundsave = (Button) findViewById(R.id.btsoundsave);
		mbtsoundsave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int selectedId = mRg.getCheckedRadioButtonId();
				selectedRb = (RadioButton) findViewById(selectedId);
				System.out.println(selectedId);
				if (selectedId == -1) {
					Toast.makeText(SoundActivity.this, "Please choose one", Toast.LENGTH_SHORT).show();
					System.out.println(selectedId);
				}
				else {
					Toast.makeText(SoundActivity.this, "You choose " + selectedRb.getText(), Toast.LENGTH_SHORT).show();
					System.out.println(selectedId);
					Intent i = new Intent(getBaseContext(), AlarmPreference.class);
					startActivity(i);
					finish();
				}	
			}
		});
	}

	private void showRdButton() {
//		String music[] = {"Payphone.mp3", "Back to December.flac", "ไม่บอกเธอ.mp3", "a.ogg"};
//		String[] music = getMusic();
		String[] music = getMusicFrRaw();
		
		// create radio button
		mRb = new RadioButton[music.length];
		mRg = new RadioGroup(this);
		mRg.setOrientation(RadioGroup.VERTICAL);
		for (int i = 0; i < music.length; i++) {
			mRb[i] = new RadioButton(this);
			mRg.addView(mRb[i]);
			String nameMusic = music[i].substring(0,1).toUpperCase().concat(music[i].substring(1,music[i].length()));
			mRb[i].setText(nameMusic);
		}
		mLinearLayout.addView(mRg);
		

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
