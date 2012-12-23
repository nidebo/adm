package listasLibros;

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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boox.R;
import com.example.boox.TabsActivity;
import com.example.boox.Main.AsyncLogin;

public class ListaLibros {
	
	String s_NombreLista;
	ArrayList<Libro> libros = new ArrayList<Libro>(); 
	
	public ListaLibros(String NombreLista) { //Constructor
		s_NombreLista = NombreLista;
	}
	
	public String getNombreLista() {
		return s_NombreLista;
	}

	public void setNombreLista(String NombreLista) {
		this.s_NombreLista = NombreLista;
	}

	public void addLibro(Libro i){//No avisa si ya existe el libro
		if (!existe(i))
			libros.add(i);
	}

	public Libro getLibroPorId(int id){
		Libro lib = null;
		for (int i=0; i<libros.size(); i++) { 
			lib = libros.get(i);
			if (lib.getIsbn()==id)
				i=libros.size();
		}
		return lib;
	}

	private boolean existe(Libro lib) {
		boolean ex = false;
		Libro aux = null;
		if (!libros.isEmpty())
			for (int i=0; i<libros.size(); i++) { 
				aux = libros.get(i); 
				 	if (aux.getIsbn()==lib.getIsbn())
				 		ex = true;
				}
		return ex;
	}
	
	public void borraLibroPorIsbn(int isbn){
		Libro lib = null;
		for (int i=0; i<libros.size(); i++) { 
			 lib = libros.get(i); 
			 	if (lib.getIsbn()==isbn)
			 		libros.remove(lib);
			}
	}
	
	public ArrayList<Libro> getListaLibros() {
		return libros;
	}
	
	//Funciones relativas al servidor
	public void guardaListaLibros(){
		//Nico haz algo aqui para meter la lista en el servidor
		//La funcion deber� recibir el nombre o id del usuario, supongo y usar s_NombreLista
		
		//Habr�a que comprobar si existe la lista y modificarla, y sino, crearla
		
	}
	
	
	public void cargaListaLibros(){
		//Como lo anterior

	}
	
	public void borraListaLibros(String lname, String uname){
		//Como lo anterior
		AsyncDelete dl = new AsyncDelete();
		dl.execute(lname, uname);
	}
	
	public class AsyncDelete extends AsyncTask<String, Void, Void> {
		
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
}


