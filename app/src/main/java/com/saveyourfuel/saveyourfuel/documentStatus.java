package com.saveyourfuel.saveyourfuel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class documentStatus extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    CardView cardView ;
    RelativeLayout mainContainer;
    int status[]= new int[5];
    String name,ph;
    TextView adhar_u,insurance_u,license_u,rc_u,adhar_v,insurance_v,license_v,rc_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
            window.setStatusBarColor(Color.parseColor("#003240"));
        }
        setContentView(R.layout.activity_document_status);


        toolbar = findViewById(R.id.toolbarU);
        toolbar.setTitle(R.string.Upload_Your_Documents);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#004E64"));
        setSupportActionBar(toolbar);
        setView();
        getStatusFromServer();

        Intent i = getIntent();
        name = i.getExtras().getString("Name", "");
        ph = i.getExtras().getString("ph", "");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(documentStatus.this, home.class);
                i.putExtra("Name", name);
                i.putExtra("ph", ph);
                startActivity(i);
                documentStatus.this.finish();
            }
        });

    }

    void setView(){
        cardView = findViewById(R.id.upload_doc_cardview);
        cardView.setOnClickListener(this);

        adhar_u = findViewById(R.id.adhar_u);
        adhar_v = findViewById(R.id.adhar_v);

        insurance_u = findViewById(R.id.insurance_u);
        insurance_v = findViewById(R.id.insurance_v);

        license_u = findViewById(R.id.license_u);
        license_v = findViewById(R.id.license_v);

        rc_u = findViewById(R.id.rc_u);
        rc_v = findViewById(R.id.rc_v);

        mainContainer = findViewById(R.id.main_container_document);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(documentStatus.this, home.class);
        i.putExtra("Name", home.name);
        i.putExtra("ph", home.ph);
        startActivity(i);
        documentStatus.this.finish();
    }

    void setTexts(){
        switch(status[1])
        {
            case 1:
                adhar_u.setTextColor(Color.parseColor("#3bbc2d"));
                adhar_v.setTextColor(Color.parseColor("#3bbc2d"));
                adhar_u.setText(R.string.uploaded);
                adhar_v.setText(R.string.verification_in_progress);
                break;
            case 0:
                adhar_u.setTextColor(Color.parseColor("#D50000"));
                adhar_v.setTextColor(Color.parseColor("#D50000"));
                adhar_u.setText(R.string.not_uploaded);
                adhar_v.setText(R.string.waiting_for_upload);
                break;
        }
        switch(status[2])
        {
            case 1:
                insurance_u.setTextColor(Color.parseColor("#3bbc2d"));
                insurance_v.setTextColor(Color.parseColor("#3bbc2d"));
                insurance_u.setText(R.string.uploaded);
                insurance_v.setText(R.string.verification_in_progress);
                break;
            case 0:
                insurance_u.setTextColor(Color.parseColor("#D50000"));
                insurance_v.setTextColor(Color.parseColor("#D50000"));
                insurance_u.setText(R.string.not_uploaded);
                insurance_v.setText(R.string.waiting_for_upload);
                break;
        }
        switch(status[3])
        {
            case 1:
                license_u.setTextColor(Color.parseColor("#3bbc2d"));
                license_v.setTextColor(Color.parseColor("#3bbc2d"));
                license_u.setText(R.string.uploaded);
                license_v.setText(R.string.verification_in_progress);
                break;
            case 0:
                license_u.setTextColor(Color.parseColor("#D50000"));
                license_v.setTextColor(Color.parseColor("#D50000"));
                license_u.setText(R.string.not_uploaded);
                license_v.setText(R.string.waiting_for_upload);
                break;
        }
        switch(status[4])
        {
            case 1:
                rc_u.setTextColor(Color.parseColor("#3bbc2d"));
                rc_v.setTextColor(Color.parseColor("#3bbc2d"));
                rc_u.setText(R.string.uploaded);
                rc_v.setText(R.string.verification_in_progress);
                break;
            case 0:
                rc_u.setTextColor(Color.parseColor("#D50000"));
                rc_v.setTextColor(Color.parseColor("#D50000"));
                rc_u.setText(R.string.not_uploaded);
                rc_v.setText(R.string.waiting_for_upload);
                break;
        }

    }
    void getStatusFromServer(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retrieving data");
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                progressDialog.dismiss();
            }
        });

        String url = "http://139.59.29.124:3000/document-status";

        StringRequest statusReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("response",res.toString());
                            if(res.getString("code").contentEquals("800")){
                                status[0] = Integer.parseInt(res.getString("profile"));
                                status[1] = Integer.parseInt(res.getString("vehicle"));
                                status[2] = Integer.parseInt(res.getString("insurance"));
                                status[3] = Integer.parseInt(res.getString("license"));
                                status[4] = Integer.parseInt(res.getString("rc"));
                                setTexts();
                            }
                            else{
                                Snackbar.make( mainContainer,"Some error occurred" , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }

                        } catch (Exception e) {
                            Snackbar.make( mainContainer,"Error retrieving data" , Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make( mainContainer,"No internet connection" , Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }

        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();

                    SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    params.put("id", preferences.getString("id", ""));



                return params;
            }
        };

        queue.add(statusReq);
    }

    @Override
    public void onClick(View v) {
        Log.d("debugger","here inside onclick");
        switch (v.getId()){
            case R.id.upload_doc_cardview:
                Intent i = new Intent("com.saveyourfuel.saveyourfuel.uploadActivity");
                i.putExtra("Name",home.name);
                Log.d("debugger","cardview clicked");
                i.putExtra("ph",home.ph);
                startActivity(i);
                this.finish();
                break;
            default:
                Log.d("debugger","here inside onclick");
                break;
        }
    }
}
