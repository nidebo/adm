package com.example.boox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class CrossingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossing);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_crossing, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); //NavUtils.navigateUpFromSameTask(this);
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
