package com.example.boox;

import internet.PruebaInternet;
import usuarios.Usuario;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Main extends Activity {
	///////////////////////////////
	public Usuario usuarioActual= new Usuario("UsuarioDePrueba", 46015, "contrasenya"); 
	///////////////////////////////
		
	//private TabHost mTabHost;
    //private Resources mResources;
 
    //private static final String TAG_SCHEDULED = "Scheduled";
    //private static final String TAG_CREATE = "Create";
    //private static final String TAG_OPTIONS = "Options";
    //private static final String PREF_STICKY_TAB = "stickyTab";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        //mTabHost = getTabHost();
        //mResources = getResources(); 
        
        //addBooksTab();  
        //addCrossingsTab();
        //addFriendsTab();

        //mTabHost.setCurrentTabByTag("BooksTabActivity");
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	    	case R.id.subitem3:
	    		startActivity(new Intent(this, SettingsActivity.class));
	        	return true;
	    	}
    	return false;
	}

	public void onPressNotAUser(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, SignUpActivity.class);
    	startActivity(intent);
    }

	public void onPressNico(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, PruebaInternet.class);
    	startActivity(intent);
    }
    public void onPressEliu(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, TabsActivity.class);
       	startActivity(intent);
    }
    public void onPressMario(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    
    /*private void addBooksTab() {
    	 
    	Intent intent = new Intent(this, BooksTabFragment.class);
 
        TabSpec spec = mTabHost.newTabSpec(TAG_SCHEDULED);
        spec.setIndicator(mResources.getString(R.string.title_activity_books_tab));
        spec.setContent(intent);
 
        mTabHost.addTab(spec);
    }*/
    
}
