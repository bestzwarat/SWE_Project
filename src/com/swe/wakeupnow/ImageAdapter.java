package com.swe.wakeupnow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	
	    private Context mContext;

	    public ImageAdapter(Context c ) {
	        mContext = c;
	    }

	    public int getCount() {
	        return mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new AbsListView.LayoutParams(300, 370));
	            imageView.getLayoutParams().height = 240;
				imageView.getLayoutParams().width = 200;
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//	            imageView.setPadding(4,4,4,4);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	       imageView.setImageResource(mThumbIds[position]);
	      return imageView;
	   }

	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable.zero, R.drawable.zero,
	            R.drawable.zero, R.drawable.zero,
	            R.drawable.zero, R.drawable.zero,
	            R.drawable.zero, R.drawable.zero,
	            R.drawable.zero, R.drawable.zero,
	            R.drawable.zero, R.drawable.zero,
	        
	           
	    }; 
	} 
