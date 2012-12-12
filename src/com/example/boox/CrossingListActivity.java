package com.example.boox;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CrossingListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossing_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_crossing_list, menu);
        return true;
    }
}
