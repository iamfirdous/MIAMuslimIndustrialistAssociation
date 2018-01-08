package com.nexusinfo.mia_muslimindustrialistassociation.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nexusinfo.mia_muslimindustrialistassociation.MyApplication;

/**
 * Created by firdous on 11/28/2017.
 */

public class InternetConnectivityReceiver extends BroadcastReceiver {

    public static InternetConnectivityReceiverListener listener;

    public InternetConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();

        boolean isConnected = activeInfo != null && activeInfo.isConnectedOrConnecting();

        if(listener != null){
            listener.onNetworkConnectionChanged(isConnected);
        }
    }

    public static boolean isConnected() {
        NetworkInfo activeInfo = ((ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeInfo != null && activeInfo.isConnectedOrConnecting();
    }

    public interface InternetConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
