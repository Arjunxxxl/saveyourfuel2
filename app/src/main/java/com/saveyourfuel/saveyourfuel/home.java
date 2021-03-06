package com.saveyourfuel.saveyourfuel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class home extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    public static String name, ph;
    TextView nameT, phT;
    Bitmap profileImage = null;
    ImageView pic;
    LinearLayout buy, funds, documents;
    String profile_image = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        name = sharedPref.getString("Name","");
        ph = sharedPref.getString("ph","");

        String languageToLoad  = sharedPref.getString("lang","en");
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());




        setContentView(R.layout.activity_home);



        toolbar = findViewById(R.id.toolbar);


        setImage();


        toolbar.setTitle(R.string.name);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(R.string.y_profile);

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor("#004E64"));

        nameT = findViewById(R.id.user_name);
        phT = findViewById(R.id.phone_user);
        nameT.setText(name);
        phT.setText(ph);
        setView();
        pic = findViewById(R.id.user_profile_pic);
//        pic.setImageBitmap(profileImage);
    }

    void setView() {
        buy = findViewById(R.id.option1);
        buy.setOnClickListener(this);
        funds = findViewById(R.id.option2);
        funds.setOnClickListener(this);
        documents = findViewById(R.id.option3);
        documents.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutus:
                Intent i = new Intent(home.this, aboutusActivity.class);
                startActivity(i);
                break;
            case R.id.money:
                Intent i2 = new Intent(home.this, balanceActivity.class);
                startActivity(i2);
                break;

            case R.id.contactsyf:
                Intent i3 = new Intent(home.this, contactusActivity.class);
                startActivity(i3);
                break;


            case R.id.language:

                SharedPreferences sharedPrefer = getSharedPreferences("data", Context.MODE_PRIVATE);


                if (sharedPrefer.getString("lang","en").equals("en")) {
                    changeLang("hi");
                } else {
                    changeLang("en");
                }
                home.this.finish();
                Intent i5 = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i5);
                break;

            case R.id.logout:

                Toast.makeText(getApplicationContext(), R.string.logout, Toast.LENGTH_LONG).show();
                SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
                sharedPref.edit().clear().apply();
                startActivity(new Intent(home.this, loginActivity.class));
                home.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeLang(String lang) {

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("lang", lang);
        editor.apply();
    }




    private void setImage() {

        String passwordText, emailText;

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        String pass = sharedPref.getString("password", "");
        Log.d("usemane", username + "");
        Log.d("pass", pass + "");
        if (username.isEmpty() || pass.isEmpty()) {
            Intent i1 = new Intent(home.this, loginActivity.class);
            startActivity(i1);
            home.this.finish();
        } else {
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

                            profile_image = res.getString("profile");
                            String imageString = profile_image;
                            if (!imageString.isEmpty()) {
                                byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
                                profileImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                pic.setImageBitmap(profileImage);
                            }


                        } else {
                            Snackbar.make(findViewById(android.R.id.content), R.string.erroroccured_login_logout, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            startActivity(new Intent(home.this, loginActivity.class));
                            home.this.finish();
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
//        StringRequest stringRequest = new Str
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option1:

                Intent i2 = new Intent(home.this,buyActivity.class);
                startActivity(i2);
                break;
            case R.id.option2:
                Intent i3 = new Intent("com.saveyourfuel.saveyourfuel.balanceActivity");
                startActivity(i3);

                break;
            case R.id.option3:

                Intent i4 = new Intent(home.this, personalTruckInfo.class);
                startActivity(i4);
                break;

        }
    }
}
