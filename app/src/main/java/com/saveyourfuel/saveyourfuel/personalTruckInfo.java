package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saveyourfuel.saveyourfuel.models.truckInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class personalTruckInfo extends AppCompatActivity {

    ArrayList<truckInfo> trucks = new ArrayList<>();
    Spinner truckSelect;
    FloatingActionButton addButton, updateButton;
    TextView permitType, pValidFrom, pValidTill, iAmount, iCompany, iValidFrom, iValidTill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_truck_info);
        setView();
        loadData();
    }

    void setView(){
        addButton = findViewById(R.id.addbutton);
        truckSelect = findViewById(R.id.truck_list);
        updateButton= findViewById(R.id.updatebutton);
        permitType = findViewById(R.id.permit_type);
        pValidFrom = findViewById(R.id.permit_valid_from);
        pValidTill = findViewById(R.id.permit_valid_till);
        iAmount = findViewById(R.id.insurance_amount);
        iCompany = findViewById(R.id.insurance_company);
        iValidFrom = findViewById(R.id.insurance_validfrom);
        iValidTill = findViewById(R.id.insurance_validtill);


    }


    void loadData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        String url = "http://139.59.29.124:3000/truck-personal-list?id="+id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for( int i=0;i<jsonArray.length();i++){
                        JSONObject tmp = jsonArray.getJSONObject(i);

                        trucks.add(new truckInfo(tmp.getString("permit_type"),
                                tmp.getString("permit_till"),
                                tmp.getString("permit_from"),
                                tmp.getString("insurance_till"),
                                tmp.getString("insurance_from"),
                                tmp.getString("insurance_amount"),
                                tmp.getString("insurance_company")));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                Snackbar.make(findViewById(android.R.id.content), R.string.check_your_connection, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

            }
        });
        queue.add(stringRequest);
    }
}
