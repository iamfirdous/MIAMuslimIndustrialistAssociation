package com.nexusinfo.mia_muslimindustrialistassociation.ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nexusinfo.mia_muslimindustrialistassociation.MyApplication;
import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.connection.DatabaseConnection;
import com.nexusinfo.mia_muslimindustrialistassociation.models.UserModel;
import com.nexusinfo.mia_muslimindustrialistassociation.receivers.InternetConnectivityReceiver;
import com.nexusinfo.mia_muslimindustrialistassociation.utils.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity implements InternetConnectivityReceiver.InternetConnectivityReceiverListener {

    private TextView tvError, tvForgotPassword;
    private EditText etLoginName, etPassword;
    private Button buttonLogin;
    private ProgressBar progressBar;

    private UserModel user;
    private DatabaseConnection databaseConnection;

    private String loginName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvError = findViewById(R.id.textView_error_loginActivity);
        tvForgotPassword = findViewById(R.id.textView_forgot_password_login);
        etLoginName = findViewById(R.id.editText_login_name);
        etPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);
        progressBar = findViewById(R.id.progressBar_login);

        progressBar.setVisibility(View.INVISIBLE);

        user = new UserModel();
        databaseConnection = new DatabaseConnection(DatabaseConnection.MIA_DB_NAME);

        showError(InternetConnectivityReceiver.isConnected());

        buttonLogin.setOnClickListener(view -> {

            if(!InternetConnectivityReceiver.isConnected()){
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(R.string.errorMessageForInternet);
            }
            else {
                tvError.setVisibility(View.GONE);
                Log.e("Available", "Internet Available....  :) :) :D");

                LoginTask task = new LoginTask();
                task.execute("");
            }

        });

        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                buttonLogin.performClick();
                return true;
            }
            return false;
        });

        tvForgotPassword.setOnClickListener(view -> {
//            Intent preForgotIntent = new Intent(this, ForgotPasswordActivity.class);
//            preForgotIntent.putExtra("User", user);
//            startActivity(preForgotIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    private void showError(boolean isConnected) {

        if (!isConnected) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(R.string.errorMessageForInternet);
        }
        else {
            tvError.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showError(isConnected);
    }

    class LoginTask extends AsyncTask<String, String, UserModel> {

        @Override
        protected void onPreExecute() {
//            loginName = etLoginName.getText().toString().trim();
//            password = etPassword.getText().toString().trim();
//
//            boolean notEmpty = true;
//
//            if(loginName.equals("")){
//                notEmpty = false;
//                etLoginName.setError("Enter your login name");
//                cancel(true);
//            }
//            if(password.equals("")){
//                notEmpty = false;
//                etPassword.setError("Enter your password");
//                cancel(true);
//            }
//            if(notEmpty){
//                loadStart();
//            }
        }

        String s = "";

        @Override
        protected UserModel doInBackground(String... strings) {
            try{
                Connection conn = databaseConnection.getConnection();
                Statement stmt = conn.createStatement();

                    ResultSet rs = stmt.executeQuery("SELECT * FROM MMember");

                    while (rs.next()){
                        s = rs.getString("Name");
                    }

//                String query = "SELECT " + DatabaseConnection.COL_ROLLNO + ", "
//                        + DatabaseConnection.COL_FATHERMOBILE + ", "
//                        + DatabaseConnection.COL_STUDENTID +
//                        " FROM " + DatabaseConnection.VIEW_STUDENT_DETAILS_FOR_REPORT +
//                        " WHERE " + DatabaseConnection.COL_ROLLNO + " = '" + loginName + "' AND "
//                        + DatabaseConnection.COL_PASSWORD + " = '" + password + "' AND "
//                        + DatabaseConnection.COL_STATUS + " = 'Regular' AND "
//                        + DatabaseConnection.COL_CMPID + " = '" + user.getCmpId() + "' AND "
//                        + DatabaseConnection.COL_BRCODE + " = '" + user.getBrCode() + "'";
//                Log.e("LoginQuery", query);
//
//                ResultSet rs = stmt.executeQuery(query);
//
//                boolean wrongCredentials = true;
//
//                if (rs.next()){
//                    user.setUserID(rs.getString(DatabaseConnection.COL_ROLLNO));
//                    user.setFatherMobile(rs.getString(DatabaseConnection.COL_FATHERMOBILE));
//                    user.setStudentID(rs.getInt(DatabaseConnection.COL_STUDENTID));
//                    wrongCredentials = false;
//                }
//
//                if(wrongCredentials){
//                    publishProgress("WrongCredentials");
//                    cancel(true);
//                }

            }
            catch (Exception e){
                publishProgress("Exception");
                cancel(true);
                Log.e("Exception", e.toString());
            }

            return user;
        }

        @Override
        protected void onPostExecute(UserModel userModel) {
            Util.showCustomToast(LoginActivity.this, s, 1);
        }

        private void loadStart(){
            progressBar.setVisibility(View.VISIBLE);
            etLoginName.setEnabled(false);
            etPassword.setEnabled(false);
            buttonLogin.setEnabled(false);
            tvForgotPassword.setEnabled(false);
        }

        private void loadFinish(){
            progressBar.setVisibility(View.INVISIBLE);
            etLoginName.setEnabled(true);
            etPassword.setEnabled(true);
            buttonLogin.setEnabled(true);
            tvForgotPassword.setEnabled(true);
        }
    }
}
