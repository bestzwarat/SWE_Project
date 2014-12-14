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

	public RingtonePreference(Context ctxt) {
        this(ctxt, null);
    }

	public RingtonePreference(Context ctxt, AttributeSet attrs) {
		super(ctxt, attrs);
		setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
	}
	
	@Override
    protected View onCreateDialogView() {
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
        return mRg;
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
    }

//    @Override
//    protected View onCreateView (ViewGroup parent) {
//         View prefView = super.onCreateView(parent);
//         LinearLayout layout = new LinearLayout(parent.getContext());
//         LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT, 2);
//         layout.addView(prefView, lp);
//         timeDisplay = new TextView(parent.getContext());
//         timeDisplay.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
//         timeDisplay.setText(toString());
//         timeDisplay.setTextSize(30);
//         LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT, 1);
//         layout.addView(timeDisplay, lp2);
//         return layout;
//    }
//
//    @Override
//    protected void onDialogClosed(boolean positiveResult) {
//        super.onDialogClosed(positiveResult);
//
//        if (positiveResult) {
//            picker.clearFocus();
//            lastHour=picker.getCurrentHour();
//            lastMinute=picker.getCurrentMinute();
//
//            String time=String.valueOf(lastHour)+":"+String.valueOf(lastMinute);
//
//            if (callChangeListener(time)) {
//                persistString(time);
//                timeDisplay.setText(toString());
//            }
//			Toast.makeText(getContext(), "Set time to " + toString(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected Object onGetDefaultValue(TypedArray a, int index) {
//        return(a.getString(index));
//    }
//
//    @Override
//    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
//        String time=null;
//
//        if (restoreValue) {
//            if (defaultValue==null) {
//                time=getPersistedString("00:00");
//            }
//            else {
//                time=getPersistedString(defaultValue.toString());
//            }
//        }
//        else {
//            if (defaultValue==null) {
//                time="00:00";
//            }
//            else {
//                time=defaultValue.toString();
//            }
//            if (shouldPersist()) {
//                persistString(time);
//            }
//        }
//
//        String[] timeParts=time.split(":");
//        lastHour=Integer.parseInt(timeParts[0]);
//        lastMinute=Integer.parseInt(timeParts[1]);;
//    }
//
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
