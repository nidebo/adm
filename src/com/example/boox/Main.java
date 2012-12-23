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

import usuarios.Usuario;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity {
	
	Context contexto = this;
	///////////////////////////////
	public Usuario usuarioActual= new Usuario("UsuarioDePrueba", 46015, "contrasenya"); 
	///////////////////////////////
		
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
	
	public class AsyncLogin extends AsyncTask<Void, Void, Void> {

		//boolean cool = true;
		StringBuilder sb;
		String responseString;
		String uname;
		String pass;
		
		@Override
		protected void onPreExecute(){
			EditText et1 = (EditText) findViewById(R.id.login_username);
			EditText et2 = (EditText) findViewById(R.id.login_password);
			uname = et1.getText().toString();
			pass = et2.getText().toString();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			
			URL url;
			try {
				//String uname = "nicolas";
				url = new URL("http://boox.eu01.aws.af.cm/checkUser/"+uname+"/"+pass);
			
			    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			     
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			// Stop the indeterminate progress bar and close dialog
	        setProgressBarIndeterminateVisibility(false);
	        progressDialog.dismiss();
			
			if(responseString.equals("true")){
		        Intent intent = new Intent(contexto, TabsActivity.class);
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
	}
    /*private void addBooksTab() {
    	 
    	Intent intent = new Intent(this, BooksTabFragment.class);
 
        TabSpec spec = mTabHost.newTabSpec(TAG_SCHEDULED);
        spec.setIndicator(mResources.getString(R.string.title_activity_books_tab));
        spec.setContent(intent);
 
        mTabHost.addTab(spec);
    }*/
    
}
