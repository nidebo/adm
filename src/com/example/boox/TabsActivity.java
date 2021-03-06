package com.example.boox;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



public class TabsActivity extends FragmentActivity implements ActionBar.TabListener{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	final int mode = Activity.MODE_PRIVATE;
	public static final String myPrefs = "prefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabs);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i=0; i<mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_tabs, menu);
    
		return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.search:
    		startActivity(new Intent(this, SearchBookActivity.class));
            return true;
        case R.id.submenu_share:
        	Intent intent2 = new Intent(Intent.ACTION_SEND); 
        	intent2.setType("text/plain"); 
        	intent2.putExtra(Intent.EXTRA_SUBJECT, "BooX"); 
        	intent2.putExtra(Intent.EXTRA_TEXT, "http://ooomf.com/boox"); 

        	Intent chooser = Intent.createChooser(
        			intent2, "tell a friend about BooX"); 
        	startActivity(chooser);
        	return true;
    	case R.id.submenu_add_list:
    		startActivity(new Intent(this, AddNewListActivity.class));
    		return true;     
            
    	case R.id.submenu_find_friend:
    		startActivity(new Intent(this, SearchFriendActivity.class));
    		return true;
    	case R.id.submenu_about:
    		startActivity(new Intent(this, AboutActivity.class));
        	return true;
        	
    	case R.id.submenu_logout:
			SharedPreferences mySharedPreferences = getSharedPreferences(myPrefs,
					mode);
			SharedPreferences.Editor myEditor = mySharedPreferences.edit();
			//myEditor.putString("username", "");
			myEditor.remove("username");
			myEditor.commit();
			finish();
	        Intent intent = new Intent(this, LoginActivity.class);
	       	startActivity(intent);
    	
    	}
    	return false;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, 
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	/*-------------------------*/

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			switch(i){
				case 0:
					fragment = new TabBooksFragment();
					break;
				case 1:
					fragment = new TabCrossingsFragment();
					break;
				case 2:
					fragment = new TabFriendsFragment(); //DummySectionFragment();
					break;
			};
			Bundle args = new Bundle();
			//args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0: return getString(R.string.tab1).toUpperCase();
				case 1: return getString(R.string.tab2).toUpperCase();
				case 2: return getString(R.string.tab3).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	/*public static class DummySectionFragment extends Fragment {

		 //The fragment argument representing the section number for this
		 //fragment.
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			
			View view;
			if((getArguments().getInt(ARG_SECTION_NUMBER)) == 1){
				view = inflater.inflate(R.layout.books_tab, null);
			}
			else
				if(getArguments().getInt(ARG_SECTION_NUMBER) == 2)
					view = inflater.inflate(R.layout.fragment_book_details, null);
				else
					if(getArguments().getInt(ARG_SECTION_NUMBER)==3)
						view = inflater.inflate(R.layout.resultado_busqueda_libros, null);
					else
						view = inflater.inflate(R.layout.activity_tabs, null);
			

			return view;
		}
	}*/

}
