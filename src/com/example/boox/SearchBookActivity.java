package com.example.boox;

import com.example.boox.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SearchBookActivity extends Activity {
	
	Spinner spinner_searchby;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_book);

		spinner_searchby = (Spinner) findViewById(R.id.searchby_spinner);
		ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, 
				R.array.searchbook_searchby_options, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_searchby.setAdapter(spinner_adapter);
		spinner_searchby.setSelection(0);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_book, menu);
		return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.subitem3:
    		startActivity(new Intent(this, SettingsActivity.class));
        	return true;
    	case R.id.subitem4:
    		startActivity(new Intent(this, AboutActivity.class));
        	return true;
    	}
    	return false;
	}
	
	public void onPressSearch(View view){
		if(spinner_searchby.getSelectedItemPosition() == 0){
			EditText etxt1 = (EditText) findViewById(R.id.editText1);
			String titulo = etxt1.getText().toString();
			if(titulo != null){
				Intent i = new Intent(this, SearchBookResultActivity.class);
				i.putExtra("modo", 0);
				i.putExtra("contenido", titulo);
				startActivity(i);
			}
		}
		if(spinner_searchby.getSelectedItemPosition() == 1){
			EditText etxt1 = (EditText) findViewById(R.id.editText1);
			String autor = etxt1.getText().toString();
			if(autor != null){
				Intent i = new Intent(this, SearchBookResultActivity.class);
				i.putExtra("modo", 1);
				i.putExtra("contenido", autor);
				startActivity(i);
			}
		}
		if(spinner_searchby.getSelectedItemPosition() == 2){
			EditText etxt2 = (EditText) findViewById(R.id.editText1);
			String isbn = etxt2.getText().toString();
			if(isbn != null){
				Intent i = new Intent(this, SearchBookResultActivity.class);
				i.putExtra("modo", 2);
				i.putExtra("contenido", isbn);
				startActivity(i);
			}
			
		}
			
	}
	public void onPressScanBarcode(View view) {

		IntentIntegrator integrator = new IntentIntegrator(
				SearchBookActivity.this);
		integrator.addExtra("SCAN_WIDTH", 800);
		integrator.addExtra("SCAN_HEIGHT", 200);
		integrator.addExtra("RESULT_DISPLAY_DURATION_MS", 3000L);
		integrator.addExtra("PROMPT_MESSAGE", "Custom prompt to scan a product");
		integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
				resultCode, intent);
		if (result != null) {
			String contents = result.getContents();
			if (contents != null) {
				Intent i = new Intent(this, SearchBookResultActivity.class);
				i.putExtra("modo", 2);
				i.putExtra("contenido", contents);
				startActivity(i);
			} else {
				// showDialog(R.string.result_failed,
				// getString(R.string.result_failed_why));
			}
		}
	}
    

}
