package com.spps.mandal.Connectivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.spps.mandal.Feedback;
import com.spps.mandal.MainActivity;
import com.spps.mandal.ShowYaatra;
import com.spps.mandal.WebService;

public class SaveFeedback {
    private static Context context;
    private static String email;
    private static String feedbackOfUser;
    private static String displayText;
    private static String userId;

    private static  String webMethName ="CreateFeedbackDetail";

    public SaveFeedback(Feedback feedback) {
        context = feedback;
    }

    public static void saveFeedback(String idOfUser,String emailOfUserFeedback, String userFeedback) {
        userId = idOfUser;
        email = emailOfUserFeedback;
        feedbackOfUser = userFeedback;
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.Feedback(userId,email,feedbackOfUser,webMethName);
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("Result");
            builder.setMessage(displayText);
            android.app.AlertDialog alert1 = builder.create();
            alert1.show();
            if (displayText.equals("Send Feedback Succesfully.")) {
                Intent first = new Intent(context, MainActivity.class);
                context.startActivity(first);
            }
        }
    }
}
