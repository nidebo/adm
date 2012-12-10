package com.example.boox;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class DetallesLibroActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.detalles_libro);
		
		if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
			DetallesLibro booklist = new DetallesLibro();
            booklist.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
            	.beginTransaction()
            	.add(android.R.id.content, booklist)
            	.commit();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalles_libro, menu);
		return true;
	}

}
