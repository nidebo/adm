package com.example.boox;

import java.util.ArrayList;

import listasLibros.GestorListas;
import listasLibros.Libro;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import apiGoogle.InterfazAPI;

public class FriendsBooksActivity extends ListActivity {

	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";
	String uname;
	String fname;

	ArrayList<Libro> libros = new ArrayList<Libro>();
	Libro libro = new Libro();	
	String usuario;
	Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//SharedPreferences mySharedPreferences = this.getSharedPreferences(myPrefs, mode);
				
		//uname = mySharedPreferences.getString("username", "");
		//GestorListas gl = new GestorListas(uname, this.context);
		
		Bundle extras = getIntent().getExtras();
		fname = extras.getString("friend");		
		//InterfazAPI api = new InterfazAPI();
		//Funcion para obtener los libros
		//se meten en libros
		//for donde se muestran los titulos
		ArrayList<String> titulos = new ArrayList<String>();
		titulos.add("Libros de "+fname.toString());
		titulos.add("Prueba2");
		titulos.add("Prueba3");
		titulos.add("Prueba4");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titulos));				
	}

	/*@Override
	protected void onListItemClick(ListView list, View view, int position, long id){
		super.onListItemClick(list, view, position, id);
			Intent i = new Intent();
			i.setClass();
			i.putExtra();
			startActivity(i);
	}
	*/	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_search_book_result, menu);
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
