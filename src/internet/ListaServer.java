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
	
	public class ListaLibros {
		ArrayList<auxLibro> alibro;
		
		public ArrayList<auxLibro> getLibros() {
			return alibro;
		}
	}
	
	public class ListaListas {
		ArrayList <auxLista> alista;
		
		public ArrayList<auxLista> getListas() {
			return alista;
		}
	}
	
	public class auxLibro {
		String isbn;
		
		public String getIsbn() {
			return isbn;
		}
	}
	
	public class auxLista {
		String name;
		
		public String getName() {
			return name;
		}
	}
	
	public void borraLista(String lname, String uname){
		
		AsyncDeleteList dl = new AsyncDeleteList();
		dl.execute(lname, uname);
	}
	
	public void creaLista(String lname, String uname){

		AsyncSaveList sl = new AsyncSaveList();
		sl.execute(lname, uname);
	}
	
	public void agregaLibro(String lname, String uname, String isbn){

		AsyncSaveBook sl = new AsyncSaveBook();
		sl.execute(lname, uname, isbn);
	}
	
	public void borraLibro(String lname, String uname, String isbn){
		
		AsyncDeleteBook db = new AsyncDeleteBook();
		db.execute(lname,uname,isbn);
	}
	
	public ArrayList<String> obtenerLibrosLista(String lname, String uname){
		
		ArrayList<String> isbnLibros=new ArrayList<String>();
		ListaLibros aux = new ListaLibros();
		
		AsyncGetBooks gb = new AsyncGetBooks();

		try {
			aux = gb.execute(lname,uname).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i < aux.getLibros().size(); i++) {
    		isbnLibros.add(aux.getLibros().get(i).getIsbn());
    	}
		//Aqui te bajas del servidor los libros de la lista NombreLista de Usuario y los metes en isbnLibros
		return isbnLibros;
	}
	
	public ArrayList<String> obtenerListas(String uname){
		ArrayList<String> nombreListas=new ArrayList<String>();
		ListaListas aux = new ListaListas();
		
		AsyncGetLists gl = new AsyncGetLists();

		try {
			aux = gl.execute(uname).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i < aux.getListas().size(); i++) {
    		nombreListas.add(aux.getListas().get(i).getName());
    	}
		//Aqui te bajas del servidor las listas de Usuario y las metes en isbnLibros
		return nombreListas;
	}
	
	public class AsyncDeleteList extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute(){

		}
		
		@Override
		protected Void doInBackground(String... params) {
			//////////////
			HttpClient client = new DefaultHttpClient();

			HttpPut request = new HttpPut("http://boox.eu01.aws.af.cm/" +
					params[1] + "/delCustomList"); 
			
			JSONObject json = new JSONObject();
			try {
				json.put("uname", params[0]);
				json.put("custom", params[1]);
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
	
	public class AsyncSaveList extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute(){

		}
		
		@Override
		protected Void doInBackground(String... params) {
			//////////////
			HttpClient client = new DefaultHttpClient();

			HttpPut request = new HttpPut("http://boox.eu01.aws.af.cm/" +
					params[1] + "/addCustomList"); 
			
			JSONObject json = new JSONObject();
			try {
				json.put("uname", params[0]);
				json.put("custom", params[1]);
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

	public class AsyncSaveBook extends AsyncTask<String, Void, Void> {
	
	@Override
	protected void onPreExecute(){

	}
	
	@Override
	protected Void doInBackground(String... params) {
		//////////////
		HttpClient client = new DefaultHttpClient();

		HttpPut request = new HttpPut("http://boox.eu01.aws.af.cm/" +
				params[1] + "/addBookToCustomList"); 
		
		JSONObject json = new JSONObject();
		try {
			json.put("uname", params[0]);
			json.put("custom", params[1]);
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

	public class AsyncDeleteBook extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute(){

		}
		
		@Override
		protected Void doInBackground(String... params) {
			//////////////
			HttpClient client = new DefaultHttpClient();

			HttpPut request = new HttpPut("http://boox.eu01.aws.af.cm/" +
					params[1] + "/delBookFromCustomList"); 
			
			JSONObject json = new JSONObject();
			try {
				json.put("uname", params[0]);
				json.put("custom", params[1]);
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
	
	public class AsyncGetBooks extends AsyncTask<String, Void, ListaLibros> {

		//boolean cool = true;
		StringBuilder sb;
		String responseString;
		
		@Override
		protected ListaLibros doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			 URL url;
			 ListaLibros li = new ListaLibros();
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
				
				try {
					json = new JSONObject(responseString);

				    li = gson.fromJson(json.toString(),
						ListaLibros.class);
				   // txt.setText(String.valueOf(al.getListaAmigos().size()));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			     
			return li;
		}
	
	}
	
	public class AsyncGetLists extends AsyncTask<String, Void, ListaListas> {
		//boolean cool = true;
				StringBuilder sb;
				String responseString;
				
				@Override
				protected ListaListas doInBackground(String... params) {
					// TODO Auto-generated method stub
					
					 URL url;
					 ListaListas li = new ListaListas();
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
						
						try {
							json = new JSONObject(responseString);

						    li = gson.fromJson(json.toString(),
								ListaListas.class);
						   // txt.setText(String.valueOf(al.getListaAmigos().size()));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					     
					return li;
				}
			
	}

}
