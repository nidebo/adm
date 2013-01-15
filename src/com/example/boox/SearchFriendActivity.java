package com.example.boox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import usuarios.Usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SearchFriendActivity extends Activity {

	Context context = this;
	ProgressDialog progressDialog;
	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";
	String uname = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_friend);
        getActionBar().setDisplayHomeAsUpEnabled(true);

		SharedPreferences mySharedPreferences = getSharedPreferences(myPrefs, mode);
		uname = mySharedPreferences.getString("username", "");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_friend, menu);
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
    	}
    	return false;
	}
    public void onPressSearch(View view) {
    	
        //setProgressBarIndeterminateVisibility(true);
       // progressDialog = ProgressDialog.show(context, 
        	//	getResources().getString(R.string.signup_sending),
        //		getResources().getString(R.string.signup_pleasewait));
		AsyncFindFriend ff = new AsyncFindFriend();
		ff.execute(null, null, null);
    }
 
    public class AsyncFindFriend extends AsyncTask<Void, Void, Void> {

		StringBuilder sb = new StringBuilder();
		String responseString;
		int flag = 1;
		String friend;
		
		@Override
		protected void onPreExecute(){
			EditText et1 = (EditText) findViewById(R.id.friendSearch);
			friend = et1.getText().toString();
			if(friend.equals(""))
				flag = 2;
			if(friend.equals(uname))
				flag = 3;
		}
		

		@Override
		protected Void doInBackground(Void... params) {
			
			if(flag == 1){
				HttpParams httpParameters = new BasicHttpParams();

				int timeoutConnection = 1500;
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

				int timeoutSocket = 1500;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				DefaultHttpClient client = new DefaultHttpClient(httpParameters);
				HttpGet request = new HttpGet(
						"http://boox.eu01.aws.af.cm/users/"+friend);
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
						while ((line = reader.readLine()) != null) { 
						    sb.append(line + "\n"); 
						}
						stream.close();
						responseString = sb.toString();

					}
				////////////////////////////////////////////////////////////////////////////////////////////////////
				} catch (ConnectTimeoutException e) {
					flag = 0;
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					flag = 0;
					e.printStackTrace();
				} catch (IOException e) {
					flag = 0;
					e.printStackTrace();
				}
			}    
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
	       // setProgressBarIndeterminateVisibility(false);
	       // progressDialog.dismiss();
	        if(flag == 1 && responseString == "")
	        	flag = 2;
			if(flag == 0){
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.friends_internet_error), 
						Toast.LENGTH_SHORT);
				toast.show();
			}
			if(flag == 1){
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();

				JSONObject json;
				
				try {
					Usuario us;
					json = new JSONObject(responseString);
				    us = gson.fromJson(json.toString(),
						Usuario.class);
			    	Intent intent = new Intent();
			        intent.setClass(context, UserProfileActivity.class);
			        intent.putExtra("uname", uname);
			        intent.putExtra("fname", us.getId());
			        intent.putExtra("full", us.getNombre());
			        intent.putExtra("modo", "nosesabe");
			        startActivity(intent);
		        	
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if(flag == 2){
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.friends_not_found), 
						Toast.LENGTH_SHORT);
				toast.show();
			}
			if(flag == 3){
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.friends_auto_find), 
						Toast.LENGTH_SHORT);
				toast.show();
			}

		}
	
	}

}
