package apiGoogle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.TextView;


import com.example.boox.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import zzzDuplicados.BookAPI;
import zzzDuplicados.BookList;

import listasLibros.Libro;

public class InterfazAPI {
	
	Libro libro1;
	ArrayList<Libro> ListaDeLibros;
	
	public InterfazAPI(){
		////////  LIBROS DE PRUEBA  ///////////
		/*for (int i=1;i<6;i++){
			Random generator = new Random();
			ArrayList<String> autores=new ArrayList<String>();
			autores.add("Franz Beckenbauer");
			autores.add("David Albelda");
			ArrayList<String> categorias=new ArrayList<String>();
			categorias.add("Ficción");
			categorias.add("Aventuras");

			libro1=new Libro("00"+generator.nextInt());

			libro1.setAutores(autores);
			libro1.setCategorias(categorias);
			libro1.setDescripcion(" Descripción del libro bla bla bla \n Lineanueva bla bla bla Batman!");
			libro1.setEditorial("Editorial Lluis Vives");
			libro1.setFechaPublicacion("21 diciembre de 2012");
			libro1.setIdioma("Franchute");
			libro1.setNumeroDePaginas(435);
			libro1.setPais("Francia");
			libro1.setPuntuacionMedia((float) 7.23);
			libro1.setTitulo("The lord de los anillos");
			ListaDeLibros.add(libro1);
		}*/
		///////// FIN LIBROS DE PRUEBA ///////////
	}
	public Libro ObtenerLibroPorId(String Id){
		
		return libro1;	
	}
	public BookAPI ObtenerLibroPorIsbn(String isbn){
		BookAPI book = new BookAPI();
		AsyncBookIsbn ab = new AsyncBookIsbn();	
		try {
			book = ab.execute(isbn).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;	
	}
	public ArrayList<Libro> ObtenerListaLibrosPopulares(){
		
		return ListaDeLibros;
	}
public ArrayList<Libro> ObtenerListaLibrosPorAutor(String autor){
		
		return ListaDeLibros;
	}
public ArrayList<Libro> ObtenerListaLibrosPorBusqueda(String busqueda){
	//No se como funcionará lo de la api, pero de alguna forma se podrán buscar libros,
	//si hay que hacerlo por separado o no ya no lo sé, simplemente añade más funciones
	//si hace falta, en plan buscar por editorial o por cualquier campo que pueda ser util
	return ListaDeLibros;
}
public class AsyncBookIsbn extends AsyncTask<String, Void, BookAPI> {

	//boolean cool = true;
	StringBuilder sb;
	String responseString;
	String sturl;
	
	@Override
	protected BookAPI doInBackground(String... isbn) {
		// TODO Auto-generated method stub
		
		 URL url;		 
		try {
			sturl = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn[0];
			url = new URL(sturl);
		
		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) { 
			    sb.append(line + "\n"); 
			}
			in.close();
			responseString = sb.toString();
		    
		    urlConnection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		JSONObject json;
		List<BookAPI> libros = null;
		try {
			
			json = new JSONObject(responseString);
			BookList detailbook  = gson.fromJson(responseString, BookList.class);
			libros = detailbook.getItems();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return libros.get(0);
	}

}	
	
}
