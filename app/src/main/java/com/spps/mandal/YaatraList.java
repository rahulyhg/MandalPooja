package com.spps.mandal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.spps.mandal.Adapter.CountrySpinnerAdapter;
import com.spps.mandal.Connectivity.CitySpinnerList;
import com.spps.mandal.Connectivity.CountrySpinnerList;
import com.spps.mandal.Connectivity.DistrictSpinnerList;
import com.spps.mandal.Connectivity.GuruSwamiSpinnerList;
import com.spps.mandal.Connectivity.MandalSpinnerList;
import com.spps.mandal.Connectivity.StateSpinnerList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YaatraList extends BaseActivity implements  View.OnClickListener{

    FloatingActionButton submit;
    Spinner country;
    Spinner state;
    Spinner city;

    int counter =1;

    String countryName;
    String countryId;
    private String[] countryArrayList;
    private CountrySpinnerAdapter countrySpinnerAdapter;
    private ProgressDialog countryDialogBox;
    private List<String> countryIdList = new ArrayList<String>();
    private List<String> countryNameList = new ArrayList<String>();

    String stateName;
    String stateId;
    private String[]stateArrayList;
    //private DivisionSpinnerAdapter stateSpinnerAdapter;
    private ProgressDialog stateDialogBox;
    private List<String> stateIdList = new ArrayList<String>();
    private List<String> stateNameList = new ArrayList<String>();

    String cityName;
    String cityId;
    private String[]cityArrayList;
    //private DivisionSpinnerAdapter citySpinnerAdapter;
    private ProgressDialog cityDialogBox;
    private List<String> cityIdList = new ArrayList<String>();
    private List<String> cityNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yaatra_list);

        country=(Spinner) findViewById(R.id.spinnerForCountry);
        state=(Spinner) findViewById(R.id.spinnerForState);
        city=(Spinner) findViewById(R.id.spinnerForCity);
        submit=(FloatingActionButton) findViewById(R.id.submitYaatraFab);

        submit.setOnClickListener(this);

        getCountrydDetails();
        getStateDetails();
        getcityDetails();
    }

    private void getCountrydDetails() {
        countryNameList.clear();
        countryIdList.clear();
        countryArrayList = new String[]{
                "Select Country"
        };
        countryNameList = new ArrayList<>(Arrays.asList(countryArrayList));
        countrySpinnerAdapter = new CountrySpinnerAdapter(this, R.layout.spinneritem,countryNameList);
        getListOfCountry();
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countrySpinnerAdapter);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    countryName = parent.getItemAtPosition(position).toString();
                    countryId = countryIdList.get(position).toString();
                    stateNameList.clear();
                    stateIdList.clear();
                    getListOfState();
                    state.setSelection(stateNameList.indexOf(0));
                    countrySpinnerAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getListOfCountry() {
        countryDialogBox = ProgressDialog.show(this, "", "Fetching Country Please Wait...", true);
        CountrySpinnerList countrySpinnerList = new CountrySpinnerList(YaatraList.this);
        countrySpinnerList.FetchAllcountry(countryNameList, countryIdList, countrySpinnerAdapter,countryDialogBox,counter);
    }

    private void getStateDetails() {
        stateArrayList = new String[]{
                "Select State"
        };
        stateNameList = new ArrayList<>(Arrays.asList(stateArrayList));
        countrySpinnerAdapter = new CountrySpinnerAdapter(this, R.layout.spinneritem,stateNameList);
        getListOfState();
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(countrySpinnerAdapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    stateName = parent.getItemAtPosition(position).toString();
                    stateId = stateIdList.get(position);
                    cityNameList.clear();
                    cityIdList.clear();
                    getListOfcity();
                    city.setSelection(cityNameList.indexOf(0));
                    countrySpinnerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getListOfState() {
        stateDialogBox = ProgressDialog.show(this, "", "Fetching State Please Wait...", true);
        StateSpinnerList stateSpinnerList = new StateSpinnerList(this);
        stateSpinnerList.FetchAllState(stateNameList, stateIdList, countrySpinnerAdapter, countryId,stateDialogBox,counter);
    }


    private void getcityDetails() {
        cityArrayList = new String[]{
                "Select City"
        };
        cityNameList = new ArrayList<>(Arrays.asList(cityArrayList));
        countrySpinnerAdapter = new CountrySpinnerAdapter(this, R.layout.spinneritem,cityNameList);
       // getListOfcity();
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(countrySpinnerAdapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    cityName = parent.getItemAtPosition(position).toString();
                    cityId = cityIdList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getListOfcity() {
        cityDialogBox = ProgressDialog.show(this, "", "Fetching City Please Wait...", true);
        CitySpinnerList citySpinnerList = new CitySpinnerList(this);
        citySpinnerList.FetchAllCity(cityNameList, cityIdList, countrySpinnerAdapter,countryId, stateId,cityDialogBox,counter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.submitYaatraFab){

            if (countryName == null || countryName.isEmpty()) {
                Toast.makeText(YaatraList.this, "Please select Country.", Toast.LENGTH_LONG).show();
            }
            else if (stateName == null || stateName.isEmpty()) {
                Toast.makeText(YaatraList.this, "Please select State.", Toast.LENGTH_LONG).show();
            }

            else if (cityName == null || cityName.isEmpty()) {
                Toast.makeText(YaatraList.this, "Please select City.", Toast.LENGTH_LONG).show();
            }
            else{
                Intent getList = new Intent(YaatraList.this,ShowYaatra.class);
                getList.putExtra("countryName",countryId);
                getList.putExtra("stateName",stateId);
                getList.putExtra("cityName",cityId);
                startActivity(getList);
            }

        }

    }
}
