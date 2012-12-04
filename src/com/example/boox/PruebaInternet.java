package com.example.boox;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.boox.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PruebaInternet extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prueba_internet);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_prueba_internet, menu);
		return true;
	}
	
	public class Async extends AsyncTask<Void, Void, Void> {

		//boolean cool = true;
		StringBuilder sb;
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			 URL url;
			try {
				url = new URL("http://boox.eu01.aws.af.cm/users");
			
			    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				sb = new StringBuilder();
				String line = null;
				line = reader.readLine();
				sb.append(line);
				in.close();
			    
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

		protected void onPostExecute(Void result) {
			
	        TextView txt = (TextView) findViewById(R.id.textoprueba);
			txt.setText(sb.toString());

		}
	
	}
	
	
	public class AsyncAdd extends AsyncTask<Void, Void, Void> {

		//boolean cool = true;

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			HttpClient httpclient = new DefaultHttpClient();  
            HttpPost httppost = new HttpPost("http://boox.eu01.aws.af.cm/users"); 
            EditText etxt = (EditText) findViewById(R.id.editText1);
            String username = etxt.getText().toString();

            //String uname ="{\"name\":\"" + username + "\"}";

            JSONObject json = new JSONObject();
            
            try {

				json.put("name", username);
                StringEntity se;
				se = new StringEntity(json.toString());
 
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httppost.setEntity(se);
                HttpResponse response;
				response = httpclient.execute(httppost);

                HttpEntity responseEntity =response.getEntity();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			     
			return null;
		}

		protected void onPostExecute(Void result) {
			
	        EditText etxt = (EditText) findViewById(R.id.editText1);
			etxt.setText("");

		}
	
	}
	

	public void onPressPrueba(View view) {
        // Do something in response to button

		Async fr = new Async();
		fr.execute(null, null, null);
			
    }
	
	public void onPressAnyadir(View view) {
        // Do something in response to button

		AsyncAdd a1 = new AsyncAdd();
		a1.execute(null, null, null);
			
    }


}
