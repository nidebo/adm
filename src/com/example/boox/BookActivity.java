package com.example.boox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import listasLibros.Libro;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends FragmentActivity implements OnRatingBarChangeListener  {
	
	String book_title = "unknown_book";  //Default string. Setter below
	
    final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";
	String uname = "";
	String id;
	Libro book = new Libro();
	
	View bookdetails_view;
	ImageView thumbnail;
	TextView title, subtitle, author, publisher, num_pages;
	TextView description_title, description;
	
	// Variables para RatingBar
    RatingBar ratingBar;
    TextView countText;
    int count;
    float curRate;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_book_details);	
		
		//Activate the up button
		getActionBar().setDisplayHomeAsUpEnabled(true);
				
		SharedPreferences mySharedPreferences = getParent().getSharedPreferences(myPrefs, mode);
		uname = mySharedPreferences.getString("username", "");
        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");
        //GestorListas gl = new GestorListas(uname, BookActivity.this); 
        //lib = gl.getLibroPorId(id);
		MyBD mbd = new MyBD(BookActivity.this,uname);
        book = mbd.DetalleLibroId(id);


        loadThumbnail();
        
        loadBookData();
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){

        //DecimalFormat decimalFormat = new DecimalFormat("#.#");
        //curRate = Float.valueOf(decimalFormat.format((curRate * count + rating) / ++count));
        Toast.makeText(BookActivity.this,
                "You rated this with " + curRate + " points", Toast.LENGTH_SHORT).show();
        //setRatingBar.setRating(curRate);
        countText.setText(count + " Ratings");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_book_details, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                Intent parentActivityIntent = new Intent(this, BookListActivity.class);
                parentActivityIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(parentActivityIntent);
                finish();
                return true;
            case R.id.submenu_share:
            	Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            	sharingIntent.setType("text/html");
            	sharingIntent.putExtra(
            			android.content.Intent.EXTRA_TEXT, 
            			Html.fromHtml("<p>I just viewed " + book_title + " on BooX</p>"));
            	startActivity(Intent.createChooser(sharingIntent, "Share with"));
            	return true;
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
        return super.onOptionsItemSelected(item);
    }
    
    private void loadBookData(){

        title = (TextView) findViewById(R.id.title);
        title.setText(book.getTitulo());
        book_title = book.getTitulo();
        
        subtitle = (TextView) findViewById(R.id.subtitle);
        subtitle.setText(book.getSubtitulo());
        
        String authors = book.getAutores().get(0);
        if(book.getAutores().size() > 1)
        	for(int i=1; i<book.getAutores().size(); ++i)
        		authors = authors + ", " + book.getAutores().get(i);
        author = (TextView) findViewById(R.id.author);
        author.setText(authors);

        publisher = (TextView) findViewById(R.id.publisher);
        publisher.setText(book.getEditorial());

        num_pages = (TextView) findViewById(R.id.numpages);
        num_pages.setText(book.getNumeroDePaginas());

        description = (TextView) findViewById(R.id.description);
        description.setText(book.getDescripcion());
        
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(book.getPuntuacionMedia());
        ratingBar.setOnRatingBarChangeListener(this);
    }
	
	private void loadThumbnail(){

		String img_route = "/got_thumbnail.jpeg";

		String imageInSD = Environment.getExternalStorageDirectory().getAbsolutePath() + img_route;
		Log.d("DetallesLibro", "loadThumbnail(" + imageInSD + ")");
		
		FileInputStream is = null;
        BufferedInputStream bis = null;
        try {
            is = new FileInputStream(new File(imageInSD));
            bis = new BufferedInputStream(is);
            Bitmap bitmap = BitmapFactory.decodeStream(bis);
            //Bitmap useThisBitmap = Bitmap.createScaledBitmap(bitmap, 30, 60, true);
            //bitmap.recycle();
            //Display bitmap (useThisBitmap)
            thumbnail = (ImageView) bookdetails_view.findViewById(R.id.thumbnail_imageView);
    		/*if(thumbnail != null) 
    			Log.d("DetallesLibro", "bitmap loaded into thumbnail");*/
            thumbnail.setImageBitmap(bitmap);
        } 
        catch(Exception e){
            //Try to recover
        }
        finally{
            try{
                if(bis != null){
                    bis.close();
                }
                if(is != null){
                    is.close();
                }
            }catch(Exception e){
            }
        }
	}

}
