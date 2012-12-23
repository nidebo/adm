package com.example.boox;

import com.example.boox.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchBookActivity extends Activity {
	
	Spinner searchby;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_book);

		searchby = (Spinner) findViewById(R.id.searchby_spinner);
		ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, 
				R.array.searchbook_searchby_options, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchby.setAdapter(spinner_adapter);
		searchby.setSelection(0);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_book, menu);
		return true;
	}

}
