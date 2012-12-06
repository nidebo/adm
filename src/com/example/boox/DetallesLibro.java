package com.example.boox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class DetallesLibro extends Activity {
	
	ImageView thumbnail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.detalles_libro);
		loadThumbnail();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalles_libro, menu);
		return true;
	}
	
	private void loadThumbnail(){

		String img_name = "got_thumbnail";
		String img_route = "/";

		String imageInSD = Environment.getExternalStorageDirectory().getAbsolutePath() + img_route + img_name + ".jpeg";
		//String imageInSD = "/sdcard/got_thumbnail.jpeg";
		Log.d("loadThumbnail", "Opening " + imageInSD);
		
		Bitmap bitmap = BitmapFactory.decodeFile(imageInSD);
		
        thumbnail = (ImageView)findViewById(R.id.thumbnail_imageView);
        thumbnail.setImageBitmap(bitmap);
	}

}
