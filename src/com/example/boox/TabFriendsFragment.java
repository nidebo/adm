package com.example.boox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	AmigoList al = new AmigoList();
	String uname = "";

	String strings[] = new String[]{
			"Friend 1", 
			"Friend 2",
			"Friend 3"};

	public ArrayList<String> list = new ArrayList<String>(); 
	
	
	@Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);


        
		SharedPreferences mySharedPreferences = this.getActivity().getSharedPreferences(myPrefs, mode);
		uname = mySharedPreferences.getString("username", "");
        
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
	       setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
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

		StringBuilder sb = new StringBuilder();
		String responseString;
		boolean flag = true;
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			///////////////////////////////////////////////
			HttpParams httpParameters = new BasicHttpParams();

			int timeoutConnection = 1500;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

			int timeoutSocket = 1500;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient client = new DefaultHttpClient(httpParameters);
			HttpGet request = new HttpGet(
					"http://boox.eu01.aws.af.cm/users/"+params[0]+"/friendList");
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
				flag = false;
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				flag = false;
				e.printStackTrace();
			} catch (IOException e) {
				flag = false;
				e.printStackTrace();
			}
			     
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if(flag){
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();

				JSONObject json;
				
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
			else {
		    	   Toast toast = Toast.makeText(
							getActivity(), 
							getResources().getString(R.string.friends_internet_error), 
							Toast.LENGTH_SHORT);
					toast.show();
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
        intent.putExtra("uname", uname);
        intent.putExtra("fname", al.getListaAmigos().get(pos).getId());
        intent.putExtra("full", al.getListaAmigos().get(pos).getNombre());
        intent.putExtra("cp", al.getListaAmigos().get(pos).getCP());
        intent.putExtra("modo", "borrar");
        startActivity(intent);
    	
        //showDetails(pos);
    }
	
	public void addItem(String s){
		list.add(s);
	}
	
}