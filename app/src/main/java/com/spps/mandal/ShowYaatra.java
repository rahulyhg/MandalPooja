package com.spps.mandal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.spps.mandal.Adapter.YaatraDetailsAdapter;
import com.spps.mandal.Connectivity.Fetch_Yaatra;
import com.spps.mandal.InternetConnectivity.NetworkChangeReceiver;
import com.spps.mandal.Model.YaatraItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ShowYaatra extends BaseActivity {

    public List<YaatraItems> yaatraItems = new ArrayList<YaatraItems>();
    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;
    boolean doubleBackToExitPressedOnce = false;

    String country;
    String state;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_yaatra);

        Intent intent = getIntent();
        country = intent.getStringExtra("countryName");
        state = intent.getStringExtra("stateName");
        city = intent.getStringExtra("cityName");

        recyclerView = (RecyclerView) findViewById(R.id.yaatraListRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.smoothScrollToPosition(0);
        reviewAdapter = new YaatraDetailsAdapter(yaatraItems);
        recyclerView.setAdapter(reviewAdapter);

        try {
            Fetch_Yaatra fetch_Yaatra = new Fetch_Yaatra(ShowYaatra.this);
            fetch_Yaatra.fetchYaatraDetails(yaatraItems,recyclerView, reviewAdapter,country,state,city);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        ShowYaatra.this.finish();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
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
        PackageManager pm = ShowYaatra.this.getPackageManager();
        ComponentName component = new ComponentName(ShowYaatra.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = ShowYaatra.this.getPackageManager();
        ComponentName component = new ComponentName(ShowYaatra.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



}

