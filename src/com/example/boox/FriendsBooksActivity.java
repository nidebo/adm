package com.example.boox;

import internet.ListaServer;

import java.util.ArrayList;

import listasLibros.Libro;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import apiGoogle.InterfazAPI;

public class FriendsBooksActivity extends ListActivity {

	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";
	String uname;
	String fname;

    AdapterView.AdapterContextMenuInfo info;
	ArrayList<Libro> libros = new ArrayList<Libro>();	
	String usuario;
	Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

		
		SharedPreferences mySharedPreferences = this.getSharedPreferences(myPrefs, mode);
				
		uname = mySharedPreferences.getString("username", "");
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
				libros.add(lib);
				titulos.add(lib.getTitulo());
			}
		}
		this.getActionBar().setTitle("Libros de "+fname);
        registerForContextMenu(getListView());
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titulos));
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		//ArrayList<String> misListas = gl.getNombresListas();
		info = (AdapterContextMenuInfo) menuInfo;
		//menu.setHeaderTitle("Add to:"); 
		menu.add(Menu.NONE, info.position, 0, R.string.crossing_proposal);
		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		//proponer crossing
		
		Toast toast = Toast.makeText(this.getApplicationContext(), "PENE PENE PENE", Toast.LENGTH_SHORT);
		toast.show();
		
		
		return true;
	}

	

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id){
		super.onListItemClick(list, view, position, id);
		MyBD mbd = new MyBD(this,uname);
		mbd.BorrarTemporal();
		mbd.InsertarTemporal(libros.get(position));
		Intent i = new Intent();
		i.setClass(context, BookActivity.class);
		i.putExtra("id", libros.get(position).getId());
		i.putExtra("temp", 1);
		startActivity(i);
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_search_book_result, menu);
		return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case android.R.id.home:
            // This is called when the Home (Up) button is pressed
            // in the Action Bar.
            Intent parentActivityIntent = new Intent(this, TabsActivity.class);
            parentActivityIntent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            finish(); 
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
    	return false;
	}
}
