package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                final Intent mainIntent = new Intent(splashActivity.this, home.class);
                checkLogin();
//                splashActivity.this.startActivity(mainIntent);
//                splashActivity.this.finish();
            }
        }, 500);

    }


    private void checkLogin() {

        final String passwordText, emailText;

        SharedPreferences sharedPref = getSharedPreferences("data",Context.MODE_PRIVATE);
        String username = sharedPref.getString("username","");
        String pass = sharedPref.getString("password","");
        Log.d("usemane",username+" ");
        Log.d("pass",pass+" ");
        Log.d("QEQWRTYYENBF","RGAHTSRYJRTHERWTAEREHTRYT");
        passwordText = pass;
        emailText = username;
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://139.59.29.124:3000/authentication?id=" + emailText + "&pwd=" + passwordText;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);

                    Log.d("response", "response " + res);
                    if (res.getString("code").contentEquals("800")) {


                        Intent i = new Intent(splashActivity.this,home.class);
                        i.putExtra("Name",res.getString("name"));
                        i.putExtra("ph",res.getString("phone"));
                        startActivity(i);
                        splashActivity.this.finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Login Please!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(splashActivity.this,loginActivity.class));
                        splashActivity.this.finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                Toast.makeText(getBaseContext(), "check your connection...", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(stringRequest);
//        StringRequest stringRequest = new Str
    }

}
