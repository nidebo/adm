package com.example.boox;

import java.util.ArrayList;

import listasLibros.GestorListas;
import listasLibros.Libro;

import com.example.boox.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import apiGoogle.InterfazAPI;

public class SearchBookResultActivity extends ListActivity {
	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";
	String uname;

	ArrayList<Libro> libros = new ArrayList<Libro>();
	Libro libro = new Libro();
	int modo;
	String cont;
	Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences mySharedPreferences = this.getSharedPreferences(myPrefs, mode);
				
		uname = mySharedPreferences.getString("username", "");
		GestorListas gl = new GestorListas(uname, SearchBookResultActivity.this);
		
		Bundle extras = getIntent().getExtras();
		modo = extras.getInt("modo");
		cont = extras.getString("contenido");
		
		
		InterfazAPI api = new InterfazAPI();
		ArrayList<String> titulo = new ArrayList<String>();
		titulo.add("prueba");
		titulo.add(cont.toString());
		ArrayList<String> titulos = new ArrayList<String>();

		if(modo == 0){
			libros = api.ObtenerListaLibrosPorTitulo(cont);
			if(libros != null){
				for(int i=0; i < libros.size(); i++)
					titulos.add(libros.get(i).getTitulo());
			}
			else{
				titulos.add("No encontrado");
			}
		}
		else{
			if(modo == 1){
				libros = api.ObtenerListaLibrosPorAutor(cont);
				if(libros != null){
					for(int i=0; i < libros.size(); i++)
						titulos.add(libros.get(i).getTitulo());
				}
				else{
					titulos.add("No encontrado");
					}
			}
			else{
				if(modo == 2){
					libro = api.ObtenerLibroPorIsbn(cont);
					if(libro != null){
						titulos.add(libro.getTitulo());
					}
					else{
						titulos.add("No encontrado");
					}
				}
			}
		}
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titulos));				
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id){
		super.onListItemClick(list, view, position, id);
		if(list.getItemAtPosition(0) != "No encontrado"){
			Intent i = new Intent();
			i.setClass(context, BookActivity.class);
			if(modo == 2)
				i.putExtra("id", libro.getId());
			else
				i.putExtra("id", libros.get(position).getId());
			startActivity(i);
		}
	}
		
	
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
