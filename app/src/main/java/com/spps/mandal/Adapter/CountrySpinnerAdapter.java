package com.spps.mandal.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spps.mandal.Register;
import com.spps.mandal.YaatraList;

import java.util.ArrayList;
import java.util.List;

public class CountrySpinnerAdapter extends ArrayAdapter {

    private ArrayList arrayList;
    private Context context;

    public CountrySpinnerAdapter(YaatraList yaatraList, int spinneritem, List<String> countryNameList) {
        super(yaatraList,spinneritem,countryNameList);
    }

    public CountrySpinnerAdapter(Register register, int spinneritem, List<String> countryNameList) {
        super(register,spinneritem,countryNameList);
    }


    @Override
    public boolean isEnabled(int position) {
        if(position == 0) {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        else {
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        return view;
    }
}