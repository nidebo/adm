package com.example.boox;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CrossingListFragment extends ListFragment implements OnItemClickListener {

    boolean mDualPane;
    int mCurCheckPosition = 0; 

	/*String crossings[] = new String[]{
			"Crossing 1", 
			"Crossing 2",
			"Crossing 3",
			"Crossing 4"};

	public ArrayList<String> crossinglist = new ArrayList<String>();*/
	
	public ListView crossingListView;
	public ArrayList<CrossingListRow> crossingList;
    AdapterView.AdapterContextMenuInfo info;
	
	
	@Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        
        crossingList = new ArrayList<CrossingListRow>();
        CrossingListRow row;
        
        row = new CrossingListRow();
        row.setThumb1(R.drawable.got_thumbnail_small);
        row.setTitle1("Juego de Tronos");
        row.setAuthor1("G.R.R. Martin");
        row.setThumb2(R.drawable.got_thumbnail_small);
        row.setTitle2("Choque de Reyes");
        row.setAuthor2("G.R.R. Martin");
        row.setState("Rejected");
        crossingList.add(row);

		crossingListView = (ListView) getActivity().findViewById(R.id.crossingList);
		CrossingListAdapter adapter = new CrossingListAdapter(getActivity(), crossingList);
        crossingListView.setAdapter(adapter);
        crossingListView.setOnItemClickListener(this);

        // Populate list with our static array of titles.
		/*for(int i=0; i<crossings.length; ++i)
			crossinglist.add(crossings[i]);	
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                crossinglist));*/

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        /*View detallesCrossing = getActivity().findViewById(R.id.details);
        mDualPane = detallesCrossing != null && detallesCrossing.getVisibility() == View.VISIBLE;
    	Log.d("CrossingList", "onActivityCreated -> mDualPane = " + (mDualPane ? "true" : "false"));

        if(savedState != null){
        	Log.d("CrossingList", "savedState = null");
            // Restore last state for checked position.
            mCurCheckPosition = savedState.getInt("curChoice", 0);
        }
        if(mDualPane){
        	Log.d("CrossingList", "mDualPane = true");
            // In dual-pane mode, list view highlights selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }*/
    }

	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		
        Intent intent = new Intent();
        intent.setClass(getActivity(), CrossingActivity.class);
        intent.putExtra("index", position);
        startActivity(intent);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}



	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		info = (AdapterContextMenuInfo) menuInfo;

		menu.setHeaderTitle("Crossing options"); //(crossingList.get(info.position).getTitle2());
		menu.add(Menu.NONE, v.getId(), 0, "View");
		menu.add(Menu.NONE, v.getId(), 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getTitle() == "View"){
			Intent intent = new Intent();
            intent.setClass(getActivity(), CrossingActivity.class);
            //intent.putExtra("index", item.getPosition());
            startActivity(intent);
		}else if(item.getTitle() == "Delete"){
			// Do something
		}else {
			return false;
		}
		return true;
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
    
    /*@Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
    	Log.d("CrossingList", "onListItemClick(" + pos + ")");
        showDetails(pos);
    }*/

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		for(int i=0; i<strings.length; ++i)
			list.add(strings[i]);	
		ArrayAdapter<String> mAdapter = 
    			new ArrayAdapter<String>(getActivity().getBaseContext(), 
    									 android.R.layout.simple_list_item_1, 
    									 list);
		setListAdapter(mAdapter);
    	return super.onCreateView(inflater, container, savedInstanceState);
	}*/
    
    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    /*void showDetails(int index) {
    	
        mCurCheckPosition = index;

        /*if(mDualPane){
        	Log.d("CrossingList", "showDetails(DualPane)");
            // We can display everything in-place with fragments.
            // Have the list highlight this item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is shown, replace if needed.
            DetallesLibroFragment bookDetails = (DetallesLibroFragment) getFragmentManager().findFragmentById(R.id.details);
            if (bookDetails == null || bookDetails.getShownIndex() != index) {
                // Make new fragment to show this selection.
                bookDetails = DetallesLibroFragment.newInstance(index);

                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, bookDetails);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else { 
        	Log.d("CrossingList", "showDetails(MonoPane)");
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), CrossingActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        //}
    }*/

	//@Override
	//public void onStart(){
		//super.onStart();
		//getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
	//}
	
	//public void addItem(String s){
		//crossinglist.add(s);
	//}

	/*
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_tab);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        
        lvDatos = (ListView) findViewById(R.id.listView1);
    	
    	//final ArrayList<String> mLista = new ArrayList<String>();
    	final ArrayAdapter<String> mAdapter = 
    			new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings); //mLista);
    	lvDatos.setAdapter(mAdapter);
        
        //mLista.add("Lista Prueba 1");
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_books_tab, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
	
	/*private void loadThumbnail(){

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
    		//if(thumbnail != null) 
    			//Log.d("DetallesLibro", "bitmap loaded into thumbnail");
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
	}*/

}
