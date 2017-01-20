package com.spps.mandal.Connectivity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.spps.mandal.AddYaatra;
import com.spps.mandal.EditYaatra;
import com.spps.mandal.Login;
import com.spps.mandal.MainActivity;
import com.spps.mandal.Model.YaatraItems;
import com.spps.mandal.Register;
import com.spps.mandal.ShowYaatra;
import com.spps.mandal.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class EditYaatraDetails {

    private static Context context;
    private static final String url="";
    private static String displayText;
    private static String dateOfPooja;
    private static String dateOfYaatra;
    private static String dateOfDarshan;
    private static String userId;
    private static ProgressDialog progressDialog;
    private static  String webMethName ="UpdateYatraDetail";

    public EditYaatraDetails(EditYaatra editYaatra) {
        context = editYaatra;
    }

    public void editYaatraDetails(String selectedDateForYatra, String selectedDateForPooja, String selectedDateForDarshan, String idOfUser, ProgressDialog progressDialogBox) {

        dateOfYaatra = selectedDateForYatra;
        dateOfPooja = selectedDateForPooja;
        dateOfDarshan = selectedDateForDarshan;
        userId = idOfUser;
        EditYaatraDetails.progressDialog = progressDialogBox;

        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.EditYaatra(dateOfPooja,dateOfYaatra,dateOfDarshan,userId,webMethName);
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            progressDialog.dismiss();
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("Result");
            builder.setMessage(displayText);
            android.app.AlertDialog alert1 = builder.create();
            alert1.show();
            if (displayText.equals("Yatra Detail Update Succesfully.")) {
                Intent first = new Intent(context, MainActivity.class);
                context.startActivity(first);
            }
        }
    }
}
