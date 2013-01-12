package com.example.boox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends Activity {

	String fname = "";
	String uname = "";
	Context context = this;
	ProgressDialog progressDialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	uname = extras.getString("uname");
            fname = extras.getString("fname");
            TextView id = (TextView) findViewById(R.id.userprofile_username);
            id.setText(fname);
            String full = extras.getString("full");
            TextView id2 = (TextView) findViewById(R.id.userprofile_fullname);
            id2.setText(full);
        }
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user_profile, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.search:
    		startActivity(new Intent(this, SearchBookActivity.class));
            return true;
    	case R.id.submenu_settings:
    		startActivity(new Intent(this, SettingsActivity.class));
        	return true;
    	case R.id.submenu_about:
    		startActivity(new Intent(this, AboutActivity.class));
        	return true;
    	}
    	return false;
	}
    
    
    public void onPressDeleteFriend(View view) {
    	
        setProgressBarIndeterminateVisibility(true);
        progressDialog = ProgressDialog.show(context, 
        		getResources().getString(R.string.signup_sending),
        		getResources().getString(R.string.signup_pleasewait));
		AsyncDelFriend df = new AsyncDelFriend();
		df.execute(null, null, null);
    }
    
    public class AsyncDelFriend extends AsyncTask<Void, Void, Integer> {
    	
    	boolean cool = true;
    	
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

				HttpParams httpParameters = new BasicHttpParams();

				int timeoutConnection = 1500;
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

				int timeoutSocket = 1500;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				DefaultHttpClient client = new DefaultHttpClient(httpParameters); 
	            HttpPut httpput = new HttpPut("http://boox.eu01.aws.af.cm/users/"+uname+"/delFriend"); 

	            JSONObject json = new JSONObject();
	            
	            try {
					json.put("uname", uname);
					json.put("fname", fname);

	                StringEntity se;
					se = new StringEntity(json.toString());
	 
	                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	                httpput.setEntity(se);
	                HttpResponse response;
					response = client.execute(httpput);

	                HttpEntity responseEntity = response.getEntity();
					InputStream stream = responseEntity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(stream));
					String line = null;
					line = reader.readLine();

	            	} catch (ConnectTimeoutException e) {
	            		cool = false;
	            		e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						cool = false;
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						cool = false;
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						cool = false;
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						cool = false;
						e.printStackTrace();
					}
			
  
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {

			// Stop the indeterminate progress bar and close dialog
	        setProgressBarIndeterminateVisibility(false);
	        progressDialog.dismiss();
			
			if(cool){
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.friends_delete), 
						Toast.LENGTH_SHORT);
				toast.show();
		        Intent intent = new Intent(context, TabsActivity.class);
		       	startActivity(intent);
			}
			else {
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.friends_internet_error), 
						Toast.LENGTH_SHORT);
				toast.show();
			}

		}
	
	}
}
