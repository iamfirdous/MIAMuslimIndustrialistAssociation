package com.nexusinfo.mia_muslimindustrialistassociation.connection;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by firdous on 11/29/2017.
 */

public class DatabaseConnection extends BaseConnection {

    public DatabaseConnection(String databaseName) {
        DB = databaseName;
    }

    @Override
    public Connection getConnection() {
        Connection conn = null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName(DRIVER); //.newInstance();
            conn = DriverManager.getConnection(CONN_STRINGS[0] + IP_ADDRESS + CONN_STRINGS[1] + DB + CONN_STRINGS[2] + DB_USERNAME + CONN_STRINGS[3] + DB_PASSWORD + CONN_STRINGS[4]);
        }
        catch (SQLException e){
            Log.e("Exception", e.toString());
        }
        catch (ClassNotFoundException e){
            Log.e("Exception", e.toString());
        }
        catch (Exception e){
            Log.e("Exception", e.toString());
        }

        return conn;
    }

    @Override
    public String toString() {
        return super.toString() + " DB Name: " + DB;
    }
}
