package com.example.boox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UserProfileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String uname = extras.getString("uname");
            TextView id = (TextView) findViewById(R.id.userprofile_username);
            id.setText(uname);
            String full = extras.getString("full");
            TextView id2 = (TextView) findViewById(R.id.userprofile_fullname);
            id2.setText(full);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user_profile, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
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
    	return false;
	}
}
