package com.example.boox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BookListActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_activity);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            BookListFragment booklist = new BookListFragment();
            booklist.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
            	.beginTransaction()
            	.add(android.R.id.content, booklist)
            	.commit();
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                Intent parentActivityIntent = new Intent(this, Tabs.class);
                parentActivityIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(parentActivityIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_activity, menu);
        return true;
    }
}
