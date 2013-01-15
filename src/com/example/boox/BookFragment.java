package com.example.boox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

import listasLibros.Libro;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class BookFragment extends Fragment implements OnRatingBarChangeListener {
	
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
	
	/**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static BookFragment newInstance(int index) {
    	Log.d("BookFragment", "newInstance("+index+")");
    	BookFragment f = new BookFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }
    
    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
		
        SharedPreferences mySharedPreferences = this.getActivity().getParent().getSharedPreferences(myPrefs, mode);
		uname = mySharedPreferences.getString("username", "");
        Bundle extras = this.getActivity().getIntent().getExtras();
        id = extras.getString("id");
        //GestorListas gl = new GestorListas(uname, BookActivity.this); 
        //lib = gl.getLibroPorId(id);
		MyBD mbd = new MyBD(getActivity(),uname);
        book = mbd.DetalleLibroId(id);


        title = (TextView) getActivity().findViewById(R.id.title);
        title.setText(book.getTitulo());
        
        subtitle = (TextView) getActivity().findViewById(R.id.subtitle);
        subtitle.setText(book.getSubtitulo());
        
        String authors = book.getAutores().get(0);
        if(book.getAutores().size() > 1)
        	for(int i=1; i<book.getAutores().size(); ++i)
        		authors = authors + ", " + book.getAutores().get(i);
        author = (TextView) getActivity().findViewById(R.id.author);
        author.setText(authors);

        publisher = (TextView) getActivity().findViewById(R.id.publisher);
        publisher.setText(book.getEditorial());

        num_pages = (TextView) getActivity().findViewById(R.id.numpages);
        num_pages.setText(book.getNumeroDePaginas());

        description = (TextView) getActivity().findViewById(R.id.description);
        description.setText(book.getDescripcion());
        
        ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
        ratingBar.setRating(book.getPuntuacionMedia());
        ratingBar.setOnRatingBarChangeListener(this);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    	Log.d("BookFragment", "onCreateView");
        if (container == null) {
            // Currently in a layout without a container, so no
            // reason to create our view.
            return null;
        }

        bookdetails_view = inflater.inflate(R.layout.fragment_book, container, false);
        loadThumbnail();
        
    	Log.d("BookFragment", "onCreateView done");
        
        return bookdetails_view;
    }

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){

        //DecimalFormat decimalFormat = new DecimalFormat("#.#");
        //curRate = Float.valueOf(decimalFormat.format((curRate * count + rating) / ++count));
        Toast.makeText(this.getActivity(),
                "You rated this with " + curRate + " points", Toast.LENGTH_SHORT).show();
        //setRatingBar.setRating(curRate);
        countText.setText(count + " Ratings");
	}
    

/*
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
	*/
	
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
