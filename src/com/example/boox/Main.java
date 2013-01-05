package com.example.boox;

import internet.PruebaInternet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.conn.ConnectTimeoutException;

import listasLibros.GestorListas;
import listasLibros.Libro;

import usuarios.Usuario;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import apiGoogle.InterfazAPI;

public class Main extends Activity {
	
	Context context = this;
	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";

	public Usuario usuarioActual= new Usuario("pito", "46015", "contrasenya"); 
	public GestorListas listasUsuario=new GestorListas (usuarioActual.getId());
	//ArrayList<Libro> prueba=listasUsuario.getListaAll();
	InterfazAPI api = new InterfazAPI();

	//private TabHost mTabHost;
    //private Resources mResources;
 
    //private static final String TAG_SCHEDULED = "Scheduled";
    //private static final String TAG_CREATE = "Create";
    //private static final String TAG_OPTIONS = "Options";
    //private static final String PREF_STICKY_TAB = "stickyTab";
	
	ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Libro lib =api.ObtenerLibroPorIsbn("9781594130014");
       // listasUsuario.AddLibroEnLista(lib, "lista1");
        listasUsuario.AddLibroEnCompartibles("0000000000000");
        
        
        
        
        
        // This must be called before ANY content is created
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        
        setContentView(R.layout.main);
        

        //mTabHost = getTabHost();
        //mResources = getResources(); 
        
        //addBooksTab();  
        //addCrossingsTab();
        //addFriendsTab();

        //mTabHost.setCurrentTabByTag("BooksTabActivity");
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	    	case R.id.subitem3:
	    		startActivity(new Intent(this, SettingsActivity.class));
	        	return true;
	    	}
    	return false;
	}

	public void onPressNotAUser(View view) {

    	Intent intent = new Intent(this, SignUpActivity.class);
    	startActivity(intent);
    }

	public void onPressNico(View view) {
    	Intent intent = new Intent(this, PruebaInternet.class);
    	startActivity(intent);
    }
	
    public void onPressEliu(View view) {
    	
        Intent intent = new Intent(this, TabsActivity.class);
       	startActivity(intent);
    }
    
    public void onPressMario(View view) {

        Intent intent = new Intent(this, SearchBookActivity.class);
        startActivity(intent);
    }
    
    
	public void onPressLogin(View view) {
		
        setProgressBarIndeterminateVisibility(true);
        progressDialog = ProgressDialog.show(Main.this, 
        		getResources().getString(R.string.login_logging),
        		getResources().getString(R.string.login_pleasewait));

		AsyncLogin lg = new AsyncLogin();
		lg.execute(null, null, null);
    }
		
	
	public class AsyncLogin extends AsyncTask<Void, Void, Boolean> {

		//boolean cool = true;
		StringBuilder sb;
		String responseString;
		String uname;
		String pass;
		boolean flag = true;
		
		@Override
		protected void onPreExecute(){
			EditText et1 = (EditText) findViewById(R.id.login_username);
			EditText et2 = (EditText) findViewById(R.id.login_password);
			uname = et1.getText().toString();
			pass = et2.getText().toString();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			
			URL url;
			if(IsConnectedToNetwork(context)){
				try {
					//String uname = "nicolas";
					url = new URL("http://boox.eu01.aws.af.cm/checkUser/"+uname+"/"+pass);
				
				    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				    urlConnection.setConnectTimeout(1500);
				    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					String line = null;
					line = reader.readLine();
					in.close();
					responseString = line;
				    
				    urlConnection.disconnect();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					flag = false;
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					flag = false;
					e.printStackTrace();
				}
			}
			else
				flag = false;
			     
			return flag;
		}

		@Override
		protected void onPostExecute(Boolean result) {

			// Stop the indeterminate progress bar and close dialog
	        setProgressBarIndeterminateVisibility(false);
	        progressDialog.dismiss();
	        if(result){
	        	if(responseString.equals("true")){
					
					SharedPreferences mySharedPreferences = getSharedPreferences(myPrefs,
							mode);
					SharedPreferences.Editor myEditor = mySharedPreferences.edit();

					myEditor.putString("username", uname);
					myEditor.commit();
			        Intent intent = new Intent(context, TabsActivity.class);
			       	startActivity(intent);
			       	
				}
				else {
					Toast toast = Toast.makeText(
							getApplicationContext(), 
							getResources().getString(R.string.login_invalid), 
							Toast.LENGTH_SHORT);
					toast.show();
				}
	        }
	        else {
	        	Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.login_internet_error), 
						Toast.LENGTH_SHORT);
				toast.show();
	        }		
		}
	}
	
    public boolean IsConnectedToNetwork(Context context)
    {
        ConnectivityManager conManager = (ConnectivityManager) 
context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] allNetworkInfo = conManager.getAllNetworkInfo();
        NetworkInfo currNetworkInfo;
        boolean anythingConnected = false;
        for (int i = 0; i < allNetworkInfo.length; i++)
        {
            currNetworkInfo = allNetworkInfo[i];
            if (currNetworkInfo.getState() == NetworkInfo.State.CONNECTED)
               anythingConnected = true;
        }

        return anythingConnected;
    }


    /*private void addBooksTab() {
    	 
    	Intent intent = new Intent(this, BooksTabFragment.class);
 
        TabSpec spec = mTabHost.newTabSpec(TAG_SCHEDULED);
        spec.setIndicator(mResources.getString(R.string.title_activity_books_tab));
        spec.setContent(intent);
 
        mTabHost.addTab(spec);
    }*/
    
}
