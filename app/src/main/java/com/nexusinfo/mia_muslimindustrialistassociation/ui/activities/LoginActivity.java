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

import com.nexusinfo.mia_muslimindustrialistassociation.LocalDatabaseHelper;
import com.nexusinfo.mia_muslimindustrialistassociation.MyApplication;
import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.connection.DatabaseConnection;
import com.nexusinfo.mia_muslimindustrialistassociation.model.UserModel;
import com.nexusinfo.mia_muslimindustrialistassociation.receivers.InternetConnectivityReceiver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.nexusinfo.mia_muslimindustrialistassociation.utils.Util.showCustomToast;

public class LoginActivity extends AppCompatActivity implements InternetConnectivityReceiver.InternetConnectivityReceiverListener {

    private TextView tvError, tvForgotPassword;
    private EditText etLoginEmail, etPassword;
    private Button buttonLogin;
    private ProgressBar progressBar;

    private UserModel user;
    private DatabaseConnection databaseConnection;

    private String loginEmail, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvError = findViewById(R.id.textView_error_loginActivity);
        tvForgotPassword = findViewById(R.id.textView_forgot_password_login);
        etLoginEmail = findViewById(R.id.editText_login_email);
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
            loginEmail = etLoginEmail.getText().toString().trim();
            password = etPassword.getText().toString().trim();

            boolean notEmpty = true;

            if(loginEmail.equals("")){
                notEmpty = false;
                etLoginEmail.setError("Enter your login email");
                cancel(true);
            }
            if(password.equals("")){
                notEmpty = false;
                etPassword.setError("Enter your password");
                cancel(true);
            }
            if(notEmpty){
                loadStart();
            }
        }

        @Override
        protected UserModel doInBackground(String... strings) {
            try{
                Connection conn = databaseConnection.getConnection();
                Statement stmt = conn.createStatement();

                String query = "SELECT Authentication, cmpid, brcode " +
                        " FROM Login " +
                        " WHERE LoginName = '" + loginEmail + "' AND " +
                        " Password = '" + password + "'";
                Log.e("LoginQuery", query);

                ResultSet rs = stmt.executeQuery(query);

                boolean wrongCredentials = true;

                if (rs.next()){
                    user.setMemberEmail(loginEmail);
                    user.setAuth(rs.getString("Authentication"));
                    user.setCmpId(rs.getString("cmpid"));
                    user.setBrCode(rs.getString("brcode"));

                    Statement stmt1 = conn.createStatement();

                    String query1 = "SELECT MemberID, Name, Mobile " +
                            " FROM MMember " +
                            " WHERE Email = '" + user.getMemberEmail() + "'";
                    ResultSet rs1 = stmt1.executeQuery(query1);

                    if(rs1.next()) {
                        user.setMemberId(rs1.getInt("MemberID"));
                        user.setMemberName(rs1.getString("Name"));
                        user.setMemberMobile(rs1.getString("Mobile"));
                    }

                    wrongCredentials = false;
                }

                if(wrongCredentials){
                    publishProgress("WrongCredentials");
                    cancel(true);
                }

            }
            catch (Exception e){
                publishProgress("Exception");
                cancel(true);
                Log.e("Exception", e.toString());
            }

            return user;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[0].equals("Exception")){
                showCustomToast(LoginActivity.this, "Some error occurred.",1);
                loadFinish();
            }
            if(values[0].equals("WrongCredentials")){
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(R.string.errorMessageForWrongCredentials);
                loadFinish();
            }
        }

        @Override
        protected void onPostExecute(UserModel userModel) {
            if (LocalDatabaseHelper.getInstance(LoginActivity.this).addData(user)) {
                showCustomToast(LoginActivity.this, "Login successful",1);
                Intent studentDetailsIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(studentDetailsIntent);
                finish();
            }
            else {
                Log.e("LocalDBProblem", "Data not added");
            }
        }

        private void loadStart(){
            progressBar.setVisibility(View.VISIBLE);
            etLoginEmail.setEnabled(false);
            etPassword.setEnabled(false);
            buttonLogin.setEnabled(false);
            tvForgotPassword.setEnabled(false);
        }

        private void loadFinish(){
            progressBar.setVisibility(View.INVISIBLE);
            etLoginEmail.setEnabled(true);
            etPassword.setEnabled(true);
            buttonLogin.setEnabled(true);
            tvForgotPassword.setEnabled(true);
        }
    }
}
