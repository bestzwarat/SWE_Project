package com.swe.wakeupnow;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class RingtonePreference extends DialogPreference {
	
	private RadioGroup mRg;
	private RadioButton[] mRb;
	private RadioButton selectedRb;
	private TextView ringtoneDisplay;
	private LinearLayout mLinearLayout;

	public RingtonePreference(Context ctxt) {
        this(ctxt, null);
    }

	public RingtonePreference(Context ctxt, AttributeSet attrs) {
		super(ctxt, attrs);
        setNegativeButtonText("Cancel");
	}
	
	private void showRdButton() {
//		String music[] = {"Payphone.mp3", "Back to December.flac", "ไม่บอกเธอ.mp3", "a.ogg"};
//		String[] music = getMusic();
		String[] music = getMusicFrRaw();
		
		// create radio button
		mRb = new RadioButton[music.length];
		mRg = new RadioGroup(getContext());
		mRg.setOrientation(RadioGroup.VERTICAL);
		for (int i = 0; i < music.length; i++) {
			mRb[i] = new RadioButton(getContext());
			mRg.addView(mRb[i]);
			String nameMusic = music[i].substring(0,1).toUpperCase().concat(music[i].substring(1,music[i].length()));
			mRb[i].setText(nameMusic);
		}
		mLinearLayout.addView(mRg);
	}
	
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
	
	@Override
    protected View onCreateDialogView() {	
		mLinearLayout = new LinearLayout(getContext().getApplicationContext());
		mRg=new RadioGroup(getContext().getApplicationContext());
        return(mRg);
    }

    @Override
    protected void onBindDialogView(View v) {
    	super.onBindDialogView(v);
    	showRdButton();
    }

//    @Override
//    protected View onCreateView (ViewGroup parent) {
//         View prefView = super.onCreateView(parent);
//         LinearLayout mLinearLayout = new LinearLayout(parent.getContext());
//         LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT, 2);
//         mLinearLayout.addView(prefView, lp);
////         ringtoneDisplay = new TextView(parent.getContext());
////         ringtoneDisplay.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
////         ringtoneDisplay.setText(toString());
////         ringtoneDisplay.setTextSize(30);
////         LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT, 1);
////         layout.addView(ringtoneDisplay, lp2);
//         return mLinearLayout;
//    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

//        if (positiveResult) {
//            picker.clearFocus();
//            lastHour=picker.getCurrentHour();
//            lastMinute=picker.getCurrentMinute();
//
//            String ringtone=String.valueOf(lastHour)+":"+String.valueOf(lastMinute);
//
//            if (callChangeListener(ringtone)) {
//                persistString(ringtone);
//                ringtoneDisplay.setText(toString());
//            }
//			Toast.makeText(getContext(), "Choose" + toString(), Toast.LENGTH_SHORT).show();
//        }
    }

//    @Override
//    protected Object onGetDefaultValue(TypedArray a, int index) {
//        return(a.getString(index));
//    }
//
//    @Override
//    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
//        String ringtone=null;
//
//        if (restoreValue) {
//            if (defaultValue==null) {
//            	ringtone=getPersistedString("None");
//            }
//            else {
//            	ringtone=getPersistedString(defaultValue.toString());
//            }
//        }
//        else {
//            if (defaultValue==null) {
//            	ringtone="None";
//            }
//            else {
//            	ringtone=defaultValue.toString();
//            }
//            if (shouldPersist()) {
//                persistString(ringtone);
//            }
//        }
//
//    }

    
}
