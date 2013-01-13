package com.example.boox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

public class BookActivity extends FragmentActivity {
	/*
    final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";
	String uname = "";
	String id;
	Libro lib = new Libro();
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.detalles_libro);	
		//Activate the up button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
        //SharedPreferences mySharedPreferences = this.getParent().getSharedPreferences(myPrefs, mode);
		//uname = mySharedPreferences.getString("username", "");
        //GestorListas gl = new GestorListas(uname); 
        //Bundle extras = this.getIntent().getExtras();
        //id = extras.getString("id");
        //lib = gl.getLibroPorId(id);
		
		if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
			BookFragment booklist = new BookFragment();
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
		getMenuInflater().inflate(R.menu.activity_book_details, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                Intent parentActivityIntent = new Intent(this, BookListActivity.class);
                parentActivityIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(parentActivityIntent);
                finish();
                return true;
            case R.id.submenu_share:
            	Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            	sharingIntent.setType("text/html");
            	sharingIntent.putExtra(
            			android.content.Intent.EXTRA_TEXT, 
            			Html.fromHtml("<p>elcresan just viewed Juego de Tronos on BooX</p>"));
            	startActivity(Intent.createChooser(sharingIntent, "Share with"));
            	return true;
        	case R.id.search:
        		startActivity(new Intent(this, SearchBookActivity.class));
                return true;
        	case R.id.submenu_settings:
        		startActivity(new Intent(this, SettingsActivity.class));
            	return true;
        	case R.id.submenu_about:
        		startActivity(new Intent(this, AboutActivity.class));
            	return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
