package com.blogspot.pavankreddy.indiasnewsapp.Activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blogspot.pavankreddy.indiasnewsapp.Fragments.FavFragment;
import com.blogspot.pavankreddy.indiasnewsapp.Fragments.NewsFragment;
import com.blogspot.pavankreddy.indiasnewsapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    public static final String CATEGORY_KEY = "CATEGORY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_categories_action_bar));
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getString(R.string.test_device_id))
                .build();
        adView.loadAd(adRequest);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.set_reminder)
        {
            startActivity(new Intent(this,SetAnAlarm.class));
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentManager fragment_manager = getSupportFragmentManager();

        if (id == R.id.business_news && isInternetAvailable())
        {
            fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CATEGORY_KEY,getString(R.string.business));
            fragment.setArguments(bundle);
            setTitle(getString(R.string.business_news));
        }
        else if (id == R.id.entertainment_news && isInternetAvailable())
        {
            fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CATEGORY_KEY,getString(R.string.entertainment));
            fragment.setArguments(bundle);
            setTitle(getString(R.string.entertainment_news));
        }
        else if (id == R.id.technology_news && isInternetAvailable())
        {
            fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CATEGORY_KEY,getString(R.string.technology));
            fragment.setArguments(bundle);
            setTitle(getString(R.string.tech_news));
        }
        else if (id == R.id.sports_news && isInternetAvailable())
        {
            fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CATEGORY_KEY,getString(R.string.sports));
            fragment.setArguments(bundle);
            setTitle(getString(R.string.sports_news));
        }
        else if (id == R.id.general_news && isInternetAvailable())
        {
            fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CATEGORY_KEY,getString(R.string.general));
            fragment.setArguments(bundle);
            setTitle(getString(R.string.general_news));
        }
        else if (id == R.id.health_news && isInternetAvailable())
        {
            fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CATEGORY_KEY,getString(R.string.health));
            fragment.setArguments(bundle);
            setTitle(getString(R.string.health_news));
        }
        else if (id == R.id.science_news && isInternetAvailable())
        {
            fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CATEGORY_KEY,getString(R.string.science));
            fragment.setArguments(bundle);
            setTitle(getString(R.string.science_news));
        }
        else if(id == R.id.fav_news)
        {
            fragment = new FavFragment();
            setTitle(getString(R.string.fac_news));
        }
        else
        {
            showInternetUnavaialbeAlert();
        }




        if(fragment!=null){
            FragmentTransaction ft = fragment_manager.beginTransaction();
            ft.replace(R.id.fragment_container,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isInternetAvailable()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void showInternetUnavaialbeAlert()
    {
        new AlertDialog.Builder(this).setTitle("INTERNET UNAVAILABLE")
                .setMessage("Sorry Cannot Load Data With out Internet Connection!!")
                .setPositiveButton("YES", null)
                .setCancelable(true)
                .setIcon(R.drawable.ic_cancel_black_24dp).show();
    }
}
