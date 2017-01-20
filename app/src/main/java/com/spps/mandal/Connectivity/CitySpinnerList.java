package com.spps.mandal.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.spps.mandal.Adapter.CountrySpinnerAdapter;
import com.spps.mandal.Register;
import com.spps.mandal.WebService;
import com.spps.mandal.YaatraList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CitySpinnerList {
    private static Context context;
    private static CountrySpinnerAdapter SpinnerAdapterAsynch;
    private static List<String> allNameList = new ArrayList<String>();
    private static List<String> allIdList = new ArrayList<String>();

    private static String ResponseResult;
    private static String state;
    private static String country;
    private static String webMethodName;
    private static ProgressDialog progressDialogBox;
    int count;

    public CitySpinnerList(YaatraList yaatraList) {
        context = yaatraList;
    }

    public CitySpinnerList(Register register) {
        context = register;
    }

    public void FetchAllCity(List<String> cityNameList, List<String> cityIdList, CountrySpinnerAdapter countrySpinnerAdapter, String nameOfcountry, String nameOfState, ProgressDialog cityDialogBox, int counter) {
        allNameList = cityNameList;
        allIdList =cityIdList;
        SpinnerAdapterAsynch = countrySpinnerAdapter;
        country =nameOfcountry;
        state =nameOfState;
        progressDialogBox = cityDialogBox;

        count = counter;

        if(count == 1){
            webMethodName = "listcity";
        }else{
            webMethodName = "listCitiesMaster";
        }

        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }


    public static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            ResponseResult = WebService.AllCity(country,state,webMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            progressDialogBox.dismiss();

            if (ResponseResult.isEmpty()) {
                // progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to Fetch City.");
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
            } else {
                //progressDialogBox.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(ResponseResult);
                   allNameList.add("Select City");
                    allIdList.add("0");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allNameList.add(obj.getString("cityName"));
                            allIdList.add(obj.getString("cityID"));
                            SpinnerAdapterAsynch.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}

