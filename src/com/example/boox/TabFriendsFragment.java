package com.example.boox;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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

import usuarios.AmigoList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TabFriendsFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 1;
	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";

	String strings[] = new String[]{
			"Friend 1", 
			"Friend 2",
			"Friend 3"};

	public ArrayList<String> list = new ArrayList<String>(); 
	
	
	@Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);


        
		SharedPreferences mySharedPreferences = this.getActivity().getSharedPreferences(myPrefs, mode);
		String uname = mySharedPreferences.getString("username", "");
        
		if(!uname.equals("")){
			AsyncFriends a2 = new AsyncFriends();
			a2.execute(uname, null, null);
		}
		else {
			//no se pudo obtener el usuario del que listar los amigos
	        // Populate list with our static array of titles.
	        if(list.isEmpty()){
	        	for(int i=0; i<strings.length; ++i)
	        		list.add(strings[i]);	
	        	setListAdapter(new ArrayAdapter<String>(getActivity(),
	        			android.R.layout.simple_list_item_1,
	        			list));
	        }
			
		}


        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        /*View detallesLibro = getActivity().findViewById(R.id.details);
        mDualPane = detallesLibro != null && detallesLibro.getVisibility() == View.VISIBLE;
    	Log.d("BooksTab", "onActivityCreated -> mDualPane = " + (mDualPane ? "true" : "false"));

        if(savedState != null){
        	Log.d("BooksTab", "savedState = null");
            // Restore last state for checked position.
            mCurCheckPosition = savedState.getInt("curChoice", 0);
        }
        if(mDualPane){
        	Log.d("BooksTab", "mDualPane = true");
            // In dual-pane mode, list view highlights selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }*/
    }
	
	public class AsyncFriends extends AsyncTask<String, Void, Void> {

		StringBuilder sb;
		String responseString;
		boolean flag = true;
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			URL url;

			try {
				//String uname = "nicolas";
				url = new URL("http://boox.eu01.aws.af.cm/users/"+params[0]+"/friendList");
			
			    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			    urlConnection.setConnectTimeout(1500);
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

		@Override
		protected void onPostExecute(Void result) {
		
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();

				JSONObject json;
				AmigoList al;
				try {
					json = new JSONObject(responseString);

				    al = gson.fromJson(json.toString(),
						AmigoList.class);
				    //txt.setText(String.valueOf(al.getListaAmigos().size()));
			        if(list.isEmpty()){
		        	for(int i=0; i<al.getListaAmigos().size(); ++i)
		        		list.add(al.getListaAmigos().get(i).getNombre());	
		        	setListAdapter(new ArrayAdapter<String>(getActivity(),
		        			android.R.layout.simple_list_item_1,
		        			list));
		        	}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
	
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
    	Log.d("TabFriendsFragment", "onListItemClick(" + pos + ")");
    	
    	Intent intent = new Intent();
        intent.setClass(getActivity(), UserProfileActivity.class);
        intent.putExtra("index", pos);
        startActivity(intent);
    	
        //showDetails(pos);
    }
	
	public void addItem(String s){
		list.add(s);
	}
	
}