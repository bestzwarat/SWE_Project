package com.swe.wakeupnow;

import java.util.Random;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

public class MatchGameActivity extends Activity implements OnItemClickListener{
	
	MatchGameActivity gridview;
    ImageView imageView, firstView;
   
    
    private Integer[] mThumbIds = { R.drawable.one, R.drawable.one,
            R.drawable.two, R.drawable.two , R.drawable.three,
           R.drawable.three, R.drawable.four, R.drawable.four,
            R.drawable.five , R.drawable.five , R.drawable.six, 
            R.drawable.six};
    public int width;
    public int height;
    
    private int pairRight = 0;
    private int pairPic = 6;
    int opened = 0;
    int firstClick, secondClick;   
    int numColumns = 3; 
    
    
   
    @Override
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        
        shuffleArray(mThumbIds);
       
        
        android.widget.GridView gridview = (android.widget.GridView) findViewById(R.id.gridview1);
        gridview.setNumColumns(numColumns);
        gridview.setVerticalSpacing (2);
        gridview.setHorizontalSpacing (2);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setOnItemClickListener(this); 
         
        
    }
    
    
	private void shuffleArray(Integer[] mThumbIds2) {
		// TODO Auto-generated method stub
    	 Random rnd = new Random();
	        for (int i = mThumbIds2.length - 1; i >= 0; i--) {
	            int index = rnd.nextInt(i + 1);
	            // Simple swap
	            int a = mThumbIds2[index];
	            mThumbIds2[index] = mThumbIds2[i];
	            mThumbIds2[i] = a;
	        }
    }
	

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

    	imageView = (ImageView) v;
        imageView.setAdjustViewBounds(true);    
        opened++;
        
        final Handler handler = new Handler();
        if (opened == 1) {
            firstClick = position;
            firstView = (ImageView) v;
            imageView.setImageResource(mThumbIds[position]);
        } else if (opened == 2) {
        	if( firstClick != position )
        	{	
            secondClick = position;
            imageView.setImageResource(mThumbIds[position]);
            imageView.setClickable(false);
            if (mThumbIds[firstClick].compareTo(mThumbIds[secondClick]) == 0) {
                Toast.makeText(MatchGameActivity.this, "That's Great!",
                        Toast.LENGTH_SHORT).show();
                handler.postDelayed(removeImage, 500);
                pairRight++;
                if (pairRight == pairPic) {
                	//finish
                	finish();
                }
            } else {
                handler.postDelayed(cardBack, 500);
            }
        }}
    }
    private Runnable removeImage = new Runnable() {
        public void run() {
            imageView.setVisibility(View.GONE);
            firstView.setVisibility(View.GONE);
            opened = 0;
        }
    };

    private Runnable cardBack = new Runnable() {
        public void run() {
            imageView.setImageResource(R.drawable.zero);
            firstView.setImageResource(R.drawable.zero);
            opened = 0;
        }
    }; 
    
}
