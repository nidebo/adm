package apiGoogle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.EditText;

import com.example.boox.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import librosGoogle.BookAPI;
import librosGoogle.BookList;
import listasLibros.Libro;

public class InterfazAPI {

	public Libro ObtenerLibroPorId(String id){
				
		BookAPI book = new BookAPI();
		AsyncBookId ab = new AsyncBookId();	
		id = id.replaceAll("\\s+", "");
		try {
			book = ab.execute(id).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if(book != null)
			return pasarDeBookApiALibro(book);	
		else
			return null;
	}
	
	public Libro ObtenerLibroPorIsbn(String isbn) {
		BookAPI book = new BookAPI();
		AsyncBookIsbn ab = new AsyncBookIsbn();
		isbn = isbn.replaceAll("\\s+", "");
		try {
			book = ab.execute(isbn).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if (book != null)
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



public class AsyncBookId extends AsyncTask<String, Void, BookAPI> {

	StringBuilder sb;
	String responseString;
	String sturl;
	
	@Override
	protected BookAPI doInBackground(String... id) {
		// TODO Auto-generated method stub
		
			HttpParams httpParameters = new BasicHttpParams();

			boolean flag = true;
			int timeoutConnection = 1500;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

			int timeoutSocket = 1500;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient client = new DefaultHttpClient(httpParameters);
			HttpGet request = new HttpGet(
					"https://www.googleapis.com/books/v1/volumes?q=d:"+id[0]+"&key=AIzaSyC9DevpMpZeWSTZFBwjhzql2iJKvpVwF7M");
			request.setHeader("Accept", "application/json");
			BasicHttpResponse response;
			
			try {
				response = (BasicHttpResponse) client.execute(request);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream stream = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(stream));
					sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) { 
					    sb.append(line + "\n"); 
					}
					stream.close();
					responseString = sb.toString();
				}
			} catch (ConnectTimeoutException e) {
				flag = false;
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				flag = false;
				e.printStackTrace();
			} catch (IOException e) {
				flag = false;
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

public class AsyncBookIsbn extends AsyncTask<String, Void, BookAPI> {

	StringBuilder sb;
	String responseString;
	String sturl;
	
	@Override
	protected BookAPI doInBackground(String... isbn) {
		// TODO Auto-generated method stub
		
			HttpParams httpParameters = new BasicHttpParams();

			boolean flag = true;
			int timeoutConnection = 1500;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

			int timeoutSocket = 1500;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient client = new DefaultHttpClient(httpParameters);
			HttpGet request = new HttpGet(
					"https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn[0]+"&key=AIzaSyC9DevpMpZeWSTZFBwjhzql2iJKvpVwF7M");
			request.setHeader("Accept", "application/json");
			BasicHttpResponse response;
			
			try {
				response = (BasicHttpResponse) client.execute(request);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream stream = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(stream));
					sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) { 
					    sb.append(line + "\n"); 
					}
					stream.close();
					responseString = sb.toString();
				}
			} catch (ConnectTimeoutException e) {
				flag = false;
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				flag = false;
				e.printStackTrace();
			} catch (IOException e) {
				flag = false;
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

			HttpParams httpParameters = new BasicHttpParams();

			boolean flag = true;
			int timeoutConnection = 1500;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

			int timeoutSocket = 1500;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient client = new DefaultHttpClient(httpParameters);
			HttpGet request = new HttpGet(
					"https://www.googleapis.com/books/v1/volumes?q=inauthor:"+author[0]+"&maxResults=10&key=AIzaSyC9DevpMpZeWSTZFBwjhzql2iJKvpVwF7M");
			request.setHeader("Accept", "application/json");
			BasicHttpResponse response;
			
			try {
				response = (BasicHttpResponse) client.execute(request);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream stream = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(stream));
					sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) { 
					    sb.append(line + "\n"); 
					}
					stream.close();
					responseString = sb.toString();
				}
			} catch (ConnectTimeoutException e) {
				flag = false;
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				flag = false;
				e.printStackTrace();
			} catch (IOException e) {
				flag = false;
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

		
		HttpParams httpParameters = new BasicHttpParams();

		boolean flag = true;
		int timeoutConnection = 1500;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

		int timeoutSocket = 1500;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		HttpGet request = new HttpGet(
				"https://www.googleapis.com/books/v1/volumes?q=intitle:"+title[0]+"&maxResults=10&key=AIzaSyC9DevpMpZeWSTZFBwjhzql2iJKvpVwF7M");
		request.setHeader("Accept", "application/json");
		BasicHttpResponse response;
		
		try {
			response = (BasicHttpResponse) client.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream stream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(stream));
				sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) { 
				    sb.append(line + "\n"); 
				}
				stream.close();
				responseString = sb.toString();
			}
		} catch (ConnectTimeoutException e) {
			flag = false;
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		} catch (IOException e) {
			flag = false;
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
				flag = false;
			else
				libros = detailbook.getItems();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag)
			return libros;
		else
			return null;
	}
}

