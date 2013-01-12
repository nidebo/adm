package com.example.boox;

import java.util.ArrayList;
import listasLibros.Libro;

import com.example.boox.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import apiGoogle.InterfazAPI;

public class SearchBookResultActivity extends ListActivity {
	ArrayList<Libro> libros = new ArrayList<Libro>();
	Libro libro = new Libro();
	int modo;
	String cont;
	Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		modo = extras.getInt("modo");
		cont = extras.getString("contenido");
		
		
		InterfazAPI api = new InterfazAPI();
		ArrayList<String> titulos = new ArrayList<String>();


		if(modo == 0){
			libros = api.ObtenerListaLibrosPorTitulo(cont);
			if(libros != null){
				for(int i=0; i < libros.size(); i++)
					titulos.add(libros.get(i).getTitulo());
			}
			else{
				Toast to = new Toast(this);
				to.setText("No encontrado");
				to.show();
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
					Toast to = new Toast(this);
					to.setText("No encontrado");
					to.show();
					}
			}
			else{
				if(modo == 2){
					libro = api.ObtenerLibroPorIsbn(cont);
					if(libro != null){
						titulos.add(libro.getTitulo());
					}
					else{
						Toast to = new Toast(this);
						to.setText("No encontrado");
						to.show();
					}
				}
			}
		}
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titulos));				
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id){
		super.onListItemClick(list, view, position, id);
        Intent i = new Intent();
        i.setClass(context, BookActivity.class);
        if(modo == 2)
        	i.putExtra("isbn", libro.getIsbn());
        else
        	i.putExtra("isbn", libros.get(position).getIsbn());
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
