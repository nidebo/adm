package com.example.boox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

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

public class LoginActivity extends Activity {
	
	Context context = this;
	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";

	//public Usuario usuarioActual= new Usuario("pito", "46015", "contrasenya"); 
	//public GestorListas listasUsuario=new GestorListas (usuarioActual.getId());
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
        //listasUsuario.AddLibroEnCompartibles("0000000000000");
        
        
        
        
        
        // This must be called before ANY content is created
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        
        setContentView(R.layout.activity_login);
        

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
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	    	case R.id.submenu_settings:
	    		startActivity(new Intent(this, SettingsActivity.class));
	        	return true;
	    	}
    	return false;
	}

	public void onPressNotAUser(View view) {

    	Intent intent = new Intent(this, SignUpActivity.class);
    	startActivity(intent);
    }
	
    public void onPressEliu(View view) {
    	
        Intent intent = new Intent(this, TabsActivity.class);
       	startActivity(intent);
    }
    
    
	public void onPressLogin(View view) {
		
        setProgressBarIndeterminateVisibility(true);
        progressDialog = ProgressDialog.show(LoginActivity.this, 
        		getResources().getString(R.string.login_logging),
        		getResources().getString(R.string.login_pleasewait));

		AsyncLogin lg = new AsyncLogin();
		lg.execute(null, null, null);
    }
		
	
	public class AsyncLogin extends AsyncTask<Void, Void, Integer> {

		
		StringBuilder sb;
		String responseString;
		String uname;
		String pass;
		int flag = 1;
		
		@Override
		protected void onPreExecute(){
			EditText et1 = (EditText) findViewById(R.id.login_username);
			EditText et2 = (EditText) findViewById(R.id.login_password);
			uname = et1.getText().toString();
			pass = et2.getText().toString();
			Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
			Matcher m = p.matcher(uname);
			if(!m.find())
				flag = 2;
			else{
				m = p.matcher(pass);
				if(!m.find())
					flag = 2;
			}
				
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			if(flag == 1){
				HttpParams httpParameters = new BasicHttpParams();

				int timeoutConnection = 1500;
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

				int timeoutSocket = 1500;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				DefaultHttpClient client = new DefaultHttpClient(httpParameters);
				HttpGet request = new HttpGet(
						"http://boox.eu01.aws.af.cm/checkUser/"+uname+"/"+pass);
				request.setHeader("Accept", "application/json");
				BasicHttpResponse response;
				// HttpResponse response;
				try {
					response = (BasicHttpResponse) client.execute(request);
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						InputStream stream = entity.getContent();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(stream));
						String line = null;
						line = reader.readLine();
						stream.close();
						responseString = line;
					}
				////////////////////////////////////////////////////////////////////////////////////////////////////
				} catch (ConnectTimeoutException e) {
					flag = 3;
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					flag = 3;
					e.printStackTrace();
				} catch (IOException e) {
					flag = 3;
					e.printStackTrace();
				}
			}
			
			     
			return flag;
		}

		@Override
		protected void onPostExecute(Integer result) {

			// Stop the indeterminate progress bar and close dialog
	        setProgressBarIndeterminateVisibility(false);
	        progressDialog.dismiss();
	       if(result == 1){
	    	   if(responseString.equals("true")){
					
					SharedPreferences mySharedPreferences = getSharedPreferences(myPrefs,
							mode);
					SharedPreferences.Editor myEditor = mySharedPreferences.edit();

					myEditor.putString("username", uname);
					myEditor.commit();
			        Intent intent = new Intent(context, TabsActivity.class);
			       	startActivity(intent);
			       	
				}
				else{
					Toast toast = Toast.makeText(
							getApplicationContext(), 
							getResources().getString(R.string.login_invalid), 
							Toast.LENGTH_SHORT);
					toast.show();
				} 
	       } else if(result == 3){
	    	   Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.login_internet_error), 
						Toast.LENGTH_SHORT);
				toast.show();
	       }  else if(result == 2){
	    	   Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.login_alphanum_error), 
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
