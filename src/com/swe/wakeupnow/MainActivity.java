package com.swe.wakeupnow;

import com.swe.wakeupnow.FeedReaderContract.FeedEntry;

import android.app.Activity;
import android.content.Intent;
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
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mCreateAlarm = (Button) findViewById(R.id.create_alarm);
        mCreateAlarm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlarmSetting.class);
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
