package com.example.boox;

import com.example.boox.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BuscarLibrosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscar_libros);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buscar_libros, menu);
		return true;
	}

}
