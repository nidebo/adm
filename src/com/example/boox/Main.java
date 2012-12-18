package com.example.boox;

import usuarios.Usuario;
import internet.PruebaInternet;

import com.example.boox.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Main extends TabActivity {
	///////////////////////////////
	public Usuario usuarioActual= new Usuario("UsuarioDePrueba", 46015, "contraseña"); 
	///////////////////////////////
	private TabHost mTabHost;
    private Resources mResources;
 
    private static final String TAG_SCHEDULED = "Scheduled";
    private static final String TAG_CREATE = "Create";
    private static final String TAG_OPTIONS = "Options";
    private static final String PREF_STICKY_TAB = "stickyTab";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        mTabHost = getTabHost();
        mResources = getResources(); 
        
        //addBooksTab();  
        //addCrossingsTab();
        //addFriendsTab();

        mTabHost.setCurrentTabByTag("BooksTabActivity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onPressNico(View view) {
        // Do something in response to button
        	Intent intent = new Intent(this, PruebaInternet.class);
        	startActivity(intent);
        }
    public void onPressEliu(View view) {
        // Do something in response to button
        	Intent intent = new Intent(this, Tabs.class);
        	startActivity(intent);
        }
    public void onPressMario(View view) {
        // Do something in response to button
        	Intent intent = new Intent(this, BookDetailsActivity.class);
        	startActivity(intent);
        }
    
    private void addBooksTab() {
    	 
    	Intent intent = new Intent(this, BooksTabFragment.class);
 
        TabSpec spec = mTabHost.newTabSpec(TAG_SCHEDULED);
        spec.setIndicator(mResources.getString(R.string.title_activity_books_tab));
        spec.setContent(intent);
 
        mTabHost.addTab(spec);
    }
    
}
