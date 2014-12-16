package com.swe.wakeupnow;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MathGameActivity extends Activity {
	
	private AlarmScreen alarmScreen;
	
	private int mPhotoIds[] = new int [] { R.drawable.img0, R.drawable.img1, R.drawable.img2, R.drawable.img3,
			R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8,
			R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12, R.drawable.img13,
			R.drawable.img14, R.drawable.img15, R.drawable.img16, R.drawable.img17, R.drawable.img18,
			R.drawable.img19};

	private int[] answer = new int [] {11,5,28,2,3,22,4,30,6,2,26,2,7,3,18,1,13,6,36,2};
	private Button btAns1;
	private Button btAns2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_math);
		
		btAns1 = (Button) findViewById(R.id.button1);
		btAns2 = (Button) findViewById(R.id.button2);
		
		btAns1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				alarmScreen.stopMediaOrVibrate();
				finish();
			}
		});
	}
	

}
