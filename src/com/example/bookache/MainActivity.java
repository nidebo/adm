package com.example.bookache;

import com.example.bookache.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Creo la barra para meter los tabs
        final ActionBar tabsbar = getActionBar();
        tabsbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // tab listener para cuando el usuario las toca.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
        // Add 3 tabs.
            tabsbar.addTab(tabsbar.newTab().setText(R.string.books).setTabListener(tabListener));
            tabsbar.addTab(tabsbar.newTab().setText(R.string.crossing).setTabListener(tabListener));
            tabsbar.addTab(tabsbar.newTab().setText(R.string.friends).setTabListener(tabListener));
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
    
}
