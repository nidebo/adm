package com.example.boox;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class BookListActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_activity);
        
        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            BookList booklist = new BookList();
            booklist.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
            	.beginTransaction()
            	.add(android.R.id.content, booklist)
            	.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_activity, menu);
        return true;
    }
}
