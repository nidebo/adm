package com.example.boox;

import internet.PruebaInternet;

import com.example.boox.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

	public void onPressScanBarcode(View view) {

		IntentIntegrator integrator = new IntentIntegrator(
				SearchBookActivity.this);
		integrator.addExtra("SCAN_WIDTH", 800);
		integrator.addExtra("SCAN_HEIGHT", 200);
		integrator.addExtra("RESULT_DISPLAY_DURATION_MS", 3000L);
		integrator.addExtra("PROMPT_MESSAGE", "Custom prompt to scan a product");
		integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);

		// Intent intent = new Intent(this, PruebaInternet.class);
		// startActivity(intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
				resultCode, intent);
		if (result != null) {
			String contents = result.getContents();
			if (contents != null) {
				// showDialog(R.string.result_succeeded, result.toString());
				TextView text = (TextView) findViewById(R.id.textView2);
				text.setText(result.toString());
			} else {
				// showDialog(R.string.result_failed,
				// getString(R.string.result_failed_why));
			}
		}
	}
    

}