public Libro pasarDeBookApiALibro(BookAPI book){
	
	String desconocido = "Unknown";
	//String desconocido = getResources().getString(R.string.unknown_field);
			
	
	
	Libro lib = new Libro( book.getId());
	
	
	String isbn=book.getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier();
	if (isbn==null){
		if(book.getVolumeInfo().getIndustryIdentifiers().get(0).getType()=="ISBN_10")
			isbn=book.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier();
		else
			isbn=desconocido;
	}
	
	//lib.setIsbn(isbn);
	
	if (book.getVolumeInfo().getAuthors()==null){
		ArrayList<String> aut=new ArrayList<String>();
		aut.add(desconocido);
		lib.setAutores(aut);
	}
	else{
		lib.setAutores(book.getVolumeInfo().getAuthors());
	}
	
	if(book.getVolumeInfo().getPublisher()==null){
		lib.setEditorial(desconocido);
	}
	else
		lib.setEditorial(book.getVolumeInfo().getPublisher());
	
	
	if(book.getVolumeInfo().getDescription()==null){
		lib.setDescripcion(desconocido);
	}
	else
		lib.setDescripcion(book.getVolumeInfo().getDescription());
	
	
	if(book.getVolumeInfo().getPublishedDate()==null){
		lib.setFechaPublicacion(desconocido);
	}
	else
		lib.setFechaPublicacion(book.getVolumeInfo().getPublishedDate());
	

	/*if(book.getVolumeInfo().getPageCount()==0){
		lib.setNumeroDePaginas(0);
	}
	else*/
		lib.setNumeroDePaginas(book.getVolumeInfo().getPageCount());
	
	
	if(book.getVolumeInfo().getMainCategory()==null){
		lib.setCategoriaPrincipal(desconocido);
	}
	else
		lib.setCategoriaPrincipal(book.getVolumeInfo().getMainCategory());

	
	if (book.getVolumeInfo().getCategories()==null){
		ArrayList<String> categ=new ArrayList<String>();
		categ.add(desconocido);
		lib.setAutores(categ);
	}
	else{
		lib.setAutores(book.getVolumeInfo().getCategories());
	}
	

	/*if(book.getVolumeInfo().getAverageRating()==0.0){
		lib.setPuntuacionMedia((float) 0);
	}
	else*/
		lib.setPuntuacionMedia(book.getVolumeInfo().getAverageRating());

	
	if(book.getSaleInfo().getCountry()==null){
		lib.setPais(desconocido);
	}
	else
		lib.setPais(book.getSaleInfo().getCountry());
	
	
	if(book.getVolumeInfo().getLanguage()==null){
		lib.setIdioma(desconocido);
	}
	else
		lib.setIdioma(book.getVolumeInfo().getLanguage());

		
	lib.setImageLinks(book.getVolumeInfo().getImageLinks());
		

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
