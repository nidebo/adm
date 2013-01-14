package com.example.boox;

import internet.ListaServer;

import java.util.ArrayList;

import listasLibros.Libro;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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
		InterfazAPI api = new InterfazAPI();

		ArrayList<Libro> libs = new ArrayList<Libro>();
		ListaServer ls = new ListaServer();
		Libro lib = new Libro();
		ArrayList<String> compartibles = new ArrayList<String>();
		ArrayList<String> titulos = new ArrayList<String>();
		compartibles = ls.obtenerLibrosLista(fname, "adminLibrosPorUsuario");
		for(int i=0; i< compartibles.size(); i++){
			lib = api.ObtenerLibroPorId(compartibles.get(i));
			if(lib != null){
				libs.add(lib);
				titulos.add(lib.getTitulo());
			}
		}
	
		titulos.add(0,"Libros de "+fname.toString());
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
