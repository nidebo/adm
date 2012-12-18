package com.example.boox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

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

public class BookDetailsFragment extends Fragment {
	
	View bookdetails_view;
	ImageView thumbnail;
	
	/**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static BookDetailsFragment newInstance(int index) {
    	Log.d("DetallesLibro", "newInstance("+index+")");
    	BookDetailsFragment f = new BookDetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }
    
    /*@Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        View view = getActivity().findViewById(R.id.details);
    }*/
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    	Log.d("BookDetailsFragment", "onCreateView");
        if (container == null) {
            // Currently in a layout without a container, so no
            // reason to create our view.
            return null;
        }

        bookdetails_view = inflater.inflate(R.layout.detalles_libro, container, false);
        loadThumbnail();
        
    	Log.d("DetallesLibro", "onCreateView done");
        
        return bookdetails_view;
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

		//String img_name = "got_thumbnail";
		String img_route = "/got_thumbnail.jpeg";

		String imageInSD = Environment.getExternalStorageDirectory().getAbsolutePath() + img_route;
		//String imageInSD = "/sdcard/got_thumbnail.jpeg";
		Log.d("DetallesLibro", "loadThumbnail(" + imageInSD + ")");
		
		/*Bitmap bitmap = BitmapFactory.decodeFile(imageInSD);
		
        thumbnail = (ImageView)getActivity().findViewById(R.id.thumbnail_imageView);
        thumbnail.setImageBitmap(bitmap);*/
		
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
    		if(thumbnail != null) 
    			Log.d("DetallesLibro", "bitmap loaded into thumbnail");
            thumbnail.setImageBitmap(bitmap);
        } 
        catch (Exception e) {
            //Try to recover
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
            }
        }
	}

}
