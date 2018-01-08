package com.nexusinfo.mia_muslimindustrialistassociation;

import android.app.Application;

import com.nexusinfo.mia_muslimindustrialistassociation.receivers.InternetConnectivityReceiver;

/**
 * Created by firdous on 11/28/2017.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
    }

    public static synchronized MyApplication getInstance() {
        return mApplication;
    }

    public void setConnectivityListener(InternetConnectivityReceiver.InternetConnectivityReceiverListener listener) {
        InternetConnectivityReceiver.listener = listener;
    }
}
