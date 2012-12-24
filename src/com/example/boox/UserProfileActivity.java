package com.example.boox;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UserProfileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user_profile, menu);
        return true;
    }
}
