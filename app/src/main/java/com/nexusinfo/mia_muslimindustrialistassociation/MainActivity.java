package com.nexusinfo.mia_muslimindustrialistassociation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nexusinfo.mia_muslimindustrialistassociation.connection.DatabaseConnection;
import com.nexusinfo.mia_muslimindustrialistassociation.receivers.InternetConnectivityReceiver;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.HomeActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.LoginActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.utils.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements InternetConnectivityReceiver.InternetConnectivityReceiverListener {

    private CardView cardView;
    private ImageView ivRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = findViewById(R.id.cardView_no_internet_connection);
        ivRefresh = findViewById(R.id.imageView_refresh_main);

        if(LocalDatabaseHelper.getInstance(this).isDataExist()){

            checkForInternet(InternetConnectivityReceiver.isConnected());

            ivRefresh.setOnClickListener(view -> {
                checkForInternet(InternetConnectivityReceiver.isConnected());
            });
        }
        else {
            Intent schoolCodeIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(schoolCodeIntent);
            finish();
        }
    }

    public void checkForInternet(boolean isConnected) {
        if(isConnected){
            cardView.setVisibility(View.GONE);

            Intent studentDetailsIntent = new   Intent(MainActivity.this, HomeActivity.class);
            startActivity(studentDetailsIntent);
            finish();
        }
        else {
            cardView.setVisibility(View.VISIBLE);
            ivRefresh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkForInternet(isConnected);
    }
}