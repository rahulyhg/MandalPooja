package com.spps.mandal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.spps.mandal.InternetConnectivity.CheckInternetConnection;
import com.spps.mandal.InternetConnectivity.NetworkChangeReceiver;
import com.spps.mandal.SessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private long TIME = 5000;
    private EditText editTextUserId;
    private EditText editTextPassword;
    //private TextView txtForgotPassword;
    private TextView tv;
    private Button btnSignIn;
    private Button btnRegister;
    private ProgressDialog progressDialog;

    private String userName;
    private String userPassword;
    private String method = "AddloginDetail";
    public String loginResponseResult;

    public  String saveUserType;
    public  String saveUserId;
    public  String saveName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUserId = (EditText) findViewById(R.id.txtLoginUserId);
        editTextPassword = (EditText) findViewById(R.id.txtLoginpassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnRegister = (Button) findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {

        if (CheckInternetConnection.getInstance(this).isOnline()) {
            if (v.getId() == R.id.btnSignIn) {

                userName = editTextUserId.getText().toString();
                userPassword = editTextPassword.getText().toString();

                if (editTextUserId.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter Your User Id.", Toast.LENGTH_LONG).show();
                } else if (editTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter Password.", Toast.LENGTH_LONG).show();
                } else {

                    progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMessage("Logging In.");
                    progressDialog.show();
                    LoginAsyncCallWS task = new LoginAsyncCallWS();
                    task.execute();
                }
            }
            else if (v.getId() == R.id.btnSignUp){
                Intent gotoSignup = new Intent(Login.this,Register.class);
                startActivity(gotoSignup);
            }
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet connection!");
            builder.setMessage("Please Check Your Internet Connection.");
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public class LoginAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            loginResponseResult = WebService.CreateLogin(userName,userPassword,method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(loginResponseResult.equals("Invalid UserName & Password")) {
                progressDialog.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Result");
                builder.setMessage(loginResponseResult);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else if(loginResponseResult.equals("No Network Found")) {
                progressDialog.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Result");
                builder.setMessage("Unable To Login. Please Try Again Later.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                progressDialog.hide();
                SessionManager sessionManager = new SessionManager(Login.this);
                try {
                    JSONArray jArr = new JSONArray(loginResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        saveUserType = obj.getString("user_Type");
                        saveName = obj.getString("full_Name");
                        saveUserId = obj.getString("id");

                        sessionManager.createUserLoginSession(saveUserId,saveUserType,saveName);
                        editTextUserId.setText("");
                        editTextPassword.setText("");
                        Intent gotoHome = new Intent(Login.this,MainActivity.class);
                        startActivity(gotoHome);
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        PackageManager pm = Login.this.getPackageManager();
        ComponentName component = new ComponentName(Login.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

    }

    @Override
    protected void onResume() {
        super.onResume();

        PackageManager pm = Login.this.getPackageManager();
        ComponentName component = new ComponentName(Login.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
