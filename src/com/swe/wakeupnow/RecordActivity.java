package com.swe.wakeupnow;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RecordActivity extends Activity {
	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = null;

	private Button mRecordButton;
	private MediaRecorder mRecorder = null;

	private Button mPlayButton;
	private MediaPlayer mPlayer = null;
	
	private Button mChooseButton;
	private int numRec = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		
		mRecordButton = (Button) findViewById(R.id.record_button);
		mPlayButton = (Button) findViewById(R.id.play_button);
		mChooseButton = (Button) findViewById(R.id.choose_button);
        
        mRecordButton.setOnClickListener(new OnClickListener() {
        	boolean mStartRecording = true;
        	@Override
            public void onClick(View v) {
        		numRec++;
                onRecord(mStartRecording);
                if (mStartRecording) {
                	mRecordButton.setText(R.string.record_stop);
                } else {
                	mRecordButton.setText(R.string.record_start);
                }
                mStartRecording = !mStartRecording;
            }
        });
        
        
        mPlayButton.setOnClickListener(new OnClickListener() {
        	boolean mStartPlaying = true;
        	@Override
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                	mPlayButton.setText(R.string.record_calcel);
                } else {
                	mPlayButton.setText(R.string.record_play);
                }
                mStartPlaying = !mStartPlaying;
            }
        });
        
        mChooseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (numRec != 0){
					Intent returnIntent = new Intent();
					String result = fileName();
					returnIntent.putExtra("result",result);
					setResult(RESULT_OK,returnIntent);
					finish();
				}
				else {
					Toast.makeText(getBaseContext(), "Please record your sound", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	@Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

	private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(fileName());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    
    private String fileName() {
    	mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        return mFileName += "/YourRecord.mp3";
    }
}
