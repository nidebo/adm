package com.example.boox;

import internet.ListaServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import listasLibros.Libro;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import apiGoogle.InterfazAPI;

public class FriendsBooksActivity extends ListActivity {

	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";
	String uname;
	String fname;
	
	
	Libro libro = new Libro();
    AdapterView.AdapterContextMenuInfo info;
	ArrayList<Libro> libros = new ArrayList<Libro>();	
	String usuario;
	Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

		
		SharedPreferences mySharedPreferences = this.getSharedPreferences(myPrefs, mode);
				
		uname = mySharedPreferences.getString("username", "");
		//GestorListas gl = new GestorListas(uname, this.context);
		
		Bundle extras = getIntent().getExtras();
		fname = extras.getString("friend");		
		InterfazAPI api = new InterfazAPI();

		ArrayList<Libro> libs = new ArrayList<Libro>();
		ListaServer ls = new ListaServer();
		Libro lib = new Libro();
		ArrayList<String> compartibles = new ArrayList<String>();
		ArrayList<String> titulos = new ArrayList<String>();
		compartibles = ls.obtenerLibrosLista(fname, "adminLibrosPorUsuario");
		for(int i=0; i< compartibles.size(); i++){
			lib = api.ObtenerLibroPorId(compartibles.get(i));
			if(lib != null){
				libros.add(lib);
				titulos.add(lib.getTitulo());
			}
		}
		this.getActionBar().setTitle("Libros de "+fname);
        registerForContextMenu(getListView());
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titulos));
	}

public class AsyncAddCrossing extends AsyncTask<Void, Void, Integer> {
    	

    	int flag = 0;
    	//1 -> contraseña dup, 2 -> internet, 3 -> username ya existe
    	
		@Override
		protected void onPreExecute(){
			
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(flag == 0){
				HttpParams httpParameters = new BasicHttpParams();

				int timeoutConnection = 1500;
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

				int timeoutSocket = 1500;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				DefaultHttpClient client = new DefaultHttpClient(httpParameters); 
	            HttpPost httppost = new HttpPost("http://boox.eu01.aws.af.cm/users/"+uname+"/addCrossing"); 


	            JSONObject json = new JSONObject();
	            
	            try {
					json.put("user2", fname);
					json.put("book2", libro.getId()); // PONER EL SELECCIONADO
					StringEntity se;
					se = new StringEntity(json.toString());
	 
	                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	                httppost.setEntity(se);
	                HttpResponse response;
					response = client.execute(httppost);

	                HttpEntity responseEntity = response.getEntity();
					InputStream stream = responseEntity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(stream));
					String line = null;
					line = reader.readLine();
	                if(line.equals("Error")){
	                	flag = 3;
	                }
	            	} catch (ConnectTimeoutException e) {
	            		flag = 2;
	            		e.printStackTrace();
					} catch (JSONException e) {
						flag = 2;
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						flag = 2;
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						flag = 2;
						e.printStackTrace();
					} catch (IOException e) {
						flag = 2;
						e.printStackTrace();
					}
			}
  
			return flag;
		}

		@Override
		protected void onPostExecute(Integer result) {

			// Stop the indeterminate progress bar and close dialog
	        setProgressBarIndeterminateVisibility(false);
			
			if(result == 1){
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.signup_confirm_pass_error), 
						Toast.LENGTH_SHORT);
				toast.show();
			}
			else if(result == 2){
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.signup_internet_error), 
						Toast.LENGTH_SHORT);
				toast.show();
			}

		}
	
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		//ArrayList<String> misListas = gl.getNombresListas();
		info = (AdapterContextMenuInfo) menuInfo; 
		menu.add(Menu.NONE, info.position, 0, R.string.crossing_proposal);
		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {		
		//proponer crossing
		libro = libros.get(item.getItemId());
		AsyncAddCrossing ab = new AsyncAddCrossing();
		try{
			ab.execute();
		}catch(Exception e){
			return false;
		}
		Toast toast = Toast.makeText(this.getApplicationContext(), "Crossing created", Toast.LENGTH_SHORT);
		toast.show();
		
		
		return true;
	}

	

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id){
		super.onListItemClick(list, view, position, id);
		MyBD mbd = new MyBD(this,uname);
		mbd.BorrarTemporal();
		mbd.InsertarTemporal(libros.get(position));
		Intent i = new Intent();
		i.setClass(context, BookActivity.class);
		i.putExtra("id", libros.get(position).getId());
		i.putExtra("temp", 1);
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
    	case android.R.id.home:
            // This is called when the Home (Up) button is pressed
            // in the Action Bar.
            Intent parentActivityIntent = new Intent(this, TabsActivity.class);
            parentActivityIntent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            finish(); 
            return true;
    	case R.id.search:
    		startActivity(new Intent(this, SearchBookActivity.class));
            return true;
    	case R.id.submenu_about:
    		startActivity(new Intent(this, AboutActivity.class));
        	return true;
    	}
    	return false;
	}
}
