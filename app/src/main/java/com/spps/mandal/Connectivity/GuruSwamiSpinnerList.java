package com.spps.mandal.Connectivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.spps.mandal.Adapter.GuruSwamiSpinnerAdapter;
import com.spps.mandal.Register;
import com.spps.mandal.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GuruSwamiSpinnerList {


    private static Context context;

    private static GuruSwamiSpinnerAdapter guruSwamiSpinnerAdapter;
    private static List<String> allGuruSwamiList = new ArrayList<String>();
    private static List<String> allGuruSwamiIdList = new ArrayList<String>();

    private static String guruSwamiResponseResult;
    private static String webMethodName = "listGuruswami";
    private static String nameOfMandal;

    public GuruSwamiSpinnerList(Register register) {
        context = register;
    }

    public void FetchAllGurSwamiList(List<String> guruSwamiNameList, List<String> guruSwamiIdList, GuruSwamiSpinnerAdapter SpinnerAdapterOfguruSwami, String mandalName) {
        allGuruSwamiList = guruSwamiNameList;
        allGuruSwamiIdList = guruSwamiIdList;
        guruSwamiSpinnerAdapter = SpinnerAdapterOfguruSwami;
        nameOfMandal =mandalName;
        GuruSwamiAsyncCallWS task = new GuruSwamiAsyncCallWS();
        task.execute();
    }

    public static class GuruSwamiAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
           guruSwamiResponseResult = WebService.AllGuruSwamiName(nameOfMandal,webMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(guruSwamiResponseResult.isEmpty()) {
                //               progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to Fetch Guruswami list. ");
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
                    JSONArray jsonArray = new JSONArray(guruSwamiResponseResult);
                    allGuruSwamiIdList.add("0");
                    allGuruSwamiList.add("Select GuruSwamy");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allGuruSwamiList.add(obj.getString("full_Name"));
                            allGuruSwamiIdList.add(obj.getString("id"));
                            guruSwamiSpinnerAdapter.notifyDataSetChanged();
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
