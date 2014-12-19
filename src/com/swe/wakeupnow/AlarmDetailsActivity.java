package com.swe.wakeupnow;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmDetailsActivity extends Activity {
	
	private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
	
	private AlarmModel alarmDetails;
	
	private TimePicker timePicker;
	private EditText edtName;
	private CustomSwitch chkWeekly;
	private CustomSwitch chkSunday;
	private CustomSwitch chkMonday;
	private CustomSwitch chkTuesday;
	private CustomSwitch chkWednesday;
	private CustomSwitch chkThursday;
	private CustomSwitch chkFriday;
	private CustomSwitch chkSaturday;
	private TextView txtToneSelection;
	private CheckBox checkBox;
	private TextView txtGameSelection;
	private AlertDialog toneDialog;
	private AlertDialog gameDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		
		setContentView(R.layout.activity_details);

		getActionBar().setTitle("Create New Alarm");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		timePicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
		edtName = (EditText) findViewById(R.id.alarm_details_name);
		chkWeekly = (CustomSwitch) findViewById(R.id.alarm_details_repeat_weekly);
		chkSunday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_sunday);
		chkMonday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_monday);
		chkTuesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_tuesday);
		chkWednesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_wednesday);
		chkThursday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_thursday);
		chkFriday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_friday);
		chkSaturday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_saturday);
		txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);
		checkBox = (CheckBox) findViewById(R.id.checkbox_vibrate);
		txtGameSelection = (TextView) findViewById(R.id.alarm_label_game_selection);
		long id = getIntent().getExtras().getLong("id");
		
		if (id == -1) {
			alarmDetails = new AlarmModel();
		} else {
			alarmDetails = dbHelper.getAlarm(id);
			
			timePicker.setCurrentMinute(alarmDetails.timeMinute);
			timePicker.setCurrentHour(alarmDetails.timeHour);
			
			edtName.setText(alarmDetails.name);
			
			chkWeekly.setChecked(alarmDetails.repeatWeekly);
			chkSunday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SUNDAY));
			chkMonday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.MONDAY));
			chkTuesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.TUESDAY));
			chkWednesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.WEDNESDAY));
			chkThursday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.THURSDAY));
			chkFriday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.FRIDAY));
			chkSaturday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SATURDAY));
			txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
			txtGameSelection.setText(alarmDetails.game);
			checkBox.setChecked(alarmDetails.vibrate);
		}

		final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
		ringToneContainer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createToneDialog();
			}
		});
		
		final LinearLayout vibrateContainer = (LinearLayout) findViewById(R.id.alarm_vibrate_container);
		vibrateContainer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (v != null) {
			        checkBox.setChecked(!checkBox.isChecked());
			        if (checkBox.isChecked()) {
			        	Vibrator vibrator = ((Vibrator) getSystemService(VIBRATOR_SERVICE));
			        	vibrator.vibrate(800);
			        	alarmDetails.vibrate = true;
			        }
			        else {
			        	alarmDetails.vibrate = false;
			        }
			    }
			}
		});
		
		final LinearLayout gameContainer = (LinearLayout) findViewById(R.id.alarm_game_container);
		gameContainer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createGameDialog();
			}
		});
	}
	
	private void createToneDialog() {
		final CharSequence[] items = {"Select form your phone","Record"};

        // Creating and Building the Dialog 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose alarm tone from");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
        	Intent intent;
            switch(item)
            {
                case 0:
                	intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
    				startActivityForResult(intent , 1);
                    break;
                case 1:
                	intent = new Intent(AlarmDetailsActivity.this, RecordActivity.class);
                	startActivityForResult(intent , 2);
                    break;
            }
            toneDialog.dismiss();
            }
        });
        toneDialog = builder.create();
        toneDialog.show();
	}
	
	private void createGameDialog() {
		final CharSequence[] items = {"None","Random Game","Calculation Game","Picture Pair Game","Tic Tac Toe Game"};

        // Creating and Building the Dialog 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your game");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
           
            switch(item)
            {
                case 0:
                	txtGameSelection.setText(R.string.game_none);
                	alarmDetails.game = "None";
                    break;
                case 1:
                	txtGameSelection.setText(R.string.game_random);
                	alarmDetails.game = "Random Game";
                    break;
                case 2:
                	txtGameSelection.setText(R.string.game_math);
                	alarmDetails.game = "Calculation Game";
                    break;
                case 3:
                	txtGameSelection.setText(R.string.game_match);
                	alarmDetails.game = "Picture Pair Game";
                    break;
                case 4:
                	txtGameSelection.setText(R.string.game_ttt);
                	alarmDetails.game = "Tic Tac Toe Game";
                    break;
            }
            gameDialog.dismiss();
            }
        });
        gameDialog = builder.create();
        gameDialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
		        switch (requestCode) {
			        case 1: {			        	
			        	alarmDetails.alarmTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
//			        	Toast.makeText(getBaseContext(), alarmDetails.alarmTone.toString(), Toast.LENGTH_LONG).show();
			        	txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
			            break;
			        }
			        default: {
			            break;
			        }
		        }
		    }
		}
		if (requestCode == 2) {
			if (resultCode == RESULT_OK) {
				String path = data.getStringExtra("result");
				alarmDetails.alarmTone = Uri.fromFile(new File(path));
//				Toast.makeText(getBaseContext(), alarmDetails.alarmTone.toString(), Toast.LENGTH_LONG).show();
				txtToneSelection.setText("Your sound record");
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alarm_details, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home: {
				finish();
				break;
			}
			case R.id.action_save_alarm_details: {
				if (alarmDetails.alarmTone == null) {
					Toast.makeText(getBaseContext(), R.string.alert_ringtone, Toast.LENGTH_LONG).show();
				}
				else {
					if (alarmDetails.game == null) {
						Toast.makeText(getBaseContext(), R.string.alert_game, Toast.LENGTH_LONG).show();
					}
					else {
						updateModelFromLayout();
						
						AlarmManagerHelper.cancelAlarms(this);
						
						if (alarmDetails.id < 0) {
							dbHelper.createAlarm(alarmDetails);
						} else {
							dbHelper.updateAlarm(alarmDetails);
						}
						
						AlarmManagerHelper.setAlarms(this);
						
						setResult(RESULT_OK);
						finish();
					}
				}
			}
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void updateModelFromLayout() {
		alarmDetails.timeMinute = timePicker.getCurrentMinute().intValue();
		alarmDetails.timeHour = timePicker.getCurrentHour().intValue();
		alarmDetails.name = edtName.getText().toString();
		alarmDetails.repeatWeekly = chkWeekly.isChecked();
		alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, chkSunday.isChecked());	
		alarmDetails.setRepeatingDay(AlarmModel.MONDAY, chkMonday.isChecked());	
		alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, chkTuesday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, chkWednesday.isChecked());	
		alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, chkThursday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.FRIDAY, chkFriday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, chkSaturday.isChecked());
		
		if (alarmDetails.getRepeatingDay(0) == false && alarmDetails.getRepeatingDay(1) == false
				&& alarmDetails.getRepeatingDay(2) == false && alarmDetails.getRepeatingDay(3) == false
				&& alarmDetails.getRepeatingDay(4) == false && alarmDetails.getRepeatingDay(5) == false
						&& alarmDetails.getRepeatingDay(6) == false) {
//			Toast.makeText(getBaseContext(), "Don't has a check", Toast.LENGTH_SHORT).show();
			Calendar c = Calendar.getInstance();
			int currentDay = c.get(Calendar.DAY_OF_WEEK);
			int currentHour = c.get(Calendar.HOUR_OF_DAY);
			int currentMin = c.get(Calendar.MINUTE);
			if (timePicker.getCurrentHour().intValue() < currentHour) {
				alarmDetails.setRepeatingDay(currentDay,true);
			}
			else if (timePicker.getCurrentHour().intValue() < currentHour) {
				if (timePicker.getCurrentMinute().intValue() <= currentMin) {
					alarmDetails.setRepeatingDay(currentDay,true);
				}
				else {
					alarmDetails.setRepeatingDay(currentDay-1,true);
				}
			}
			else {
				alarmDetails.setRepeatingDay(currentDay-1,true);
			}
		}
		alarmDetails.isEnabled = true;
	}
}
