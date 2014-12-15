package com.swe.wakeupnow;

//import android.app.AlertDialog;
//import android.app.ListActivity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.Window;
//
//public class MainActivity extends ListActivity {
//
//	private AlarmListAdapter mAdapter;
//	private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
//	private Context mContext;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		
//		mContext = this;
//		
//		requestWindowFeature(Window.FEATURE_ACTION_BAR);
//		
//		setContentView(R.layout.activity_alarm_list);
//
//		mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());
//		
//		setListAdapter(mAdapter);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.alarm_list, menu);
//		return true;
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		switch (item.getItemId()) {
//			case R.id.action_add_new_alarm: {
//				startAlarmDetailsActivity(-1);
//				break;
//			}
//		}
//
//		return super.onOptionsItemSelected(item);
//	}
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		
//		if (resultCode == RESULT_OK) {
//	        mAdapter.setAlarms(dbHelper.getAlarms());
//	        mAdapter.notifyDataSetChanged();
//	    }
//	}
//	
//	public void setAlarmEnabled(long id, boolean isEnabled) {
//		AlarmManagerHelper.cancelAlarms(this);
//		
//		AlarmModel model = dbHelper.getAlarm(id);
//		model.isEnabled = isEnabled;
//		dbHelper.updateAlarm(model);
//		
//		AlarmManagerHelper.setAlarms(this);
//	}
//
//	public void startAlarmDetailsActivity(long id) {
//		Intent intent = new Intent(this, AlarmDetailsActivity.class);
//		intent.putExtra("id", id);
//		startActivityForResult(intent, 0);
//	}
//	
//	public void deleteAlarm(long id) {
//		final long alarmId = id;
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage("Please confirm")
//		.setTitle("Delete set?")
//		.setCancelable(true)
//		.setNegativeButton("Cancel", null)
//		.setPositiveButton("Ok", new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				//Cancel Alarms
//				AlarmManagerHelper.cancelAlarms(mContext);
//				//Delete alarm from DB by id
//				dbHelper.deleteAlarm(alarmId);
//				//Refresh the list of the alarms in the adaptor
//				mAdapter.setAlarms(dbHelper.getAlarms());
//				//Notify the adapter the data has changed
//				mAdapter.notifyDataSetChanged();
//				//Set the alarms
//				AlarmManagerHelper.setAlarms(mContext);
//			}
//		}).show();
//	}
////}

import com.swe.wakeupnow.FeedReaderContract.FeedEntry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

	private Button mCreateAlarm;
	private SharedPreferences mPrefs;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mCreateAlarm = (Button) findViewById(R.id.create_alarm);
        mCreateAlarm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlarmPreference.class);
				startActivity(i);
				finish();
			}
		});
        
//        AlarmDbHelper dbHelper = new AlarmDbHelper(getBaseContext());
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        // Define a projection that specifies which columns from the database
//        // you will actually use after this query.
//        String[] projection = {
//        		FeedEntry._ID,
//        		FeedEntry.COLUMN_NAME_HOUR,
//        		FeedEntry.COLUMN_NAME_MINUTE,
//        };
//
//        // How you want the results sorted in the resulting Cursor
//        String sortOrder = FeedEntry._ID + " DESC";
//
//        Cursor c = db.query(
//        		FeedEntry.TABLE_NAME,  // The table to query
//        		projection,                               // The columns to return
//        		"",                                // The columns for the WHERE clause
//        		null,                            // The values for the WHERE clause
//        		null,                                     // don't group the rows
//        		null,                                     // don't filter by row groups
//        		sortOrder                                 // The sort order
//        );
//        c.moveToFirst();
//        while(c.moveToNext()){
//			int id = c.getInt(0);
//			String hh = c.getString(1);
//			String mm = c.getString(2);
//			System.out.println(id);
//			System.out.println(hh);
//			System.out.println(mm);
//		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
