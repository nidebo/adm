package com.example.bookache;

import java.util.ArrayList;

import com.example.bookache.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	ListView lvDatos;

	String strings[] = new String[]{
			"All", 
			"Wishlist",
			"Givelist",
			"Custom list 1",
			"Custom list 2",
			"Custom list 3",
			"Custom list 4",
			"Custom list 5",
			"Custom list 6",
			"Custom list 7"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvDatos = (ListView) findViewById(R.id.listView1);
    	
    	//final ArrayList<String> mLista = new ArrayList<String>();
    	final ArrayAdapter<String> mAdapter = 
    			new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings/*mLista*/);
    	lvDatos.setAdapter(mAdapter);
        
        //mLista.add("Lista Prueba 1");
        mAdapter.notifyDataSetChanged();
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
