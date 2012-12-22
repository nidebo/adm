package com.example.boox;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CrossingListAdapter extends BaseAdapter {
	
	private ArrayList<CrossingListRow> data;
    Context c;
    
    CrossingListAdapter(ArrayList<CrossingListRow> _data, Context _c){
        data = _data;
        c = _c;
    }
   
    @Override
	public int getCount() {
        return data.size();
    }
    
    @Override
	public Object getItem(int position) {
        return data.get(position);
    }
 
    @Override
	public long getItemId(int position) {
        return position;
    }
   
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	View view = convertView;
    	if(view == null){
    		LayoutInflater li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		view = li.inflate(R.layout.activity_crossing_list_row, null);
    	}

    	ImageView thumb1 = (ImageView)view.findViewById(R.id.thumbnail1);
    	TextView title1  = (TextView) view.findViewById(R.id.title1);
    	TextView author1 = (TextView) view.findViewById(R.id.author1);
		ImageView thumb2 = (ImageView)view.findViewById(R.id.thumbnail2);
		TextView title2  = (TextView) view.findViewById(R.id.title2);
		TextView author2 = (TextView) view.findViewById(R.id.author2);
		TextView state   = (TextView) view.findViewById(R.id.state);
 
		CrossingListRow row = (CrossingListRow) data.get(position);
		
		thumb1.setImageResource(row.getThumb1());
		title1.setText(row.getTitle1());
		author1.setText(row.getAuthor1());
		thumb2.setImageResource(row.getThumb2());
		title2.setText(row.getTitle2());
		author2.setText(row.getAuthor2());   
		state.setText("State: " + row.getState());
		
		return view;
    }

}
