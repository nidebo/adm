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

import utiles.AmigoList;

import com.example.boox.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	
	
	public class AsyncFriends extends AsyncTask<Void, Void, Void> {

		//boolean cool = true;
		StringBuilder sb;
		String responseString;
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			 URL url;
			try {
				//String uname = "nicolas";
				url = new URL("http://boox.eu01.aws.af.cm/users/nicolas/friendList");
			
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
			
			TextView txt = (TextView) findViewById(R.id.textView1);
			//txt.setText(sb.toString());
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			JSONObject json;
			AmigoList al;
			try {
				json = new JSONObject(responseString);

			    al = gson.fromJson(json.toString(),
					AmigoList.class);
			    txt.setText(String.valueOf(al.getAmigos().size()));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

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

            //String uname ="{\"uname\":\"" + username + "\"}";

            JSONObject json = new JSONObject();
            
            try {

				json.put("uname", username);
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
	
	public void onPressAmigos(View view) {
		
		AsyncFriends a2 = new AsyncFriends();
		a2.execute(null, null, null);


	}
}
