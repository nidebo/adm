package com.example.boox;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TabFriendsFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 1;

	String strings[] = new String[]{
			"Friend 1", 
			"Friend 2",
			"Friend 3"};

	public ArrayList<String> list = new ArrayList<String>(); 
	
	
	@Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Populate list with our static array of titles.
        if(list.isEmpty()){
        	for(int i=0; i<strings.length; ++i)
        		list.add(strings[i]);	
        	setListAdapter(new ArrayAdapter<String>(getActivity(),
        			android.R.layout.simple_list_item_1,
        			list));
        }

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        /*View detallesLibro = getActivity().findViewById(R.id.details);
        mDualPane = detallesLibro != null && detallesLibro.getVisibility() == View.VISIBLE;
    	Log.d("BooksTab", "onActivityCreated -> mDualPane = " + (mDualPane ? "true" : "false"));

        if(savedState != null){
        	Log.d("BooksTab", "savedState = null");
            // Restore last state for checked position.
            mCurCheckPosition = savedState.getInt("curChoice", 0);
        }
        if(mDualPane){
        	Log.d("BooksTab", "mDualPane = true");
            // In dual-pane mode, list view highlights selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
    	Log.d("TabFriendsFragment", "onListItemClick(" + pos + ")");
    	
    	Intent intent = new Intent();
        intent.setClass(getActivity(), UserProfileActivity.class);
        intent.putExtra("index", pos);
        startActivity(intent);
    	
        //showDetails(pos);
    }
	
	public void addItem(String s){
		list.add(s);
	}
	
}