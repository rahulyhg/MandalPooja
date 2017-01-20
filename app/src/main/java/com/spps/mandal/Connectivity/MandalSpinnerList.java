package com.spps.mandal.Connectivity;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.spps.mandal.Adapter.MandalSpinnerAdapter;
import com.spps.mandal.Register;
import com.spps.mandal.WebService;
import com.spps.mandal.YaatraList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MandalSpinnerList {

    private static Context context;
    private static MandalSpinnerAdapter mandalSpinnerAdapter;
    private static List<String> allMandalList = new ArrayList<String>();
    private static List<String> allMandalIdList = new ArrayList<String>();

    private static String mandalResponseResult;
    private static String webMethodName = "ListMandalName";

    public MandalSpinnerList(Register register) {
        context = register;
    }


    public void FetchAllMandalList(List<String> mandalNameList, List<String> mandalIdList, MandalSpinnerAdapter SpinnerAdapterOfmandal) {
        allMandalList = mandalNameList;
        allMandalIdList = mandalIdList;
        mandalSpinnerAdapter = SpinnerAdapterOfmandal;
        MandalAsyncCallWS task = new MandalAsyncCallWS();
        task.execute();
    }


    public static class MandalAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            mandalResponseResult = WebService.AllMandalName(webMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(mandalResponseResult.isEmpty()) {
                // progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to Fetch Mandal Name ");
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
                //progressDialogBox.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(mandalResponseResult);
                    allMandalIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allMandalList.add(obj.getString("mandal_Name"));
                            allMandalIdList.add(obj.getString("id"));
                            //mandalSpinnerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}
