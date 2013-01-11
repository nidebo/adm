package com.example.boox;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class SplashScreenActivity extends Activity {
	
    // Set the display time, in milliseconds (or extract it out as a configurable parameter)
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }
    
    @Override
    protected void onResume(){
        super.onResume();
        
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        // Obtain the sharedPreference, default to true if not available
        
        boolean isSplashEnabled = true; //sp.getBoolean("isSplashEnabled", true);
 
        if (isSplashEnabled){
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    //Finish the splash activity so it can't be returned to.
                    SplashScreenActivity.this.finish();
                    // Create an Intent that will start the main activity.
                    Intent mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    SplashScreenActivity.this.startActivity(mainIntent);
                }
            }, SPLASH_DISPLAY_LENGTH);
        }else{
            // if the splash is not enabled, then finish the activity immediately and go to main.
            finish();
            Intent mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            SplashScreenActivity.this.startActivity(mainIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
        return true;
    }
}
