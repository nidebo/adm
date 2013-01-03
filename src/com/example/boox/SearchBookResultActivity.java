package com.example.boox;

import java.util.ArrayList;
import listasLibros.Libro;

import com.example.boox.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import apiGoogle.InterfazAPI;

public class SearchBookResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_book_result);
		ListView lv = (ListView) findViewById(R.id.ListView1);
		
		Bundle extras = getIntent().getExtras();
		int modo = extras.getInt("modo");
		String cont = extras.getString("contenido");
		
		
		InterfazAPI api = new InterfazAPI();
		String[] titulos = {" ", " ", " " ," ", " ", " " ," ", " ", " " ," "};
		ArrayList<Libro> libros = null;

		
		if(modo == 0){
			libros = api.ObtenerListaLibrosPorTitulo(cont);
			if(libros != null){
				for(int i=0; i < libros.size(); i++)
					titulos[i] = libros.get(i).getTitulo();
				lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titulos));
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
						titulos[i] = libros.get(i).getTitulo();
					lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titulos));
				}
				else{
					Toast to = new Toast(this);
					to.setText("No encontrado");
					to.show();
					}
			}
			else{
				if(modo == 2){
					Libro libro = new Libro();
					libro = api.ObtenerLibroPorIsbn(cont);
					if(libro != null){
						titulos[0] = libro.getTitulo();
						lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titulos));
					}
					else{
						Toast to = new Toast(this);
						to.setText("No encontrado");
						to.show();
					}
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_search_book_result, menu);
		return true;
	}

}
