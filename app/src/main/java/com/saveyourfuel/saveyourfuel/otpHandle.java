package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class otpHandle extends AppCompatActivity implements View.OnClickListener {
    EditText otp;
    Button submit;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_handle);
        setView();
        id = getIntent().getExtras().getString("id","");
        generateOtp();
    }

    void generateOtp(){
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://139.59.29.124:3000/set-otp?id=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    Log.d("response", "response " + res);
                    if (res.getString("code").contentEquals("800")) {

                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "cant generate OTP", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

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
    void setView(){
        otp = findViewById(R.id.otp);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                onSubmit();
                break;
        }
    }

    void onSubmit(){
        submit.setEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://139.59.29.124:3000/verify-otp?id=" + id +"&otp="+otp.getText().toString() ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    Log.d("response", "response " + res);
                    if (res.getString("code").contentEquals("800")) {

                        Intent i = new Intent(otpHandle.this, loginActivity.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(),"Phone Number verified! Login Again",Toast.LENGTH_SHORT).show();
                        otpHandle.this.finish();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Check your otp", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                submit.setEnabled(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                Snackbar.make(findViewById(android.R.id.content), R.string.check_your_connection, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                submit.setEnabled(true);
            }
        });
        queue.add(stringRequest);
    }

}
