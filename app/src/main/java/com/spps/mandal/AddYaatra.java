package com.spps.mandal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spps.mandal.Connectivity.AddYaatraDetails;
import com.spps.mandal.Connectivity.Fetch_Yaatra;
import com.spps.mandal.InternetConnectivity.NetworkChangeReceiver;
import com.spps.mandal.SessionManager.SessionManager;

import java.util.Calendar;
import java.util.HashMap;

public class AddYaatra extends BaseActivity implements View.OnClickListener {

    private StringBuilder date;
    String selectedDateForYatra= "";
    String selectedDateForDarshan= "";
    String selectedDateForPooja= "";
    boolean doubleBackToExitPressedOnce = false;

    private DatePicker datePicker;
    private Calendar calendar;
    private EditText clientName;
    private int year, month, day;
    Button submit;
    EditText txtDateOfYaatra;
    EditText txtDateOfDarshan;
    EditText txtDateOfPooja;
    int count;
    String userId;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_yaatra);

        SessionManager sessionManagerNgo = new SessionManager(AddYaatra.this);
        HashMap<String, String> typeOfUser = sessionManagerNgo.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);

        txtDateOfYaatra = (EditText) findViewById(R.id.txtDateOfYaatra);
        txtDateOfDarshan = (EditText) findViewById(R.id.txtDateOfDarshan);
        txtDateOfPooja = (EditText) findViewById(R.id.txtDateOfPooja);

        submit = (Button) findViewById(R.id.btnAddYaatra);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        txtDateOfPooja.setOnClickListener(this);
        txtDateOfYaatra.setOnClickListener(this);
        txtDateOfDarshan.setOnClickListener(this);
        submit.setOnClickListener(this);

        txtDateOfPooja.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                count = 1;
                setDate(view);
                return false;
            }
        });

        txtDateOfYaatra.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                count = 2;
                setDate(view);
                return false;
            }
        });
        txtDateOfDarshan.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                count = 3;
                setDate(view);
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddYaatra) {
            if (selectedDateForYatra == "" || selectedDateForPooja == "" || selectedDateForDarshan == "")  {
                Toast.makeText(this, "Please Select Date For All", Toast.LENGTH_SHORT).show();
            }
            else{

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait.");
                progressDialog.show();

                try {

                    AddYaatraDetails addYaatraDetails = new AddYaatraDetails(AddYaatra.this);
                    addYaatraDetails.addYaatraDetails(selectedDateForYatra,selectedDateForPooja, selectedDateForDarshan,userId,progressDialog);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("deprecation")

    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "Select Date", Toast.LENGTH_SHORT)
//                .show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {

        if(count ==1){
            txtDateOfPooja.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
            date = new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year);

            selectedDateForPooja = date.toString();
        }
        else if(count ==2){
            txtDateOfYaatra.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
            date = new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year);

            selectedDateForYatra = date.toString();
        }
        else if(count ==3){
            txtDateOfDarshan.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
            date = new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year);

            selectedDateForDarshan = date.toString();
        }

    }


    @Override
    public void onBackPressed() {
        AddYaatra.this.finish();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = AddYaatra.this.getPackageManager();
        ComponentName component = new ComponentName(AddYaatra.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = AddYaatra.this.getPackageManager();
        ComponentName component = new ComponentName(AddYaatra.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
