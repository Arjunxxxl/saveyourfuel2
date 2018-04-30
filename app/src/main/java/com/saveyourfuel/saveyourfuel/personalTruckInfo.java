package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class personalTruckInfo extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    ArrayList<truckInfo> trucks = new ArrayList<>();
    String currentSelect="";
    String name, ph;
    ArrayAdapter<String> spinnerAdapter;
    Spinner truckSelect;
    FloatingActionButton addButton,deleteButton;
    ImageView updateButton;
    Toolbar toolbar;
    RequestQueue queue;
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
        addButton.setOnClickListener(this);
        deleteButton = findViewById(R.id.deletebutton);
        deleteButton.setOnClickListener(this);

        truckSelect = findViewById(R.id.truck_list);
        updateButton= findViewById(R.id.uploadbutton);
        updateButton.setOnClickListener(this);
        permitType = findViewById(R.id.permit_type);
        pValidFrom = findViewById(R.id.permit_valid_from);
        pValidTill = findViewById(R.id.permit_valid_till);
        iAmount = findViewById(R.id.insurance_amount);
        iCompany = findViewById(R.id.insurance_company);
        iValidFrom = findViewById(R.id.insurance_validfrom);
        iValidTill = findViewById(R.id.insurance_validtill);

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.add("Select truck...");
        spinnerAdapter.notifyDataSetChanged();
        truckSelect.setAdapter(spinnerAdapter);
        truckSelect.setOnItemSelectedListener(this);

        toolbar = findViewById(R.id.toolbarU);
        toolbar.setTitle(R.string.uploadDocuments);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#004E64"));
        setSupportActionBar(toolbar);


        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        name = sharedPref.getString("Name","");
        ph = sharedPref.getString("ph","");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(personalTruckInfo.this, home.class);
                startActivity(i);
                personalTruckInfo.this.finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(personalTruckInfo.this, home.class);
        startActivity(i);
        personalTruckInfo.this.finish();
    }


    void loadData(){

        queue = Volley.newRequestQueue(this);
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        String url = "http://139.59.29.124:3000/truck-personal-list?id="+id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("resplength ",jsonArray.length()+"");

                    for( int i=0;i<jsonArray.length();i++){
                        JSONObject tmp = jsonArray.getJSONObject(i);

                        trucks.add(new truckInfo(tmp.getString("permit_type"),
                                tmp.getString("valid_till_permit"),
                                tmp.getString("valid_from_permit"),
                                tmp.getString("valid_till_insurance"),
                                tmp.getString("valid_from_insurance"),
                                tmp.getString("amount_insurance"),
                                tmp.getString("insurance_company"),
                                tmp.getString("truck_no")));

                        spinnerAdapter.add(tmp.getString("truck_no"));
                        spinnerAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uploadbutton:
                if(!currentSelect.isEmpty()){
                    Intent i = new Intent(personalTruckInfo.this, uploadActivity.class);
                    i.putExtra("truck_no",currentSelect);
                    startActivity(i);
                }
                else {
                    Toast.makeText(this,"Select a truck",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.addbutton:
                addTruck();
                break;
            case R.id.deletebutton:
                if(!currentSelect.isEmpty())
                deleteTruck();
                else{
                    Toast.makeText(this,"Select a truck first", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void deleteTruck(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(personalTruckInfo.this);
        alertDialog.setTitle("Delete a truck");
        alertDialog.setMessage("You sure want to delete truck no. "+currentSelect);

        alertDialog.setPositiveButton("DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                        String id = sharedPreferences.getString("id","");
                        String url = "http://139.59.29.124:3000/truck-personal-delete?id="+id+"&truck_no="+currentSelect;
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getString("code").contains("800")){
                                        Toast.makeText(getApplicationContext(),"Truck deleted successfully",Toast.LENGTH_SHORT).show();

                                        Intent i = getIntent();
                                        personalTruckInfo.this.finish();
                                        startActivity(i);

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Some error occurred",Toast.LENGTH_SHORT).show();
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
                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                currentSelect="";
                break;

            default:
                updateRecords(trucks.get(position-1));
                currentSelect=trucks.get(position-1).truckNo;
                break;


        }
    }

    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }
    void addTruck(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(personalTruckInfo.this);
        alertDialog.setTitle("Add a truck");
        alertDialog.setMessage("Enter Truck Number");

        final EditText input = new EditText(personalTruckInfo.this);

        LinearLayout layout  = new LinearLayout(this);


        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(input);

        layout.setPadding(50, 0, 50, 0);

        alertDialog.setView(layout);

        final String[] newTruckNo = new String[1];
       // alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("ADD",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        newTruckNo[0] = input.getText().toString();
                        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                        String id = sharedPreferences.getString("id","");
                        String url = "http://139.59.29.124:3000/truck-personal-add?id="+id+"&truck_no="+newTruckNo[0];
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getString("code").contains("800")){
                                        Toast.makeText(getApplicationContext(),"Truck added successfully",Toast.LENGTH_SHORT).show();

                                        Intent i = getIntent();
                                        personalTruckInfo.this.finish();
                                        startActivity(i);

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Some error occurred",Toast.LENGTH_SHORT).show();
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
                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
    void updateRecords(truckInfo truck){
        if(!truck.permitType.contains("null")){
            permitType.setText(truck.permitType);
        }else {
            permitType.setText("Not updated");
        }

        if(!truck.permitFrom.contains("null")){
            pValidFrom.setText(truck.permitFrom.substring(0,10));
        }else {
            pValidFrom.setText("Not updated");
        }

        if(!truck.permitTill.contains("null")){
            pValidTill.setText(truck.permitTill.substring(0,10));
        }else {
            pValidTill.setText("Not updated");
        }

        if(!truck.insuranceCompany.contains("null")){
            iCompany.setText(truck.insuranceCompany);
        }else {
            iCompany.setText("Not updated");
        }
        if(!truck.insuranceAmount.contains("null")){
            iAmount.setText(truck.insuranceAmount);
        }else {
            iAmount.setText("Not updated");
        }

        if(!truck.insuranceFrom.contains("null")){
            iValidFrom.setText(truck.insuranceFrom.substring(0,10));
        }else {
            iValidFrom.setText("Not updated");
        }

        if(!truck.insuranceTill.contains("null")){
            iValidTill.setText(truck.insuranceTill.substring(0,10));
        }else {
            iValidTill.setText("Not updated");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
