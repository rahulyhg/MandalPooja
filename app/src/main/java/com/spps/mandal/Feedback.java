package com.spps.mandal;


import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spps.mandal.Connectivity.AddYaatraDetails;
import com.spps.mandal.Connectivity.SaveFeedback;
import com.spps.mandal.InternetConnectivity.NetworkChangeReceiver;
import com.spps.mandal.SessionManager.SessionManager;

import java.util.HashMap;
import java.util.TooManyListenersException;

public class Feedback extends BaseActivity implements View.OnClickListener {

    EditText feedbackEmail;
    EditText feedbackOfUser;
    Button feedbackSubmit;

    String emailOfUserFeedback = "";
    String userId = "";
    String userFeedback = "";
    private long TIME = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        SessionManager sessionManagerNgo = new SessionManager(Feedback.this);
        HashMap<String, String> typeOfUser = sessionManagerNgo.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);

        feedbackEmail = (EditText) findViewById(R.id.feedbackEmail);
        feedbackOfUser = (EditText) findViewById(R.id.feedbackOfUser);
        feedbackSubmit = (Button) findViewById(R.id.feedbackSubmit);

        feedbackSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        v.setEnabled(false);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                v.setEnabled(true);
            }
        }, TIME);

        if(v.getId() == R.id.feedbackSubmit) {

            if(feedbackEmail.equals("")) {
                Toast.makeText(Feedback.this, "Please Enter EmailId.", Toast.LENGTH_LONG).show();
            }
            else if(feedbackOfUser.equals("")) {
                Toast.makeText(Feedback.this, "Please Enter Feedback.", Toast.LENGTH_LONG).show();
            }
            else {
                emailOfUserFeedback = feedbackEmail.getText().toString();
                userFeedback = feedbackOfUser.getText().toString();
                new UploadFeedbackToServer().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            }
        }
    }

    public class UploadFeedbackToServer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                SaveFeedback saveFeedback = new SaveFeedback(Feedback.this);
                saveFeedback.saveFeedback(userId,emailOfUserFeedback, userFeedback);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = Feedback.this.getPackageManager();
        ComponentName component = new ComponentName(Feedback.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = Feedback.this.getPackageManager();
        ComponentName component = new ComponentName(Feedback.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }
}
