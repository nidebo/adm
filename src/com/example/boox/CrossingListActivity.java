package com.example.boox;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CrossingListActivity extends FragmentActivity implements OnItemClickListener {
	
	public ListView crossingListView;
	public ArrayList<CrossingListRow> crossingList;
    AdapterView.AdapterContextMenuInfo info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossing_list);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        /*if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            CrossingListFragment crossinglist = new CrossingListFragment();
            crossinglist.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
            	.beginTransaction()
            	.add(android.R.id.content, crossinglist)
            	.commit();
        }*/
        
        crossingList = new ArrayList<CrossingListRow>();
        CrossingListRow row;
        
        for(int i=0; i<5; i++){
	        row = new CrossingListRow();
	        row.setThumb1(R.drawable.got_thumbnail_small);
	        row.setTitle1("Juego de Tronos");
	        row.setAuthor1("G.R.R. Martin");
	        row.setThumb2(R.drawable.got_thumbnail_small);
	        row.setTitle2("Choque de Reyes");
	        row.setAuthor2("G.R.R. Martin");
	        row.setState("Rejected");
	        crossingList.add(row);
        }

		crossingListView = (ListView) findViewById(R.id.crossingList);
		CrossingListAdapter adapter = new CrossingListAdapter(this, crossingList);
        crossingListView.setAdapter(adapter);
        crossingListView.setOnItemClickListener(this);
        
        registerForContextMenu(crossingListView);
    }

	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		
        Intent intent = new Intent();
        intent.setClass(this, CrossingActivity.class);
        intent.putExtra("index", position);
        startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_crossing_list, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                Intent parentActivityIntent = new Intent(this, TabsActivity.class);
                parentActivityIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(parentActivityIntent);
                finish();
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
            intent.setClass(this	, CrossingActivity.class);
            //intent.putExtra("index", item.getPosition());
            startActivity(intent);
		}else if(item.getTitle() == "Delete"){
			// Do something
		}else {
			return false;
		}
		return true;
	}

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }*/
}
