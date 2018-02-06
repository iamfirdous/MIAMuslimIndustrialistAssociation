package com.nexusinfo.mia_muslimindustrialistassociation.ui.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexusinfo.mia_muslimindustrialistassociation.LocalDatabaseHelper;
import com.nexusinfo.mia_muslimindustrialistassociation.MainActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.models.MemberModel;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.ProductFragment;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.SearchFragment;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.ServiceFragment;
import com.nexusinfo.mia_muslimindustrialistassociation.viewmodels.MemberViewModel;

import static com.nexusinfo.mia_muslimindustrialistassociation.utils.Util.showCustomToast;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mManager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    private View header;
    private ImageView ivMemberPhoto;
    private TextView tvMemberName, tvMemberDesignation;

    private static MemberViewModel viewModel;
    public static MemberModel model;

    public static RelativeLayout relativeLayoutLoad, relativeLayoutSomeErr;
    public static View appBarLayout;
    public static FrameLayout frameLayout;
    public static ImageView ivRetry;

    public static final String PROFILE_FRAGMENT = "PROFILE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = ViewModelProviders.of(this).get(MemberViewModel.class);

        relativeLayoutLoad = findViewById(R.id.relativeLayout_load);
        relativeLayoutSomeErr = findViewById(R.id.relativeLayout_someError);
        appBarLayout = findViewById(R.id.appBarLayout);
        frameLayout = findViewById(R.id.content_main);
        ivRetry = findViewById(R.id.imageView_refresh_stu);

        FetchData task = new FetchData(this);
        task.execute();

        if(task.isCancelled()){
            showCustomToast(this, "Some error occurred, try again.",1);
            someError(this);
            return;
        }
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

        header = mNavigationView.getHeaderView(0);
        ivMemberPhoto = header.findViewById(R.id.imageView_memberPhotoHeader);
        tvMemberName = header.findViewById(R.id.textView_memberNameHeader);
        tvMemberDesignation = header.findViewById(R.id.textView_memberDesignationHeader);

        tvMemberName.setText(model.getName());
        tvMemberDesignation.setText(model.getDesignation());

        byte[] photoData = model.getPhoto();

        if(photoData != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
            ivMemberPhoto.setImageBitmap(bmp);
        }

        ivMemberPhoto.setOnClickListener(view -> {
            startProfileActivity();
        });

        tvMemberName.setOnClickListener(view -> {
            startProfileActivity();
        });

        tvMemberDesignation.setOnClickListener(view -> {
            startProfileActivity();
        });

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
                startProfileActivity();
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
        startProfileActivity();
    }

    static class FetchData extends AsyncTask<String, String, String> {

        HomeActivity activity;

        FetchData(Activity activity){
            this.activity = (HomeActivity) activity;
        }

        @Override
        protected void onPreExecute() {
            appBarLayout.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
            relativeLayoutLoad.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                if(viewModel.getMember() == null) {
                    viewModel.setMember(activity.getApplicationContext(), false, 0);
                }
            }
            catch (Exception e){
                Log.e("Exception", e.toString());
                publishProgress("Exception");
                cancel(true);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[0].equals("Exception")){
                relativeLayoutLoad.setVisibility(View.GONE);
                showCustomToast(activity.getApplicationContext(), "Some error occurred, try again.",1);
                //TODO: Try without finish()
//                finish();
                someError(activity);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            model = viewModel.getMember();
            relativeLayoutLoad.setVisibility(View.GONE);
            appBarLayout.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.VISIBLE);
            activity.initializeUI();
        }
    }

    public static void someError(HomeActivity activity) {
        relativeLayoutSomeErr.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);

        ivRetry.setOnClickListener(view -> {
            Intent refresh = new Intent(activity.getApplicationContext(), MainActivity.class);
            activity.startActivity(refresh);
            activity.finish();
        });
    }

    private void startProfileActivity() {
        Intent viewProfileIntent = new Intent(this, MemberProfileActivity.class);
        viewProfileIntent.putExtra("MemberModel", model);
        viewProfileIntent.putExtra("Of", "ThisMember");
        startActivity(viewProfileIntent);
    }
}
