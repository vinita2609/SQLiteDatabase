package com.example.naamkaran;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.naamkaran.Interface.UpdateFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class TabActivity extends AppCompatActivity implements RecyclerviewAdapter.RecyclerviewAdapterListener{
    //implements RecyclerviewAdapter.RecyclerviewAdapterListener
    private TextView mTextMessage;
    TextView mTextMessage1;

    private List<NameDisplay> nameList;
    TabLayout tabLayout;
    ViewPager viewPager;
    RecyclerviewAdapter nameAdaptor;
    SearchView searchView = null;
    MenuItem search;
    Toolbar toolbar;
    private ProgressDialog pDialog;

    public static final int STORAGE_PERMISSION_REQUEST_CODE= 1;

    UpdateFragment updatfrag ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTitle(null);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tab);

        int permissionCheckStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheckStorage == PackageManager.PERMISSION_GRANTED) {

        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("You need to give permission to access storage in order to work this feature.");
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("GIVE PERMISSION", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        ActivityCompat.requestPermissions(TabActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_REQUEST_CODE);
                    }
                });
                builder.show();

            }
            else {
                ActivityCompat.requestPermissions(TabActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);

            }

        }
        //TO-DO  showProgDig();
        if (!isNetworkAvailable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Internet Connection Required")
                    .setCancelable(false)
                    .setPositiveButton("Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }





    /*    toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        Log.d("mehul", " im tab activity");

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //viewPager.setAdapter(viewPagerAdapter);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPagerAdapter adapter = new ViewPagerAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        // final ViewPagerAdapter adapter = new ViewPagerAdapter(this,getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("mehul", "onTabSelected tab activity");
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //mTextMessage1 = (TextView) findViewById(R.id.message);

      /*  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation1);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);public*/
    }
    //    public boolean onCreateOptionsMenu (Menu menu){
//            // MenuInflater inflater = getActivity().getMenuInflater();
//
//            getMenuInflater().inflate(R.menu.menu_main, menu);
//            // getMenuInflater().inflate(R.menu.menu_main, menu);
//            MenuItem search = menu.findItem(R.id.search);
//
//           //  SearchView searchView = (SearchView) search.getActionView();
//          android.widget.SearchView searchView =
//                    (android.widget.SearchView) search.getActionView();
//            search(searchView);
//
//
//            return true;
//        }
//
//
//
//
//    public boolean onOptionsItemSelected (MenuItem item){
//
//            return super.onOptionsItemSelected(item);
//        }
//
//        private void search (android.widget.SearchView searchView){
//
//            searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//
//                    nameAdaptor.getFilter().filter(newText);
//                    return true;
//                }
//            });
//
//        }
//
//
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

//   searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (nameAdaptor != null) {
                    nameAdaptor.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (nameAdaptor != null) {
                    nameAdaptor.getFilter().filter(newText);
                }
                updatfrag.searchList(newText);

                return false;
            }
        });
        return true;
    }
    //
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateApi(UpdateFragment listener) {
        updatfrag = listener;
    }

//        @Override
//        public void onBackPressed() {
//            // close search view on back button pressed
//            if (!searchView.isIconified()) {
//                searchView.setIconified(true);
//                return;
//            }
//            super.onBackPressed();
//        }


    @Override
    public void onNameSelected(NameDisplay nameDisplay) {
        Toast.makeText(getApplicationContext(), "Selected: " + nameDisplay.getName() + ", " + nameDisplay.getMeaning(), Toast.LENGTH_LONG).show();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
//    private void showProgDig() {
//        pDialog = new ProgressDialog(tab.this);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);
//        pDialog.show();
//    }
//    private Boolean exit = false;


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(TabActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.home_logo);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                        finish();

                    }
                })
                .setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.menu_main, menu);
//    SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
//    MenuItem mSearchMenuItem = menu.findItem(R.id.search);
//    searchView = (SearchView) menu.findItem(R.id.search)
//            .getActionView();
//
//    if (mSearchMenuItem != null) {
//        searchView = (SearchView) mSearchMenuItem.getActionView();
//    }
//    if (searchView != null) {
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(this
//                .getComponentName()));
//    }
//    searchView.setIconifiedByDefault(true);
//    MenuItemCompat.getTooltipText(mSearchMenuItem);
//
//    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
//        public boolean onQueryTextChange(String query) {
//            // this is your adapter that will be filtered
//            PagerAdapter pagerAdapter = (PagerAdapter) viewPager
//                    .getAdapter();
//            for (int i = 0; i < pagerAdapter.getCount(); i++) {
//
//                Fragment viewPagerFragment = (Fragment) viewPager
//                        .getAdapter().instantiateItem(viewPager, i);
//                if (viewPagerFragment != null
//                        && viewPagerFragment.isAdded()) {
//
//                    if (viewPagerFragment instanceof HinduFragment) {
//                        hinduFragment = (HinduFragment) viewPagerFragment;
//                        if (hinduFragment != null) {
//                            hinduFragment.beginSearch(query);
//                        }
//                    } else if (viewPagerFragment instanceof MuslimFragment) {
//                        muslimFragment = (MuslimFragment) viewPagerFragment;
//                        if (muslimFragment != null) {
//                            muslimFragment.(query);
//                        }
//                    }
//                }
//            }
//
//            return false;
//
//        }
//
//        public boolean onQueryTextSubmit(String query) {
//            // Here u can get the value "query" which is entered in the
//
//            return false;
//
//        }
//    };
//    searchView.setOnQueryTextListener(queryTextListener);
//
//    return super.onCreateOptionsMenu(menu);
//}
//}

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //setTitle(R.string.title_home);

                    Intent intent = new Intent(tab.this, Imgbtn.class);


//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
                    startActivity(intent);
                    //finish();
                    return true;
                // break;
                case R.id.favourite:
                    // setTitle(R.string.title_fav);
                    Intent i = new Intent(tab.this, MainActivity.class);
                    startActivity(i);
                    return true;
                // break;
                case R.id.share:
                    //  setTitle(R.string.title_share);
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Check it out. This is a wonderful Naamkaran app: ";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Sharing Options:"));


                    return true;
                //break;
            }
            // finish();
            //  return true;
            return false;*/
            // return loadFragment(fragment);
        }

