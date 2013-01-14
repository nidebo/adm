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

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import librosGoogle.BookAPI;
import librosGoogle.BookList;
import listasLibros.Libro;

public class InterfazAPI {

	public Libro ObtenerLibroPorId(String id){				
		BookAPI book = new BookAPI();
		Libro result = new Libro();
		id = id.replaceAll("\\s+", "");
		AsyncBookId ab = new AsyncBookId();
		try {
			book = ab.execute(id).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if(book == null)
			return null;
		result = pasarDeBookApiALibro(book);
		return result;
	}

	public Libro ObtenerLibroPorIsbn(String isbn) {
		BookList books = new BookList();
		Libro result = new Libro();
		AsyncBookIsbn ab = new AsyncBookIsbn();
		isbn = isbn.replaceAll("\\s+", "");
		try {
			books = ab.execute(isbn).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if (books.getTotalItems() == 0)
			return null;
		result = pasarDeBookApiALibro(books.getItems().get(0));
		return result;
	}

	public ArrayList<Libro> ObtenerListaLibrosPorAutor(String autor){
		ArrayList<Libro> result = new ArrayList<Libro>();
		BookList books = new BookList();
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
		if(books.getTotalItems() == 0)	
			return null;

		result = pasarListaDeBookApisALibro(books.getItems());
		return result;

	}

	public ArrayList<Libro> ObtenerListaLibrosPorTitulo(String titulo){
		ArrayList<Libro> result = new ArrayList<Libro>();
		BookList books = new BookList();
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
		if(books.getTotalItems() == 0)
			return null;
		result = pasarListaDeBookApisALibro(books.getItems());
		return result;
	}



	public class AsyncBookId extends AsyncTask<String, Void, BookAPI> {

		StringBuilder sb;
		String responseString;
		String sturl;

		@Override
		protected BookAPI doInBackground(String... id) {
			// TODO Auto-generated method stub

			HttpParams httpParameters = new BasicHttpParams();

			int timeoutConnection = 1500;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

			int timeoutSocket = 1500;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient client = new DefaultHttpClient(httpParameters);
			HttpGet request = new HttpGet(
					"https://www.googleapis.com/books/v1/volumes/"+id[0]+"?key=AIzaSyC9DevpMpZeWSTZFBwjhzql2iJKvpVwF7M");
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
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			JSONObject json;
			BookAPI book = new BookAPI();
			try {

				json = new JSONObject(responseString);
				book  = gson.fromJson(responseString, BookAPI.class);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return book;
		}

	}

	public class AsyncBookIsbn extends AsyncTask<String, Void, BookList> {

		StringBuilder sb;
		String responseString;
		String sturl;

		@Override
		protected BookList doInBackground(String... isbn) {
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
				e.printStackTrace();
			} catch (ClientProtocolException e) {
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
			BookList detailbook = new BookList();
			try {

				json = new JSONObject(responseString);
				detailbook  = gson.fromJson(responseString, BookList.class);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return detailbook;
		}

	}

	public class AsyncBookAuthor extends AsyncTask<String, Void, BookList> {

		StringBuilder sb;
		String responseString;
		String sturl;

		@Override
		protected BookList doInBackground(String... author) {
			// TODO Auto-generated method stub

			HttpParams httpParameters = new BasicHttpParams();

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
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			JSONObject json;
			BookList libros = new BookList();
			try {
				json = new JSONObject(responseString);
				libros  = gson.fromJson(responseString, BookList.class);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return libros;
		}
	}

	public class AsyncBookTitle extends AsyncTask<String, Void, BookList> {

		StringBuilder sb = null;
		String responseString = null;
		String sturl = null;

		@Override
		protected BookList doInBackground(String... title) {
			// TODO Auto-generated method stub


			HttpParams httpParameters = new BasicHttpParams();

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
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			JSONObject json;
			BookList detailbook = new BookList();
			try {
				json = new JSONObject(responseString);
				detailbook  = gson.fromJson(responseString, BookList.class);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return detailbook;
		}
	}

	public Libro pasarDeBookApiALibro(BookAPI book){
		String desconocido = "";

		Libro lib = new Libro(book.getId());
		try{
			String isbn;
			if(book.getVolumeInfo().getIndustryIdentifiers().size()>=2)
				isbn=book.getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier();
			else{
				if(book.getVolumeInfo().getIndustryIdentifiers().size()>=1&&book.getVolumeInfo().getIndustryIdentifiers().get(0).getType()=="ISBN_10")
					isbn=book.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier();
				else
					isbn=desconocido;
			}

			lib.setIsbn(isbn);
		}
		catch(Exception e){
			lib.setIsbn(desconocido);
		}

		try{
			if(book.getVolumeInfo().getTitle()==null){
				lib.setTitulo(desconocido);
			}
			else
				lib.setTitulo(book.getVolumeInfo().getTitle());
		}
		catch(Exception e){
			lib.setTitulo(desconocido);
		}

		try{
			if(book.getVolumeInfo().getSubtitle()==null){
				lib.setSubtitulo(desconocido);
			}
			else
				lib.setSubtitulo(book.getVolumeInfo().getSubtitle());
		}
		catch(Exception e){
			lib.setSubtitulo(desconocido);
		}

		try{
			if (book.getVolumeInfo().getAuthors()==null){
				ArrayList<String> aut=new ArrayList<String>();
				aut.add(desconocido);
				lib.setAutores(aut);
			}
			else{
				lib.setAutores(book.getVolumeInfo().getAuthors());
			}
		}
		catch(Exception e){
			ArrayList<String> aut=new ArrayList<String>();
			aut.add(desconocido);
			lib.setAutores(aut);
		}

		try{
			if(book.getVolumeInfo().getPublisher()==null){
				lib.setEditorial(desconocido);
			}
			else
				lib.setEditorial(book.getVolumeInfo().getPublisher());
		}
		catch(Exception e){
			lib.setEditorial(desconocido);
		}

		try{
			if(book.getVolumeInfo().getDescription()==null){
				lib.setDescripcion(desconocido);
			}
			else
				lib.setDescripcion(book.getVolumeInfo().getDescription());
		}
		catch(Exception e){
			lib.setDescripcion(desconocido);
		}

		try{
			if(book.getVolumeInfo().getPublishedDate()==null){
				lib.setFechaPublicacion(desconocido);
			}
			else
				lib.setFechaPublicacion(book.getVolumeInfo().getPublishedDate());
		}
		catch(Exception e){
			lib.setFechaPublicacion(desconocido);
		}

		/*if(book.getVolumeInfo().getPageCount()==0){
		lib.setNumeroDePaginas(0);
		}
		else*/
		lib.setNumeroDePaginas(book.getVolumeInfo().getPageCount());

		try{
			if(book.getVolumeInfo().getMainCategory()==null){
				lib.setCategoriaPrincipal(desconocido);
			}
			else
				lib.setCategoriaPrincipal(book.getVolumeInfo().getMainCategory());
		}
		catch(Exception e){
			lib.setCategoriaPrincipal(desconocido);
		}

		try{
			if (book.getVolumeInfo().getCategories()==null){
				ArrayList<String> categ=new ArrayList<String>();
				categ.add(desconocido);
				lib.setCategorias(categ);
			}
			else
				lib.setCategorias((book.getVolumeInfo().getCategories()));
		}
		catch(Exception e){
			ArrayList<String> categ=new ArrayList<String>();
			categ.add(desconocido);
			lib.setCategorias(categ);
		}

		/*if(book.getVolumeInfo().getAverageRating()==0.0){
		lib.setPuntuacionMedia((float) 0);
		}
		else*/
		lib.setPuntuacionMedia(book.getVolumeInfo().getAverageRating());

		try{
			if(book.getSaleInfo().getCountry()==null){
				lib.setPais(desconocido);
			}
			else
				lib.setPais(book.getSaleInfo().getCountry());
		}
		catch(Exception e){
			lib.setPais(desconocido);
		}

		try{
			if(book.getVolumeInfo().getLanguage()==null){
				lib.setIdioma(desconocido);
			}
			else
				lib.setIdioma(book.getVolumeInfo().getLanguage());
		}
		catch(Exception e){
			lib.setIdioma(desconocido);
		}

		lib.setImageLinks(book.getVolumeInfo().getImageLinks());

		return lib;
	}

	public ArrayList<Libro> pasarListaDeBookApisALibro(List<BookAPI> books){
		ArrayList<Libro> arrayLibros= new ArrayList<Libro>();
		if (books.size()==0)
			return null;
		for (int i=0;i<books.size();i++){
			arrayLibros.add(pasarDeBookApiALibro(books.get(i)));
		}
		return arrayLibros;
	}

}
