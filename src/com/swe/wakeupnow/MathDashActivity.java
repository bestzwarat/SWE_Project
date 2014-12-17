package com.swe.wakeupnow;

import com.swe.wakeupnow.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MathDashActivity extends Activity {
	

	private int correct = 0;
	private int total = 7;
	private TextView solvedTextView;
	private TextView answerTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mathdash);

		refreshEquation();
		
	}

	private void refreshEquation() {
		final RandomEquation randomEquation = new RandomEquation();

		answerTextView = (TextView) findViewById(R.id.gameEquation);
		answerTextView.setText(randomEquation.equation);
		solvedTextView = (TextView) findViewById(R.id.gameSolved);
		solvedTextView.setText(correct + "/" + total);

		View.OnClickListener clickListener = new View.OnClickListener() {

			public void onClick(View v) {
				if (((Button) v).getText() == Integer.toString(randomEquation.answer)) {
					correct += 1;
					if (correct == total) {
						//finish
						Toast.makeText(getBaseContext(), "Wake Up Now!!!", Toast.LENGTH_LONG).show();
						finish();
					}
				}
				solvedTextView.setText(correct + "/" + total);
				refreshEquation();
			}
		};

		Button gameButton1 = (Button) findViewById(R.id.gameButton1);
		gameButton1.setText(Integer.toString(randomEquation.answer1));
		gameButton1.setOnClickListener(clickListener);

		Button gameButton2 = (Button) findViewById(R.id.gameButton2);
		gameButton2.setText(Integer.toString(randomEquation.answer2));
		gameButton2.setOnClickListener(clickListener);

		Button gameButton3 = (Button) findViewById(R.id.gameButton3);
		gameButton3.setText(Integer.toString(randomEquation.answer3));
		gameButton3.setOnClickListener(clickListener);

		Button gameButton4 = (Button) findViewById(R.id.gameButton4);
		gameButton4.setText(Integer.toString(randomEquation.answer4));
		gameButton4.setOnClickListener(clickListener);
	}
}
