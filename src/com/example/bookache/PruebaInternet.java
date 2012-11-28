package com.example.bookache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.example.bookache.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
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

	public void onPressPrueba(View view) {
        // Do something in response to button
        TextView txt = (TextView) findViewById(R.id.textoprueba);
        HttpParams httpParameters = new BasicHttpParams();

		DefaultHttpClient client = new DefaultHttpClient(httpParameters);

		HttpGet request = new HttpGet(
				"http://bookache.eu01.aws.af.cm/books/50b540267903044071000002");
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
				StringBuilder sb = new StringBuilder();
				String line = null;
				line = reader.readLine();
				sb.append(line);
				stream.close();
				txt.setText(sb.toString());

			}
		} catch (ConnectTimeoutException e) {
			
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }


}
