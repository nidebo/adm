package com.example.boox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import listasLibros.GestorListas;

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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewListActivity extends Activity {
	
	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";
	String uname = "";
	Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_list);

		SharedPreferences mySharedPreferences = getSharedPreferences(myPrefs, mode);
		uname = mySharedPreferences.getString("username", "");
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_friend, menu);
		return true;
	}
	
    public void onPressAdd(View view) {

		EditText et = (EditText) findViewById(R.id.add_list);
		GestorListas gl = new GestorListas(uname);
		String nlista = et.getText().toString();
    	gl.AddListaVacia(nlista);
    	//this.finish();
     }
 
   }
