package com.example.boox;

import listasLibros.GestorListas;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    	String nlista = et.getText().toString();
		GestorListas gl = new GestorListas(uname, AddNewListActivity.this);
		if(nlista.equals("All") || nlista.equals("Favoritos")){
			Toast toast = Toast.makeText(
					this, 
					getResources().getString(R.string.login_invalid), 
					Toast.LENGTH_SHORT);
			toast.show();
			//t.setText("Invalid name");
			return;
		}
		if(gl.AddListaVacia(nlista) == false){
			//t.setText("List already exist");
			Toast toast = Toast.makeText(
					this, 
					getResources().getString(R.string.login_invalid), 
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
    	Intent intent = new Intent();
    	gl.AddListaVacia(nlista);
        intent.setClass(context, TabsActivity.class);
        intent.putExtra("addlist", nlista);
        startActivity(intent);
    	//this.finish();
    }
 
}
