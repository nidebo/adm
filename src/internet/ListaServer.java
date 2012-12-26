package internet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.AsyncTask;

public class ListaServer {
	
	private class ListaLibros {
		private List<auxLibro> books;
		
		public List<auxLibro> getLibros() {
			return books;
		}
		
		public void setLibros(List<auxLibro> libros) {
			this.books = libros;
		}
	}
	
	private class ListaListas {
		private List <auxLista> customLists;
		
		public List<auxLista> getListas() {
			return customLists;
		}
		
		public void setListas(List<auxLista> listas) {
			this.customLists = listas;
		}
	}
	
	private class auxLibro {
		String isbn;
		
		public auxLibro() {
		}
		
		public auxLibro(String isbn) {
			this.isbn = isbn;
		}
		
		public String getIsbn() {
			return isbn;
		}
		
		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}
	}
	
	private class auxLista {
		String name;
		
		public auxLista() {
		}
		
		public auxLista(String name) {
			this.name = name;
		}
				
		public String getName() {
			return name;
		}
				
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public void borraListaDeUsuario(String lname, String uname){
		
		AsyncDeleteList dl = new AsyncDeleteList();
		dl.execute(lname, uname);
	}
	
	public void creaListaDeUsuario(String lname, String uname){

		AsyncSaveList sl = new AsyncSaveList();
		sl.execute(lname, uname);
	}
	
	public void agregaLibroALista(String lname, String uname, String isbn){

		AsyncSaveBook sl = new AsyncSaveBook();
		sl.execute(lname, uname, isbn);
	}
	
	public void borraLibroDeLista(String lname, String uname, String isbn){
		
		AsyncDeleteBook db = new AsyncDeleteBook();
		db.execute(lname,uname,isbn);
	}
	
	public ArrayList<String> obtenerLibrosLista(String lname, String uname){
		
		ArrayList<String> isbnLibros=new ArrayList<String>();		
		AsyncGetBooks gb = new AsyncGetBooks();

		try {
			isbnLibros = gb.execute(lname,uname).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Aqui te bajas del servidor los libros de la lista NombreLista de Usuario y los metes en isbnLibros
		return isbnLibros;
	}
	
	public ArrayList<String> obtenerListas(String uname){
		
		ArrayList<String> nombreListas=new ArrayList<String>();
		AsyncGetLists gl = new AsyncGetLists();

		try {
			nombreListas = gl.execute(uname).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Aqui te bajas del servidor las listas de Usuario y las metes en isbnLibros
		return nombreListas;
	}
	
	private class AsyncDeleteList extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute(){

		}
		
		@Override
		protected Void doInBackground(String... params) {
			//////////////
			HttpClient client = new DefaultHttpClient();

			HttpPut request = new HttpPut("http://boox.eu01.aws.af.cm/users/" +
					params[1] + "/delCustomList"); 
			
			JSONObject json = new JSONObject();
			try {
				json.put("uname", params[1]);
				json.put("custom", params[0]);
	            StringEntity se;
				se = new StringEntity(json.toString());
	            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	            request.setEntity(se);
				HttpResponse response = client.execute(request);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			     
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {


		}
	}
	
	private class AsyncSaveList extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute(){

		}
		
		@Override
		protected Void doInBackground(String... params) {
			//////////////
			HttpClient client = new DefaultHttpClient();

			HttpPut request = new HttpPut("http://boox.eu01.aws.af.cm/users/" +
					params[1] + "/addCustomList"); 
			
			JSONObject json = new JSONObject();
			try {
				json.put("uname", params[1]);
				json.put("custom", params[0]);
	            StringEntity se;
				se = new StringEntity(json.toString());
	            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	            request.setEntity(se);
				HttpResponse response = client.execute(request);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			     
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {


		}
	}

	private class AsyncSaveBook extends AsyncTask<String, Void, Void> {
	
	@Override
	protected void onPreExecute(){

	}
	
	@Override
	protected Void doInBackground(String... params) {
		//////////////
		HttpClient client = new DefaultHttpClient();

		HttpPut request = new HttpPut("http://boox.eu01.aws.af.cm/users/" +
				params[1] + "/addBookToCustomList"); 
		
		JSONObject json = new JSONObject();
		try {
			json.put("uname", params[1]);
			json.put("custom", params[0]);
			json.put("book", params[2]);
            StringEntity se;
			se = new StringEntity(json.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            request.setEntity(se);
			HttpResponse response = client.execute(request);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		     
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {


	}
}

	private class AsyncDeleteBook extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute(){

		}
		
		@Override
		protected Void doInBackground(String... params) {
			//////////////
			HttpClient client = new DefaultHttpClient();

			HttpPut request = new HttpPut("http://boox.eu01.aws.af.cm/users/" +
					params[1] + "/delBookFromCustomList"); 
			
			JSONObject json = new JSONObject();
			try {
				json.put("uname", params[1]);
				json.put("custom", params[0]);
				json.put("book", params[2]);
	            StringEntity se;
				se = new StringEntity(json.toString());
	            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	            request.setEntity(se);
				HttpResponse response = client.execute(request);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			     
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {


		}
	}
	
	private class AsyncGetBooks extends AsyncTask<String, Void, ArrayList<String>> {

		//boolean cool = true;
		StringBuilder sb;
		String responseString;
		
		@Override
		protected ArrayList<String> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			 URL url;
			 ListaLibros li = new ListaLibros();
			 ArrayList<String> res = new ArrayList<String>();
			try {
				//String uname = "nicolas";
				url = new URL("http://boox.eu01.aws.af.cm/users/" + 
						params[1] + "/getCustomList/" + params[0]);
			
			    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				sb = new StringBuilder();
				String line = null;
				//line = reader.readLine();
				//sb.append(line);
				while ((line = reader.readLine()) != null) { 
				    sb.append(line + "\n"); 
				}
				in.close();
				responseString = sb.toString();
			    
			    urlConnection.disconnect();
			
			 	GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();

				JSONObject json;
				
				json = new JSONObject(responseString);

			    li = gson.fromJson(json.toString(), ListaLibros.class);
			    
				
				for(int i=0; i < li.getLibros().size(); i++) {
		    		res.add(li.getLibros().get(i).getIsbn());
		    	}
				   // txt.setText(String.valueOf(al.getListaAmigos().size()));
			} catch (JSONException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
			     
			return res;
		}
		
	
	}
	
	private class AsyncGetLists extends AsyncTask<String, Void, ArrayList<String>> {
		//boolean cool = true;
				StringBuilder sb;
				String responseString;
				
				@Override
				protected ArrayList<String> doInBackground(String... params) {
					// TODO Auto-generated method stub
					
					 URL url;
					 ListaListas li = new ListaListas();
					 ArrayList<String> res = new ArrayList<String>();
					try {
						//String uname = "nicolas";
						url = new URL("http://boox.eu01.aws.af.cm/users/" + 
								params[0] + "/getAllCustomLists");
					
					    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
					    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

						BufferedReader reader = new BufferedReader(
								new InputStreamReader(in));
						sb = new StringBuilder();
						String line = null;
						//line = reader.readLine();
						//sb.append(line);
						while ((line = reader.readLine()) != null) { 
						    sb.append(line + "\n"); 
						}
						in.close();
						responseString = sb.toString();
					    
					    urlConnection.disconnect();
					    
					    GsonBuilder builder = new GsonBuilder();
						Gson gson = builder.create();

						JSONObject json;
						
						json = new JSONObject(responseString);

						li = gson.fromJson(json.toString(),	ListaListas.class);
						
						for(int i=0; i < li.getListas().size(); i++) {
				    		res.add(li.getListas().get(i).getName());
				    	}
						
					} catch (JSONException e) {
							// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					     
					return res;
				}
			
	}

}
