package com.example.boox;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

//no comprueba si usuario ya existe, DE MOMENTO
public class SignUpActivity extends Activity {
	
	Context context = this;
	
	ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // This must be called before ANY content is created
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        
        setContentView(R.layout.activity_sign_up);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sign_up, menu);
        return true;
    }
    
	public void onPressSignUp(View view) {
		
        setProgressBarIndeterminateVisibility(true);
        progressDialog = ProgressDialog.show(context, 
        		getResources().getString(R.string.signup_sending),
        		getResources().getString(R.string.signup_pleasewait));

		AsyncAdd add = new AsyncAdd();
		add.execute(null, null, null);
    }
    
    
    public class AsyncAdd extends AsyncTask<Void, Void, Void> {
    	
    	EditText ptxt;
    	EditText ctxt;
    	String pass;
    	boolean flag = false;
    	//falta comprobar que usuario ya existe
    	
		@Override
		protected void onPreExecute(){
			ptxt = (EditText) findViewById(R.id.signup_password);
			ctxt = (EditText) findViewById(R.id.signup_confirm_password);
			pass = ptxt.getText().toString();
			String pass2 = ctxt.getText().toString();
			if(pass.equals(pass2))
				flag = true;
			
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(flag){
				HttpClient httpclient = new DefaultHttpClient();  
	            HttpPost httppost = new HttpPost("http://boox.eu01.aws.af.cm/users"); 
	            EditText utxt = (EditText) findViewById(R.id.signup_username);
	            EditText cptxt = (EditText) findViewById(R.id.signup_zipcode);
	            EditText fulltxt = (EditText) findViewById(R.id.signup_fullname);
	            
	            String username = utxt.getText().toString();
	            String cp = cptxt.getText().toString();
	            String full = fulltxt.getText().toString();
	           
	            //String uname ="{\"uname\":\"" + username + "\"}";

	            JSONObject json = new JSONObject();
	            
	            try {
					json.put("uname", username);
					json.put("pass", pass);
					json.put("postal", cp);
					json.put("fullname", full);
	                StringEntity se;
					se = new StringEntity(json.toString());
	 
	                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	                httppost.setEntity(se);
	                HttpResponse response;
					response = httpclient.execute(httppost);

	                HttpEntity responseEntity = response.getEntity();
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
			}
  
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			// Stop the indeterminate progress bar and close dialog
	        setProgressBarIndeterminateVisibility(false);
	        progressDialog.dismiss();
			
			if(!flag){
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.signup_confirm_pass_error), 
						Toast.LENGTH_SHORT);
				toast.show();
			}
			else {
				Toast toast = Toast.makeText(
						getApplicationContext(), 
						getResources().getString(R.string.signup_user_created), 
						Toast.LENGTH_SHORT);
				toast.show();
				
		        Intent intent = new Intent(context, TabsActivity.class);
		       	startActivity(intent);
			}
	        //EditText etxt = (EditText) findViewById(R.id.editText1);
			//etxt.setText("");

		}
	
	}
    
}
