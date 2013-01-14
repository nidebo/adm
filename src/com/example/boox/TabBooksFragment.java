package com.example.boox;

import java.util.ArrayList;

import listasLibros.GestorListas;
import listasLibros.Libro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import apiGoogle.InterfazAPI;

public class TabBooksFragment extends ListFragment {

	private static final String DEBUG_TAG = "TabBooksFragment";

    boolean mDualPane;
    int mCurCheckPosition = 0;

    AdapterView.AdapterContextMenuInfo info;

    final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";
	GestorListas gl;
	String uname = "";

	public ArrayList<String> list = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	@Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        

        SharedPreferences mySharedPreferences = this.getActivity().getSharedPreferences(myPrefs, mode);
		uname = mySharedPreferences.getString("username", "");
		//String addL = mySharedPreferences.getString("addlist", "");

		gl = new GestorListas(uname, this.getActivity());     
        ArrayList<String> listas= gl.getNombresListas();
        listas.add(0, "All");
        listas.add(1, "Crossing List");
        // Populate list with our static array of titles.
        if(list.isEmpty()){
			for(int i=0; i<listas.size(); ++i)
				list.add(listas.get(i));
			
			//if(addL.equals("true")){
		        Bundle extras = getActivity().getIntent().getExtras();
		        String nueva;
		        if(extras != null){
		        	nueva = extras.getString("addlist");
		        	list.add(nueva);
		        }
			//}

	        setListAdapter(new ArrayAdapter<String>(getActivity(),
	                android.R.layout.simple_list_item_1,
	                list));
        }
        registerForContextMenu(getListView());

    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		info = (AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Crossing options"); //(crossingList.get(info.position).getTitle2());
		menu.add(Menu.NONE, info.position, 0, "View");
		menu.add(Menu.NONE, info.position, 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getTitle() == "View"){
			Intent intent = new Intent();
			intent.setClass(getActivity(), BookListActivity.class);
	        if(item.getItemId() == 0)
	        	intent.putExtra("all", 1);
	        else if(item.getItemId() == 1)
	        		intent.putExtra("all", 2);
	        else intent.putExtra("all", 0);
	        	intent.putExtra("lista", list.get(item.getItemId()));
	        	startActivity(intent);
		}else if(item.getTitle() == "Delete"){
			if(list.get(item.getItemId()).equals("All") || list.get(item.getItemId()).equals("Crossing List") || list.get(item.getItemId()).equals("Favoritos") ){
				Toast toa = Toast.makeText(this.getActivity().getApplicationContext(), "This list can't be deleted" ,Toast.LENGTH_SHORT);
				toa.show();
			}else{
				gl.BorraLista(list.get(item.getItemId()));
				list.remove(item.getItemId());
				this.setListAdapter((new ArrayAdapter<String>(getActivity(),
		                android.R.layout.simple_list_item_1,
		                list)));				
			}
		}else {
			return false;
		}
		return true;
	}



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
    	Log.d("BooksTabFragment", "onListItemClick(" + pos + ")");
    	
    	Intent intent = new Intent();
        intent.setClass(getActivity(), BookListActivity.class);
        if(pos == 0)
        	intent.putExtra("all", 1);
        else 
        	if(pos == 1)
        		intent.putExtra("all", 2);
        	else
        		intent.putExtra("all", 0);
        intent.putExtra("lista", list.get(pos));
        startActivity(intent);
    	
        //showDetails(pos);
    }

	private void saveData() {
		Log.d(DEBUG_TAG, "saveData");
    	SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
    	Editor editor = preferences.edit();
	}
    
    private void restoreData() {
		Log.d(DEBUG_TAG, "restoreData");
    	SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
    	
    	//EditText text = (EditText) findViewById(R.id.settings_name);
    	//text.setText(preferences.getString("username", ""));
    	
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
    /*void showDetails(int index) {
    	
        mCurCheckPosition = index;

        if(mDualPane){
        	Log.d("BooksTab", "showDetails(DualPane");
            // We can display everything in-place with fragments.
            // Have the list highlight this item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is shown, replace if needed.
            DetallesLibro bookDetails = (DetallesLibro) getFragmentManager().findFragmentById(R.id.details);
            if (bookDetails == null || bookDetails.getShownIndex() != index) {
                // Make new fragment to show this selection.
                bookDetails = DetallesLibro.newInstance(index);

                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, bookDetails);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
        	Log.d("BooksTab", "showDetails(MonoPane)");
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetallesLibro.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }*/

	//@Override
	//public void onStart(){
		//super.onStart();
		//getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
	//}

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
