package com.example.boox;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BookListFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 0; 

	String books[] = new String[]{
			"Juego de Tronos", 
			"Choque de Reyes",
			"Tormenta de Espadas",
			"Fest’n de Cuervos",
			"Danza de Dragones",
			"Dr‡cula",
			"El Conde de Montecristo",
			"Los Tres Mosqueteros",
			"El Hobbit",
			"El Se–or de los Anillos"};

	public ArrayList<String> booklist = new ArrayList<String>();
	
	
	@Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Populate list with our static array of titles.
		for(int i=0; i<books.length; ++i)
			booklist.add(books[i]);	
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                booklist));

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detallesLibro = getActivity().findViewById(R.id.details);
        mDualPane = detallesLibro != null && detallesLibro.getVisibility() == View.VISIBLE;
    	Log.d("BookList", "onActivityCreated -> mDualPane = " + (mDualPane ? "true" : "false"));

        if(savedState != null){
        	Log.d("BookList", "savedState = null");
            // Restore last state for checked position.
            mCurCheckPosition = savedState.getInt("curChoice", 0);
        }
        if(mDualPane){
        	Log.d("BookList", "mDualPane = true");
            // In dual-pane mode, list view highlights selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
    	Log.d("BookList", "onListItemClick(" + pos + ")");
        showDetails(pos);
    }

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		for(int i=0; i<strings.length; ++i)
			list.add(strings[i]);	
		ArrayAdapter<String> mAdapter = 
    			new ArrayAdapter<String>(getActivity().getBaseContext(), 
    									 android.R.layout.simple_list_item_1, 
    									 list);
		setListAdapter(mAdapter);
    	return super.onCreateView(inflater, container, savedInstanceState);
	}*/
    
    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
    	
        mCurCheckPosition = index;

        if(mDualPane){
        	Log.d("BookList", "showDetails(DualPane)");
            // We can display everything in-place with fragments.
            // Have the list highlight this item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is shown, replace if needed.
            BookFragment bookDetails = (BookFragment) getFragmentManager().findFragmentById(R.id.details);
            if (bookDetails == null || bookDetails.getShownIndex() != index) {
                // Make new fragment to show this selection.
                bookDetails = BookFragment.newInstance(index);

                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, bookDetails);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
        	Log.d("BookList", "showDetails(MonoPane)");
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), BookActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

	//@Override
	//public void onStart(){
		//super.onStart();
		//getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
	//}
	
	public void addItem(String s){
		booklist.add(s);
	}

	/*
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_tab);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        
        lvDatos = (ListView) findViewById(R.id.listView1);
    	
    	//final ArrayList<String> mLista = new ArrayList<String>();
    	final ArrayAdapter<String> mAdapter = 
    			new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings); //mLista);
    	lvDatos.setAdapter(mAdapter);
        
        //mLista.add("Lista Prueba 1");
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_books_tab, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
