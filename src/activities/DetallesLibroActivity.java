package activities;

import com.example.bookache.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DetallesLibroActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalles_libro);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalles_libro, menu);
		return true;
	}

}
