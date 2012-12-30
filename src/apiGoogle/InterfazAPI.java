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


import librosGoogle.BookAPI;
import librosGoogle.BookList;
import librosGoogle.VolumeInfo.ImageLinks;
import listasLibros.Libro;

public class InterfazAPI {

public Libro ObtenerLibroPorIsbn(String isbn){
		BookAPI book = new BookAPI();
		AsyncBookIsbn ab = new AsyncBookIsbn();	
		isbn = isbn.replaceAll("\\s+", "");
		try {
			book = ab.execute(isbn).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(book != null)
			return pasarDeBookApiALibro(book);	
		else
			return null;
	}

public ArrayList<Libro> ObtenerListaLibrosPorAutor(String autor){
	List<BookAPI> books = null;
	AsyncBookAuthor ab = new AsyncBookAuthor();	
	autor = autor.replaceAll("\\s+", "+");
	try {
		books = ab.execute(autor).get();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(books != null)	
		return pasarListaDeBookApisALibro(books);
	else
		return null;

	}

public ArrayList<Libro> ObtenerListaLibrosPorTitulo(String titulo){
	List<BookAPI> books = null;
	AsyncBookTitle ab = new AsyncBookTitle();	
	titulo = titulo.replaceAll("\\s+", "+");
	try {
		books = ab.execute(titulo).get();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(books != null)
		return pasarListaDeBookApisALibro(books);
	else
		return null;
	}

public class AsyncBookIsbn extends AsyncTask<String, Void, BookAPI> {

	StringBuilder sb;
	String responseString;
	String sturl;
	
	@Override
	protected BookAPI doInBackground(String... isbn) {
		// TODO Auto-generated method stub
		
		 URL url;		 
		try {
			sturl = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn[0]+"&key=AIzaSyC9DevpMpZeWSTZFBwjhzql2iJKvpVwF7M";
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
			if(detailbook.getTotalItems() == 0)
				libros = null;
			else
				libros = detailbook.getItems();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(libros == null)
			return null;
		else
			return libros.get(0);
	}

}

public class AsyncBookAuthor extends AsyncTask<String, Void, List<BookAPI>> {

	StringBuilder sb;
	String responseString;
	String sturl;
	
	@Override
	protected List<BookAPI> doInBackground(String... author) {
		// TODO Auto-generated method stub
		
		 URL url;		 
		try {
			sturl = "https://www.googleapis.com/books/v1/volumes?q=inauthor:"+author[0]+"&key=AIzaSyC9DevpMpZeWSTZFBwjhzql2iJKvpVwF7M";
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
			if(detailbook.getTotalItems() == 0)
				libros = null;
			else
				libros = detailbook.getItems();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return libros;
	}
}

public class AsyncBookTitle extends AsyncTask<String, Void, List<BookAPI>> {

	StringBuilder sb = null;
	String responseString = null;
	String sturl = null;
	
	@Override
	protected List<BookAPI> doInBackground(String... title) {
		// TODO Auto-generated method stub
		
		 URL url;		 
		try {
			sturl = "https://www.googleapis.com/books/v1/volumes?q=intitle:"+title[0]+"&key=AIzaSyC9DevpMpZeWSTZFBwjhzql2iJKvpVwF7M";
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
			if(detailbook.getTotalItems() == 0)
				libros = null;
			else
				libros = detailbook.getItems();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return libros;
	}
}

public Libro pasarDeBookApiALibro(BookAPI book){
	Libro lib = new Libro(book.getVolumeInfo().getIndustryIdentifiers().toString(),
			book.getVolumeInfo().getTitle(),
			book.getVolumeInfo().getAuthors(),
			book.getVolumeInfo().getPublisher(),
			book.getVolumeInfo().getDescription(),
			book.getVolumeInfo().getPublishedDate(),
			book.getVolumeInfo().getPageCount(),
			book.getVolumeInfo().getMainCategory(),
			book.getVolumeInfo().getCategories(),
			book.getVolumeInfo().getAverageRating(),
			book.getSaleInfo().getCountry(),
			book.getVolumeInfo().getLanguage(),
			book.getVolumeInfo().getImageLinks());

	return lib;
}

public ArrayList<Libro> pasarListaDeBookApisALibro(List<BookAPI> books){
	ArrayList<Libro> arrayLibros= new ArrayList<Libro>();
	for (int i=0;i<books.size();i++){
		arrayLibros.add(pasarDeBookApiALibro(books.get(i)));
	}
	return arrayLibros;
}

}
