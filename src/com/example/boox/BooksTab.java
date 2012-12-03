package com.example.boox;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BooksTab extends ListFragment {

	String strings[] = new String[]{
			"All", 
			"Wishlist",
			"Givelist",
			"Custom list 1",
			"Custom list 2",
			"Custom list 3",
			"Custom list 4",
			"Custom list 5",
			"Custom list 6",
			"Custom list 7"};
	
	//ListView lvDatos;

	//public BooksTab() {
	//}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							Bundle savedInstanceState) {
		
		ArrayAdapter<String> mAdapter = 
    			new ArrayAdapter<String>(getActivity().getBaseContext(), 
    									 android.R.layout.simple_list_item_1, 
    									 strings); //mLista);
    	setListAdapter(mAdapter);
    	
    	return super.onCreateView(inflater, container, savedInstanceState);
		
		// Create a new TextView and set its text to the fragment's section
		// number argument value.
		
		//View view = inflater.inflate(R.layout.books_tab, null);
		//return view;
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
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
