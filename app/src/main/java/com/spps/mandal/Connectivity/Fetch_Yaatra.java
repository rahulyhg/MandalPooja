package com.spps.mandal.Connectivity;

import android.support.v7.widget.RecyclerView;

import com.spps.mandal.Model.YaatraItems;
import com.spps.mandal.ShowYaatra;
import com.spps.mandal.WebService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.List;

public class Fetch_Yaatra {

    private static Context context;
    private static final String url="";
    private static String displayText;
    private static String country;
    private static String state;
    private static String district;
    private static String city;
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<YaatraItems> notificationItemsArrayForAsyncTask;
    private static  String webMethName ="ViewYatraDetails";

    public Fetch_Yaatra(ShowYaatra showYaatra) {
        context= showYaatra;
    }

    public void fetchYaatraDetails(List<YaatraItems> yaatraItems, RecyclerView recyclerView, RecyclerView.Adapter reviewAdapter, String countryName, String stateName, String cityName) {
        adapterForAsyncTask= reviewAdapter;
        recyclerViewForAsyncTask= recyclerView;
        notificationItemsArrayForAsyncTask = yaatraItems;
        country = countryName;
        state = stateName;
        city = cityName;


        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = WebService.FetchYaatraDetails(country,state,city,webMethName);
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            if(displayText.equals("Not found Yatra Details..!!") || displayText.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Yaatra Details Not Found.");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(displayText);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            YaatraItems yaatraItems = new YaatraItems();
                            yaatraItems.setmandalName(obj.getString("mandal_Name"));
                            yaatraItems.setguruswamiName(obj.getString("full_Name"));
                            yaatraItems.setdateOfMandalPooja(obj.getString("date_Of_mandal_pooja"));
                            yaatraItems.setdateOfMandalYaatra(obj.getString("date_Of_yatra"));
                            yaatraItems.setdateOfMandalDarshan(obj.getString("date_Of_darshan"));
                            notificationItemsArrayForAsyncTask.add(yaatraItems);
                            adapterForAsyncTask.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
