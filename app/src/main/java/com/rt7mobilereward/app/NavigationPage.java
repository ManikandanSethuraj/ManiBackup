package com.rt7mobilereward.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NavigationPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected String navName=" ", navEmail=" ";
    TextView navNameTitle,navEmailTitle;

    public String gotToken = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            navName = intent.getExtras().getString("UserName");
            navEmail = intent.getExtras().getString("Email");
            gotToken = intent.getExtras().getString("Token");
            Log.d("Name", navName);
            Log.d("Email", navEmail);
            Log.d("Token", gotToken);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View vView = navigationView.getHeaderView(0);


        navNameTitle = (TextView)vView.findViewById(R.id.nav_name_show);
        navEmailTitle = (TextView)vView.findViewById(R.id.nav_email_show);
        navNameTitle.setText(navName);
        navEmailTitle.setText(navEmail);







    }

    @Override
    protected void onResume() {
        super.onResume();

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
        getMenuInflater().inflate(R.menu.navigation_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String titleNav = " ";
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (id == R.id.nav_stores) {
            // Handle the camera action


            MapsActivity mapsActivity = new MapsActivity();
           FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_navigation_page, mapsActivity);
          //  fragmentTransaction.addToBackStack(null);
            titleNav="STORES";
            getSupportActionBar().setTitle(titleNav);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_rewards) {

            FragTabRewardsPage fragTabRewardsPage = new FragTabRewardsPage();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_navigation_page, fragTabRewardsPage);
          //  fragmentTransaction.addToBackStack(null);
            titleNav="REWARDS";
            getSupportActionBar().setTitle(titleNav);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_gift) {

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
