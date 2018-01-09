package com.nexusinfo.mia_muslimindustrialistassociation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nexusinfo.mia_muslimindustrialistassociation.connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar bar = findViewById(R.id.progressBar_bar);

        DatabaseConnection db = new DatabaseConnection("MIA_DB");

        Connection conn = db.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MMember");
            String value = "";
            while (rs.next()) {
                value = rs.getString("Name");
            }
            Toast.makeText(this, value, Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
