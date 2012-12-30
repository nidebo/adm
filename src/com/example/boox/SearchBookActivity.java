package com.example.boox;

import java.util.ArrayList;

import listasLibros.Libro;

import com.example.boox.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import apiGoogle.InterfazAPI;

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
	public void onPressSearch(View view){
		if(spinner_searchby.getSelectedItemPosition() == 0){
			EditText etxt1 = (EditText) findViewById(R.id.editText1);
			String titulo = etxt1.getText().toString();
			if(titulo != null){
			InterfazAPI api = new InterfazAPI();
			ArrayList<Libro> libros = null;
			libros = api.ObtenerListaLibrosPorTitulo(titulo);
			TextView txt = (TextView) findViewById(R.id.textView2);
			if(libros != null)
				txt.setText(libros.get(1).getTitulo());	
			else
				txt.setText("No encontrado");
			}
		}
		if(spinner_searchby.getSelectedItemPosition() == 1){
			EditText etxt1 = (EditText) findViewById(R.id.editText1);
			String autor = etxt1.getText().toString();
			if(autor != null){
			InterfazAPI api = new InterfazAPI();
			ArrayList<Libro> libros = null;
			libros = api.ObtenerListaLibrosPorAutor(autor);
			TextView txt = (TextView) findViewById(R.id.textView2);
			if(libros != null)
				txt.setText(libros.get(1).getTitulo());	
			else
				txt.setText("No encontrado");
			}
		}
		if(spinner_searchby.getSelectedItemPosition() == 2){
			EditText etxt2 = (EditText) findViewById(R.id.editText1);
			String isbn = etxt2.getText().toString();
			if(isbn != null){
			InterfazAPI api = new InterfazAPI();
			Libro libro = new Libro();
			libro = api.ObtenerLibroPorIsbn(isbn);
			TextView txt = (TextView) findViewById(R.id.textView2);
			if(libro != null)
				txt.setText(libro.getTitulo());	
			else
				txt.setText("No encontrado");
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
				TextView isbn = (TextView) findViewById(R.id.textView2);
				isbn.setText(result.toString());
				//Llamar aqui a SearchBookResultActivity
			} else {
				// showDialog(R.string.result_failed,
				// getString(R.string.result_failed_why));
			}
		}
	}
    

}
