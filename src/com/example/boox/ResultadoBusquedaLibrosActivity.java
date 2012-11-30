package com.example.boox;

import com.example.boox.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ResultadoBusquedaLibrosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultado_busqueda_libros);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.resultado_busqueda_libros, menu);
		return true;
	}

}
