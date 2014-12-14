package com.swe.wakeupnow;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.*;

import com.swe.wakeupnow.FeedReaderContract.FeedEntry;

public class AlarmSetting extends Activity {
	
	private Button mCancel;
	private Button mSave;
	private ListView lsvSetting;
	private TimePicker timeSetting;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		
		mCancel = (Button) findViewById(R.id.btalarmback);
		mSave = (Button) findViewById(R.id.btalarmsave);
		lsvSetting = (ListView)findViewById(R.id.lsvSetting);
		timeSetting = (TimePicker)findViewById(R.id.timePicker);
		
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
				int hh = timeSetting.getCurrentHour();
				int mm = timeSetting.getCurrentMinute();
//				saveSetting(hh, mm);
				
				
				Intent i = new Intent(getBaseContext(), MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		
		bindingSetting();
		lsvSetting.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				if(position == 0){
					Intent i = new Intent(getBaseContext(), SoundActivity.class);
					startActivity(i);
					finish();
				}
				else if(position == 1){
					Intent i = new Intent(getBaseContext(), GameActivity.class);
					startActivity(i);
					finish();
					
				}
				
			}
		});
	
	}
	
	private void saveSetting(int hh,int mm){
		 AlarmDbHelper dbHelper = new AlarmDbHelper(getBaseContext());
		
		// Gets the data repository in write mode
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(FeedEntry.COLUMN_NAME_HOUR, hh);
		values.put(FeedEntry.COLUMN_NAME_MINUTE, mm);

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
		         FeedEntry.TABLE_NAME,
		         "null",
		         values);
		 
//		 AlarmDbHelper dbHelper = new AlarmDbHelper(getBaseContext());
//		 SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//		// Define a projection that specifies which columns from the database
//		// you will actually use after this query.
//		String[] projection = {"id","hh","mm"};
//
//		// How you want the results sorted in the resulting Cursor
//		String sortOrder = "id DESC";
//
//		Cursor c = db.query(
//		    TABLE_NAME,  // The table to query
//		    projection,  // The columns to return
//		    "",          // The columns for the WHERE clause
//		    null, "",          // The values for the WHERE clause
//		    null,                                     // don't group the rows
//		    null,                                     // don't filter by row groups
//		    sortOrder // The sort orderS		    
//		    );
//	
//		c.moveToFirst();
//		while(c.moveToNext()){
//			int id = c.getInt(0);
//			String hh = c.getString(1);
//			String mm = c.getString(2);
//			System.out.println(id);
//			System.out.println(hh);
//			System.out.println(mm);
//		}

	} 
	
	private void bindingSetting(){
		
		String val[] =  new String[]{"Sound","Game"};
		
		 final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_list_item_1, val);
		 lsvSetting.setAdapter(adapter);
		
	}
	
	

}
