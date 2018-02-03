package com.nexusinfo.mia_muslimindustrialistassociation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nexusinfo.mia_muslimindustrialistassociation.LocalDatabaseHelper;
import com.nexusinfo.mia_muslimindustrialistassociation.MainActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.ProductFragment;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.SearchFragment;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.ServiceFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mManager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    private View header;

    public static final String PROFILE_FRAGMENT = "PROFILE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeUI();
    }

    public void initializeUI () {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mManager = getSupportFragmentManager();
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras.getBoolean("fromProfile")){
            if (extras.getBoolean("products"))
                mManager.beginTransaction().replace(R.id.content_main, new ProductFragment()).commit();
            if (extras.getBoolean("services"))
                mManager.beginTransaction().replace(R.id.content_main, new ServiceFragment()).commit();
        }
        else {
            mManager.beginTransaction().replace(R.id.content_main, new SearchFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            LocalDatabaseHelper.getInstance(this).deleteData();
                            Intent logout = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                            logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(logout);
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;

            case R.id.action_refresh:
                Intent refresh = new Intent(this, MainActivity.class);
                startActivity(refresh);
                finish();
                break;

            case R.id.action_change_password:
//                Intent changePassword = new Intent(this, ChangePasswordActivity.class);
//                startActivity(changePassword);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_search:
                mManager.beginTransaction().replace(R.id.content_main, new SearchFragment(), "").commit();
                break;
            case R.id.nav_products:
                mManager.beginTransaction().replace(R.id.content_main, new ProductFragment()).commit();
                break;
            case R.id.nav_services:
                mManager.beginTransaction().replace(R.id.content_main, new ServiceFragment()).commit();
                break;
            case R.id.nav_profile:
                Intent viewProfileIntent = new Intent(this, MemberProfileActivity.class);
                viewProfileIntent.putExtra("Of", "ThisMember");
                startActivity(viewProfileIntent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void productsMenu(View view){
        mManager.beginTransaction().replace(R.id.content_main, new ProductFragment()).commit();
    }

    public void servicesMenu(View view){
        mManager.beginTransaction().replace(R.id.content_main, new ServiceFragment()).commit();
    }

    public void profileMenu(View view){
        Intent viewProfileIntent = new Intent(this, MemberProfileActivity.class);
        viewProfileIntent.putExtra("Of", "ThisMember");
        startActivity(viewProfileIntent);
    }
}
